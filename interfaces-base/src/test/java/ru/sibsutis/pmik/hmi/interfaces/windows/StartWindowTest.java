package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;

import java.io.IOException;

/**
 * Модульные тесты визуальных компонентов приветственного окна приложения.
 */
public class StartWindowTest extends InterfacesTest {

    @SuppressWarnings("unused")
    @Start
    private void start(Stage stage) throws IOException {
        prepareStartWindow(stage);
    }

    /**
     * Проверяем, что окно содержит корректный заголовок.
     */
    @Test
    void givenStartWindow_whenShowed_thenHasCorrectTitle() {
        // arrange
        String expectedTitle = "Изучение правил построения интерфейсов";

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
        int expectedWidth = 720;
        int expectedHeight = 512;

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
        ImageView startImageView = (ImageView) windowScene.lookup("#startImageView");
        Label header = (Label) windowScene.lookup("#header");
        Label message = (Label) windowScene.lookup("#message");
        TextField code = (TextField) windowScene.lookup("#code");
        ImageView universityImageView = (ImageView) windowScene.lookup("#universityImageView");

        // assert
        Assertions.assertNotNull(startImageView);
        Assertions.assertNotNull(header);
        Assertions.assertNotNull(message);
        Assertions.assertNotNull(code);
        Assertions.assertNotNull(universityImageView);
    }

    /**
     * Проверяем корректность размеров основного изображения.
     */
    @Test
    void givenStartWindow_whenStartImageViewShowed_thenHasCorrectSizes() {
        // arrange
        ImageView startImageView = (ImageView) windowScene.lookup("#startImageView");
        int expectedWidth = 300;
        int expectedHeight = 520;

        // act
        int actualWidth = (int)startImageView.getFitWidth();
        int actualHeight = (int)startImageView.getFitHeight();

        // assert
        Assertions.assertEquals(expectedWidth, actualWidth);
        Assertions.assertEquals(expectedHeight, actualHeight);
    }

    /**
     * Проверяем корректность текста заголовка.
     */
    @Test
    void givenStartWindow_whenHeaderShowed_thenHasCorrectText() {
        // arrange
        Label header = (Label) windowScene.lookup("#header");
        String expectedText = "Добро пожаловать!";

        // act
        String actualText = header.getText();

        // assert
        Assertions.assertEquals(expectedText, actualText);
    }

    /**
     * Проверяем корректность текста сообщения пользователю.
     */
    @Test
    void givenStartWindow_whenMessageShowed_thenHasCorrectText() {
        // arrange
        Label message = (Label) windowScene.lookup("#message");
        String expectedText = "Введите код для начала работы";

        // act
        String actualText = message.getText();

        // assert
        Assertions.assertEquals(expectedText, actualText);
    }

    /**
     * Проверяем корректность текста плейсхолдера поля ввода кода.
     */
    @Test
    void givenStartWindow_whenCodeShowed_thenHasCorrectText() {
        // arrange
        TextField code = (TextField) windowScene.lookup("#code");
        String expectedText = "Код";

        // act
        String actualText = code.getPromptText();

        // assert
        Assertions.assertEquals(expectedText, actualText);
    }

    /**
     * Проверяем корректность размеров иконки университета.
     */
    @Test
    void givenStartWindow_whenUniversityImageViewShowed_thenHasCorrectSizes() {
        // arrange
        ImageView universityImageView = (ImageView) windowScene.lookup("#universityImageView");
        int expectedWidth = 70;
        int expectedHeight = 70;

        // act
        int actualWidth = (int)universityImageView.getFitWidth();
        int actualHeight = (int)universityImageView.getFitHeight();

        // assert
        Assertions.assertEquals(expectedWidth, actualWidth);
        Assertions.assertEquals(expectedHeight, actualHeight);
    }

    /**
     * Проверяем корректность размеров иконки университета.
     */
    @Test
    void givenStartWindow_whenUniversityImageViewClicked_thenBrowserOpened() {
        // arrange
        ImageView universityImageView = (ImageView) windowScene.lookup("#universityImageView");

        // act
        Executable underTest = () ->
            Event.fireEvent(
                    universityImageView,
                    new MouseEvent(
                            MouseEvent.MOUSE_CLICKED,
                            0,
                            0,
                            0,
                            0,
                            MouseButton.PRIMARY,
                            1,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            true,
                            null
                    )
            );

        // assert
        Assertions.assertThrows(IllegalStateException.class, underTest);
        Mockito.verify(applicationMock).getHostServices();
        Mockito.verify(hostServicesMock).showDocument(Mockito.eq("https://sibsutis.ru"));
    }

}
