package ru.sibsutis.pmik.hmi.interfaces.forms;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static ru.sibsutis.pmik.hmi.interfaces.windows.MainWindowTest.*;

/**
 * Модульные тесты визуальных компонентов формы справочной информации.
 */
@Isolated
public class TheoryFormTest extends InterfacesTest {

    private HBox helpContent;

    @SuppressWarnings("unused")
    @Start
    protected void start(Stage stage) throws IOException {
        prepareMainWindow(stage);
    }

    @BeforeEach
    void setUp() throws ExecutionException, InterruptedException {
        MenuBar menuBar = (MenuBar) windowScene.lookup(MAIN_MENU_SELECTOR);
        Menu menu2 = menuBar.getMenus().get(1);
        MenuItem item22 = menu2.getItems().get(1);

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            // act
            item22.fire();
            completableFuture.complete("completed");
        });
        completableFuture.get();
        helpContent = (HBox) windowScene.lookup(HELP_CONTENT_SELECTOR);
    }

    /**
     * Проверяем, что форма содержит все необходимые элементы.
     */
    @Test
    void givenTheoryForm_whenShowed_thenHasItems() {
        // arrange

        // act
        Accordion helpMenu = (Accordion) helpContent.lookup("#helpMenu");
        VBox webViewArea = (VBox) helpContent.lookup("#webViewArea");

        // assert
        Assertions.assertNotNull(helpMenu);
        Assertions.assertNotNull(webViewArea);
    }

    /**
     * Проверяем, что по умолчанию открывается первая глава.
     */
    @Test
    @SuppressWarnings("unchecked")
    void givenTheoryForm_whenShowed_thenFirstChapterOpened() {
        // arrange
        Accordion helpMenu = (Accordion) helpContent.lookup("#helpMenu");
        VBox webViewArea = (VBox) helpContent.lookup("#webViewArea");
        String expectedSelectedItem = "Описание технологии";

        // act
        TitledPane firstPane = helpMenu.getPanes().get(0);
        ListView<String> chapters = (ListView<String>) firstPane.getContent();
        String actualSelectedItem = chapters.getSelectionModel().getSelectedItem();

        // assert
        Assertions.assertTrue(firstPane.isExpanded());
        Assertions.assertEquals(expectedSelectedItem, actualSelectedItem);
        Assertions.assertFalse(webViewArea.getChildren().isEmpty());
    }

    /**
     * Проверяем, что глава отображается, если она выбрана.
     */
    @Test
    @SuppressWarnings("unchecked")
    void givenTheoryForm_whenChapterIsSelected_thenOneShowed() {
        // arrange
        Accordion helpMenu = (Accordion) helpContent.lookup("#helpMenu");
        VBox webViewArea = (VBox) helpContent.lookup("#webViewArea");
        MainForm mainForm = (MainForm) controller;
        TheoryForm theoryForm = mainForm.getTheoryForm();
        int expectedTheme = 0;
        int expectedChapter = 1;
        TitledPane testedPane = helpMenu.getPanes().get(expectedTheme);

        Platform.runLater(() -> {
            // act
            theoryForm.openChapter(expectedTheme, expectedChapter);
            ListView<String> chapters = (ListView<String>) testedPane.getContent();
            int actualChapter = chapters.getSelectionModel().getSelectedIndex();

            // assert
            Assertions.assertTrue(testedPane.isExpanded());
            Assertions.assertEquals(expectedChapter, actualChapter);
            Assertions.assertFalse(webViewArea.getChildren().isEmpty());
        });
    }

    /**
     * Проверяем, что последняя глава открывается.
     */
    @Test
    @SuppressWarnings("unchecked")
    void givenTheoryForm_whenLastChapterIsSelected_thenOneShowed() {
        // arrange
        Accordion helpMenu = (Accordion) helpContent.lookup("#helpMenu");
        VBox webViewArea = (VBox) helpContent.lookup("#webViewArea");
        MainForm mainForm = (MainForm) controller;
        TheoryForm theoryForm = mainForm.getTheoryForm();
        int expectedChapter = 0;
        String expectedVariantName = "Вариант 1";

        Platform.runLater(() -> {
            // act
            theoryForm.openLastTheme();
            LinkedList<TitledPane> panes = new LinkedList<>(helpMenu.getPanes());
            TitledPane testedPane = panes.getLast();
            ListView<String> chapters = (ListView<String>) testedPane.getContent();
            int actualChapter = chapters.getSelectionModel().getSelectedIndex();
            String actualVariantName = testedPane.getText();

            // assert
            Assertions.assertTrue(testedPane.isExpanded());
            Assertions.assertEquals(expectedChapter, actualChapter);
            Assertions.assertFalse(webViewArea.getChildren().isEmpty());
            Assertions.assertEquals(expectedVariantName, actualVariantName);
        });
    }

}
