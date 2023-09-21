package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

public class Pupil extends Entity {

    protected int id;
    protected int tutorId;
    protected String firstname;
    protected String lastname;
    protected Date birthdate;
    protected Date examDate;

    private final StringProperty firstnameProperty = new SimpleStringProperty();
    private final StringProperty lastnameProperty = new SimpleStringProperty();

    public String getFirstnameProperty() {
        return firstnameProperty.get();
    }

    public StringProperty firstnamePropertyProperty() {
        return firstnameProperty;
    }

    public void setFirstnameProperty(String firstnameProperty) {
        this.firstnameProperty.set(firstnameProperty);
    }

    public String getLastnameProperty() {
        return lastnameProperty.get();
    }

    public StringProperty lastnamePropertyProperty() {
        return lastnameProperty;
    }

    public void setLastnameProperty(String lastnameProperty) {
        this.lastnameProperty.set(lastnameProperty);
    }

    public int getId() {
        return id;
    }

    public int getTutorId() {
        return tutorId;
    }

    public void setTutorId(int tutorId) {
        this.tutorId = tutorId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
        setFirstnameProperty(firstname);
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
        setLastnameProperty(lastname);
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

}
