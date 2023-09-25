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
    private final IntegerProperty summedPoints = new SimpleIntegerProperty();

    public ExamTableData(Exam exam) {
        this.exam = exam;
        this.examNumber.setValue(exam.getExamNumber());
        this.label.setValue(exam.schoolSubject);
        this.points.setValue(exam.getPoints());
        this.summedPoints.setValue(exam.getPoints()*4);

    }

    public Exam getExam() {
        return exam;
    }

    public int getExamNumber() {
        return examNumber.get();
    }

    public IntegerProperty examNumberProperty() {
        return examNumber;
    }

    public void setExamNumber(int examNumber) {
        this.examNumber.set(examNumber);
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public int getPoints() {
        return points.get();
    }

    public IntegerProperty pointsProperty() {
        return points;
    }

    public void setPoints(int points) {
        this.points.set(points);
    }

    public int getSummedPoints() {
        return summedPoints.get();
    }

    public IntegerProperty summedPointsProperty() {
        return summedPoints;
    }

    public void setSummedPoints(int summedPoints) {
        this.summedPoints.set(summedPoints);
    }
}
