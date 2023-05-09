package ru.sibsutis.pmik.hmi.interfaces;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import ru.sibsutis.pmik.hmi.interfaces.windows.StartWindow;

import java.io.IOException;
import java.net.URL;

/**
 * JavaFX приложение "Изучение правил построения интерфейсов".
 */
public class GUIStarter extends Application {

    public static final String FAVICON_PATH = "/icons/interfaces.png";

    /**
     * Точка входа в JavaFX приложение.
     *
     * @param stage Контейнер компонентов.
     */
    @Override
    public void start(Stage stage) {
        Class<? extends GUIStarter> clazz = getClass();
        URL faviconPath = clazz.getResource(FAVICON_PATH);
        URL formPath = clazz.getResource(StartWindow.START_FORM_PATH);

        if (faviconPath == null) {
            Platform.exit();
            return;
        }

        if (formPath == null) {
            Platform.exit();
            return;
        }

        try {
            StartWindow.prepareStage(stage, faviconPath, formPath, this);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Точка входа в Java приложение.
     * <p>
     * Необходима для запуска из ОС.
     *
     * @param args Параметры приложения.
     */
    public static void main(String[] args) {
        launch(args);
    }

}
