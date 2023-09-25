package edu.tk.examcalc.component;

import edu.tk.examcalc.entity.Exam;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ExamTableData {

    private final Exam exam;

    private final IntegerProperty examNumber = new SimpleIntegerProperty();
    private final StringProperty label = new SimpleStringProperty();
    private final IntegerProperty points = new SimpleIntegerProperty();

    public ExamTableData(Exam exam) {
        this.exam = exam;

    }

    public Exam getExam() {
        return exam;
    }



}
