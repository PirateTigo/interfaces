package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Контроллер формы программы по варианту 9.
 */
@SuppressWarnings("unused")
public class Program9Form extends AbstractProgramForm {

    private static final String HEADER = "Расчет объема геометрического тела";

    private static final String CUBE_ERROR_TEXT = "Укажите ребро куба";

    private static final String PRISM_ERROR_TEXT = "Укажите площадь основания и высоту призмы";

    private static final String CONE_ERROR_TEXT = "Укажите площадь основания и высоту конуса";

    private static final String PYRAMID_ERROR_TEXT = "Укажите площадь основания и высоту пирамиды";

    private static final String SPHERE_ERROR_TEXT = "Укажите радиус сферы";

    private static final String CYLINDER_ERROR_TEXT = "Укажите площадь основания и высоту цилиндра";

    private static final String PARALLELEPIPED_ERROR_TEXT = "Укажите площадь основания и высоту параллелепипеда";

    private static final String ERROR_FIELD_CLASS = "error-field";

    private final ToggleGroup figures = new ToggleGroup();

    /**
     * Радиокнопка "Куб".
     */
    @FXML
    RadioButton cube;

    /**
     * Радиокнопка "Призма".
     */
    @FXML
    RadioButton prism;

    /**
     * Радиокнопка "Конус".
     */
    @FXML
    RadioButton cone;

    /**
     * Радиокнопка "Пирамида".
     */
    @FXML
    RadioButton pyramid;

    /**
     * Радиокнопка "Сфера".
     */
    @FXML
    RadioButton sphere;

    /**
     * Радиокнопка "Цилиндр".
     */
    @FXML
    RadioButton cylinder;

    /**
     * Радиокнопка "Параллелепипед".
     */
    @FXML
    RadioButton parallelepiped;

    /**
     * Название поля ввода ребра.
     */
    @FXML
    Label edgeLabelProgram9;

    /**
     * Поле ввода ребра.
     */
    @FXML
    TextField edgeProgram9;

    /**
     * Название поля ввода высоты.
     */
    @FXML
    Label heightLabelProgram9;

    /**
     * Поле ввода высоты.
     */
    @FXML
    TextField heightProgram9;

    /**
     * Название поля ввода площади основания.
     */
    @FXML
    Label squareLabelProgram9;

    /**
     * Поле ввода площади основания.
     */
    @FXML
    TextField squareProgram9;

    /**
     * Название поля ввода радиуса.
     */
    @FXML
    Label radiusLabelProgram9;

    /**
     * Поле ввода радиуса.
     */
    @FXML
    TextField radiusProgram9;

    /**
     * Кнопка "Объем".
     */
    @FXML
    Button volumeButton;

    /**
     * Название поля вывода результата.
     */
    @FXML
    Label resultLabelProgram9;

    /**
     * Индекс размерности результата.
     */
    @FXML
    Label dimensionIndex;

    /**
     * Поле вывода результата.
     */
    @FXML
    Label resultProgram9;

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    protected void initialize() {
        super.initialize();

        cube.setToggleGroup(figures);
        prism.setToggleGroup(figures);
        cone.setToggleGroup(figures);
        pyramid.setToggleGroup(figures);
        sphere.setToggleGroup(figures);
        cylinder.setToggleGroup(figures);
        parallelepiped.setToggleGroup(figures);

        figures.selectToggle(cube);

        // Добавляем обработчик кнопки "Объем"
        volumeButton.setOnAction(event -> {
            RadioButton figureButton = (RadioButton)figures.getSelectedToggle();
            evaluateVolume(figureButton.getText());
        });

        // Добавляем обработчики полей ввода
        edgeProgram9.textProperty().addListener((observable, oldValue, newValue) ->
                edgeProgram9.getStyleClass().remove(ERROR_FIELD_CLASS));
        heightProgram9.textProperty().addListener((observable, oldValue, newValue) ->
                heightProgram9.getStyleClass().remove(ERROR_FIELD_CLASS));
        squareProgram9.textProperty().addListener((observable, oldValue, newValue) ->
                squareProgram9.getStyleClass().remove(ERROR_FIELD_CLASS));
        radiusProgram9.textProperty().addListener((observable, oldValue, newValue) ->
                radiusProgram9.getStyleClass().remove(ERROR_FIELD_CLASS));

        // Устанавливаем шрифты
        setFont(cube, 16, false, false);
        setFont(prism, 16, false, false);
        setFont(cone, 16, false, false);
        setFont(pyramid, 16, false, false);
        setFont(sphere, 16, false, false);
        setFont(cylinder, 16, false, false);
        setFont(parallelepiped, 16, false, false);
        setFont(edgeLabelProgram9, 16, false, false);
        setFont(edgeProgram9, 16, false, false);
        setFont(heightLabelProgram9, 16, false, false);
        setFont(heightProgram9, 16, false, false);
        setFont(squareLabelProgram9, 16, false, false);
        setFont(squareProgram9, 16, false, false);
        setFont(radiusLabelProgram9, 16, false, false);
        setFont(radiusProgram9, 16, false, false);
        setFont(volumeButton, 16, true, false);
        setFont(resultLabelProgram9, 16, false, false);
        setFont(dimensionIndex, 10, false, false);
        setFont(resultProgram9, 16, false, false);
    }

