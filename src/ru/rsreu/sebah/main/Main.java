package ru.rsreu.sebah.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.rsreu.sebah.controller.Controller;
import ru.rsreu.sebah.model.Model;
import ru.rsreu.sebah.view.View;

import java.io.IOException;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Donkey kong");
        BorderPane root = new BorderPane();
        primaryStage.setScene(new Scene(root, View.WIDTH_WINDOW, View.HEIGHT_WINDOW));
        primaryStage.show();
        try {
            Model model = new Model();
            root.requestFocus();
            Controller controller = new Controller(model);
            root.setOnKeyPressed(a -> controller.addKey(a.getCode()));
            root.setOnKeyReleased(a -> controller.removeKey(a.getCode()));
            View view = new View(controller, root);
            controller.setListener(view);
            model.initialize();
            model.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}