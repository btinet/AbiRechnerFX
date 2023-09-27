package edu.tk.examcalc.component;

import edu.tk.examcalc.entity.Exam;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

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
                .addColumn("kritisch bei",null,150)
                .addColumn("Erfolg bei","neededPoints",150)
                .addColumn("notwendig (4-fach)","neededSummedPoints",150)
                .addColumn("Gesamtpunkte (neu)","summedExamPoints",150)
                .addColumn("Endnote (neu)","newGrade",150)
        ;

        this.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(ExamTableData item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || item.getNeededPoints() == 0) {
                    setStyle("");
                }
                else if (item.getNeededPoints() > 0) {
                    setStyle("-fx-background-color: lightseagreen;");
                }
                else if (item.getNeededPoints() < 0) {
                    setStyle("-fx-background-color: #ffd7d1;");
                }
                else {
                    setStyle("");
                }
                if(getTableView().getSelectionModel().getSelectedItems().contains(item))
                    setTextFill(Color.WHITE);
                else
                    setTextFill(Color.BLACK);

            }
        });



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