package edu.tk.examcalc.component;

import javafx.scene.control.Button;

public class IconButton extends Button {

    public IconButton() {
        super();
    }

    public IconButton(String text) {
        super(text);
    }

    public void setIcon(String iconCode) {
        setGraphic(new Icon(iconCode));
    }

}
