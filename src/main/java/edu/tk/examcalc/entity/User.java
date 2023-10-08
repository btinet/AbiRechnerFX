package edu.tk.examcalc.entity;

import edu.tk.db.model.Entity;
import edu.tk.db.model.ManyToOne;
import edu.tk.db.model.ORM;
import edu.tk.examcalc.repository.UserRoleRepository;

public class User extends Entity {


    @ORM
    protected Integer id;
    @ManyToOne(entity=UserRole.class, origin = "id")
    protected Integer userRoleId;
    @ORM
    protected String username;
    @ORM
    protected String password;
    @ORM
    protected String email;
    @ORM
    protected String firstname;
    @ORM
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
