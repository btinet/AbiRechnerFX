package edu.tk.examcalc.repository;
;
import edu.tk.db.model.Repository;
import edu.tk.examcalc.entity.Pupil;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class PupilRepository extends Repository<Pupil> {

    public ArrayList<Pupil> findAllGroupedByPupil(){
        try {
            HashMap<String,String> order = new HashMap<>();
            order.put("examDate","DESC");

            return this.createQueryBuilder()
                    .selectOrm()
                    .groupBy("id")
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

    public Pupil findOneGroupedByPupil(int id){
        try {
            HashMap<String,String> order = new HashMap<>();
            order.put("examDate","DESC");

            String tableName = generateSnakeTailString(type.getSimpleName());

            return this.createQueryBuilder()
                    .selectOrm()
                    .groupBy("id")
                    .orderBy(order)
                    .andWhere(tableName + ".id = ?")
                    .setParameter(1,id)
                    .getQuery()
                    .getOneOrNullResult()
                    ;

        } catch (SQLException e) {
            this.catchException(e);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}