package edu.tk.examcalc.controller;


import edu.tk.db.global.Session;
import edu.tk.examcalc.MainApplication;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    public BorderPane root;

    public StringProperty statusTextProperty = new SimpleStringProperty();
    Text statusText = new Text();
    public MenuBar menuBar = new MenuBar();
    public HBox statusBar = new HBox();
    public VBox topBox = new VBox();
    public VBox appContent = new VBox();

    public void switchToController(Controller controller) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(controller.getFxmlResource())));
        try {
            root.setCenter(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        root.setTop(topBox);
        root.setCenter(appContent);
        root.setBottom(statusBar);

        topBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));


        if (Controller.login.getValue().equals(false)) {

            topBox.getChildren().add(new MenuBar());

            statusTextProperty.setValue("abgemeldet");
            switchToController(new AuthenticationController());
        }

        Label pageLabel = new Label();
        pageLabel.textProperty().bind(Controller.pageTitleProperty);
        pageLabel.setFont(new Font(16));
        pageLabel.setPadding(new Insets(5,10,5,10));
        topBox.getChildren().add(pageLabel);

        MenuItem menuItem1 = new MenuItem("Punkte ermitteln");
        menuItem1.setOnAction(t -> switchToController(new CalculateController()));
        MenuItem menuItem2 = new MenuItem("Lernende verwalten");
        menuItem2.setOnAction(t -> switchToController(new PupilController()));
        MenuItem menuItem3 = new MenuItem("Abmelden");
        menuItem3.setOnAction(t -> switchToController(new AuthenticationController()));
        menuItem3.setOnAction(t -> Controller.login.setValue(false));

        MenuItem menuItem4 = new MenuItem("Option 4");
        MenuItem menuItem5 = new MenuItem("Option 5");
        MenuItem menuItem6 = new MenuItem("Option 6");


        Menu menu1 = new Menu("Datei");
        Menu menu2 = new Menu("?");
        menu1.getItems().addAll(menuItem1,menuItem2,menuItem3);
        menu2.getItems().addAll(menuItem4,menuItem5,menuItem6);

        Controller.login.addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(true)) {
                switchToController(new PupilController());
                statusTextProperty.setValue("angemeldet als: " + Session.getUser());
                topBox.getChildren().set(0,menuBar);
            } else {
                switchToController(new AuthenticationController());
                statusTextProperty.setValue("abgemeldet");
                topBox.getChildren().set(0,new MenuBar());
            }
        });



        menuBar.getMenus().addAll(menu1,menu2);
        statusBar.setPadding(new Insets(5,5,5,5));

        statusText.textProperty().bind(statusTextProperty);
        statusBar.getChildren().add(statusText);
    }
}