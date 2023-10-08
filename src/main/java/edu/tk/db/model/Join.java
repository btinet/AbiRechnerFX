package edu.tk.db.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Join {
    Class<? extends Entity> entity();
    String origin();
}

