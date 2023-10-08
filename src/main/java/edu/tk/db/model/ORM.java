package edu.tk.db.model;

import edu.tk.examcalc.entity.Tutor;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ORM {
    String label();

}

