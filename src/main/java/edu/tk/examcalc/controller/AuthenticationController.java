package edu.tk.examcalc.controller;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationController extends Controller {
    public BorderPane content;

    private final MasterDetailPane masterDetailPane = new MasterDetailPane();
    private final TextField usernameTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Label loginErrorLabel = new Label();

    public AuthenticationController() {
        super("authentication-login.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button button = new Button("Anmelden");
        button.setDefaultButton(true);
        loginErrorLabel.setTextFill(Color.RED);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        Tab loginTab = new Tab("Anmelden");
        Tab passwordResetTab = new Tab("Passwort vergessen");
        tabPane.getTabs().addAll(loginTab,passwordResetTab);
        VBox centerBox = new VBox();
        GridPane form = new GridPane();
        centerBox.setSpacing(25);
        centerBox.setPadding(new Insets(25,25,25,25));
        form.setVgap(15);
        form.setHgap(15);
        form.setGridLinesVisible(false);
        HBox spacer = new HBox();
        HBox buttonGroup = new HBox(5);
        Button resetButton = new Button("Reset");
        resetButton.setCancelButton(true);
        buttonGroup.getChildren().addAll(button,resetButton);
        spacer.setPrefWidth(100);

        SearchableComboBox<String> comboBox = new SearchableComboBox<>();
        comboBox.getItems().addAll("Informatik","Mathematik","Biologie","Geographie","Sport");



        form.addRow(0,new Label("Fach"),comboBox);
        form.addRow(1,new Label("Kennung"), usernameTextField);
        form.addRow(2,new Label("Passwort"), passwordField);

        button.setOnAction(this::login);
        Label title = new Label("Willkommen zurück");
        Label subTitle = new Label("Bitte anmelden, um fortzufahren.");
        VBox textBlock = new VBox(title,subTitle);
        title.setFont(Font.font(16));
        //centerBox.getChildren().add(textBlock);
        centerBox.getChildren().add(form);
        centerBox.getChildren().add(buttonGroup);
        centerBox.getChildren().add(loginErrorLabel);
        ScrollPane scrollPane = new ScrollPane(centerBox);
        masterDetailPane.setMasterNode(scrollPane);
        masterDetailPane.setDetailSide(Side.RIGHT);
        masterDetailPane.setDividerPosition(.3);
        loginTab.setContent(masterDetailPane);
        content.setCenter(tabPane);
    }

    public void login(ActionEvent e) {
        if(usernameTextField.getText().equals("mei") && passwordField.getText().equals("kolleg")) {
            System.out.println("Einloggen!");
            Controller.login.setValue(true);
        } else {
            System.out.println("Zugangsdaten sind falsch!");
            loginErrorLabel.setText("Das hat leider nicht geklappt.");
        }
    }

}
