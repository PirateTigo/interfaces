package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;

import java.io.IOException;

/**
 * Модульные тесты визуальных компонентов окна "Об авторе".
 */
@Isolated
public class AuthorWindowTest extends InterfacesTest {

    @SuppressWarnings("unused")
    @Start
    private void start(Stage stage) throws IOException {
        prepareAuthorWindow(stage);
    }

    /**
     * Проверяем, что окно содержит корректный заголовок.
     */
    @Test
    void givenStartWindow_whenShowed_thenHasCorrectTitle() {
        // arrange
        String expectedTitle = "Информация об авторе";

        // act
        String actualTitle = windowStage.getTitle();

        // assert
        Assertions.assertEquals(expectedTitle, actualTitle);
    }

    /**
     * Проверяем, что окно неизменяемого размера.
     */
    @Test
    void givenStartWindow_whenShowed_thenIsNotResizable() {
        // arrange

        // act
        boolean isResizable = windowStage.isResizable();

        // assert
        Assertions.assertFalse(isResizable);
    }

    /**
     * Проверяем корректность размеров окна.
     */
    @Test
    void givenStartWindow_whenShowed_thenHasCorrectSizes() {
        // arrange
        int expectedWidth = 600;
        int expectedHeight = 300;

        // act
        int actualWidth = (int)windowStage.getScene().getWidth();
        int actualHeight = (int)windowStage.getScene().getHeight();

        // assert
        Assertions.assertEquals(expectedWidth, actualWidth);
        Assertions.assertEquals(expectedHeight, actualHeight);
    }

    /**
     * Проверяем наличие в окне всех визуальных компонентов.
     */
    @Test
    void givenStartWindow_whenShowed_thenHasItems() {
        // arrange

        // act
        AnchorPane authorContainer = (AnchorPane) windowScene.lookup("#authorContainer");
        TextArea mainText = (TextArea) windowScene.lookup("#mainText");

        // assert
        Assertions.assertNotNull(authorContainer);
        Assertions.assertNotNull(mainText);
    }

}
