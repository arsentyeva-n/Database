/**
 * @author: Arsentyeva N.V.
 */

package com.example.database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("mainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 747, 638);
        stage.setResizable(false); // запрет на изменение окна
        Image applicationIcon = new Image(getClass().getResourceAsStream("icon/icon.png"));
        stage.getIcons().add(applicationIcon);
        stage.setTitle("Database");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}