    @Override
    protected String getHeader() {
        return HEADER;
    }

    private void evaluateVolume(String figure) {
        BigDecimal result;
        double height, square;
        switch (figure) {
            case "Куб":
                double edge;
                try {
                    edge = Double.parseDouble(edgeProgram9.getText());
                    if (edge < 0) {
                        edgeProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(CUBE_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    edgeProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(CUBE_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(edge)
                        .pow(3);
                break;
            case "Призма":
                try {
                    square = Double.parseDouble(squareProgram9.getText());
                    if (square < 0) {
                        squareProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(PRISM_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    squareProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(PRISM_ERROR_TEXT);
                    return;
                }
                try {
                    height = Double.parseDouble(heightProgram9.getText());
                    if (height < 0) {
                        heightProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(PRISM_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    heightProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(PRISM_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(square)
                        .multiply(BigDecimal.valueOf(height));
                break;
            case "Конус":
                try {
                    square = Double.parseDouble(squareProgram9.getText());
                    if (square < 0) {
                        squareProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(CONE_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    squareProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(CONE_ERROR_TEXT);
                    return;
                }
                try {
                    height = Double.parseDouble(heightProgram9.getText());
                    if (height < 0) {
                        heightProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(CONE_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    heightProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(CONE_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(square)
                        .multiply(BigDecimal.valueOf(height))
                        .divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_UP);
                break;
            case "Пирамида":
                try {
                    square = Double.parseDouble(squareProgram9.getText());
                    if (square < 0) {
                        squareProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(PYRAMID_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    squareProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(PYRAMID_ERROR_TEXT);
                    return;
                }
                try {
                    height = Double.parseDouble(heightProgram9.getText());
                    if (height < 0) {
                        heightProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(PYRAMID_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    heightProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(PYRAMID_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(square)
                        .multiply(BigDecimal.valueOf(height))
                        .divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_UP);
                break;
            case "Сфера":
                double radius;
                try {
                    radius = Double.parseDouble(radiusProgram9.getText());
                    if (radius < 0) {
                        radiusProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(SPHERE_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    radiusProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(SPHERE_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(radius)
                        .pow(3)
                        .multiply(BigDecimal.valueOf(4))
                        .multiply(BigDecimal.valueOf(Math.PI))
                        .divide(BigDecimal.valueOf(3), 10, RoundingMode.HALF_UP);
                break;
            case "Цилиндр":
                try {
                    square = Double.parseDouble(squareProgram9.getText());
                    if (square < 0) {
                        squareProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(CYLINDER_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    squareProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(CYLINDER_ERROR_TEXT);
                    return;
                }
                try {
                    height = Double.parseDouble(heightProgram9.getText());
                    if (height < 0) {
                        heightProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(CYLINDER_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    heightProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(CYLINDER_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(square)
                        .multiply(BigDecimal.valueOf(height));
                break;
            default:
                // Параллелепипед
                try {
                    square = Double.parseDouble(squareProgram9.getText());
                    if (square < 0) {
                        squareProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(PARALLELEPIPED_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    squareProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(PARALLELEPIPED_ERROR_TEXT);
                    return;
                }
                try {
                    height = Double.parseDouble(heightProgram9.getText());
                    if (height < 0) {
                        heightProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(PARALLELEPIPED_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    heightProgram9.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(PARALLELEPIPED_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(square)
                        .multiply(BigDecimal.valueOf(height));
        }
        edgeProgram9.getStyleClass().remove(ERROR_FIELD_CLASS);
        heightProgram9.getStyleClass().remove(ERROR_FIELD_CLASS);
        squareProgram9.getStyleClass().remove(ERROR_FIELD_CLASS);
        radiusProgram9.getStyleClass().remove(ERROR_FIELD_CLASS);
        resultProgram9.setText(
                result.setScale(2, RoundingMode.HALF_UP)
                        .toPlainString()
        );
    }

    /**
     * Показывает ошибку пользователю.
     */
    private void showError(String errorText) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Невозможно выполнить расчет");
            alert.setContentText(errorText);
            alert.initOwner(parentForm.getMainStage());
            alert.showAndWait();
        });
    }

}
