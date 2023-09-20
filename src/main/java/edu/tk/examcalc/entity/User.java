package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;

public class User extends Entity {

    protected Integer id;
    protected String username;
    protected String password;


    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
