package edu.tk.db.model;

import edu.tk.examcalc.entity.Pupil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ManyToOne {
    Class<? extends Entity> entity();
    String origin();
}

