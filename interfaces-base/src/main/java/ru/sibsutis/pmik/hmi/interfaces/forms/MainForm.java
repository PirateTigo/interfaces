package ru.sibsutis.pmik.hmi.interfaces.forms;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.sibsutis.pmik.hmi.interfaces.forms.programs.AbstractProgramForm;
import ru.sibsutis.pmik.hmi.interfaces.forms.programs.ProgramFormDescriptor;
import ru.sibsutis.pmik.hmi.interfaces.windows.QuestionDialog;
import ru.sibsutis.pmik.hmi.interfaces.windows.AuthorWindow;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static ru.sibsutis.pmik.hmi.interfaces.windows.AuthorWindow.AUTHOR_FORM_PATH;

/**
 * Контроллер формы основного окна приложения.
 */
public class MainForm {

    public static final String STYLES_PATH = "/forms/styles.css";

    public static final String FONT_REGULAR_PATH = "/fonts/Roboto-Regular.ttf";

    public static final String FONT_BOLD_PATH = "/fonts/Roboto-Bold.ttf";

    public static final String FONT_ITALIC_PATH = "/fonts/Roboto-Italic.ttf";

    public static final String FONT_BOLD_ITALIC_PATH = "/fonts/Roboto-BoldItalic.ttf";

    private static final String STUDENT_IMAGE_PATH = "/images/student.png";

    private static final String VARIANT_CHOICE_IMAGE_PATH = "/images/variant-choice.png";

    private static final String THEORY_IMAGE_PATH = "/images/theory.png";

    private static final String TIP_IMAGE_PATH = "/images/tip.png";

    private static final String THEORY_FORM_PATH = "/forms/theory.fxml";

    private static final String VARIANT_CHOOSING_QUESTION =
            "Данное действие приведет к закрытию анализируемой программы. \n\n" +
            "Вы действительно желаете указать другой код?";

    private static final String VARIANT_CHOOSING_TIP =
            "Изменить введенный ранее код анализируемой программы";

    private static final String THEORY_BUTTON_TIP_DEFAULT =
            "Теория по проведению анализа интерфейса";

    private static final String PROGRAM_ANALYSIS_BACK_TIP =
            "Возвращение к анализу программы";

    private static final String HELP_BUTTON_TIP =
            "Описание функциональных возможностей анализируемой программы";

    private static final String BUTTON_PRESSED_CLASS = "button-pressed";

    private static final String PROGRAM_ANALYSIS_HEADER = "Анализ программы";

    private static final String HELP_HEADER = "Справочная информация";

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
     * Основное содержимое рабочей области.
     */
    private List<Node> mainContent;

    /**
     * Содержимое справочной информации.
     */
    private List<Node> theoryContent;

    /**
     * Справочная информация.
     */
    private AnchorPane theoryPane;

    /**
     * Форма справочной информации.
     */
    private TheoryForm theoryForm;

    /**
     * Признак того, что кнопка теории нажата.
     */
    private boolean isTheoryOpened = false;

    /**
     * Описатель формы.
     */
    private ProgramFormDescriptor programFormDescriptor;

    /**
     * Контейнер визуальных компонентов формы.
     */
    @FXML
    AnchorPane root;

    /**
     * Главное меню.
     */
    @FXML
    MenuBar mainMenu;

    /**
     * Панель управления.
     */
    @FXML
    ToolBar buttons;

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
     * Основной заголовок раздела программы.
     */
    @FXML
    Label mainHeader;

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
     * Рабочая область.
     */
    @FXML
    AnchorPane content;

    /**
     * Устанавливает основное окно приложения.
     */
    public void setMainStage(Stage stage) {
        // Устанавливаем обработчик закрытия окна приложения
        stage.setOnCloseRequest(windowEvent -> exit());
        mainStage = stage;
    }

    /**
     * Возвращает основное окно приложения.
     */
    public Stage getMainStage() {
        return mainStage;
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
     * Возвращает номер варианта.
     */
    public int getVariant() {
        return variant;
    }

    /**
     * Устанавливает номер варианта.
     *
     * @param variant Номер варианта.
     */
    public void setVariant(int variant) {
        this.variant = variant;
        openProgram();
    }

    /**
     * Получает главное меню.
     */
    public MenuBar getMainMenu() {
        return mainMenu;
    }

    /**
     * Получает панель управления.
     */
    public ToolBar getButtons() {
        return buttons;
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
            if (programFormDescriptor != null) {
                programFormDescriptor.getProgramForm().close();
            }
            startStage.show();
        }
    }

    /**
     * Выполняет выход из приложения.
     */
    @SuppressWarnings("unused")
    public void exit() {
        if (programFormDescriptor != null) {
            programFormDescriptor.getProgramForm().close();
        }
        Platform.exit();
    }

    /**
     * Либо открывает справочную информацию, либо анализируемую программу,
     * в зависимости от текущего состояния программы.
     */
    public void theoryButtonHandler() {
        if (!openTheory()) {
            backToProgramAnalysis();
        }
    }

    /**
     * Открывает описание функциональных возможностей анализируемой программы.
     */
    public void helpButtonHandler() {
        openTheory();
        theoryForm.openLastTheme();
    }

