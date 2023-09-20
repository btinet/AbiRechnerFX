package edu.tk.examcalc.controller;

import edu.tk.db.model.Condition;
import edu.tk.examcalc.entity.User;
import edu.tk.examcalc.form.LoginForm;
import edu.tk.examcalc.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.MasterDetailPane;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AuthenticationController extends Controller {
    public BorderPane content;
    private final MasterDetailPane masterDetailPane = new MasterDetailPane();
    private final LoginForm loginForm = new LoginForm();
    public AuthenticationController() {
        super("authentication-login.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setPageTitle("Anmelden");

        Tab loginTab = new Tab("Anmelden");
        Tab passwordResetTab = new Tab("Passwort vergessen");

        loginForm.getSubmitButton().setOnAction(this::login);
        loginForm.getResetButton().setOnAction(this::resetForm);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(loginTab,passwordResetTab);

        VBox centerBox = new VBox();
        centerBox.setSpacing(25);
        centerBox.setPadding(new Insets(25));

        centerBox.getChildren().add(loginForm.render());
        ScrollPane scrollPane = new ScrollPane(centerBox);
        masterDetailPane.setMasterNode(scrollPane);
        masterDetailPane.setDetailSide(Side.RIGHT);
        masterDetailPane.setDividerPosition(.4);
        loginTab.setContent(masterDetailPane);
        content.setCenter(tabPane);
    }

    private void login(ActionEvent e) {
        loginForm.tryLogin(new UserRepository(false));
    }

    private void resetForm(ActionEvent e) {

    }

}
