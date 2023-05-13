package ru.sibsutis.pmik.hmi.interfaces.forms;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import ru.sibsutis.pmik.hmi.interfaces.help.HelpManager;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Контроллер формы справочной информации.
 */
public class TheoryForm {

    private static final String BASE_HELP_DIRECTORY = "/help";

    /**
     * Форма внешнего окна приложения.
     */
    private MainForm parentForm;

    /**
     * Структура элементов меню.
     */
    private Map<String, Map<String, String>> menuStructure;

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
     * Область просмотра веб-содержимого.
     */
    @FXML
    VBox webViewArea;

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
        webViewArea.setPrefWidth(width * 0.8);
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
     * Открывает справочную информацию по теме с номером
     * {@code theme} и главе с номером {@code chapter}.
     * <p>
     * Нумерация начинается с 0.
     *
     * @param theme Номер темы.
     * @param chapter Номер главы.
     */
    @SuppressWarnings("unchecked")
    public void openChapter(int theme, int chapter) {
        TitledPane expandedPane = helpMenu.getPanes().get(theme);
        helpMenu.setExpandedPane(expandedPane);
        ListView<String> chapters = (ListView<String>) expandedPane.getContent();
        chapters.getSelectionModel().select(chapter);
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    @SuppressWarnings("unused")
    private void initialize() throws URISyntaxException, IOException {
        // Загружаем структуру меню
        HelpManager helpManager = new HelpManager();
        menuStructure = helpManager.getMenuStructure();
        List<TitledPane> panes = new LinkedList<>();
        menuStructure.keySet().forEach(theme -> {
            ListView<String> subMenu = new ListView<>();
            subMenu.getItems().addAll(menuStructure.get(theme).keySet());
            TitledPane titledPane = new TitledPane(theme, subMenu);
            panes.add(titledPane);

            // Устанавливаем обработчик выбора пункта меню
            subMenu.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        String chapter = subMenu.getSelectionModel().getSelectedItem();
                        loadChapter(theme, chapter);
                    });
        });
        helpMenu.getPanes().addAll(panes);
    }

    /**
     * Выполняет загрузку описания главы справочной информации.
     *
     * @param theme Тема справочной информации.
     * @param chapter Глава справочной информации.
     */
    private void loadChapter(String theme, String chapter) {
        String relativeChapterPath = BASE_HELP_DIRECTORY + "/" + menuStructure.get(theme).get(chapter);
        Class<?> clazz = getClass();
        URL absoluteChapterURL = Objects.requireNonNull(clazz.getResource(relativeChapterPath));
        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        webEngine.load(absoluteChapterURL.toString());
        webViewArea.getChildren().clear();
        webViewArea.getChildren().add(browser);
    }

}
