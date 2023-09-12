module edu.tk.fxcontrollertest {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens edu.tk.fxcontrollertest to javafx.fxml;
    exports edu.tk.fxcontrollertest;
    exports edu.tk.fxcontrollertest.controller;
    opens edu.tk.fxcontrollertest.controller to javafx.fxml;
}