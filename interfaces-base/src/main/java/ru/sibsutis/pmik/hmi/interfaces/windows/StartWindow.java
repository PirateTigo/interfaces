package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.sibsutis.pmik.hmi.interfaces.forms.StartForm;

import java.io.IOException;
import java.net.URL;

import static ru.sibsutis.pmik.hmi.interfaces.windows.MainWindow.MAIN_WINDOW_TITLE;

/**
 * Приветственное окно приложения.
 */
public class StartWindow {

    public final static String START_FORM_PATH =
            "/forms/start.fxml";

    private final static double START_WINDOW_WIDTH = 720;

    private final static double START_WINDOW_HEIGHT = 512;

    /**
     * Наполняет контейнер компонентов окна.
     *
     * @param stage Контейнер компонентов окна.
     * @param faviconLocation URL размещения иконки приложения.
     * @param startFormLocation URL размещения fxml-файла описания стартовой формы.
     * @param application Ссылка на приложение.
     * @throws IOException Если fxml-файл описания формы недоступен.
     */
    public static void prepareStage(
            Stage stage,
            URL faviconLocation,
            URL startFormLocation,
            Application application) throws IOException {
        FXMLLoader startFormLoader = new FXMLLoader(startFormLocation);
        StartForm controller = new StartForm();
        startFormLoader.setController(controller);
        stage.setScene(new Scene(startFormLoader.load(), START_WINDOW_WIDTH, START_WINDOW_HEIGHT));
        stage.setResizable(false);
        stage.setTitle(MAIN_WINDOW_TITLE);
        stage.getIcons().add(new Image(faviconLocation.toExternalForm()));
        controller.setMainStage(stage);
        controller.setFaviconPath(faviconLocation);
        controller.setApplication(application);
    }

}
