package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;
import edu.tk.examcalc.repository.UserRoleRepository;

public class User extends Entity {


    protected Integer id;
    protected Integer userRoleId;
    protected String username;
    protected String password;
    protected String email;
    protected String firstname;
    protected String lastname;

    private final UserRoleRepository roleRepository = new UserRoleRepository();

    public String toString() {
        return this.firstname + ' ' + this.lastname;
    }

    public Integer getId() {
        return id;
    }

    public void setUserRole(UserRole userRole) {
        this.userRoleId = userRole.getId();
    }

    public UserRole getUserRole() {
        return (UserRole) roleRepository.find(this.userRoleId);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
