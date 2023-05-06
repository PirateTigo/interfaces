package ru.sibsutis.pmik.hmi.interfaces.forms;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

/**
 * Контроллер формы основного окна приложения.
 */
public class MainForm {

    /**
     * URL размещения иконки приложения.
     */
    private URL faviconPath;

    /**
     * Контейнер визуальных компонентов формы.
     */
    @FXML
    AnchorPane root;

    /**
     * Устанавливает основное окно приложения.
     */
    public void setMainStage(Stage stage) {
        // Устанавливаем обработчик закрытия окна приложения
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
    }

    /**
     * Устанавливает путь до иконки приложения.
     */
    public void setFaviconPath(URL faviconPath) {
        this.faviconPath = faviconPath;
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    @SuppressWarnings("unused")
    private void initialize() {

    }
}
