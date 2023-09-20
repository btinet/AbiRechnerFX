package edu.tk.examcalc.repository;

import edu.tk.db.model.Repository;
import edu.tk.examcalc.entity.Pupil;

public class PupilRepository extends Repository {
    public PupilRepository(Boolean naturalCase) {
        super(naturalCase);
        setEntity(new Pupil());
    }
}
