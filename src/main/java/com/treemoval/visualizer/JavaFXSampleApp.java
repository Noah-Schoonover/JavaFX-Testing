package com.treemoval.visualizer;

import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//----------------------------------------------------------------------------------------------------------------------
// ::JavaFXSampleApp
//
/**
 * The JavaFXSampleApp is a testing project for JavaFX 3D.
 *
 * @author Noah Schoonover
 */
public class JavaFXSampleApp extends Application {

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

//        root.getChildren().add(world);
//        root.setDepthTest(DepthTest.ENABLE);


        ForestGroup forest = new ForestGroup();

        ForestScene scene = new ForestScene(forest);
        scene.setFill(Color.LIGHTBLUE);

        primaryStage.setTitle("JavaFX Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();

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
