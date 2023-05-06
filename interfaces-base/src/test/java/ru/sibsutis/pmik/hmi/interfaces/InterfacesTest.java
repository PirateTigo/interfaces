package ru.sibsutis.pmik.hmi.interfaces;

import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import ru.sibsutis.pmik.hmi.interfaces.windows.MainWindow;

import java.io.IOException;
import java.util.Objects;

/**
 * Базовый класс для поддержки тестирования визуальных компонентов
 * JavaFX с помощью библиотеки TestFX.
 */
@ExtendWith(ApplicationExtension.class)
public class InterfacesTest {

    protected Stage mainWindowStage;
    protected Scene mainWindowScene;

    protected void prepareMainWindow(Stage stage, Object controller) throws IOException {
        this.mainWindowStage = stage;
        Class<?> clazz = getClass();
        MainWindow.prepareStage(
            stage,
            Objects.requireNonNull(clazz.getResource(GUIStarter.FAVICON_PATH)),
            Objects.requireNonNull(clazz.getResource(MainWindow.MAIN_FORM_PATH)),
            controller
        );
        mainWindowScene = stage.getScene();
        stage.show();
    }

}
