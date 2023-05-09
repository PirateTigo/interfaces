package ru.sibsutis.pmik.hmi.interfaces.forms;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.sibsutis.pmik.hmi.interfaces.windows.MainWindow;

import java.net.URL;
import java.util.Objects;

/**
 * Контроллер формы приветственного окна приложения.
 */
public class StartForm {

    private static final String MAIN_IMAGE_PATH = "/images/start.png";

    private static final String UNIVERSITY_IMAGE_PATH = "/images/university.png";

    private static final String UNIVERSITY_URL = "https://sibsutis.ru";

    private static final String ERROR_FIELD_CSS_CLASS = "error-field";

    private static final String NUMBER_FORMAT_ERROR = "Введите целое положительное число";

    private static final int VARIANTS_COUNT = 15;

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
     * Сообщение об ошибке.
     */
    @FXML
    Label error;

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
     * Приветственное окно.
     */
    private Stage startStage;

    /**
     * Основное окно приложения.
     */
    private Stage mainStage;

    /**
     * Устанавливает приветственное окно приложения.
     */
    public void setStartStage(Stage startStage) {
        this.startStage = startStage;
        // Устанавливаем обработчик закрытия окна приложения
        startStage.setOnCloseRequest(windowEvent -> Platform.exit());
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
     * Возвращает основное окно приложения.
     *
     * @return Основное окно приложения.
     */
    public Stage getMainStage() {
        return mainStage;
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    @SuppressWarnings("unused")
    private void initialize() {
        Class<?> clazz = getClass();

        // Устанавливаем общие параметры
        URL stylesPath = Objects.requireNonNull(clazz.getResource(MainForm.STYLES_PATH));
        root.getStylesheets().add(stylesPath.toExternalForm());
        root.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                showMainWindow();
            }
        });

        // Добавляем картинку
        URL startImagePath = Objects.requireNonNull(clazz.getResource(MAIN_IMAGE_PATH));
        Image startImage = new Image(startImagePath.toString(), true);
        startImageView.setImage(startImage);

        // Добавляем заголовок
        URL fontPath = Objects.requireNonNull(clazz.getResource(MainForm.FONT_REGULAR_PATH));
        Font font = Font.loadFont(fontPath.toExternalForm(), 40);
        header.setFont(font);

        // Добавляем приглашение
        font = Font.loadFont(fontPath.toExternalForm(), 16);
        message.setFont(font);

        // Добавляем поле ввода кода
        code.setFont(font);
        code.textProperty().addListener((observable -> resetErrorMessage()));

        // Добавляем сообщение об ошибке
        error.setFont(font);

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

    /**
     * Отображает основное окно программы.
     */
    private void showMainWindow() {
        String rawCode = code.getText();
        try {
            int rawCodeInt = Integer.parseInt(rawCode);
            if (rawCodeInt < 0) {
                setErrorMessage(NUMBER_FORMAT_ERROR);
                return;
            }
            int variant = rawCodeInt % VARIANTS_COUNT;
            startStage.hide();

            // Создаем новое основное окно приложения
            mainStage = new Stage();
            URL mainFormPath = Objects.requireNonNull(
                    getClass().getResource(MainWindow.MAIN_FORM_PATH)
            );
            MainForm mainForm = MainWindow.prepareStage(
                    mainStage,
                    faviconPath,
                    mainFormPath
            );
            mainForm.setFaviconPath(faviconPath);
            mainForm.setVariant(variant);
            mainStage.show();
        } catch (NumberFormatException ex) {
            setErrorMessage(NUMBER_FORMAT_ERROR);
        } catch (Throwable ex) {
            setErrorMessage(ex.getMessage());
        }
    }

    /**
     * Устанавливает сообщение об ошибке ввода.
     *
     * @param errorMessage Сообщение об ошибке ввода.
     */
    private void setErrorMessage(String errorMessage) {
        code.getStyleClass().add(ERROR_FIELD_CSS_CLASS);
        error.setText(errorMessage);
    }

    /**
     * Убирает сообщение об ошибке ввода.
     */
    private void resetErrorMessage() {
        error.setText("");
        code.getStyleClass().remove(ERROR_FIELD_CSS_CLASS);
    }

}
