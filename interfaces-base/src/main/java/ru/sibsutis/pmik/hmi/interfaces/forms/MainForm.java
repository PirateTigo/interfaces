package ru.sibsutis.pmik.hmi.interfaces.forms;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.sibsutis.pmik.hmi.interfaces.windows.QuestionDialog;

import java.net.URL;
import java.util.Objects;

/**
 * Контроллер формы основного окна приложения.
 */
public class MainForm {

    public static final String STYLES_PATH = "/forms/styles.css";

    public static final String FONT_REGULAR_PATH = "/fonts/Roboto-Regular.ttf";

    public static final String FONT_BOLD_PATH = "/fonts/Roboto-Bold.ttf";

    private static final String STUDENT_IMAGE_PATH = "/images/student.png";

    private static final String VARIANT_CHOICE_IMAGE_PATH = "/images/variant-choice.png";

    private static final String THEORY_IMAGE_PATH = "/images/theory.png";

    private static final String HELP_IMAGE_PATH = "/images/help.png";

    private static final String VARIANT_CHOOSING_QUESTION =
            "Данное действие приведет к закрытию анализируемой программы. \n\n" +
            "Вы действительно желаете выбрать другой вариант?";

    /**
     * Основное окно приложения.
     */
    private Stage mainStage;

    /**
     * Приветственное окно.
     */
    private Stage startStage;

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
     * Контейнер для изображения студента.
     */
    @FXML
    HBox studentBox;

    /**
     * Изображение студента.
     */
    @FXML
    ImageView studentView;

    /**
     * Изображение кнопки выбора варианта.
     */
    @FXML
    ImageView variantChoiceView;

    /**
     * Кнопка выбора варианта.
     */
    @FXML
    Button variantChoice;

    /**
     * Изображение кнопки теории.
     */
    @FXML
    ImageView theoryView;

    /**
     * Кнопка теории.
     */
    @FXML
    Button theory;

    /**
     * Изображение кнопки помощи.
     */
    @FXML
    ImageView helpView;

    /**
     * Кнопка помощи.
     */
    @FXML
    Button help;

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
        mainStage = stage;
    }

    /**
     * Устанавливает приветственное окно.
     */
    public void setStartStage(Stage stage) {
        startStage = stage;
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
     * Обработчик кнопки выбора нового варианта.
     */
    public void initVariantChoosing() {
        QuestionDialog questionDialog =
                new QuestionDialog(VARIANT_CHOOSING_QUESTION, faviconPath);
        Boolean isNeedNewVariant = questionDialog.showAndWait().orElse(false);

        if (isNeedNewVariant) {
            mainStage.close();
            startStage.show();
        }
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    @SuppressWarnings("unused")
    private void initialize() {
        Class<?> clazz = getClass();

        // Устанавливаем общие параметры
        URL stylesPath = Objects.requireNonNull(clazz.getResource(STYLES_PATH));
        root.getStylesheets().add(stylesPath.toExternalForm());
        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            // Обеспечиваем адаптивный отступ для изображения студента
            studentBox.setPadding(new Insets(
                    0.0,
                    0.0,
                    -newValue.doubleValue() * 0.02,
                    0.0
            ));

            // Обеспечиваем адаптивный размер шрифта для кнопок панели управления
            int fontSize = (int)(newValue.doubleValue() * 0.02);
            setButtonFont(variantChoice, fontSize);
            setButtonFont(theory, fontSize);
            setButtonFont(help, fontSize);
        });

        // Добавляем изображение студента
        URL studentImagePath = Objects.requireNonNull(clazz.getResource(STUDENT_IMAGE_PATH));
        Image studentImage = new Image(studentImagePath.toString(), true);
        studentView.setImage(studentImage);

        // Добавляем кнопки панели управления
        initButton(VARIANT_CHOICE_IMAGE_PATH, variantChoiceView, variantChoice);
        initButton(THEORY_IMAGE_PATH, theoryView, theory);
        initButton(HELP_IMAGE_PATH, helpView, help);

        // Добавляем обработчик кнопки выбора варианта
        variantChoice.setOnAction(event -> initVariantChoosing());
    }

    /**
     * Инициализирует кнопку.
     *
     * @param imagePath Строка пути до изображения кнопки.
     * @param imageView Место загрузки изображения.
     * @param button Кнопка.
     */
    private void initButton(String imagePath, ImageView imageView, Button button) {
        Class<?> clazz = getClass();

        URL imageURL = Objects.requireNonNull(clazz.getResource(imagePath));
        Image image = new Image(imageURL.toString(), true);
        imageView.setImage(image);

        setButtonFont(button, 16);

        button.getStyleClass().add("toolbar-button");
    }

    /**
     * Устанавливает шрифт кнопки.
     *
     * @param button Кнопка.
     * @param size Размер шрифта.
     */
    private void setButtonFont(Button button, int size) {
        Class<?> clazz = getClass();
        URL fontPath = Objects.requireNonNull(clazz.getResource(FONT_BOLD_PATH));
        Font font = Font.loadFont(fontPath.toExternalForm(), size);
        button.setFont(font);
        button.setContentDisplay(ContentDisplay.TOP);
    }

}
