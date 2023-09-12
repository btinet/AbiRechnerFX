package edu.tk.fxcontrollertest.controller;

import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

public abstract class Controller implements Initializable {

    protected final String fxmlResource;
    public Controller(String fxmlResource) {
        this.fxmlResource = fxmlResource;
    }

    public String getFxmlResource() {
        return fxmlResource;
    }

}
