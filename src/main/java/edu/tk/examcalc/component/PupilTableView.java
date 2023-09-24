package edu.tk.examcalc.component;

import edu.tk.examcalc.entity.Pupil;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PupilTableView extends TableView<PupilTableData> {

    private final ArrayList<TableColumn<PupilTableData,String>> tableColumns = new ArrayList<>();
    private final ArrayList<PupilTableData> pupilList = new ArrayList<>();

    public PupilTableView(ArrayList<Pupil> dataList) {
        for (Pupil pupil:
                dataList) {
            pupilList.add(new PupilTableData(pupil));
        }
        this
                .addColumn("Jahrgang","examDateProperty")
                .addColumn("Vorname","firstnameProperty")
                .addColumn("Nachname","lastnameProperty")
                .addColumn("Geburtsdatum","birthDateProperty")
                .addColumn("Kursblock, Gesamtpunktzahl","coursePointsProperty")
                .addColumn("Tutorium","tutorProperty")
        ;
    }

    public PupilTableView addColumn(String text, String property) {
        return addColumn(text, property, 200);
    }

    public PupilTableView addColumn(String text, String property, int minWidth) {
        TableColumn<PupilTableData,String> column = new TableColumn<>(text);
        column.setMinWidth(minWidth);
        column.setCellValueFactory(
                new PropertyValueFactory<>(property)
        );
        tableColumns.add(column);
        return this;
    }

    public PupilTableView render() {
        getColumns().addAll(tableColumns);
        setItems(FXCollections.observableArrayList(pupilList));
        return this;
    }

}
