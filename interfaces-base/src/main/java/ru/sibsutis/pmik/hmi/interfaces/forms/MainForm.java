package ru.sibsutis.pmik.hmi.interfaces.forms;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
     * Номер варианта.
     */
    private int variant;

    /**
     * Контейнер визуальных компонентов формы.
     */
    @FXML
    AnchorPane root;

    /**
     * Номер варианта.
     */
    @FXML
    Label variantLabel;

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
     * Устанавливает номер варианта.
     *
     * @param variant Номер варианта.
     */
    public void setVariant(int variant) {
        this.variant = variant;
        variantLabel.setText(String.valueOf(variant));
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    @SuppressWarnings("unused")
    private void initialize() {

    }

}
