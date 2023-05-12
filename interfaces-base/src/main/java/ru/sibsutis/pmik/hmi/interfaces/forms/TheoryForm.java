package ru.sibsutis.pmik.hmi.interfaces.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import ru.sibsutis.pmik.hmi.interfaces.help.HelpManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Контроллер формы справочной информации.
 */
public class TheoryForm {

    /**
     * Форма внешнего окна приложения.
     */
    private MainForm parentForm;

    /**
     * Содержимое формы справочной информации.
     */
    @FXML
    HBox helpContent;

    /**
     * Меню справочной информации.
     */
    @FXML
    Accordion helpMenu;

    /**
     * Устанавливает форму внешнего окна.
     *
     * @param parentForm Внешнее окно.
     */
    public void setParentForm(MainForm parentForm) {
        this.parentForm = parentForm;
    }

    /**
     * Устанавливает текущую ширину внешнего окна.
     *
     * @param width Ширина.
     */
    public void setParentRootWidth(double width) {
        helpContent.setPrefWidth(width);
        helpMenu.setPrefWidth(width * 0.2);
    }

    /**
     * Устанавливает текущую высоту внешнего окна.
     *
     * @param height Высота.
     */
    public void setParentRootHeight(double height) {
        double prefHeight = height
                - parentForm.mainMenu.getHeight()
                - parentForm.buttons.getHeight();
        helpContent.setPrefHeight(prefHeight);
        helpMenu.setPrefHeight(prefHeight);
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    @SuppressWarnings("unused")
    private void initialize() throws URISyntaxException, IOException {
        HelpManager helpManager = new HelpManager();
        Map<String, Map<String, String>> menuStructure = helpManager.getMenuStructure();
        List<TitledPane> panes = new LinkedList<>();
        menuStructure.keySet().forEach(key -> {
            ListView<String> subMenu = new ListView<>();
            subMenu.getItems().addAll(menuStructure.get(key).keySet());
            TitledPane titledPane = new TitledPane(key, subMenu);
            panes.add(titledPane);
        });
        helpMenu.getPanes().addAll(panes);
    }

}
