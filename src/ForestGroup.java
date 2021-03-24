
//--------------------------------------------------------------------------------------------------
// ::ForestBuilder
//

import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

/**
 * The ForestGroup class generates the forest utilizing other mesh classes
 * (i.e. GroundMesh, TreeGroup, and Rock)
 */
public class ForestGroup extends Group {

    public ForestGroup() {

        // build the ground
        GroundMesh groundMesh = new GroundMesh(300, 300);
        getChildren().add(groundMesh);

        AmbientLight ambientGroundLight = new AmbientLight();
        ambientGroundLight.setColor(Color.rgb(200, 200, 200, 1));
        ambientGroundLight.getScope().add(groundMesh);
        getChildren().add(ambientGroundLight);


        // build the trees
        Group allTrees = new Group();
        getChildren().add(allTrees);

        AmbientLight ambientTreeLight = new AmbientLight();
        ambientTreeLight.setColor(Color.rgb(90, 90, 90, 1));
        ambientTreeLight.getScope().add(allTrees);
        getChildren().add(ambientTreeLight);

        for(int i = 0; i < 2000; i++) {
            allTrees.getChildren().addAll(new TreeGroup());
        }

        TreeGroup tree = new TreeGroup();
        tree.setTranslateY(30);
        tree.setTranslateX(30);
        tree.setTranslateZ(30);
        tree.makeRed();
        allTrees.getChildren().addAll(tree);

        RockGroup rocks = new RockGroup();
        getChildren().add(rocks);

    }

}
