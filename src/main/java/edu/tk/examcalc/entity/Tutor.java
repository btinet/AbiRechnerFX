package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;
import edu.tk.db.model.ORM;

public class Tutor extends Entity {

    @ORM
    protected int id;
    @ORM
    protected String firstname;
    @ORM
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
