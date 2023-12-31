package edu.tk.db.model;

import java.sql.SQLException;

/**
 * Der Entity-Manager verwaltet die Entitäten der jeweiligen Relationen
 */
public class EntityManager<T> extends Repository<T> {

    public EntityManager() {
        super(false);
    }

    /**
     *
     * @param e Entity-Objekt, dass den Datensatz enthält.
     */
    public void persist(T e){
        this.persist(e, "id");
    }

    /**
     *
     * @param e Entity-Objekt, dass den Datensatz enthält.
     * @param field Feldname des Primärschlüssels, falls der Name vom Standard abweicht.
     */
    public void persist(T e, String field){
        this.entity = e;
        try {
            this.createQueryBuilder()
                    .insertOrm(field)
                    .getInsertQuery()
                    .getPersistResult()
            ;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param e Entity-Objekt, dass den Datensatz enthält.
     * @param id Primärschlüssel des zu aktualisierenden Tupels.
     */
    public void persist(T e, int id){
        this.persist(e,id,"id");
    }

    /**
     *
     * @param e Entity-Objekt, dass den Datensatz enthält.
     * @param id Primärschlüssel des zu aktualisierenden Tupels.
     * @param field Feldname, falls der Name des Primärschlüssels vom Standard abweicht.
     */
    public void persist(T e, int id, String field){
        this.entity = e;
        try {
            QueryBuilder qb = this.createQueryBuilder();

            Integer nextParameter = qb.addValues(field);
            qb
                    .andWhere(field + "=?")
                    .setParameter(nextParameter,id)
                    .getUpdateQuery()
                    .getPersistResult()
            ;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     *
     * @param e Entity-Objekt, dass den Datensatz enthält.
     * @param id Primärschlüssel des zu entfernenden Tupels.
     */
    public void remove(T e, int id){
        this.entity = e;
        this.remove(e,id, "id");
    }

    /**
     *
     * @param e Entity-Objekt, dass den Datensatz enthält.
     * @param id Primärschlüssel des zu entfernenden Tupels.
     * @param field Feldname, falls der Name des Primärschlüssels vom Standard abweicht.
     */
    public void remove(T e, int id, String field){
        this.entity = e;
        QueryBuilder qb = this.createQueryBuilder();

        qb
                .andWhere(field + "=?")
                .setParameter(1,id)
                .getRemoveQuery()
                .getPersistResult()
        ;
    }

}
