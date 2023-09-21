package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.Date;

public class Pupil extends Entity {

    protected int id;
    protected int tutorId;
    protected String firstname;
    protected String lastname;
    protected LocalDate birthdate;
    protected LocalDate examDate;

    private final StringProperty firstnameProperty = new SimpleStringProperty();
    private final StringProperty lastnameProperty = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> birthdateProperty = new SimpleObjectProperty<>();

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

    public LocalDate getBirthdateProperty() {
        return birthdateProperty.get();
    }

    public ObjectProperty<LocalDate> birthdatePropertyProperty() {
        return birthdateProperty;
    }

    public void setBirthdateProperty(LocalDate birthdateProperty) {
        this.birthdateProperty.set(birthdateProperty);
    }

    public String toString() {
        return this.firstname + " " + this.lastname;
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

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        this.setBirthdateProperty(birthdate);
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

}
