package edu.tk.examcalc.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.Objects;

public abstract class Controller implements Initializable {

    public static final BooleanProperty login = new SimpleBooleanProperty(false);
    public static final StringProperty pageTitleProperty = new SimpleStringProperty();
    protected final String fxmlResource;
    private BorderPane root;
    public Controller(String fxmlResource) {
        this.fxmlResource = fxmlResource;
    }

    public void switchToController(Node contentNode, Controller controller) {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(controller.getFxmlResource())));
        try {
            setRoot(contentNode.getParent());
            root.setCenter(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setRoot(Parent root) {
        this.root = (BorderPane) root;
    }

    public String getFxmlResource() {
        return fxmlResource;
    }

    public static void setPageTitle(String pageTitleProperty) {
        Controller.pageTitleProperty.setValue(pageTitleProperty);
    }
}
