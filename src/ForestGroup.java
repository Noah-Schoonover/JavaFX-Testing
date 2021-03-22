
//--------------------------------------------------------------------------------------------------
// ::ForestBuilder
//

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

/**
 * The ForestGroup class generates the forest utilizing other mesh classes
 * (i.e. GroundMesh, TreeGroup, and Rock)
 */
public class ForestGroup extends Group {

    public ForestGroup() {

        GroundMesh groundMesh = new GroundMesh(30, 30);
        getChildren().add(groundMesh);


        for(int i = 0; i < 20; i++) {
            getChildren().addAll(new TreeGroup());
        }

        TreeGroup tree = new TreeGroup();
        tree.setTranslateY(30);
        tree.setTranslateX(30);
        tree.setTranslateZ(30);
        tree.makeRed();
        getChildren().addAll(tree);

        RockGroup rocks = new RockGroup();
        getChildren().add(rocks);

    }

}
