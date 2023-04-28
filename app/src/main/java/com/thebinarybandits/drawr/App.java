package com.thebinarybandits.drawr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/layout/view_app.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Drawr");
        stage.getIcons().add(new Image("/drawable/ic_logo.png"));
        stage.setScene(scene);
        String css = this.getClass().getResource("/styles/style.css").toExternalForm(); 
        scene.getStylesheets().add(css);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
