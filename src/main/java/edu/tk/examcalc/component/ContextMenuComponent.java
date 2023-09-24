package edu.tk.examcalc.component;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class ContextMenuComponent extends ContextMenu {

    public ContextMenuComponent addMenuItem(MenuItem menuItem) {
        getItems().add(menuItem);
        return this;
    }

    public MenuItem createAndAddMenuItem(String text) {
        MenuItem menuItem = new MenuItem(text);
        getItems().add(menuItem);
        return menuItem;
    }

    public void createAndAddMenuItem(String text, EventHandler<ActionEvent> eventHandler) {
        MenuItem menuItem = createAndAddMenuItem(text);
        menuItem.setOnAction(eventHandler);
    }

    public void setAction(MenuItem menuItem, EventHandler<ActionEvent> eventHandler) {
        menuItem.setOnAction(eventHandler);
    }

}
