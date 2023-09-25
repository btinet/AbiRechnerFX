package edu.tk.examcalc.component;

import edu.tk.examcalc.entity.Pupil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.controlsfx.control.tableview2.filter.popupfilter.PopupFilter;
import org.controlsfx.control.tableview2.filter.popupfilter.PopupStringFilter;

public class PupilTableData {

    private final Pupil pupil;
    private final StringProperty firstnameProperty = new SimpleStringProperty();
    private final StringProperty lastnameProperty = new SimpleStringProperty();
    private final StringProperty birthDateProperty = new SimpleStringProperty();
    private final StringProperty examDateProperty = new SimpleStringProperty();
    private final StringProperty coursePointsProperty = new SimpleStringProperty();
    private final StringProperty tutorProperty = new SimpleStringProperty();

    public PupilTableData (Pupil pupil) {
        this.pupil = pupil;
        firstnameProperty.setValue(pupil.getFirstname());
        lastnameProperty.setValue(pupil.getLastname());
        birthDateProperty.setValue(pupil.getBirthDate());
        examDateProperty.setValue(pupil.getExamDate());
        coursePointsProperty.setValue(pupil.getCoursePoints().toString());
        if(pupil.tutorLastname != null || pupil.tutorFirstname != null) {
            tutorProperty.setValue(pupil.tutorFirstname + " " + pupil.tutorLastname);
        }

    }

    public Pupil getPupil() {
        return pupil;
    }

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

    public String getBirthDateProperty() {
        return birthDateProperty.get();
    }

    public StringProperty birthDatePropertyProperty() {
        return birthDateProperty;
    }

    public void setBirthDateProperty(String birthDateProperty) {
        this.birthDateProperty.set(birthDateProperty);
    }

    public String getExamDateProperty() {
        return examDateProperty.get();
    }

    public StringProperty examDatePropertyProperty() {
        return examDateProperty;
    }

    public void setExamDateProperty(String examDateProperty) {
        this.examDateProperty.set(examDateProperty);
    }

    public String getCoursePointsProperty() {
        return coursePointsProperty.get();
    }

    public StringProperty coursePointsPropertyProperty() {
        return coursePointsProperty;
    }

    public void setCoursePointsProperty(String coursePointsProperty) {
        this.coursePointsProperty.set(coursePointsProperty);
    }

    public String getTutorProperty() {
        return tutorProperty.get();
    }

    public StringProperty tutorPropertyProperty() {
        return tutorProperty;
    }

    public void setTutorProperty(String tutorProperty) {
        this.tutorProperty.set(tutorProperty);
    }
}
