package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.fxml.FXML;

/**
 * Контроллер формы программы по варианту 2.
 */
@SuppressWarnings("unused")
public class Program2Form extends AbstractProgramForm {

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected String getHeader() {
        return "";
    }

}
