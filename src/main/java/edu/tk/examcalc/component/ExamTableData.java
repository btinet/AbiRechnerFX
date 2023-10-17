package edu.tk.examcalc.component;

import edu.tk.db.global.Session;
import edu.tk.examcalc.entity.Exam;
import edu.tk.examcalc.entity.Pupil;
import javafx.beans.property.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class ExamTableData {

    private final Exam exam;
    private final IntegerProperty examNumber = new SimpleIntegerProperty();
    private final StringProperty label = new SimpleStringProperty();
    private final IntegerProperty points = new SimpleIntegerProperty();
    private final IntegerProperty summedPoints = new SimpleIntegerProperty();
    private final StringProperty neededIntegerPoints = new SimpleStringProperty();
    private final StringProperty summedExamPoints = new SimpleStringProperty();
    private final StringProperty neededPoints = new SimpleStringProperty();
    private final StringProperty neededSummedPoints = new SimpleStringProperty();
    private final StringProperty newGrade = new SimpleStringProperty();
    private final StringProperty criticalPoints = new SimpleStringProperty();

    public ExamTableData(Exam exam) {
        Pupil pupil =(Pupil) Session.copy("pupil");
        assert pupil != null;

        double grade = 0.0;
        boolean lowerEnd = false;
        int prevKey = 0;
        int currentKey = 0;
        int nextKey = 0;
        int sumPoints = pupil.summedExamPoints + pupil.getCoursePoints();

        ArrayList<Integer> grades = new ArrayList<>(Grades.GRADE.keySet());
        Collections.sort(grades);
        for (Integer entry : grades) {
            if(sumPoints > entry) {
                prevKey = entry;
            }
            if(sumPoints >= entry) {
                currentKey = entry;
                lowerEnd = true;
            }
            if(lowerEnd && sumPoints < entry) {
                grade = Grades.GRADE.get(currentKey);
                nextKey = entry;
                break;
            }
            if(sumPoints >= 823) {
                grade = 1.0;
                nextKey = currentKey;
            }
        }
        Session.set("grade",grade);

        this.exam = exam;
        this.examNumber.setValue(exam.getExamNumber());
        this.label.setValue(exam.schoolSubject);
        this.points.setValue(exam.getPoints());
        this.summedPoints.setValue(exam.getPoints()*4);

        if(exam.getExamNumber() != null && exam.getExamNumber() <= 3) {
            int lowerZwischenSumme = exam.getPoints()*4 +prevKey-sumPoints;
            int zwischenSumme = exam.getPoints()*4 + nextKey-sumPoints;
            double lowerX = (double) lowerZwischenSumme*3/4 -(exam.getPoints()*2);
            double x = (double) zwischenSumme *3/4 -(exam.getPoints()*2);

            if(x < 15 && sumPoints < 823) {
                int roundedX = (int) Math.round(lowerX);
                if(roundedX >= 0) {
                    criticalPoints.setValue(String.valueOf(roundedX));
                } else {
                    criticalPoints.setValue("ohne Risiko");
                }
                DecimalFormat df = new DecimalFormat("#.#");

                neededPoints.setValue(String.valueOf(Math.ceil(x)));
                neededIntegerPoints.setValue(String.valueOf(getNeededPoints()));
                neededSummedPoints.setValue(String.valueOf(zwischenSumme));
                summedExamPoints.setValue(String.valueOf(nextKey));
                newGrade.setValue(df.format(grade - .1));
            }
        }
    }

    public Exam getExam() {
        return exam;
    }

    public int getNeededPoints() {
        if(neededPoints.get() != null) {
            return (int) Double.parseDouble(neededPoints.get());
        } else {
            return 0;
        }

    }

    public int getExamNumber() {
        return examNumber.get();
    }

    public IntegerProperty examNumberProperty() {
        return examNumber;
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public int getPoints() {
        return points.get();
    }

    public IntegerProperty pointsProperty() {
        return points;
    }

    public int getSummedPoints() {
        return summedPoints.get();
    }

    public IntegerProperty summedPointsProperty() {
        return summedPoints;
    }

    public String getSummedExamPoints() {
        return summedExamPoints.get();
    }

    public StringProperty summedExamPointsProperty() {
        return summedExamPoints;
    }

    public StringProperty neededPointsProperty() {
        return neededPoints;
    }

    public String getNeededSummedPoints() {
        return neededSummedPoints.get();
    }

    public StringProperty neededSummedPointsProperty() {
        return neededSummedPoints;
    }

    public String getNewGrade() {
        return newGrade.get();
    }

    public StringProperty newGradeProperty() {
        return newGrade;
    }

    public String getCriticalPoints() {
        return criticalPoints.get();
    }

    public StringProperty criticalPointsProperty() {
        return criticalPoints;
    }

    public String getNeededIntegerPoints() {
        return neededIntegerPoints.get();
    }

    public StringProperty neededIntegerPointsProperty() {
        return neededIntegerPoints;
    }
}
