package edu.tk.db.model;

import java.util.HashMap;

public class Condition {

    protected HashMap<String, String> findBy;

    public Condition(){
        this.init();
    }

    public Condition(String field, String value){
        this.init();
        this.add(field, value);
    }

    public Condition(HashMap<String, String> orderBy){
        this.findBy = orderBy;
    }

    protected void init(){
        this.findBy = new HashMap<>();
    }

    public Condition add(String field, String value){
        this.findBy.put(field, value);
        return this;
    }

    public HashMap<String, String> getMap(){
        return this.findBy;
    }

}
