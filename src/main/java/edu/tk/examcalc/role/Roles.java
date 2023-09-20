package edu.tk.examcalc.role;

public enum Roles {

    ADM("ADM"),
    USR("USR");

    private final String roleLabel;

    Roles(String roleLabel) {
        this.roleLabel = roleLabel;
    }

    public String toString() {
        return roleLabel;
    }

}
