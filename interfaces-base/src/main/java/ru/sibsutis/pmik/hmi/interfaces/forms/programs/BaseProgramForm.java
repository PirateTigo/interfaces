package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import ru.sibsutis.pmik.hmi.interfaces.forms.MainForm;

import java.net.URL;
import java.util.Objects;

/**
 * Контроллер анализируемой программы.
 */
public class BaseProgramForm {

    private static final String PROGRAM_FORM_PATH_TEMPLATE =
            "/forms/programs/program%s.fxml";

    private static final String PROGRAM_CLASS_TEMPLATE =
            "ru.sibsutis.pmik.hmi.interfaces.forms.programs.Program%sForm";

    /**
     * Родительская форма.
     */
    private MainForm parentForm;

    /**
     * Содержимое формы справочной информации.
     */
    @FXML
    HBox programContent;

    /**
     * Вариант программы.
     */
    @FXML
    Label variantLabel;

    /**
     * Загружает форму анализируемой программы по варианту.
     *
     * @param variant Вариант программы.
     * @return Описатель формы.
     */
    public static ProgramFormDescriptor load(int variant, MainForm mainForm) {
        int variantNumber = variant + 1;
        URL formLocation = Objects.requireNonNull(
                BaseProgramForm.class.getResource(
                        String.format(PROGRAM_FORM_PATH_TEMPLATE, variantNumber)
        ));
        FXMLLoader formLoader = new FXMLLoader(formLocation);

        try {
            Class<?> programFormClass = Class.forName(
                    String.format(PROGRAM_CLASS_TEMPLATE, variantNumber)
            );
            BaseProgramForm programForm =
                    (BaseProgramForm) programFormClass.getConstructor().newInstance();
            programForm.setParentForm(mainForm);
            formLoader.setController(programForm);
            AnchorPane programPane = formLoader.load();
            return new ProgramFormDescriptor(
                    programForm,
                    programPane.getChildren()
            );
        } catch (Throwable ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Устанавливает родительскую форму.
     *
     * @param parentForm Родительская форма.
     */
    public void setParentForm(MainForm parentForm) {
        this.parentForm = parentForm;
    }

    /**
     * Устанавливает текущую ширину формы.
     *
     * @param parentRootWidth Ширина родительской формы.
     */
    public void setWidth(double parentRootWidth) {
        programContent.setPrefWidth(parentRootWidth);
    }

    /**
     * Устанавливает текущую высоту формы.
     *
     * @param parentRootHeight Высота родительской формы.
     */
    public void setHeight(double parentRootHeight) {
        double prefHeight = parentRootHeight
                - parentForm.getMainMenu().getHeight()
                - parentForm.getButtons().getHeight();
        programContent.setPrefHeight(prefHeight);
    }

}
