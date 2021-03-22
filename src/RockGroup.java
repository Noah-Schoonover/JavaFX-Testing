import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is a group of rock meshes of random sizes and positions
 */
public class RockGroup extends Group {

    private static final int NUMBER_OF_ROCK_MODELS = 20;

    /**
     * Creates a random grouping of rocks
     */
    public RockGroup () {

        Group rockModels = ModelLoader.loadModel(
                RockGroup.class.getClassLoader().getResource("RockPackByPava.obj"));

        int r1 = ThreadLocalRandom.current().nextInt(0, NUMBER_OF_ROCK_MODELS); // exclusive of upper bound
        int r2 = (r1 + 1) % NUMBER_OF_ROCK_MODELS;
        int r3 = (r2 + 1) % NUMBER_OF_ROCK_MODELS;

        System.out.println("Random rock numbers: " + r1 + " " + r2 + " " + r3);

        MeshView rock1 = (MeshView) rockModels.getChildren().get(r1);
        MeshView rock2 = (MeshView) rockModels.getChildren().get(r2);
        MeshView rock3 = (MeshView) rockModels.getChildren().get(r3);

        rock1.setScaleX(6); rock1.setScaleY(6); rock1.setScaleZ(6);

        rock2.setScaleX(4); rock2.setScaleY(4); rock2.setScaleZ(4);
        rock2.setTranslateZ(3);

        rock3.setScaleX(4); rock3.setScaleY(4); rock3.setScaleZ(4);
        rock3.setTranslateX(-3);

        getChildren().addAll(rock1, rock2, rock3);

        setTranslateY(50);

        final PhongMaterial rockMaterial = new PhongMaterial();
        rockMaterial.setDiffuseColor(Color.web("0x404040"));
        rockMaterial.setSpecularColor(Color.BLACK);

        getChildren().forEach(rock -> ((MeshView) rock).setMaterial(rockMaterial));

    }

}
