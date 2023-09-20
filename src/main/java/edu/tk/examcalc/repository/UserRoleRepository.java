package edu.tk.examcalc.repository;

import edu.tk.db.model.Repository;
import edu.tk.examcalc.entity.User;
import edu.tk.examcalc.entity.UserRole;

public class UserRoleRepository extends Repository {
    public UserRoleRepository() {
        setEntity(new UserRole());
    }

}
