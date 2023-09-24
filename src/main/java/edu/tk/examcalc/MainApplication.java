package edu.tk.examcalc;

import edu.tk.db.global.Database;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {

    public static Image appImage;
    public static Stage stage;

    static {
        try {
            appImage = new Image(Objects.requireNonNull(MainApplication.class.getResource("/edu/tk/icons/tk.png")).openStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("index.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("AbiRechner - Treptow-Kolleg Berlin");
        stage.getIcons().add(appImage);
        stage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        MainApplication.stage = stage;

        Database.connect();

        stage.setScene(scene);
        stage.show();
    }

}