package ru.sibsutis.pmik.hmi.interfaces;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;
import ru.sibsutis.pmik.hmi.interfaces.windows.MainWindow;
import ru.sibsutis.pmik.hmi.interfaces.windows.StartWindow;

import java.io.IOException;
import java.util.Objects;

/**
 * Базовый класс для поддержки тестирования визуальных компонентов
 * JavaFX с помощью библиотеки TestFX.
 */
@ExtendWith(ApplicationExtension.class)
public class InterfacesTest {

    protected Application applicationMock;

    protected HostServices hostServicesMock;

    protected Stage windowStage;

    protected Scene windowScene;

    protected void prepareMainWindow(Stage stage) throws IOException {
        this.windowStage = stage;
        Class<?> clazz = getClass();
        MainWindow.prepareStage(
            stage,
            Objects.requireNonNull(clazz.getResource(GUIStarter.FAVICON_PATH)),
            Objects.requireNonNull(clazz.getResource(MainWindow.MAIN_FORM_PATH))
        );
        windowScene = stage.getScene();
        stage.show();
    }

    protected void prepareStartWindow(Stage stage) throws IOException {
        this.windowStage = stage;
        Class<?> clazz = getClass();
        applicationMock = Mockito.mock(Application.class);
        hostServicesMock = Mockito.mock(HostServices.class);
        Mockito.when(applicationMock.getHostServices()).thenReturn(hostServicesMock);

        StartWindow.prepareStage(
                stage,
                Objects.requireNonNull(clazz.getResource(GUIStarter.FAVICON_PATH)),
                Objects.requireNonNull(clazz.getResource(StartWindow.START_FORM_PATH)),
                applicationMock
        );
        windowScene = stage.getScene();
        stage.show();
    }

}
