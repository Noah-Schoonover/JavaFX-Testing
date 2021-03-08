import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.DrawMode;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;

import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import org.w3c.dom.ranges.Range;

//----------------------------------------------------------------------------------------------------------------------
// ::JavaFXSampleApp
//
/**
 * The JavaFXSampleApp is a testing project for JavaFX 3D.
 *
 * @author Noah Schoonover
 */
public class JavaFXSampleApp extends Application {

    final Group root = new Group();
    final Xform axisGroup = new Xform();
    final Xform groundGroup = new Xform();
    final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    private static final double CAMERA_INITIAL_DISTANCE = -450;
    private static final double CAMERA_INITIAL_X_ANGLE = 70.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 250.0;
    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.5;
    private static final double ROTATION_SPEED = 0.3;
    private static final double TRACK_SPEED = 0.3;

    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;

    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::buildCamera
    //
    /**
     * Adds and positions the camera.
     */
    private void buildCamera() {
        System.out.println("buildCamera()");
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }

    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::buildAxes
    //
    /**
     * Build a euclidean axes representation using three box shapes in the axisGroup and add it to the
     * world group.
     */
    private void buildAxes() {
        System.out.println("buildAxes()");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        //axisGroup.setVisible(false);
        world.getChildren().addAll(axisGroup);
    }

    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::handleMouse
    //
    /**
     * Adjust the camera on mouse events.
     *
     * @param scene the scene
     */
    private void handleMouse(Scene scene) {
        scene.setOnMousePressed(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged(me -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            double modifier = 1.0;

            if (me.isControlDown()) {
                modifier = CONTROL_MULTIPLIER;
            }
            if (me.isShiftDown()) {
                modifier = SHIFT_MULTIPLIER;
            }
            if (me.isPrimaryButtonDown()) {
                cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED);
                cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED);
            } else if (me.isSecondaryButtonDown()) {
                double z = camera.getTranslateZ();
                double newZ = z + mouseDeltaX * MOUSE_SPEED * modifier;
                camera.setTranslateZ(newZ);
            } else if (me.isMiddleButtonDown()) {
                cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
                cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
            }
        });
    }

    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::buildAxes
    //
    /**
     * Modify the scene on keyboard events.
     *
     * @param scene the scene
     */
    private void handleKeyboard(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z:
                    cameraXform2.t.setX(0.0);
                    cameraXform2.t.setY(0.0);
                    camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
                    cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                    cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                    break;
                case X:
                    axisGroup.setVisible(!axisGroup.isVisible());
                    break;
                case V:
                    groundGroup.setVisible(!groundGroup.isVisible());
                    break;
            }
        });
    }

    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::loadModel
    //
    /**
     * Loads a model from OBJ file type using the InteractiveMesh.org ObjModelImporter and return it as a group.
     *
     * @param url the url path to the 3d file
     * @return a JavaFX Group containing all of the imported meshes
     */
    private Group loadModel(URL url) {
        Group modelRoot = new Group();

        ObjModelImporter importer = new ObjModelImporter();
        importer.read(url);

        for (MeshView view : importer.getImport()) {
            modelRoot.getChildren().add(view);
        }

        return modelRoot;
    }

    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::getRandomInt
    //
    /**
     * Yields a random integer between min and max (inclusive)
     *
     * currently used to randomize topology
     *
     * @param min the minimum bound for the random integer
     * @param max the maximum bound for the random integer
     * @return the random integer
     */
    public int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::buildGround
    //
    /**
     * Generates the ground mesh.
     *
     * currently randomized topology
     */
    public void buildGround() {

        int meshWidth = 30;
        int meshHeight = 30;
        int scale = 10;
        int verticalScale = 3;

        final PhongMaterial groundMaterial = new PhongMaterial();
        groundMaterial.setDiffuseColor(Color.SANDYBROWN);
        groundMaterial.setSpecularColor(Color.BLACK);
        groundMaterial.setSpecularPower(10);

        // Generate point data array
        float[] points = new float[meshWidth*meshHeight*3];

        for (int i = 0; i < meshWidth*meshHeight; i++) {
            points[i*3] = (i % meshWidth) * scale;          // set x value
            points[i*3+1] = getRandomInt(0, 2) * verticalScale;    // set y value
            points[i*3+2] = (float) (i / meshHeight * scale);         // set z value
        }

//        for (int i = 0; i < meshWidth*meshHeight; i++) {
//            System.out.println(points[i*3] + ", " + points[i*3+1] + ", " + points[i*3+2]);
//        }

        // Generate texture data array
        float[] texCoords = new float[meshWidth*meshHeight*2];
        Arrays.fill(texCoords, 0);

        // Generate face data array
        int numFaces = (meshWidth-1)*(meshWidth-1)*2;
        int[] faces = new int[numFaces*6];

        for (int i = 0, f = 0; i < meshWidth*meshHeight - meshWidth; i++) {

            if(i % meshWidth == 0) continue;    // skip the first point of every row

            faces[f*6] = faces[f*6+1] = i;
            faces[f*6+2] = faces[f*6+3] = i-1;
            faces[f*6+4] = faces[f*6+5] = i-1 + meshWidth;

            f++; // next face

            faces[f*6] = faces[f*6+1] = i;
            faces[f*6+2] = faces[f*6+3] = i-1 + meshWidth;
            faces[f*6+4] = faces[f*6+5] = i + meshWidth;

            f++; //next face

        }

//        for (int i = 0; i < numFaces; i++) {
//            System.out.println(faces[i*6] + ", " + faces[i*6+1] + ", " + faces[i*6+2] + ", "
//                    + faces[i*6+3] + ", " + faces[i*6+4] + ", " + faces[i*6+5]);
//        }

        // Create a TriangleMesh
        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().addAll(points);
        mesh.getTexCoords().addAll(texCoords);
        mesh.getFaces().addAll(faces);

        // Create a MeshView
        MeshView meshView = new MeshView();
        meshView.setMesh(mesh);

        meshView.setMaterial(groundMaterial);
        //meshView.setDrawMode(DrawMode.LINE);

        world.getChildren().addAll(meshView);
    }

    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::buildNewTree
    //

    public Group buildNewTree() {

        Group tree = loadModel(getClass().getResource("lowpolytree.obj"));

        tree.setRotationAxis(Rotate.Z_AXIS);
        tree.setRotate(180.0);
        tree.setScaleX(10.0);
        tree.setScaleY(10.0);
        tree.setScaleZ(10.0);
        tree.setTranslateY(30);
        tree.setTranslateX(getRandomInt(5, 285));
        tree.setTranslateZ(getRandomInt(5, 285));

        final PhongMaterial leavesMaterial = new PhongMaterial();
        leavesMaterial.setDiffuseColor(Color.GREEN);
        leavesMaterial.setSpecularColor(Color.BLACK);
        leavesMaterial.setSpecularPower(1000);

        MeshView leaves = (MeshView) tree.getChildren().get(0);
        leaves.setMaterial(leavesMaterial);

        return tree;
    }

    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::makeTreeRed
    //
    /**
     * sets the leaves mesh of a tree group to red material to indicate that the tree is marked for cutting
     *
     * @param tree the tree to make red
     */
    public void makeTreeRed(Group tree) {
        MeshView leaves = (MeshView) tree.getChildren().get(0);
        if(!leaves.getId().equals("Cylinder_Leaves")) { return; }

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.BLACK);
        redMaterial.setSpecularPower(1000);

        leaves.setMaterial(redMaterial);
    }


    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::start
    //
    /**
     * Start the JavaFX app
     * @param primaryStage the Stage
     */
    @Override
    public void start(Stage primaryStage) {

        System.out.println("start()");

        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);

        buildCamera();
        buildAxes();
        buildGround();

        for(int i = 0; i < 20; i++) {
            Group tree = buildNewTree();
            world.getChildren().addAll(tree);
        }

        Group tree = buildNewTree();
        tree.setTranslateY(30);
        tree.setTranslateX(30);
        tree.setTranslateZ(30);
        makeTreeRed(tree);
        world.getChildren().addAll(tree);

        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.BLACK);
        handleKeyboard(scene);
        handleMouse(scene);

        primaryStage.setTitle("Molecule Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setCamera(camera);
    }

    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::main
    //
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
