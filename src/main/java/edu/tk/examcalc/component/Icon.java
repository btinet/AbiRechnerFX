package edu.tk.examcalc.component;

import org.kordamp.ikonli.javafx.FontIcon;

public class Icon extends FontIcon{

    public Icon(String iconCode) {
        super(iconCode);
    }

    public void setIcon(String iconLiteral) {
        this.setIconLiteral(iconLiteral);
    }

}
