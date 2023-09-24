package edu.tk.examcalc.form;

import edu.tk.db.global.Session;
import edu.tk.db.model.Condition;
import edu.tk.examcalc.MainApplication;
import edu.tk.examcalc.controller.Controller;
import edu.tk.examcalc.entity.User;
import edu.tk.examcalc.repository.UserRepository;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginForm extends Form {

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

    @Override
    public VBox render() {
        vBox.getChildren().addAll(this, buttonGroup, errorLabel);
        return vBox;
    }

    @Override
    public void submit() {
        UserRepository repository = new UserRepository();

        Condition loginCondition = new Condition();
        loginCondition
                .add("username",usernameTextField.getText())
                .add("password",Session.getHash(passwordField.getText()))
        ;

        User user = repository.findOneBy(loginCondition.getMap());

        if(user != null) {
            System.out.println("Einloggen!");
            Session.setUser(user);
            Controller.login.setValue(true);

        } else {
            System.out.println("Zugangsdaten sind falsch!");
            errorLabel.setText("Das hat leider nicht geklappt.");
        }
    }

    @Override
    public void cancel() {
        usernameTextField.clear();
        passwordField.clear();
    }

}
