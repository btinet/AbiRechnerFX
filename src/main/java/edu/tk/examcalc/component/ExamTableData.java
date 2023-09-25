package edu.tk.examcalc.component;

import edu.tk.db.global.Session;
import edu.tk.examcalc.entity.Exam;
import edu.tk.examcalc.entity.Pupil;
import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.Collections;

public class ExamTableData {

    private final Exam exam;

    private final IntegerProperty examNumber = new SimpleIntegerProperty();
    private final StringProperty label = new SimpleStringProperty();
    private final IntegerProperty points = new SimpleIntegerProperty();
    private final IntegerProperty summedPoints = new SimpleIntegerProperty();
    private final IntegerProperty summedExamPoints = new SimpleIntegerProperty();
    private final IntegerProperty neededPoints = new SimpleIntegerProperty();
    private final IntegerProperty neededSummedPoints = new SimpleIntegerProperty();
    private final DoubleProperty newGrade = new SimpleDoubleProperty();

    public ExamTableData(Exam exam) {
        Pupil pupil =(Pupil) Session.copy("pupil");
        assert pupil != null;

        Double grade = 0.0;
        boolean lowerEnd = false;
        int currentKey = 0;
        int nextKey = 0;
        int sumPoints = exam.getSummedExamPoints() + pupil.getCoursePoints();

        ArrayList<Integer> grades = new ArrayList<>(Grades.GRADE.keySet());
        Collections.sort(grades);
        for (Integer entry : grades) {
            if(sumPoints >= entry) {
                currentKey = entry;
                System.out.println("Punkte: " + currentKey);
                lowerEnd = true;
            }
            if(lowerEnd && sumPoints < entry) {
                grade = Grades.GRADE.get(currentKey);
                nextKey = entry;
                break;
            }
        }
        Session.set("grade",grade);

        this.exam = exam;
        this.examNumber.setValue(exam.getExamNumber());
        this.label.setValue(exam.schoolSubject);
        this.points.setValue(exam.getPoints());
        this.summedPoints.setValue(exam.getPoints()*4);

        if(exam.getExamNumber() != null && exam.getExamNumber() <= 3) {
            int zwischenSumme = exam.getPoints()*4 + nextKey-sumPoints;
            int x = zwischenSumme/4+(exam.getPoints()/3*2);

            if(x < 15) {
                neededPoints.setValue(x);
                neededSummedPoints.setValue(exam.getPoints()*4 + nextKey-sumPoints);
                summedExamPoints.setValue(nextKey);
                newGrade.setValue(grade - .1);
            }
        }





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

    public int getSummedExamPoints() {
        return summedExamPoints.get();
    }

    public IntegerProperty summedExamPointsProperty() {
        return summedExamPoints;
    }

    public void setSummedExamPoints(int summedExamPoints) {
        this.summedExamPoints.set(summedExamPoints);
    }

    public int getNeededPoints() {
        return neededPoints.get();
    }

    public IntegerProperty neededPointsProperty() {
        return neededPoints;
    }

    public void setNeededPoints(int neededPoints) {
        this.neededPoints.set(neededPoints);
    }

    public int getNeededSummedPoints() {
        return neededSummedPoints.get();
    }

    public IntegerProperty neededSummedPointsProperty() {
        return neededSummedPoints;
    }

    public void setNeededSummedPoints(int neededSummedPoints) {
        this.neededSummedPoints.set(neededSummedPoints);
    }

    public double getNewGrade() {
        return newGrade.get();
    }

    public DoubleProperty newGradeProperty() {
        return newGrade;
    }

    public void setNewGrade(double newGrade) {
        this.newGrade.set(newGrade);
    }
}
