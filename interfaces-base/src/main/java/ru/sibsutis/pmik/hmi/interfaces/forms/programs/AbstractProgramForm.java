package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ru.sibsutis.pmik.hmi.interfaces.forms.MainForm;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Objects;

/**
 * Контроллер анализируемой программы.
 */
public abstract class AbstractProgramForm {

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
    VBox programContent;

    /**
     * Вариант программы.
     */
    @FXML
    Label variantLabel;

    /**
     * Заголовок программы.
     */
    @FXML
    Label header;

    /**
     * Загружает форму анализируемой программы по варианту.
     *
     * @param variant Вариант программы.
     * @return Описатель формы.
     */
    public static ProgramFormDescriptor load(int variant, MainForm mainForm) {
        int variantNumber = variant + 1;
        URL formLocation = Objects.requireNonNull(
                AbstractProgramForm.class.getResource(
                        String.format(PROGRAM_FORM_PATH_TEMPLATE, variantNumber)
        ));
        FXMLLoader formLoader = new FXMLLoader(formLocation);

        try {
            Class<?> programFormClass = Class.forName(
                    String.format(PROGRAM_CLASS_TEMPLATE, variantNumber)
            );
            AbstractProgramForm programForm =
                    (AbstractProgramForm) programFormClass.getConstructor().newInstance();
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
        Insets variantLabelInsets = new Insets(
                20,
                20,
                20,
                parentRootWidth - 150
        );
        variantLabel.setPadding(variantLabelInsets);
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

    /**
     * Метод обязательно должен быть вызван из одноименного метода,
     * аннотированного аннотацией {@link FXML @FXML}, дочернего класса.
     */
    protected void initialize() {
        // Устанавливаем метку варианта
        setFont(variantLabel, 16, false);

        // Устанавливаем заголовок программы
        header.setText(getHeader());
        setFont(header, 32, false);
    }

    /**
     * Возвращает название заголовка программы.
     */
    protected abstract String getHeader();

    /**
     * Устанавливает шрифт для элемента управления.
     *
     * @param node Элемент управления.
     * @param size Размер шрифта.
     * @param bold Признак установки жирного начертания.
     */
    protected <T> void setFont(T node, int size, boolean bold) {
        Class<?> clazz = getClass();
        URL fontPath = Objects.requireNonNull(
                clazz.getResource(bold
                        ? MainForm.FONT_BOLD_PATH
                        : MainForm.FONT_REGULAR_PATH)
        );
        Font font = Font.loadFont(fontPath.toExternalForm(), size);
        try {
            Method setFont = node.getClass()
                    .getMethod("setFont", Font.class);
            setFont.invoke(node, font);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            ex.printStackTrace();
            // Завершаем работу метода
        }
    }

}
