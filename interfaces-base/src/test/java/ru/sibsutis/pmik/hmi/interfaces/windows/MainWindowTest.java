package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;
import ru.sibsutis.pmik.hmi.interfaces.forms.MainForm;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainWindowTest extends InterfacesTest {

    @SuppressWarnings("unused")
    @Start
    private void start(Stage stage) throws IOException {
        prepareMainWindow(stage, new MainForm());
    }

    @Test
    void givenMainWindow_whenShowed_hasCorrectTitle() {
        // arrange
        String expectedTitle = "Изучение правил построения интерфейсов";

        // act
        String actualTitle = mainWindowStage.getTitle();

        // assert
        assertEquals(expectedTitle, actualTitle);
    }

}
