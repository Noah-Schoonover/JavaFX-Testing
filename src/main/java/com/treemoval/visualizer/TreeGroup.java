package com.treemoval.visualizer;

import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;

import java.util.concurrent.ThreadLocalRandom;

//--------------------------------------------------------------------------------------------------
// ::TreeGroup
//
/**
 * The TreeGroup class handles importation of tree models, positioning, coloring, etc.
 * Extends the JavaFX Group class.
 */
public class TreeGroup extends Group {

    //--------------------------------------------------------------------------------------------------
    // TreeGroup::TreeGroup
    //
    /**
     * instantiates a tree with random coordinates
     *
     * todo should take coordinates as input
     */
    public TreeGroup() {

        getChildren().addAll(
                ModelLoader.loadModel(getClass().getResource("lowpolytree.obj"))
                        .getChildren());

        setRotationAxis(Rotate.Z_AXIS);
        setRotate(180.0);
        setScaleX(10.0);
        setScaleY(10.0);
        setScaleZ(10.0);
        setTranslateY(30);
        setTranslateX(ThreadLocalRandom.current().nextInt(5, 2990));
        setTranslateZ(ThreadLocalRandom.current().nextInt(5, 2990));

        final PhongMaterial leavesMaterial = new PhongMaterial();
        //leavesMaterial.setDiffuseColor(Color.GREEN);
        leavesMaterial.setDiffuseColor(Color.web("0x056307"));
        leavesMaterial.setSpecularColor(Color.BLACK);
        leavesMaterial.setSpecularPower(1000);

        MeshView leaves = (MeshView) getChildren().get(0);
        leaves.setMaterial(leavesMaterial);

        setDepthTest(DepthTest.ENABLE);


    }

    //--------------------------------------------------------------------------------------------------
    // TreeGroup::makeRed
    //
    /**
     * sets the leaves mesh of the TreeGroup to red material as indication that the tree is marked for cutting
     */
    public void makeRed() {

        MeshView leaves = (MeshView) getChildren().get(0);
        if(!leaves.getId().equals("Cylinder_Leaves")) { return; }

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.BLACK);

        leaves.setMaterial(redMaterial);
    }



}
