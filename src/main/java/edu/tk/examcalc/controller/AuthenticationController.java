package edu.tk.examcalc.controller;

import edu.tk.db.model.Condition;
import edu.tk.examcalc.entity.User;
import edu.tk.examcalc.form.LoginForm;
import edu.tk.examcalc.repository.UserRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.controlsfx.control.MasterDetailPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AuthenticationController extends Controller {
    @FXML
    public BorderPane content;
    @FXML
    public Tab loginTab;
    @FXML
    public Tab passwordResetTab;
    private final MasterDetailPane masterDetailPane = new MasterDetailPane();
    private final LoginForm loginForm = new LoginForm();

    public AuthenticationController() {
        super("authentication-login.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setPageTitle("Anmelden");

        loginForm.getSubmitButton().setOnAction(this::login);
        loginForm.getResetButton().setOnAction(this::resetForm);

        VBox centerBox = new VBox();
        centerBox.setSpacing(25);
        centerBox.setPadding(new Insets(25));

        VBox detailBox = new VBox();
        detailBox.setPadding(new Insets(25));
        detailBox.getChildren().add(new Text("Bitte melden Sie sich an, um Kollegiat:innen zu verwalten."));

        centerBox.getChildren().add(loginForm.render());
        ScrollPane scrollPane = new ScrollPane(centerBox);
        ScrollPane scrollPane2 = new ScrollPane(detailBox);
        scrollPane2.setFitToWidth(true);
        scrollPane2.setFitToHeight(true);
        detailBox.setStyle("-fx-background-color: FFFFFF;");
        masterDetailPane.setMasterNode(scrollPane);
        masterDetailPane.setDetailNode(scrollPane2);
        masterDetailPane.setDetailSide(Side.LEFT);
        masterDetailPane.setDividerPosition(.3);
        loginTab.setContent(masterDetailPane);
    }

    private void login(ActionEvent e) {
        loginForm.submit();
    }

    private void resetForm(ActionEvent e) {
        loginForm.cancel();
    }

}
