package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.scene.Node;

import java.util.List;

/**
 * Описатель формы.
 */
public class ProgramFormDescriptor {

    /**
     * Контроллер формы.
     */
    final private AbstractProgramForm programForm;

    /**
     * Содержимое формы.
     */
    final private List<Node> content;

    /**
     * Конструктор экземпляра дескриптора.
     *
     * @param programForm Контроллер формы.
     * @param content Содержимое формы.
     */
    public ProgramFormDescriptor(AbstractProgramForm programForm, List<Node> content) {
        this.programForm = programForm;
        this.content = content;
    }

    /**
     * Получает контроллер формы.
     */
    public AbstractProgramForm getProgramForm() {
        return programForm;
    }

    /**
     * Получает содержимое формы.
     */
    public List<Node> getContent() {
        return content;
    }

}