    /**
     * Открывает справочную информацию.
     */
    public boolean openTheory() {
        if (!isTheoryOpened) {
            theory.getStyleClass().add(BUTTON_PRESSED_CLASS);
            try {
                if (theoryPane == null) {
                    URL theoryFormLocation =
                            Objects.requireNonNull(getClass().getResource(THEORY_FORM_PATH));
                    FXMLLoader theoryFormLoader = new FXMLLoader(theoryFormLocation);

                    theoryForm = new TheoryForm();
                    theoryForm.setParentForm(this);
                    theoryFormLoader.setController(theoryForm);
                    theoryPane = theoryFormLoader.load();
                    mainContent = new LinkedList<>(content.getChildren());
                    theoryContent = new LinkedList<>(theoryPane.getChildren());
                    content.getChildren().addAll(theoryContent);
                }
                mainContent.forEach(node -> node.setVisible(false));
                theoryContent.forEach(node -> node.setVisible(true));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            installTooltip(PROGRAM_ANALYSIS_BACK_TIP, theory);
            isTheoryOpened = !isTheoryOpened;
            theoryForm.setWidth(root.getWidth());
            theoryForm.setHeight(root.getHeight());
            theoryForm.openChapter(0, 0);
            mainHeader.setText(HELP_HEADER);
            return true;
        }
        return false;
    }

    /**
     * Восстанавливает состояние анализа программы.
     */
    public void backToProgramAnalysis() {
        if (isTheoryOpened) {
            theory.getStyleClass().remove(BUTTON_PRESSED_CLASS);
            theoryContent.forEach(node -> node.setVisible(false));
            mainContent.forEach(node -> node.setVisible(true));
            installTooltip(THEORY_BUTTON_TIP_DEFAULT, theory);
            isTheoryOpened = !isTheoryOpened;
            mainHeader.setText(PROGRAM_ANALYSIS_HEADER);
        }
    }

    /**
     * Возвращает форму справочной информации.
     */
    public TheoryForm getTheoryForm() {
        return theoryForm;
    }

    /**
     * Обрабатывает кнопку меню "Об авторе".
     */
    @SuppressWarnings("unused")
    public void authorHandler() throws IOException {
        Stage stage = new Stage();
        AuthorWindow.prepareStage(
                stage,
                faviconPath,
                Objects.requireNonNull(getClass().getResource(AUTHOR_FORM_PATH))
        );
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Открывает программу по варианту.
     */
    public void openProgram() {
        programFormDescriptor =
                AbstractProgramForm.load(variant, this);
        if (programFormDescriptor != null) {
            content.getChildren().addAll(programFormDescriptor.getContent());
            programFormDescriptor.getProgramForm().setWidth(root.getWidth());
            programFormDescriptor.getProgramForm().setHeight(root.getHeight());
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
                    -newValue.doubleValue() * 0.0075,
                    0.0
            ));

            // Обеспечиваем адаптивный размер шрифта для кнопок панели управления
            int fontSize = (int)(newValue.doubleValue() * 0.02);
            setButtonFont(variantChoice, fontSize);
            setButtonFont(theory, fontSize);
            setButtonFont(help, fontSize);

            // Обеспечиваем адаптивную высоту формы справочной информации
            if (theoryForm != null) {
                theoryForm.setHeight(newValue.doubleValue());
            }
            // Обеспечиваем адаптивную высоту формы анализируемой программы
            if (programFormDescriptor != null) {
                programFormDescriptor.getProgramForm().setHeight(newValue.doubleValue());
            }
        });
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            // Обеспечиваем адаптивную ширину формы справочной информации
            if (theoryForm != null) {
                theoryForm.setWidth(newValue.doubleValue());
            }
            // Обеспечиваем адаптивную ширину формы анализируемой программы
            if (programFormDescriptor != null) {
                programFormDescriptor.getProgramForm().setWidth(newValue.doubleValue());
            }
        });

        // Добавляем изображение студента
        URL studentImagePath = Objects.requireNonNull(clazz.getResource(STUDENT_IMAGE_PATH));
        Image studentImage = new Image(studentImagePath.toString(), true);
        studentView.setImage(studentImage);
        studentView.setOnMouseClicked(event -> backToProgramAnalysis());
        Tooltip.install(studentView, new Tooltip(PROGRAM_ANALYSIS_BACK_TIP));

        // Добавляем кнопку "Вариант" панели управления
        initButton(VARIANT_CHOICE_IMAGE_PATH, variantChoiceView, variantChoice);
        installTooltip(VARIANT_CHOOSING_TIP, variantChoice);
        variantChoice.setOnAction(event -> initVariantChoosing());

        // Добавляем кнопку "Теория" панели управления
        initButton(THEORY_IMAGE_PATH, theoryView, theory);
        installTooltip(THEORY_BUTTON_TIP_DEFAULT, theory);
        theory.setOnAction(event -> theoryButtonHandler());

        // Добавляем кнопку "Подсказка" панели управления
        initButton(TIP_IMAGE_PATH, helpView, help);
        installTooltip(HELP_BUTTON_TIP, help);
        help.setOnAction(event -> helpButtonHandler());

        // Добавляем основной заголовок раздела программы
        mainHeader.setText(PROGRAM_ANALYSIS_HEADER);
        URL fontPath = Objects.requireNonNull(clazz.getResource(FONT_ITALIC_PATH));
        Font font = Font.loadFont(fontPath.toExternalForm(), 28);
        mainHeader.setFont(font);
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

    /**
     * Устанавливает подсказку для элемента формы.
     *
     * @param text Текст подсказки.
     * @param node Элемент формы.
     */
    private void installTooltip(String text, Control node) {
        Tooltip tooltip = new Tooltip(text);
        node.setTooltip(tooltip);
    }

}
