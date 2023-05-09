package ru.sibsutis.pmik.hmi.interfaces.forms;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;

/**
 * Контроллер формы приветственного окна приложения.
 */
public class StartForm {

    private static final String STYLES_PATH = "/forms/styles.css";

    private static final String MAIN_IMAGE_PATH = "/images/start.png";

    private static final String UNIVERSITY_IMAGE_PATH = "/images/university.png";

    private static final String FONT_REGULAR_PATH = "/fonts/Roboto-Regular.ttf";

    private static final String UNIVERSITY_URL = "https://sibsutis.ru";

    /**
     * Контейнер визуальных компонентов формы.
     */
    @FXML
    AnchorPane root;

    /**
     * Представление для изображения формы.
     */
    @FXML
    ImageView startImageView;

    /**
     * Приветствие.
     */
    @FXML
    Label header;

    /**
     * Приглашение для ввода кода.
     */
    @FXML
    Label message;

    /**
     * Поле ввода кода.
     */
    @FXML
    TextField code;

    /**
     * Ссылка на сайт университета.
     */
    @FXML
    ImageView universityImageView;

    /**
     * URL размещения иконки приложения.
     */
    private URL faviconPath;

    /**
     * Основной поток приложения.
     */
    private Application application;

    /**
     * Устанавливает приветственное окно приложения.
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
     * Устанавливает ссылку на основной поток приложения.
     *
     * @param application Приложение.
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    @SuppressWarnings("unused")
    private void initialize() {
        Class<?> clazz = getClass();

        // Устанавливаем стили
        URL stylesPath = Objects.requireNonNull(clazz.getResource(STYLES_PATH));
        root.getStylesheets().add(stylesPath.toExternalForm());

        // Добавляем картинку
        URL startImagePath = Objects.requireNonNull(clazz.getResource(MAIN_IMAGE_PATH));
        Image startImage = new Image(startImagePath.toString(), true);
        startImageView.setImage(startImage);

        // Добавляем заголовок
        URL fontPath = Objects.requireNonNull(clazz.getResource(FONT_REGULAR_PATH));
        Font font = Font.loadFont(fontPath.toExternalForm(), 40);
        header.setFont(font);

        // Добавляем приглашение
        font = Font.loadFont(fontPath.toExternalForm(), 16);
        message.setFont(font);

        // Добавляем поле ввода кода
        code.setFont(font);

        // Добавляем ссылку на сайт университета
        URL universityImagePath = Objects.requireNonNull(clazz.getResource(UNIVERSITY_IMAGE_PATH));
        Image universityImage = new Image(universityImagePath.toString(), true);
        universityImageView.setImage(universityImage);
        universityImageView.setOnMouseClicked(event -> {
                    application.getHostServices()
                            .showDocument(UNIVERSITY_URL);
                    universityImageView.requestFocus();
        });
    }

}
