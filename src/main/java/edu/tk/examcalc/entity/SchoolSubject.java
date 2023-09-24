package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;

public class SchoolSubject extends Entity {

    protected int id;
    protected String label;

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
