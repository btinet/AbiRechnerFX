package edu.tk.examcalc.repository;

import edu.tk.db.model.Repository;
import edu.tk.examcalc.entity.Exam;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExamRepository extends Repository<Exam> {

    public ArrayList<Exam> findAllJoinSubject(int id){
        try {
            HashMap<String,String> order = new HashMap<>();
            order.put("examNumber","ASC");
            setAlias("e");
            return this.createQueryBuilder()
                    .selectOrm()
                    .selectPublic("school_subject.label AS schoolSubject")
                    .innerJoin("school_subject","e.school_subject_id","school_subject.id")
                    .orWhere("e.pupil_id = ?")
                    .setParameter(1,id)
                    .orderBy(order)
                    .getQuery()
                    .getResult()
                    ;

        } catch (SQLException e) {
            this.catchException(e);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Integer sumExamPoints(int id){
        try {
            return this.createQueryBuilder()
                    .selectPublic("SUM(points * 4) AS summedExamPoints")
                    .orWhere("pupil_id = ?")
                    .setParameter(1,id)
                    .getQuery()
                    .getScalarResult()
                    ;

        } catch (SQLException e) {
            this.catchException(e);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
