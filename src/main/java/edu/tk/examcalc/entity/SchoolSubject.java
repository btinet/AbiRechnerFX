package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;
import edu.tk.db.model.ORM;

public class SchoolSubject extends Entity {

    @ORM
    protected int id;
    @ORM
    protected String label;

    public String toString() {
        return this.label;
    }

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
