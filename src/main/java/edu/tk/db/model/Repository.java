package edu.tk.db.model;

import edu.tk.db.global.Database;
import edu.tk.examcalc.MainApplication;
import edu.tk.examcalc.entity.Pupil;
import javafx.scene.Cursor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class Repository<T> {

    protected T entity;
    private Class<T> type;
    protected Boolean naturalCase = false;
    protected Boolean ucFirst = false;
    protected String alias;
    protected QueryBuilder<T> queryBuilder;

    public Repository() {
        this.entity = createInstance();
    }

    public Repository(Boolean naturalCase){
        if(Database.getConnection() == null) {
            Database.connect();
        }
        if(naturalCase){
            this.naturalCase = naturalCase;
        }
    }

    private T createInstance()  {
            Type t = getClass().getGenericSuperclass();
            ParameterizedType pt = (ParameterizedType) t;
            type = (Class<T>) pt.getActualTypeArguments()[0];
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
    }

    public Repository<T> setUcFirst()
    {
        this.ucFirst = true;
        return this;
    }

    public Repository<T> setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    protected QueryBuilder<T> createQueryBuilder()
    {
        return this.queryBuilder = new QueryBuilder<>(this.naturalCase,this.ucFirst,this.entity,this.alias);
    }

    public T find(int id){
        return this.doFind(id, "id");
    }

    public T find(int id,String field){
        return  this.doFind(id, field);
    }

    public T findOneBy(HashMap<String, String> condition){
        try {
            try {
                QueryBuilder<T> query = this.createQueryBuilder()
                        .selectOrm()
                        ;
                int i = 1;
                for (Map.Entry<String, String> entry : condition.entrySet()){
                    if ("null".equals(entry.getValue())) {
                        query.andWhere(entry.getKey() + " IS NULL");
                    } else {
                        query.andWhere(entry.getKey() + " = ?");
                        query.setParameter(i, entry.getValue());
                        i++;
                    }
                }
                return query.getQuery()
                        .getOneOrNullResult()
                        ;

            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            this.catchException(e);
        }
        return null;
    }


    public ArrayList<T> findBy(HashMap<String, String> condition){
        try {
            try {
                QueryBuilder<T> query = this.createQueryBuilder()
                        .selectOrm()
                        ;
                int i = 1;
                for (Map.Entry<String, String> entry : condition.entrySet()){
                    if ("null".equals(entry.getValue())) {
                        query.andWhere(entry.getKey() + " IS NULL");
                    } else {
                        query.andWhere(entry.getKey() + " = ?");
                        query.setParameter(i, entry.getValue());
                        i++;
                    }
                }
                return query.getQuery()
                        .getResult()
                        ;

            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                System.out.println("Fehler!");
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            System.out.println("Fehler!");
            this.catchException(e);
        }
        return null;
    }

    public ArrayList<T> findAll(){
        try {
            try {
                return createQueryBuilder().selectOrm().getQuery().getResult();
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            this.catchException(e);
        }
        return null;
    }

    public ArrayList<T> findAll(HashMap<String, String> orderBy){
        try {
            try {
                return this.createQueryBuilder()
                        .selectOrm()
                        .orderBy(orderBy)
                        .getQuery()
                        .getResult()
                        ;

            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            this.catchException(e);
        }
        return null;
    }

    protected T doFind(int id, String field){
        try {
            try {
                return this.createQueryBuilder()
                        .selectOrm()
                        .andWhere(field + " = ?")
                        .setParameter(1,id)
                        .getQuery()
                        .getOneOrNullResult()
                        ;

            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            this.catchException(e);
        }
        return null;
    }

    protected void catchException(SQLException e){
        Database.destroyConnection();
        System.out.println(e.getMessage());
    }

}
