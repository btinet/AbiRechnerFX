package edu.tk.examcalc.repository;

import edu.tk.db.model.Repository;
import edu.tk.examcalc.entity.Tutor;

public class TutorRepository extends Repository<Tutor> {
    public TutorRepository() {
        setEntity(new Tutor());
    }

}
