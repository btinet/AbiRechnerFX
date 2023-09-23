package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;

public class Tutor extends Entity {

    protected int id;
    protected String firstname;
    protected String lastname;

    public String toString() {
        return this.firstname + " " + this.lastname;
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
