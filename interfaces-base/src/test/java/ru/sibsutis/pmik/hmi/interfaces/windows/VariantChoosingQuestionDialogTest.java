package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;

import java.io.IOException;
import java.util.Objects;

/**
 * Модульные тесты диалогового окна подтверждения выбора нового варианта.
 */
public class VariantChoosingQuestionDialogTest extends InterfacesTest {

    private Scene dialogScene;

    @SuppressWarnings("unused")
    @Start
    protected void start(Stage stage) throws IOException {
        prepareMainWindow(stage);
    }

    /**
     * Проверяем, что окно содержит корректный текст вопроса.
     */
    @Test
    void givenDialog_whenShowed_hasCorrectMessage() {
        Runnable test = () -> {
            // arrange

            // act
            Node result = dialogScene.lookup(
                    "Вы действительно желаете выбрать другой вариант?"
            );

            // assert
            Assertions.assertNotNull(result);
        };

        runTest(test);
    }

    /**
     * Проверяем, что окно содержит корректные кнопки выбора ответа.
     */
    @Test
    void givenDialog_whenShowed_hasCorrectButtons() {
        Runnable test = () -> {
            // arrange

            // act
            Button buttonYes = (Button) dialogScene.lookup("Да");
            Button buttonNo = (Button) dialogScene.lookup("Нет");

            // assert
            Assertions.assertNotNull(buttonNo);
            Assertions.assertTrue(buttonNo.isDefaultButton());
            Assertions.assertNotNull(buttonYes);
            Assertions.assertFalse(buttonYes.isDefaultButton());
        };

        runTest(test);
    }

    /**
     * Проверяем, что после нажатия кнопки "Нет" окно закрывается.
     */
    @Test
    void givenDialog_whenButtonNoPressed_dialogIsClosed() {
        Runnable test = () -> {
            // arrange
            Button noButton = (Button) dialogScene.lookup("Нет");

            // act
            noButton.fire();
            Stage dialogStage = findDialogStage();

            // assert
            Assertions.assertNull(dialogStage);
        };

        runTest(test);
    }

    /**
     * Проверяем, что при нажатии на кнопку "Да" закрываются оба окна.
     */
    @Test
    void givenDialog_whenButtonYesPressed_bothWindowsAreClosed() {
        Runnable test = () -> {
            // arrange
            Button yesButton = (Button) dialogScene.lookup("Да");

            // act
            yesButton.fire();
            Stage stage = (Stage) Stage.getWindows()
                    .stream()
                    .filter(Window::isShowing)
                    .findAny()
                    .orElse(null);

            // assert
            Assertions.assertNull(stage);
        };

        runTest(test);
    }

    /**
     * Предварительная настройка перед каждым тестом.
     */
    private void runTest(Runnable test) {
        Button variantChoice = (Button) windowScene.lookup("#variantChoice");
        Platform.runLater(() -> {
            variantChoice.fire();
            Stage stage = findDialogStage();
            dialogScene = Objects.requireNonNull(stage).getScene();
            test.run();
        });

    }

    private Stage findDialogStage() {
        return (Stage) Stage.getWindows()
                .stream()
                .filter(Window::isShowing)
                .filter(window -> ((Stage) window).getTitle().equals("Запрос информации"))
                .findAny()
                .orElse(null);
    }

}
