package edu.tk.examcalc.repository;
;
import edu.tk.db.model.Repository;
import edu.tk.examcalc.entity.Pupil;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PupilRepository extends Repository<Pupil> {

    public ArrayList<Pupil> findAllOrdered(){
        try {
            HashMap<String,String> order = new HashMap<>();
            order.put("examDate","DESC");

            return this.createQueryBuilder()
                    .selectOrm()
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