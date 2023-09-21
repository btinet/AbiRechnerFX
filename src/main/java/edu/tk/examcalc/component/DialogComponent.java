package edu.tk.examcalc.component;

import edu.tk.examcalc.MainApplication;
import edu.tk.examcalc.form.Form;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DialogComponent extends Dialog<ButtonType> {

    public DialogComponent(String title) {
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(MainApplication.appImage);
        setTitle(title);
        setHeaderText("Datensatz hinzufügen");
    }
    public void addButtonType(ButtonType buttonType) {
        getDialogPane().getButtonTypes().add(buttonType);
    }

    public void setContent(Form form) {
        VBox vBox = new VBox();
        vBox.setSpacing(25);
        vBox.getChildren().add(form.render());
        getDialogPane().setContent(vBox);
    }

}
