package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;

public class UserRole extends Entity {

    protected Integer id;
    protected String label;

    public Integer getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
