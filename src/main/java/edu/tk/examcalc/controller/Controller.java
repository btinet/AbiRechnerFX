package edu.tk.examcalc.controller;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.util.Objects;

public abstract class Controller implements Initializable {

    public static final BooleanProperty login = new SimpleBooleanProperty(false);
    protected final String fxmlResource;
    public Controller(String fxmlResource) {
        this.fxmlResource = fxmlResource;
    }

    public String getFxmlResource() {
        return fxmlResource;
    }


}
