package edu.tk.examcalc.component;

import edu.tk.examcalc.entity.Exam;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class ExamTableView extends TableView<ExamTableData> {

    private final ArrayList<TableColumn<ExamTableData,String>> tableColumns = new ArrayList<>();
    private final ArrayList<ExamTableData> list = new ArrayList<>();

    public ExamTableView(ArrayList<Exam> dataList) {
        for (Exam exam:
                dataList) {
            list.add(new ExamTableData(exam));
        }
        this
                .addColumn("Prüfung","examNumber")
                .addColumn("Fach","label")
                .addColumn("Notenpunkte","points")
                .addColumn("Zwischensumme","summedPoints")
        ;
    }

    public ExamTableView addColumn(String text, String property) {
        return addColumn(text, property, 200);
    }

    public ExamTableView addColumn(String text, String property, int minWidth) {
        TableColumn<ExamTableData,String> column = new TableColumn<>(text);
        column.setMinWidth(minWidth);
        column.setCellValueFactory(
                new PropertyValueFactory<>(property)
        );
        tableColumns.add(column);
        return this;
    }

    public ExamTableView render() {
        getColumns().addAll(tableColumns);
        setItems(FXCollections.observableArrayList(list));
        return this;
    }

}
