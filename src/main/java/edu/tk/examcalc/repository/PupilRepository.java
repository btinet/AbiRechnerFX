package edu.tk.examcalc.repository;
;
import edu.tk.db.model.Repository;
import edu.tk.examcalc.entity.Pupil;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PupilRepository extends Repository<Pupil> {

    public ArrayList<Pupil> findAllJoin(){
        try {
            HashMap<String,String> order = new HashMap<>();
            order.put("examDate","DESC");
            setAlias("p");

            return this.createQueryBuilder()
                    .selectOrm()
                    .selectPublic("tutor.firstname AS tutorFirstname, tutor.lastname AS tutorLastname")
                    .innerJoin("tutor","p.tutor_id","tutor.id")
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

}