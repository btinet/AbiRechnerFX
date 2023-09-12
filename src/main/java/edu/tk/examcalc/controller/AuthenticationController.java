package edu.tk.examcalc.controller;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationController extends Controller {
    public BorderPane content;

    public AuthenticationController() {
        super("authentication-login.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button button = new Button("Anmelden");
        button.setDefaultButton(true);
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab loginTab = new Tab("Anmelden");
        Tab passwordResetTab = new Tab("Passwort vergessen");
        tabPane.getTabs().addAll(loginTab,passwordResetTab);
        VBox centerBox = new VBox();
        //centerBox.setAlignment(Pos.CENTER);
        GridPane form = new GridPane();
        centerBox.setSpacing(25);
        centerBox.setPadding(new Insets(15,15,15,15));
        form.setVgap(5);
        form.setHgap(5);
        form.setGridLinesVisible(false);
        HBox spacer = new HBox();
        HBox buttonGroup = new HBox(5);
        Button resetButton = new Button("Reset");
        resetButton.setCancelButton(true);
        buttonGroup.getChildren().addAll(button,resetButton);
        spacer.setPrefWidth(100);
        form.addRow(0,new Label("Fach"),new SearchableComboBox<String>());
        form.addRow(1,new Label("Kennung"),new TextField());
        form.addRow(2,new Label("Passwort"),new PasswordField());
        form.addRow(3,spacer,buttonGroup);

        button.setOnAction(this::login);
        Label title = new Label("Anmelden");
        title.setFont(Font.font(16));
        centerBox.getChildren().add(title);
        centerBox.getChildren().add(form);
        loginTab.setContent(centerBox);
        content.setCenter(tabPane);
    }

    public void login(ActionEvent e) {
        System.out.println("Einloggen!");
        Controller.login.setValue(true);
    }

}
