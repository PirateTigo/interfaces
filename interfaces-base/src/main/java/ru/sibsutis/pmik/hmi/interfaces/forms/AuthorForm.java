package ru.sibsutis.pmik.hmi.interfaces.forms;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * Контроллер формы окна информации об авторе.
 */
public class AuthorForm {
    /**
     * Контейнер элементов формы.
     */
    @FXML
    AnchorPane authorContainer;

    /**
     * Основное текстовое поле с информацией об авторе.
     */
    @FXML
    TextArea mainText;

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    @SuppressWarnings("unused")
    private void initialize() {
        mainText.setOnMouseClicked(event ->
                // Сбрасываем фокус принудительно с текстового поля
                authorContainer.requestFocus());

        Platform.runLater(() -> {
            try {
                // Сбрасываем фокус принудительно с текстового поля
                Thread.sleep(10);
                authorContainer.requestFocus();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
