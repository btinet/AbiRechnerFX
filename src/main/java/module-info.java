module edu.tk.fxcontrollertest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.jfxtras.styles.jmetro;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires io;
    requires kernel;
    requires layout;

    opens edu.tk.examcalc to javafx.fxml;
    opens edu.tk.examcalc.entity to javafx.base;
    opens edu.tk.examcalc.component to javafx.base;
    exports edu.tk.examcalc;
    exports edu.tk.examcalc.controller;
    exports edu.tk.examcalc.entity;
    exports edu.tk.examcalc.component;
    opens edu.tk.examcalc.controller to javafx.fxml;
}