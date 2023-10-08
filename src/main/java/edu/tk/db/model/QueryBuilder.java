package edu.tk.db.model;


import edu.tk.db.global.Database;
import edu.tk.examcalc.controller.Kernel;
import javafx.application.Platform;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class QueryBuilder<T> {

    private PreparedStatement statement;
    private final T entity;
    private final Boolean naturalCase;
    private final Boolean ucFirst;
    private final String alias;

    private boolean publicSelect = false;
    private final StringBuilder projection = new StringBuilder();

    private final StringBuilder insertion = new StringBuilder();
    private final StringBuilder insertData = new StringBuilder();

    private final StringBuilder table = new StringBuilder();

    private final StringBuilder condition = new StringBuilder();

    private final StringBuilder values = new StringBuilder();

    private final StringBuilder joins = new StringBuilder();

    private final StringBuilder orderBy = new StringBuilder();

    private final StringBuilder groupBy = new StringBuilder();

    private final StringBuilder having = new StringBuilder();

    private final StringBuilder query = new StringBuilder();



    private final HashMap<Integer, String> stringParameters = new HashMap<>();
    private final HashMap<Integer, Integer> integerParameters = new HashMap<>();


    public QueryBuilder (Boolean naturalCase, Boolean ucFirst, T entity, String alias) {
        this.entity = entity;
        this.naturalCase = naturalCase;
        this.ucFirst = ucFirst;
        this.alias = alias;
        Logger.getLogger(getClass().getName()).info("Neue SQL-Abfrage generieren:");
        Logger.getLogger(getClass().getName()).info("============================");
    }

    private String generateSnakeTailString(String value)
    {
        String string = String.join("_", value.split("(?=\\p{Upper})")).toLowerCase();
        if(string.charAt(0) == '_'){
            return string.substring(0);
        }
        return string;
    }

    public QueryBuilder<T> insertOrm(){
        this.insertion.append(this.getInsertColumns("id"));
        this.insertData.append(this.getInsertData("id"));
        return this;
    }

    public QueryBuilder<T> insertOrm(String primaryKey){
        this.insertion.append(this.getInsertColumns(primaryKey));
        this.insertData.append(this.getInsertData(primaryKey));
        return this;
    }

    private String getInsertData(String primaryKey){
        Field[] fields = this.entity.getClass().getDeclaredFields();
        StringBuilder columnBuilder = new StringBuilder();
        int i = 0;
        for (Field field : fields) {
            if(field.getModifiers() == Modifier.PROTECTED && !field.getName().equals(primaryKey)){
                if(i != 0) {
                    columnBuilder.append(",");
                }
                    try {
                        field.setAccessible(true);

                        if(field.get(this.entity) != null) {
                            columnBuilder.append("'");
                            columnBuilder.append(field.get(this.entity));
                            columnBuilder.append("'");
                        } else {
                            columnBuilder.append("NULL");
                        }

                        field.setAccessible(false);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                i++;
            }
        }
        if(columnBuilder.length() > 0){
            return columnBuilder.toString();
        }
        return "";
    }

    private String getInsertColumns(String primaryKey){
        Field[] fields = this.entity.getClass().getDeclaredFields();
        StringBuilder columnBuilder = new StringBuilder();
        int i = 0;
        for (Field field : fields) {
            if(field.getModifiers() == Modifier.PROTECTED && !field.getName().equals(primaryKey)){
                if(i != 0) {
                    columnBuilder.append(",");
                }
                if(this.naturalCase){
                    columnBuilder.append(field.getName());
                } else {
                    columnBuilder.append(this.generateSnakeTailString(field.getName()));
                }
                i++;
            }
        }
        if(columnBuilder.length() > 0){
            return columnBuilder.toString();
        }
        return "";
    }

    private String getColumns(){
        Field[] allFields = this.entity.getClass().getDeclaredFields();
        ArrayList<Field> fields = new ArrayList<>();
        for(Field field : allFields) {
            if(field.getModifiers() == Modifier.PROTECTED) {
                fields.add(field);
            }
        }

        StringBuilder columnBuilder = new StringBuilder();
        int i = 1;
        for (Field field : fields) {
            if(this.naturalCase){
                columnBuilder.append(field.getName());
            } else {
                if(this.alias != null) {
                    columnBuilder.append(alias).append(".");
                }

                columnBuilder.append(this.generateSnakeTailString(field.getName()));

                columnBuilder
                        .append(" AS ")
                        .append(field.getName())
                ;
            }
            ;
            if(i < fields.size()) {
                columnBuilder.append(", ");
            } else {
                columnBuilder.append(" ");
            }
            i++;
        }
        if(columnBuilder.length() > 0){
            return columnBuilder.toString();
        }
        return "";
    }

    public QueryBuilder<T> selectPublic(String fields) {
        this.publicSelect = true;
        return this.select(fields);
    }

    public QueryBuilder<T> select(String fields) {
        if(this.projection.length() > 0) {
            this.projection.append(", ");
        }
        this.projection.append(fields);
        return this;
    }

    public QueryBuilder<T> selectOrm() {
        if(this.projection.length() > 0) {
            this.projection.append(", ");
        }
        this.projection.append(this.getColumns());
        return this;
    }

    public QueryBuilder<T> insertInto(){
        if(this.naturalCase){
            this.table.append(this.entity.getClass().getSimpleName());
        } else {
            this.query.append(this.generateSnakeTailString(this.entity.getClass().getSimpleName()));
        }
        return this;
    }

    public QueryBuilder<T> innerJoin(String table,String key){
        this.joins.append(" INNER JOIN ").append(table).append(" USING (").append(key).append(")");
        return this;
    }

    public QueryBuilder<T> innerJoin(String table,String left, String right){
        this.joins.append(" INNER JOIN ")
                .append(table)
                .append(" ON (")
                .append(left)
                .append(" = ")
                .append(right)
                .append(")")
        ;
        return this;
    }

    public int addValues(String primaryKey){
        if(0 != this.values.length()){
            this.values.append(" SET ");
        }
        int i = 1;
        for (Field f: this.entity.getClass().getDeclaredFields()){
            if(f.getModifiers() == Modifier.PROTECTED && !f.getName().equals(primaryKey)){
                String fName;
                if(i > 1) {
                    this.values.append(", ");
                }
                if(this.naturalCase){
                    fName = f.getName();
                } else {
                    fName = this.generateSnakeTailString(f.getName());
                }
                try {
                    f.setAccessible(true);
                    this.values.append(fName);
                    if ("null".equals(f.get(this.entity))) {
                        this.values.append("IS NULL");
                    } else {
                        this.values.append("=?");
                        this.setParameter(i, f.get(this.entity).toString());
                        i++;
                    }
                    f.setAccessible(false);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return i;
    }

    public QueryBuilder<T> andWhere(String condition){
        if(0 != this.condition.length()){
            this.condition.append(" AND ");
        }
        this.condition.append(condition);
        return this;
    }

    public QueryBuilder<T> orWhere(String condition){
        if(0 != this.condition.length()){
            this.condition.append(" OR ");
        }
        this.condition.append(condition);
        return this;
    }

    public QueryBuilder<T> groupBy(String field){
        if(0 != this.groupBy.length()){
            this.groupBy.append(", ");
        }
        this.groupBy.append(field);
        return this;
    }

    public QueryBuilder<T> andHaving(String condition){
        if(0 != this.having.length()){
            this.having.append(" AND ");
        }
        this.having.append(condition);
        return this;
    }

    public QueryBuilder<T> orHaving(String condition){
        if(0 != this.having.length()){
            this.having.append(" OR ");
        }
        this.having.append(condition);
        return this;
    }

    public QueryBuilder<T> orderBy(HashMap<String, String> orderBy){
        int i = 1;
        for(Map.Entry<String, String> entry: orderBy.entrySet()){
            this.orderBy.append(entry.getKey()).append(" ").append(entry.getValue().toUpperCase());
            if(i < orderBy.size()){
                this.orderBy.append(", ");
            }
            i++;
        }
        return this;
    }

    public QueryBuilder<T> setParameter(Integer parameter, int value){
        this.integerParameters.put(parameter, value);
        Logger.getLogger(getClass().getName()).info(parameter + ". ? ist: " + value);
        return this;
    }

    public QueryBuilder<T> setParameter(Integer parameter, String value){
        this.stringParameters.put(parameter, value);
        Logger.getLogger(getClass().getName()).info(parameter + ". ? ist: " + value);
        return this;
    }

    public QueryBuilder<T> getUpdateQuery() throws SQLException {
        this.query.append("UPDATE ");
        if(this.naturalCase){
            this.query.append(this.entity.getClass().getSimpleName());
        } else {
            this.query.append(this.generateSnakeTailString(this.entity.getClass().getSimpleName()));
        }

        this.query.append(" SET ");

        this.query.append(this.values);
        if(0 != this.condition.length()){
            this.query.append(" WHERE ").append(this.condition);
        }

        try {
            this.statement = Database.getConnection().prepareStatement(this.query.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(!this.stringParameters.isEmpty()){
            for(Map.Entry<Integer, String> entry: this.stringParameters.entrySet()){
                if(!Objects.equals(entry.getValue(), "null")){
                    this.statement.setString(entry.getKey(),entry.getValue());
                }
            }
        }

        if(!this.integerParameters.isEmpty()){
            for(Map.Entry<Integer, Integer> entry: this.integerParameters.entrySet()){
                if(!Objects.equals(entry.getValue(), 0)){
                    this.statement.setInt(entry.getKey(),entry.getValue());
                }
            }
        }

        Logger.getLogger(getClass().getName()).info("UPDATE Query: " + this.query);
        Platform.runLater(() -> {
            Kernel.activityStringProperty.setValue(this.query.toString());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
        });

        return this;
    }

    public QueryBuilder<T> getRemoveQuery(){
        this.query.append("DELETE FROM ");

        if(this.naturalCase){
            this.query.append(this.entity.getClass().getSimpleName());
        } else {
            this.query.append(this.generateSnakeTailString(this.entity.getClass().getSimpleName()));
        }

        if(0 != this.condition.length()){
            this.query.append(" WHERE ").append(this.condition);
        }

        try {
            this.statement = Database.getConnection().prepareStatement(this.query.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(!this.stringParameters.isEmpty()){
            for(Map.Entry<Integer, String> entry: this.stringParameters.entrySet()){
                if(!Objects.equals(entry.getValue(), "null")){
                    try {
                        this.statement.setString(entry.getKey(),entry.getValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        if(!this.integerParameters.isEmpty()){
            for(Map.Entry<Integer, Integer> entry: this.integerParameters.entrySet()){
                if(!Objects.equals(entry.getValue(), 0)){
                    try {
                        this.statement.setInt(entry.getKey(),entry.getValue());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        Logger.getLogger(getClass().getName()).info("DELETE Query: " + this.query);
        Platform.runLater(() -> {
            Kernel.activityStringProperty.setValue(this.query.toString());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
        });
        return this;
    }

    public QueryBuilder<T> getInsertQuery() throws SQLException {
        // TODO: Parameter nicht in das SQL-Statement aufnehmen!
        this.query.append("INSERT INTO ");
        if(this.naturalCase){
            this.query.append(this.entity.getClass().getSimpleName());
        } else {
            this.query.append(this.generateSnakeTailString(this.entity.getClass().getSimpleName()));
        }
        this.query.append(" ( ").append(this.insertion).append(" ) ");
        this.query.append(" VALUES ");
        this.query.append(" ( ").append(this.insertData).append(" ) ");

        try {
            this.statement = Database.getConnection().prepareStatement(this.query.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(!this.stringParameters.isEmpty()){
            for(Map.Entry<Integer, String> entry: this.stringParameters.entrySet()){
                if(!Objects.equals(entry.getValue(), "null")){
                    this.statement.setString(entry.getKey(),entry.getValue());
                }
            }
        }

        if(!this.integerParameters.isEmpty()){
            for(Map.Entry<Integer, Integer> entry: this.integerParameters.entrySet()){
                if(!Objects.equals(entry.getValue(), 0)){
                    this.statement.setInt(entry.getKey(),entry.getValue());
                }
            }
        }

        Logger.getLogger(getClass().getName()).info("INSERT Query: " + this.query);
        Platform.runLater(() -> {
            Kernel.activityStringProperty.setValue(this.query.toString());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
        });
        return this;
    }

    public QueryBuilder<T> getQuery() throws SQLException {
        this.query.append("SELECT ");

        if(0 != this.projection.length()){
            this.query.append(this.projection);
        }

        if(this.naturalCase){
            this.query.append(" FROM ").append(this.entity.getClass().getSimpleName());
        } else {
            this.query.append(" FROM ").append(this.generateSnakeTailString(this.entity.getClass().getSimpleName()));
        }

        if(null != this.alias){
            this.query.append(" ").append(this.alias);
        }

        if(0 != this.joins.length()){
            this.query.append(this.joins);
        }

        if(0 != this.condition.length()){
            this.query.append(" WHERE ").append(this.condition);
        }

        if(0 != this.groupBy.length()){
            this.query.append(" GROUP BY ").append(this.groupBy);
        }

        if(0 != this.having.length()){
            this.query.append(" HAVING ").append(this.having);
        }

        if(0 != this.orderBy.length()){
            this.query.append(" ORDER BY ").append(this.orderBy);
        }

        try {
            this.statement = Database.getConnection().prepareStatement(this.query.toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if(!this.stringParameters.isEmpty()){
            for(Map.Entry<Integer, String> entry: this.stringParameters.entrySet()){
                if(!Objects.equals(entry.getValue(), "null")){
                    this.statement.setString(entry.getKey(),entry.getValue());
                }
            }
        }

        if(!this.integerParameters.isEmpty()){
            for(Map.Entry<Integer, Integer> entry: this.integerParameters.entrySet()){
                if(!Objects.equals(entry.getValue(), 0)){
                    this.statement.setInt(entry.getKey(),entry.getValue());
                }
            }
        }
        Logger.getLogger(getClass().getName()).info(String.valueOf(this.query));
        Platform.runLater(() -> {
            Kernel.activityStringProperty.setValue(this.query.toString());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
        });
        return this;
    }

    public void getPersistResult() {
        try {
            Boolean done = this.statement.execute();
            Logger.getLogger(getClass().getName()).info("Anzahl betroffener Tupel: " + this.statement.getUpdateCount());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            System.err.println("Fehlercode: " + e.getSQLState());
        }

    }

    public ArrayList<HashMap<String, String>> getListResult() throws SQLException {


        this.statement.executeQuery();
        ResultSet result = this.statement.getResultSet();
        ResultSetMetaData metaData = result.getMetaData();

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        System.err.println(metaData.getColumnCount() + " Spalten");
        while (result.next()) {
            if(!result.wasNull()){
                HashMap<String, String> row = new HashMap<>(metaData.getColumnCount());
                for (int i = 1; i <= metaData.getColumnCount();i++) {
                    row.put(metaData.getColumnLabel(i),result.getObject(i).toString());
                }
                list.add(row);
            }
        }
        Platform.runLater(() -> {
            Kernel.activityStringProperty.setValue(this.query.toString());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            
        });
        return list;
    }

    public T getOneOrNullResult() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {


        this.statement.setMaxRows(1);
        this.statement.executeQuery();
        ResultSet result;
        result = this.statement.getResultSet();
        ResultSetMetaData metaData = result.getMetaData();

        T object = null;

        while (result.next()) {

            object = (T) this.entity.getClass().getConstructor().newInstance();

            for (Field field : this.entity.getClass().getDeclaredFields()) {
                if (field.getModifiers() == Modifier.PROTECTED) {
                    String fieldName = "";

                    /* Hat einen Bug verursacht, da bereits durch SELECT user_role AS userRole ein camelCase besteht

                    if(!this.naturalCase){
                        fieldName += this.generateSnakeTailString(field.getName());
                    } else {
                        fieldName += field.getName();
                    }

                     */
                    fieldName += field.getName();

                    field.setAccessible(true);
                    if(field.getType().getSimpleName().equals("int")){
                        field.set(object, result.getInt(fieldName));
                    }
                    if(field.getType().getSimpleName().equals("Integer")){
                        field.set(object, result.getInt(fieldName));
                    }
                    if(field.getType().getSimpleName().equals("String")){
                        field.set(object, result.getString(fieldName));
                    }
                    if(field.getType().getSimpleName().equals("Boolean")){
                        field.set(object, (1 == result.getInt(fieldName)));
                    }
                    field.setAccessible(false);



                }
            }
        }


        return object;
    }

    public Integer getScalarResult() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        this.statement.setMaxRows(1);
        this.statement.executeQuery();
        ResultSet result = this.statement.getResultSet();

        Integer scalarResult = null;

        while (result.next()) {
            scalarResult = result.getInt(1);
        }

        return scalarResult;
    }

    public ArrayList<T> getResult() throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {


        this.statement.executeQuery();
        ResultSet result;
        result = this.statement.getResultSet();
        ArrayList<T> list = new ArrayList<>();

        T object;

        while (result.next()) {

            object = (T) this.entity.getClass().getConstructor().newInstance();

            for (Field field : this.entity.getClass().getDeclaredFields()) {
                if(field.getAnnotation(ORM.class) != null) {
                    System.out.println(field.getAnnotation(ORM.class).label());
                }
                if(field.getAnnotation(ManyToOne.class) != null) {
                    String entity = field.getAnnotation(ManyToOne.class).entity().getSimpleName();
                    String origin = field.getAnnotation(ManyToOne.class).origin();
                    String target = field.getName();
                    System.out.printf("ManyToOne aus Tabelle '%s' mit Spalte '%s' nach Attribut '%s'.%n",generateSnakeTailString(entity),origin,target);
                }
                if (field.getModifiers() == Modifier.PROTECTED || field.getModifiers() == Modifier.PUBLIC && this.publicSelect) {
                    String fieldName = "";
                    fieldName = field.getName();


                    field.setAccessible(true);
                    if(field.getType().getSimpleName().equals("int")){
                        field.set(object, result.getInt(fieldName));
                    }
                    if(field.getType().getSimpleName().equals("Integer")){
                        field.set(object, result.getInt(fieldName));
                    }
                    if(field.getType().getSimpleName().equals("String")){
                        field.set(object, result.getString(fieldName));
                    }
                    if(field.getType().getSimpleName().equals("Boolean")){
                        field.set(object, (1 == result.getInt(fieldName)));
                    }
                    field.setAccessible(false);

                }
            }
            list.add(object);
        }


        return list;
    }

}
