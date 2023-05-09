package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ru.sibsutis.pmik.hmi.interfaces.forms.MainForm;

import java.io.IOException;
import java.net.URL;

/**
 * Основное окно приложения.
 */
public class MainWindow {

    public final static String MAIN_WINDOW_TITLE =
            "Изучение правил построения интерфейсов";

    public final static String MAIN_FORM_PATH =
            "/forms/main.fxml";

    /**
     * Наполняет контейнер компонентов окна.
     *
     * @param stage Контейнер компонентов окна.
     * @param faviconLocation URL размещения иконки приложения.
     * @param mainFormLocation URL размещения fxml-файла описания основной формы.
     * @return Контроллер формы.
     * @throws IOException Если fxml-файл описания формы недоступен.
     */
    public static MainForm prepareStage(
            Stage stage,
            URL faviconLocation,
            URL mainFormLocation) throws IOException {
        FXMLLoader mainFormLoader = new FXMLLoader(mainFormLocation);
        MainForm controller = new MainForm();
        mainFormLoader.setController(controller);
        stage.setScene(new Scene(mainFormLoader.load()));
        stage.setMaximized(true);
        stage.setTitle(MAIN_WINDOW_TITLE);
        stage.getIcons().add(new Image(faviconLocation.toExternalForm()));
        controller.setMainStage(stage);
        controller.setFaviconPath(faviconLocation);
        return controller;
    }

}
