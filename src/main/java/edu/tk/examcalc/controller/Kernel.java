package edu.tk.examcalc.controller;


import edu.tk.db.global.Session;
import edu.tk.examcalc.MainApplication;
import edu.tk.examcalc.controller.AuthenticationController;
import edu.tk.examcalc.controller.CalculateController;
import edu.tk.examcalc.controller.Controller;
import edu.tk.examcalc.controller.PupilController;
import edu.tk.examcalc.role.Roles;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Kernel implements Initializable {

    @FXML
    public BorderPane root;

    public StringProperty statusTextProperty = new SimpleStringProperty();
    public static StringProperty activityStringProperty = new SimpleStringProperty();
    Text statusText = new Text();
    public static MenuBar menuBar = new MenuBar();
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
        pageLabel.setFont(new Font(14));
        pageLabel.setPadding(new Insets(5,10,5,10));
        topBox.getChildren().add(pageLabel);
        topBox.setStyle("-fx-background-color: linear-gradient(from 65% 65% to 90% 90%, #F2F2F2, #309CCB);");

        //MenuItem menuItem1 = new MenuItem("Punkte ermitteln");
        //menuItem1.setOnAction(t -> switchToController(new CalculateController()));
        //MenuItem menuItem2 = new MenuItem("Lernende verwalten");
        //menuItem2.setOnAction(t -> switchToController(new PupilController()));
        MenuItem menuItem3 = new MenuItem("Abmelden");
        menuItem3.setOnAction(t -> switchToController(new AuthenticationController()));
        menuItem3.setOnAction(t -> Controller.login.setValue(false));

        MenuItem menuItem4 = new MenuItem("Beenden");
        menuItem4.setOnAction(t -> {
            Platform.exit();
            System.exit(0);
        });

        MenuItem menuItem5 = new MenuItem("Hilfe");
        MenuItem menuItem6 = new MenuItem("Über...");

        MenuItem adminBoardItem = new MenuItem("Nutzer ändern...");


        Menu menu1 = new Menu("Datei");
        Menu wizardMenu = new Menu("Assistenten");
        Menu menu2 = new Menu("?");
        menu1.getItems().addAll(menuItem3,new SeparatorMenuItem(),menuItem4);
        menu2.getItems().addAll(menuItem5,new SeparatorMenuItem(),menuItem6);
        wizardMenu.getItems().add(adminBoardItem);

        Controller.login.addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(true)) {
                switchToController(new PupilController());
                statusTextProperty.setValue("angemeldet als: " + Session.getUser());
                topBox.getChildren().set(0,menuBar);
                if(Objects.equals(Session.getUser().getUserRole().getLabel(), Roles.ADM.toString())) {
                    menuBar.getMenus().add(wizardMenu);
                }
            } else {
                switchToController(new AuthenticationController());
                statusTextProperty.setValue("abgemeldet");
                topBox.getChildren().set(0,new MenuBar());
                menuBar.getMenus().remove(wizardMenu);
            }
        });

        menuBar.getMenus().addAll(menu1,menu2);

        statusBar.setPadding(new Insets(5,5,5,5));

        Text activityText = new Text();
        activityText.textProperty().bind(activityStringProperty);
        statusText.textProperty().bind(statusTextProperty);
        statusBar.getChildren().add(statusText);
        //statusBar.getChildren().add(activityText);
    }
}