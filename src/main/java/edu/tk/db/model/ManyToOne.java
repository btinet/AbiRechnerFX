package edu.tk.db.model;

import edu.tk.examcalc.entity.Pupil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ManyToOne {
    Class<? extends Entity> entity();
    String origin();
}

