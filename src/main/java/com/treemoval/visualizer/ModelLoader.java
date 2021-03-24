package com.treemoval.visualizer;

import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.scene.Group;
import javafx.scene.shape.MeshView;

import java.net.URL;

public class ModelLoader {

    //--------------------------------------------------------------------------------------------------
    // ModelLoader::loadModel
    //
    /**
     * Loads a model from OBJ file type using the InteractiveMesh.org ObjModelImporter and return it as a group.
     *
     * @param url the url path to the 3d file
     * @return a JavaFX Group containing all of the imported meshes
     */
    public static Group loadModel(URL url) {
        Group modelRoot = new Group();

        ObjModelImporter importer = new ObjModelImporter();
        importer.read(url);

        for (MeshView view : importer.getImport()) {
            modelRoot.getChildren().add(view);
        }

        return modelRoot;
    }

}
