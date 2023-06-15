package com.example.gui_fts_en_223s_pro3_s27236_intellijidea;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private static final String DICTIONARY_DIRECTORY = "dictionary";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Typing Test");
        primaryStage.setResizable(false);

        // Create the main layout container
        BorderPane root = new BorderPane();
        View view = new View(root);
        Controller controller = new Controller(primaryStage, DICTIONARY_DIRECTORY, view);

        view.setController(controller);

        // Create the scene and set it to the stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}