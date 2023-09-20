package edu.tk.examcalc.repository;

import edu.tk.db.model.Repository;
import edu.tk.examcalc.entity.User;

public class UserRepository extends Repository {
    public UserRepository(Boolean naturalCase) {
        super(naturalCase);
        setEntity(new User());
    }

}
