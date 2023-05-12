package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static ru.sibsutis.pmik.hmi.interfaces.windows.MainWindowTest.WAITING_TIMEOUT;

/**
 * Модульные тесты диалогового окна подтверждения выбора нового варианта.
 */
public class VariantChoosingQuestionDialogTest extends InterfacesTest {

    private static final String LABEL_SELECTOR = ".label";

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
    void givenDialog_whenShowed_hasCorrectMessage() throws ExecutionException, InterruptedException {
        Runnable test = () -> {
            // arrange
            String expectedText = "Данное действие приведет к закрытию анализируемой программы. \n\n" +
                    "Вы действительно желаете выбрать другой вариант?";

            // act
            Label textLabel = (Label) dialogScene.lookup(LABEL_SELECTOR);
            String actualText = textLabel.getText();

            // assert
            Assertions.assertEquals(expectedText, actualText);
        };

        runTest(test);
    }

    /**
     * Проверяем, что окно содержит корректные кнопки выбора ответа.
     */
    @Test
    void givenDialog_whenShowed_hasCorrectButtons() throws ExecutionException, InterruptedException {
        Runnable test = () -> {
            // arrange

            // act
            ObservableList<Node> buttons =
                    ((ButtonBar)dialogScene.lookup(".button-bar")).getButtons();
            Button buttonYes = (Button) buttons.get(0);
            Button buttonNo = (Button) buttons.get(1);

            // assert
            Assertions.assertNotNull(buttonNo);
            Assertions.assertTrue(buttonNo.isDefaultButton());
            Assertions.assertNotNull(buttonYes);
            Assertions.assertFalse(buttonYes.isDefaultButton());
        };

        runTest(test);
    }

    /**
     * Предварительная настройка перед каждым тестом.
     */
    private void runTest(Runnable test) throws ExecutionException, InterruptedException {
        Button variantChoice = (Button) windowScene.lookup("#variantChoice");
        Platform.runLater(variantChoice::fire);
        Stage stage = findDialogStage();
        dialogScene = Objects.requireNonNull(stage).getScene();
        test.run();
        Platform.runLater(stage::close);
    }

    private Stage findDialogStage() throws ExecutionException, InterruptedException {
        CompletableFuture<Stage> stageCompletableFuture = new CompletableFuture<>();
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Awaitility.await()
                        .atMost(Duration.ofSeconds(WAITING_TIMEOUT))
                        .until(() -> {
                            CompletableFuture<Stage> dialogCalculated = new CompletableFuture<>();
                            Platform.runLater(() -> {
                                Stage stage = (Stage) Stage.getWindows()
                                        .stream()
                                        .filter(Window::isShowing)
                                        .filter(window -> ((Stage) window).getTitle().equals("Запрос информации"))
                                        .findAny()
                                        .orElse(null);

                                // after
                                dialogCalculated.complete(stage);
                            });
                            Stage stage = dialogCalculated.get();
                            if (stage != null) {
                                stageCompletableFuture.complete(stage);
                                return true;
                            } else {
                                return false;
                            }
                        });
            } catch (Throwable ex) {
                ex.printStackTrace();
                stageCompletableFuture.complete(null);
            }
        });
        return stageCompletableFuture.get();
    }

}
