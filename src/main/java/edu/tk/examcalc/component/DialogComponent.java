package edu.tk.examcalc.component;

import edu.tk.examcalc.MainApplication;
import edu.tk.examcalc.form.Form;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DialogComponent extends Dialog<ButtonType> {

    ButtonType cancelButtonType = new ButtonType("_Abbrechen", ButtonType.CANCEL.getButtonData());
    ButtonType submitButtonType = new ButtonType("_Speichern", ButtonType.OK.getButtonData());

    public DialogComponent(String title) {
        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(MainApplication.appImage);
        setTitle(title);
        setHeaderText("Datensatz hinzufügen");
        addButtonType(submitButtonType);
        addButtonType(cancelButtonType);
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

    public ButtonType getCancelButtonType() {
        return cancelButtonType;
    }

    public void setCancelButtonType(ButtonType cancelButtonType) {
        this.cancelButtonType = cancelButtonType;
    }

    public void setCancelButtonDisabled(boolean disabled) {
        this.getDialogPane().lookupButton(getCancelButtonType()).setDisable(disabled);
    }

    public ButtonType getSubmitButtonType() {
        return submitButtonType;
    }

    public void setSubmitButtonType(ButtonType submitButtonType) {
        this.submitButtonType = submitButtonType;
    }

    public void setSubmitButtonDisabled(boolean disabled) {
        this.getDialogPane().lookupButton(getSubmitButtonType()).setDisable(disabled);
    }

}
