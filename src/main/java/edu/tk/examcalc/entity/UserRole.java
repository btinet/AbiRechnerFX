package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;
import edu.tk.db.model.ORM;

public class UserRole extends Entity {

    @ORM
    protected Integer id;
    @ORM
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
