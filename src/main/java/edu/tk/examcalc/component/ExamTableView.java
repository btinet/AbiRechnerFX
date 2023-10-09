package edu.tk.examcalc.component;

import edu.tk.examcalc.entity.Exam;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
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
                .addColumn("Nr.","examNumber",50)
                .addColumn("Fach","label",150)
                .addColumn("Notenpunkte","points",150)
                .addColumn("4-fach","summedPoints",150)
                .addColumn("kritisch bei","criticalPoints",150)
                .addColumn("Erfolg ab","neededIntegerPoints",150)
                .addColumn("notwendig (4-fach)","neededSummedPoints",150)
                .addColumn("Gesamtpunkte (neu)","summedExamPoints",150)
                .addColumn("Endnote (neu)","newGrade",150)
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
        refresh();
        return this;
    }

}
