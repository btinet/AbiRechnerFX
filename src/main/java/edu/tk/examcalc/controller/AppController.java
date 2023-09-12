package edu.tk.examcalc.controller;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML
    public BorderPane root;

    public MenuBar menuBar = new MenuBar();
    public HBox statusbar = new HBox();
    public Node appContent = new Group();

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
        root.setTop(menuBar);
        root.setCenter(appContent);
        root.setBottom(statusbar);

        MenuItem menuItem1 = new MenuItem("Punkte ermitteln");
        menuItem1.setOnAction(t -> switchToController(new CalculateController()));
        MenuItem menuItem2 = new MenuItem("Lernende verwalten");
        menuItem2.setOnAction(t -> switchToController(new PupilController()));
        MenuItem menuItem3 = new MenuItem("Option 3");

        MenuItem menuItem4 = new MenuItem("Option 4");
        MenuItem menuItem5 = new MenuItem("Option 5");
        MenuItem menuItem6 = new MenuItem("Option 6");


        Menu menu1 = new Menu("Datei");
        Menu menu2 = new Menu("?");
        menu1.getItems().addAll(menuItem1,menuItem2,menuItem3);
        menu2.getItems().addAll(menuItem4,menuItem5,menuItem6);

        menuBar.getMenus().addAll(menu1,menu2);
        statusbar.setPadding(new Insets(5,5,5,5));
        statusbar.getChildren().add(new Text("Konto abgemeldet"));
    }
}