package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Модульные тесты визуальных компонентов основного окна приложения.
 */
public class MainWindowTest extends InterfacesTest {

    @SuppressWarnings("unused")
    @Start
    private void start(Stage stage) throws IOException {
        prepareMainWindow(stage);
    }

    /**
     * Проверяем, что окно содержит корректный заголовок.
     */
    @Test
    void givenMainWindow_whenShowed_hasCorrectTitle() {
        // arrange
        String expectedTitle = "Изучение правил построения интерфейсов";

        // act
        String actualTitle = windowStage.getTitle();

        // assert
        assertEquals(expectedTitle, actualTitle);
    }

}
