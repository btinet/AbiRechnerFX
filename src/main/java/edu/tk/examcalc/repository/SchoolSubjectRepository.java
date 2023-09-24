package edu.tk.examcalc.repository;

import edu.tk.db.model.Repository;
import edu.tk.examcalc.entity.SchoolSubject;
import edu.tk.examcalc.entity.Tutor;

public class SchoolSubjectRepository extends Repository<SchoolSubject> {

    public SchoolSubjectRepository() {
        setEntity(new SchoolSubject());
    }

}
