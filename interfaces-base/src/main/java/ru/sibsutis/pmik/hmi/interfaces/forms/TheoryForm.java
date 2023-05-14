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
     * Родительская форма.
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
        helpContent.setPrefWidth(parentRootWidth);
        helpMenu.setPrefWidth(parentRootWidth * 0.2);
        webViewArea.setPrefWidth(parentRootWidth * 0.8);
    }

    /**
     * Устанавливает текущую высоту формы.
     *
     * @param parentRootHeight Высота родительской формы.
     */
    public void setHeight(double parentRootHeight) {
        double prefHeight = parentRootHeight
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
     * Открывает справочную информацию по последней теме
     * с 0-м номером главы.
     */
    public void openLastTheme() {
        openChapter(helpMenu.getPanes().size() - 1, 0);
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    @SuppressWarnings({"unused", "unchecked"})
    private void initialize() throws URISyntaxException, IOException {
        // Загружаем структуру меню
        HelpManager helpManager = new HelpManager();
        menuStructure = helpManager.getMenuStructure();
        List<TitledPane> panes = new LinkedList<>();
        menuStructure.keySet().forEach(theme -> {
            String[] themeParts = theme.split(" ");
            if (themeParts.length == 2) {
                if ("Вариант".equals(themeParts[0])) {
                    int variant;
                    try {
                        variant = Integer.parseInt(themeParts[1]);
                        if (variant != (parentForm.getVariant() + 1)) {
                            // Пропускаем
                            return;
                        }
                    } catch (NumberFormatException ex) {
                        // Игнорируем
                    }
                }
            }
            ListView<String> subMenu = new ListView<>();
            subMenu.getItems().addAll(menuStructure.get(theme).keySet());
            TitledPane titledPane = new TitledPane(theme, subMenu);
            panes.add(titledPane);

            // Устанавливаем обработчик выбора пункта меню
            subMenu.getSelectionModel().selectedItemProperty().addListener(
                    (observable, oldValue, newValue) -> {
                        String chapter = subMenu.getSelectionModel().getSelectedItem();
                        if (chapter != null) {
                            loadChapter(theme, chapter);
                            helpMenu.getPanes().forEach(pane -> {
                                ListView<String> currentSubMenu = (ListView<String>) pane.getContent();
                                if (subMenu != currentSubMenu) {
                                    currentSubMenu.getSelectionModel().clearSelection();
                                }
                            });
                        }
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
