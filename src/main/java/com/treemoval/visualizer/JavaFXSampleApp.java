package com.treemoval.visualizer;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

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
    private static final double TRACK_SPEED = 0.6;

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

        PointLight light = new PointLight();
        light.setColor(Color.WHITE);
        cameraXform3.getChildren().add(light);
        light.setTranslateZ(CAMERA_INITIAL_DISTANCE);

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

            // smart modifier to automatically increase control speed when zoomed out
            double cameraZ = cameraXform3.getTranslateZ();
            double smartModifier = 1.0;
            if (cameraZ < -300) { smartModifier = (cameraZ * -1) / 300; }

            if (me.isControlDown()) {
                modifier = CONTROL_MULTIPLIER;
            }
            if (me.isShiftDown()) {
                modifier = SHIFT_MULTIPLIER;
            }
            if (me.isPrimaryButtonDown()) {

                double ry = cameraXform.ry.getAngle() - mouseDeltaX * MOUSE_SPEED * ROTATION_SPEED;
                double rx = cameraXform.rx.getAngle() + mouseDeltaY * MOUSE_SPEED * ROTATION_SPEED;

                // limit rotation from going below the horizon
                if(rx < 3) { rx = 3; }

                cameraXform.ry.setAngle(ry);
                cameraXform.rx.setAngle(rx);

            } else if (me.isSecondaryButtonDown()) {

                double newZ = cameraZ + mouseDeltaX * MOUSE_SPEED * modifier * smartModifier;
                // limit zoom in to prevent zooming through the horizon
                if (newZ > 350) { newZ = 350; }
                // limit zoom out to prevent far clip
                if (newZ < -5000) { newZ = -5000; }
                cameraXform3.setTranslateZ(newZ);

            } else if (me.isMiddleButtonDown()) {

                double angle = Math.toRadians(cameraXform.ry.getAngle());
                double cos = Math.cos(angle);
                double sin = Math.sin(angle);

                double x = cameraXform.t.getX();
                x = x + (mouseDeltaX * cos + mouseDeltaY * sin) * MOUSE_SPEED * modifier * smartModifier * TRACK_SPEED;
                cameraXform.t.setX(x);

                double z = cameraXform.t.getZ();
                z = z + (mouseDeltaX * sin * -1 + mouseDeltaY * cos) * MOUSE_SPEED * modifier * smartModifier * TRACK_SPEED;
                cameraXform.t.setZ(z);
            }
        });
    }

    //--------------------------------------------------------------------------------------------------
    // JavaFXSampleApp::handleKeyboard
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

        ForestGroup forest = new ForestGroup();
        world.getChildren().add(forest);

        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.LIGHTBLUE);
        handleKeyboard(scene);
        handleMouse(scene);

        primaryStage.setTitle("JavaFX Sample Application");
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
