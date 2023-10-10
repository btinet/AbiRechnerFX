package edu.tk.examcalc.component;

import edu.tk.examcalc.entity.Pupil;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.table.TableFilter;
import org.controlsfx.control.tableview2.FilteredTableColumn;
import org.controlsfx.control.tableview2.FilteredTableView;
import org.controlsfx.control.tableview2.filter.popupfilter.PopupFilter;
import org.controlsfx.control.tableview2.filter.popupfilter.PopupStringFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PupilTableView extends FilteredTableView<PupilTableData> {

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
        FilteredTableColumn<PupilTableData,String> column = new FilteredTableColumn<>(text);
        column.setMinWidth(minWidth);
        column.setCellValueFactory(
                new PropertyValueFactory<>(property)
        );
        PopupFilter<PupilTableData, String> columnFilter = new PopupStringFilter<>(column);
        column.setOnFilterAction(e -> columnFilter.showPopup());
        tableColumns.add(column);
        return this;
    }

    public PupilTableView render() {
        getColumns().addAll(tableColumns);
        FilteredList<PupilTableData> filteredList = new FilteredList<>(FXCollections.observableArrayList(pupilList));
        SortedList<PupilTableData> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(this.comparatorProperty());
        //FilteredTableView.configureForFiltering(this,filteredList);
        filteredList.predicateProperty().bind(this.predicateProperty());
        setItems(sortedList);
        //TableFilter<PupilTableData> filter = new TableFilter<>(this);
        return this;
    }

}
