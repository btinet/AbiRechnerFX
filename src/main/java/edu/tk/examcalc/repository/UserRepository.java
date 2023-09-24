package edu.tk.examcalc.repository;

import edu.tk.db.model.Repository;
import edu.tk.examcalc.entity.User;

public class UserRepository extends Repository<User> {
    public UserRepository() {
        setEntity(new User());
    }

}
