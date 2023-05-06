module interfaces {
    requires javafx.controls;
    requires javafx.fxml;
    exports ru.sibsutis.pmik.hmi.interfaces;
    opens ru.sibsutis.pmik.hmi.interfaces.forms to javafx.fxml;
}