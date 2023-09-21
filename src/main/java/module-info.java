module edu.tk.fxcontrollertest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires java.sql;

    opens edu.tk.examcalc to javafx.fxml;
    opens edu.tk.examcalc.entity to javafx.base;
    exports edu.tk.examcalc;
    exports edu.tk.examcalc.controller;
    opens edu.tk.examcalc.controller to javafx.fxml;
}