package edu.tk.examcalc.repository;

import edu.tk.db.model.Repository;
import edu.tk.examcalc.entity.Exam;

public class ExamRepository extends Repository<Exam> {

    public ExamRepository() {
        setEntity(new Exam());
    }

}
