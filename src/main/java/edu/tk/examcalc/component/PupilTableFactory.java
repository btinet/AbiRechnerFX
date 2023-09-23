package edu.tk.examcalc.component;


import edu.tk.examcalc.entity.Pupil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

public class PupilTableFactory {

    private Pupil pupil;

    private final StringProperty firstnameProperty = new SimpleStringProperty();
    private final StringProperty lastnameProperty = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> birthDateProperty = new SimpleObjectProperty<>();

    public PupilTableFactory(Pupil pupil) {
        this.pupil = pupil;



    }



}
