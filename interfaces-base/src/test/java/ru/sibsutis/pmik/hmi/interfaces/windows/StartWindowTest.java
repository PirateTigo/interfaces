package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;
import org.mockito.Mockito;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;
import ru.sibsutis.pmik.hmi.interfaces.forms.StartForm;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Модульные тесты визуальных компонентов приветственного окна приложения.
 */
@Isolated
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
        ImageView startImageView =
                (ImageView) windowScene.lookup("#startImageView");
        Label header = (Label) windowScene.lookup("#header");
        Label message = (Label) windowScene.lookup("#message");
        TextField code = (TextField) windowScene.lookup("#code");
        Label error = (Label) windowScene.lookup("#error");
        Label tip = (Label) windowScene.lookup("#tip");
        ImageView universityImageView =
                (ImageView) windowScene.lookup("#universityImageView");

        // assert
        Assertions.assertNotNull(startImageView);
        Assertions.assertNotNull(header);
        Assertions.assertNotNull(message);
        Assertions.assertNotNull(code);
        Assertions.assertNotNull(error);
        Assertions.assertNotNull(tip);
        Assertions.assertNotNull(universityImageView);
    }

    /**
     * Проверяем корректность размеров основного изображения.
     */
    @Test
    void givenStartWindow_whenStartImageViewShowed_thenHasCorrectSizes() {
        // arrange
        ImageView startImageView =
                (ImageView) windowScene.lookup("#startImageView");
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
        String expectedMessageText = "Введите код для начала анализа программы";
        Label tip = (Label) windowScene.lookup("#tip");
        String expectedTipText = "Укажите числовой код и нажмите <Enter>";

        // act
        String actualMessageText = message.getText();
        String actualTipText = tip.getText();

        // assert
        Assertions.assertEquals(expectedMessageText, actualMessageText);
        Assertions.assertEquals(expectedTipText, actualTipText);
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
        ImageView universityImageView =
                (ImageView) windowScene.lookup("#universityImageView");
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
     * Проверяем, что браузер открывается при нажатии на кнопку университета.
     */
    @Test
    void givenStartWindow_whenUniversityImageViewClicked_thenBrowserOpened() throws ExecutionException, InterruptedException {
        // arrange
        ImageView universityImageView = (ImageView) windowScene.lookup("#universityImageView");

        // act
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
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
            completableFuture.complete("completed");
        });
        completableFuture.get();

        // assert
        Mockito.verify(applicationMock).getHostServices();
        Mockito.verify(hostServicesMock).showDocument(Mockito.eq("https://sibsutis.ru"));
    }

    /**
     * Проверяем корректность сообщения об ошибке при некорректном вводе.
     */
    @Test
    void givenStartWindow_whenCodeIsIncorrect_errorMsgShowed() {
        // arrange
        TextField code = (TextField) windowScene.lookup("#code");
        Label error = (Label) windowScene.lookup("#error");
        String expectedText = "Введите целое неотрицательное число";

        // act
        code.setText("a");
        Platform.runLater(() -> {
            code.fireEvent(new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    "",
                    "",
                    KeyCode.ENTER,
                    true,
                    true,
                    true,
                    true
            ));
            String actualText = error.getText();

            // assert
            Assertions.assertEquals(expectedText, actualText);
        });
    }

    /**
     * Проверяем, что приветственное окно закрывается после корректного ввода кода.
     */
    @Test
    void givenStartWindow_whenCodeIsCorrect_startFormIsHidden() {
        // arrange
        TextField code = (TextField) windowScene.lookup("#code");

        // act
        code.setText("17");
        Platform.runLater(() -> {
            code.fireEvent(new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    "",
                    "",
                    KeyCode.ENTER,
                    true,
                    true,
                    true,
                    true
            ));

            // assert
            Assertions.assertFalse(windowStage.isShowing());
        });
    }

    /**
     * Проверяем, что основное окно приложения открывается после корректного ввода кода.
     */
    @Test
    void givenStartWindow_whenCodeIsCorrect_mainFormIsOpened() {
        // arrange
        TextField code = (TextField) windowScene.lookup("#code");

        // act
        code.setText("17");
        Platform.runLater(() -> {
            code.fireEvent(new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    "",
                    "",
                    KeyCode.ENTER,
                    true,
                    true,
                    true,
                    true
            ));
            Stage mainStage = ((StartForm) controller).getMainStage();

            // assert
            Assertions.assertTrue(mainStage.isShowing());
        });
    }

    /**
     * Проверяем корректность вычисления номера варианта по введенному коду.
     */
    @Test
    void givenStartWindow_whenCodeIsCorrect_variantIsCorrect() {
        // arrange
        TextField code = (TextField) windowScene.lookup("#code");
        int expectedVariant = 7;

        // act
        code.setText("17");
        Platform.runLater(() -> {
            code.fireEvent(new KeyEvent(
                    KeyEvent.KEY_PRESSED,
                    "",
                    "",
                    KeyCode.ENTER,
                    true,
                    true,
                    true,
                    true
            ));
            Stage mainStage = ((StartForm) controller).getMainStage();
            Label variantLabel = (Label) mainStage.getScene().lookup("#variantLabel");
            String variantText = variantLabel.getText();
            String[] variantTextParts = variantText.split(" ");
            int actualVariant = Integer.parseInt(variantTextParts[1]) - 1;

            // assert
            Assertions.assertEquals(expectedVariant, actualVariant);
        });
    }

}
