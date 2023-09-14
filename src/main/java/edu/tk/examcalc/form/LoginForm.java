package edu.tk.examcalc.form;

import edu.tk.examcalc.controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginForm extends GridPane {

    private final Button submitButton = new Button("Anmelden");
    private final Button resetButton = new Button("Formular leeren");
    VBox vBox = new VBox();
    HBox buttonGroup = new HBox(5);
    private final TextField usernameTextField = new TextField();
    private final PasswordField passwordField = new PasswordField();
    private final Label errorLabel = new Label();

    public LoginForm() {
        setVgap(15);
        setHgap(100);
        setGridLinesVisible(false);

        errorLabel.setTextFill(Color.RED);

        buttonGroup.getChildren().addAll(submitButton,resetButton);
        buttonGroup.setPadding(new Insets(25,0,25,0));
        submitButton.setDefaultButton(true);
        resetButton.setCancelButton(true);

        addRow(0,new Label("Kennung"), usernameTextField);
        addRow(1,new Label("Passwort"), passwordField);

    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public VBox render() {
        vBox.getChildren().addAll(this, buttonGroup, errorLabel);
        return vBox;
    }

    public void tryLogin() {
        if(usernameTextField.getText().equals("mei") && passwordField.getText().equals("kolleg")) {
            System.out.println("Einloggen!");
            Controller.login.setValue(true);
        } else {
            System.out.println("Zugangsdaten sind falsch!");
            errorLabel.setText("Das hat leider nicht geklappt.");
        }
    }

}
