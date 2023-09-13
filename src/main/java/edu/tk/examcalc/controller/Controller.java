package edu.tk.examcalc.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.util.Objects;

public abstract class Controller implements Initializable {

    public static final BooleanProperty login = new SimpleBooleanProperty(false);
    public static final StringProperty pageTitleProperty = new SimpleStringProperty();
    protected final String fxmlResource;
    public Controller(String fxmlResource) {
        this.fxmlResource = fxmlResource;
    }

    public String getFxmlResource() {
        return fxmlResource;
    }

    public static void setPageTitle(String pageTitleProperty) {
        Controller.pageTitleProperty.setValue(pageTitleProperty);
    }
}
