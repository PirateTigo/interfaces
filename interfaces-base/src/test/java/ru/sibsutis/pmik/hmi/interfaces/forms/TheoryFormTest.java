package ru.sibsutis.pmik.hmi.interfaces.forms;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static ru.sibsutis.pmik.hmi.interfaces.windows.MainWindowTest.*;

/**
 * Модульные тесты визуальных компонентов формы справочной информации.
 */
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
        MenuItem item21 = menu2.getItems().get(0);

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            // act
            item21.fire();
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
        String expectedSelectedItem = "Глава1";

        // act
        TitledPane firstPane = helpMenu.getPanes().get(0);
        ListView<String> chapters = (ListView<String>) firstPane.getContent();
        String actualSelectedItem = chapters.getSelectionModel().getSelectedItem();

        // assert
        Assertions.assertTrue(firstPane.isExpanded());
        Assertions.assertEquals(expectedSelectedItem, actualSelectedItem);
        Assertions.assertFalse(webViewArea.getChildren().isEmpty());
    }

    @Test
    @SuppressWarnings("unchecked")
    void givenTheoryForm_whenChapterIsSelected_thenOneShowed() {
        // arrange
        Accordion helpMenu = (Accordion) helpContent.lookup("#helpMenu");
        VBox webViewArea = (VBox) helpContent.lookup("#webViewArea");
        MainForm mainForm = (MainForm) controller;
        TheoryForm theoryForm = mainForm.getTheoryForm();
        int expectedTheme = 1;
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

}
