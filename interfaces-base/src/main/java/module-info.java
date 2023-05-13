module interfaces {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    exports ru.sibsutis.pmik.hmi.interfaces;
    opens ru.sibsutis.pmik.hmi.interfaces.forms to javafx.fxml;
}