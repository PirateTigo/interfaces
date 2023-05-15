package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.parallel.Isolated;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;
import ru.sibsutis.pmik.hmi.interfaces.forms.StartForm;

import java.io.IOException;

/**
 * Модульные тесты общих визуальных компонентов форм анализируемых
 * программ.
 */
@Isolated
public class AbstractProgramFormTest extends InterfacesTest {

    @SuppressWarnings("unused")
    @Start
    private void start(Stage stage) throws IOException {
        prepareStartWindow(stage);
    }

    /**
     * Проверяем, что открывается корректный вариант программы, если он был указан.
     *
     * @param variant Вариант программы.
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14})
    void givenBaseProgramForm_thenVariantChosen_thenCorrectFormShowed(int variant) {
        // arrange
        TextField code = (TextField) windowScene.lookup("#code");

        // act
        code.setText(String.valueOf(variant));
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
            Scene mainScene = mainStage.getScene();
            Label variantLabel = (Label) mainScene.lookup("#variantLabel");
            String variantText = variantLabel.getText();
            String[] variantTextParts = variantText.split(" ");
            int actualVariant = Integer.parseInt(variantTextParts[1]) - 1;
            VBox programContent = (VBox) mainScene.lookup("#programContent");

            // assert
            Assertions.assertEquals(variant, actualVariant);
            Assertions.assertNotNull(programContent);
        });
    }

}
