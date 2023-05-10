package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

/**
 * Диалоговое окно запроса информации.
 */
public class QuestionDialog extends Dialog<Boolean> {

    private final static String QUESTION_WINDOW_TITLE = "Запрос информации";

    private final static String QUESTION_IMAGE_PATH = "/images/question.png";

    /**
     * Конструктор экземпляра окна.
     *
     * @param questionMessage Вопрос к пользователю.
     */
    public QuestionDialog(String questionMessage, URL faviconPath) {
        setTitle(QUESTION_WINDOW_TITLE);
        setHeaderText(questionMessage);
        Class<?> clazz = getClass();
        URL questionImagePath = Objects.requireNonNull(clazz.getResource(QUESTION_IMAGE_PATH));
        ImageView questionImageView = new ImageView(questionImagePath.toString());
        questionImageView.setPreserveRatio(true);
        questionImageView.setFitWidth(70);
        setGraphic(questionImageView);
        ButtonType yesButtonType = new ButtonType("Да", ButtonBar.ButtonData.YES);
        ButtonType noButtonType = new ButtonType("Нет", ButtonBar.ButtonData.NO);
        getDialogPane().getButtonTypes().addAll(yesButtonType, noButtonType);
        setResultConverter(dialogButton -> dialogButton == yesButtonType);
        Button noButton = (Button) getDialogPane().lookupButton(noButtonType);
        noButton.setDefaultButton(true);
        Button yesButton = (Button) getDialogPane().lookupButton(yesButtonType);
        yesButton.setDefaultButton(false);

        Stage stage = (Stage) getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(faviconPath.toString()));
    }

}
