package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Контроллер формы программы по варианту 10.
 */
@SuppressWarnings("unused")
public class Program10Form extends AbstractProgramForm {

    private static final String HEADER = "Расчет площади геометрической фигуры";

    private static final String SQUARE_ERROR_TEXT = "Укажите сторону (1) квадрата";

    private static final String TRIANGLE_ERROR_TEXT = "Укажите все стороны треугольника";

    private static final String TRAPEZOID_ERROR_TEXT = "Укажите все основания и высоту";

    private static final String PYRAMID_ERROR_TEXT = "Укажите площадь основания и высоту пирамиды";

    private static final String RECTANGLE_ERROR_TEXT = "Укажите стороны (1 и 2) прямоугольника";

    private static final String CIRCLE_ERROR_TEXT = "Укажите радиус окружности";

    private static final String RHOMBUS_ERROR_TEXT = "Укажите все диагонали ромба";

    private static final String PARALLELOGRAM_ERROR_TEXT = "Укажите сторону (1) и высоту параллелограмма";

    private static final String ERROR_FIELD_CLASS = "error-field";

    private final ToggleGroup figures = new ToggleGroup();

    /**
     * Радиокнопка "Квадрат".
     */
    @FXML
    RadioButton square;

    /**
     * Радиокнопка "Треугольник".
     */
    @FXML
    RadioButton triangle;

    /**
     * Радиокнопка "Трапеция".
     */
    @FXML
    RadioButton trapezoid;

    /**
     * Радиокнопка "Прямоугольник".
     */
    @FXML
    RadioButton rectangle;

    /**
     * Радиокнопка "Окружность".
     */
    @FXML
    RadioButton circle;

    /**
     * Радиокнопка "Ромб".
     */
    @FXML
    RadioButton rhombus;

    /**
     * Радиокнопка "Параллелограмм".
     */
    @FXML
    RadioButton parallelogram;

    /**
     * Название поля "Сторона 1".
     */
    @FXML
    Label back1LabelProgram10;

    /**
     * Поле "Сторона 1".
     */
    @FXML
    TextField back1Program10;

    /**
     * Название поля "Высота".
     */
    @FXML
    Label heightLabelProgram10;

    /**
     * Поле "Высота".
     */
    @FXML
    TextField heightProgram10;

    /**
     * Название поля "Основание 1".
     */
    @FXML
    Label base1LabelProgram10;

    /**
     * Поле "Основание 1".
     */
    @FXML
    TextField base1Program10;

    /**
     * Название поля "Диагональ 1".
     */
    @FXML
    Label diagonal1LabelProgram10;

    /**
     * Поле "Диагональ 1".
     */
    @FXML
    TextField diagonal1Program10;

    /**
     * Название поля "Сторона 2".
     */
    @FXML
    Label back2LabelProgram10;

    /**
     * Поле "Сторона 2".
     */
    @FXML
    TextField back2Program10;

    /**
     * Название поля "Радиус".
     */
    @FXML
    Label radiusLabelProgram10;

    /**
     * Поле "Радиус".
     */
    @FXML
    TextField radiusProgram10;

    /**
     * Название поля "Основание 2".
     */
    @FXML
    Label base2LabelProgram10;

    /**
     * Поле "Основание 2".
     */
    @FXML
    TextField base2Program10;

    /**
     * Название поля "Диагональ 1".
     */
    @FXML
    Label diagonal2LabelProgram10;

    /**
     * Поле "Диагональ 1".
     */
    @FXML
    TextField diagonal2Program10;

    /**
     * Название поля "Сторона 3".
     */
    @FXML
    Label back3LabelProgram10;

    /**
     * Поле "Сторона 3".
     */
    @FXML
    TextField back3Program10;

    /**
     * Кнопка "Площадь".
     */
    @FXML
    Button squareButton;

    /**
     * Индекс размерности результата.
     */
    @FXML
    Label dimensionIndex2;

    /**
     * Название поля вывода результата
     */
    @FXML
    Label resultLabelProgram10;

    /**
     * Поле вывода результата.
     */
    @FXML
    Label resultProgram10;

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    protected void initialize() {
        super.initialize();

        square.setToggleGroup(figures);
        triangle.setToggleGroup(figures);
        trapezoid.setToggleGroup(figures);
        rectangle.setToggleGroup(figures);
        circle.setToggleGroup(figures);
        rhombus.setToggleGroup(figures);
        parallelogram.setToggleGroup(figures);

        figures.selectToggle(square);

        // Добавляем обработчик кнопки "Площадь"
        squareButton.setOnAction(event -> {
            RadioButton figureButton = (RadioButton)figures.getSelectedToggle();
            evaluateSquare(figureButton.getText());
        });

        // Добавляем обработчики полей ввода
        back1Program10.textProperty().addListener((observable, oldValue, newValue) ->
                back1Program10.getStyleClass().remove(ERROR_FIELD_CLASS));
        back2Program10.textProperty().addListener((observable, oldValue, newValue) ->
                back2Program10.getStyleClass().remove(ERROR_FIELD_CLASS));
        back3Program10.textProperty().addListener((observable, oldValue, newValue) ->
                back3Program10.getStyleClass().remove(ERROR_FIELD_CLASS));
        heightProgram10.textProperty().addListener((observable, oldValue, newValue) ->
                heightProgram10.getStyleClass().remove(ERROR_FIELD_CLASS));
        base1Program10.textProperty().addListener((observable, oldValue, newValue) ->
                base1Program10.getStyleClass().remove(ERROR_FIELD_CLASS));
        base2Program10.textProperty().addListener((observable, oldValue, newValue) ->
                base2Program10.getStyleClass().remove(ERROR_FIELD_CLASS));
        radiusProgram10.textProperty().addListener((observable, oldValue, newValue) ->
                radiusProgram10.getStyleClass().remove(ERROR_FIELD_CLASS));
        diagonal1Program10.textProperty().addListener((observable, oldValue, newValue) ->
                diagonal1Program10.getStyleClass().remove(ERROR_FIELD_CLASS));
        diagonal2Program10.textProperty().addListener((observable, oldValue, newValue) ->
                diagonal2Program10.getStyleClass().remove(ERROR_FIELD_CLASS));

        // Устанавливаем шрифты
        setFont(square, 16, false, false);
        setFont(triangle, 16, false, false);
        setFont(trapezoid, 16, false, false);
        setFont(rectangle, 16, false, false);
        setFont(circle, 16, false, false);
        setFont(rhombus, 16, false, false);
        setFont(parallelogram, 16, false, false);
        setFont(back1LabelProgram10, 16, false, false);
        setFont(back1Program10, 16, false, false);
        setFont(heightLabelProgram10, 16, false, false);
        setFont(heightProgram10, 16, false, false);
        setFont(base1LabelProgram10, 16, false, false);
        setFont(back1Program10, 16, false, false);
        setFont(diagonal1LabelProgram10, 16, false, false);
        setFont(diagonal1Program10, 16, false, false);
        setFont(back2LabelProgram10, 16, false, false);
        setFont(back2Program10, 16, false, false);
        setFont(radiusLabelProgram10, 16, false, false);
        setFont(radiusProgram10, 16, false, false);
        setFont(base2LabelProgram10, 16, false, false);
        setFont(base2Program10, 16, false, false);
        setFont(diagonal2LabelProgram10, 16, false, false);
        setFont(diagonal2Program10, 16, false, false);
        setFont(back3LabelProgram10, 16, false, false);
        setFont(back3Program10, 16, false, false);
        setFont(squareButton, 16, true, false);
        setFont(dimensionIndex2, 10, false, false);
        setFont(resultLabelProgram10, 16, false, false);
        setFont(resultProgram10, 16, false, false);
    }

    @Override
    protected String getHeader() {
        return HEADER;
    }

    private void evaluateSquare(String figure) {
        BigDecimal result;
        double height, back1, back2, back3, base1, base2, diagonal1, diagonal2, radius;
        switch (figure) {
            case "Квадрат":
                try {
                    back1 = Double.parseDouble(back1Program10.getText());
                    if (back1 < 0) {
                        back1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(SQUARE_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    back1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(SQUARE_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(back1)
                        .pow(2);
                break;
            case "Треугольник":
                try {
                    back1 = Double.parseDouble(back1Program10.getText());
                    if (back1 < 0) {
                        back1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(TRIANGLE_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    back1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(TRIANGLE_ERROR_TEXT);
                    return;
                }
                try {
                    back2 = Double.parseDouble(back2Program10.getText());
                    if (back2 < 0) {
                        back2Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(TRIANGLE_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    back2Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(TRIANGLE_ERROR_TEXT);
                    return;
                }
                try {
                    back3 = Double.parseDouble(back3Program10.getText());
                    if (back3 < 0) {
                        back3Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(TRIANGLE_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    back3Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(TRIANGLE_ERROR_TEXT);
                    return;
                }
                BigDecimal p = BigDecimal.valueOf(back1)
                        .add(BigDecimal.valueOf(back2))
                        .add(BigDecimal.valueOf(back3))
                        .divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP);
                result = p.multiply(p.subtract(BigDecimal.valueOf(back1)))
                        .multiply(p.subtract(BigDecimal.valueOf(back2)))
                        .multiply(p.subtract(BigDecimal.valueOf(back3)))
                        .sqrt(new MathContext(10));
                break;
            case "Трапеция":
                try {
                    base1 = Double.parseDouble(base1Program10.getText());
                    if (base1 < 0) {
                        base1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(TRAPEZOID_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    base1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(TRAPEZOID_ERROR_TEXT);
                    return;
                }
                try {
                    base2 = Double.parseDouble(base2Program10.getText());
                    if (base2 < 0) {
                        base2Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(TRAPEZOID_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    base2Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(TRAPEZOID_ERROR_TEXT);
                    return;
                }
                try {
                    height = Double.parseDouble(heightProgram10.getText());
                    if (height < 0) {
                        heightProgram10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(TRAPEZOID_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    heightProgram10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(TRAPEZOID_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(base1)
                        .add(BigDecimal.valueOf(base2))
                        .divide(BigDecimal.valueOf(2), 10, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(height));
                break;
            case "Прямоугольник":
                try {
                    back1 = Double.parseDouble(back1Program10.getText());
                    if (back1 < 0) {
                        back1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(RECTANGLE_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    back1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(RECTANGLE_ERROR_TEXT);
                    return;
                }
                try {
                    back2 = Double.parseDouble(back2Program10.getText());
                    if (back2 < 0) {
                        back2Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(RECTANGLE_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    back2Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(RECTANGLE_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(back1)
                        .multiply(BigDecimal.valueOf(back2));
                break;
            case "Окружность":
                try {
                    radius = Double.parseDouble(radiusProgram10.getText());
                    if (radius < 0) {
                        radiusProgram10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(CIRCLE_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    radiusProgram10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(CIRCLE_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(radius)
                        .pow(2)
                        .multiply(BigDecimal.valueOf(Math.PI));
                break;
            case "Ромб":
                try {
                    diagonal1 = Double.parseDouble(diagonal1Program10.getText());
                    if (diagonal1 < 0) {
                        diagonal1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(RHOMBUS_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    diagonal1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(RHOMBUS_ERROR_TEXT);
                    return;
                }
                try {
                    diagonal2 = Double.parseDouble(diagonal2Program10.getText());
                    if (diagonal2 < 0) {
                        diagonal2Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(RHOMBUS_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    diagonal2Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(RHOMBUS_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(diagonal1)
                        .multiply(BigDecimal.valueOf(diagonal2));
                break;
            default:
                // Параллелограмм
                try {
                    back1 = Double.parseDouble(back1Program10.getText());
                    if (back1 < 0) {
                        back1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(PARALLELOGRAM_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    back1Program10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(PARALLELOGRAM_ERROR_TEXT);
                    return;
                }
                try {
                    height = Double.parseDouble(heightProgram10.getText());
                    if (height < 0) {
                        heightProgram10.getStyleClass().add(ERROR_FIELD_CLASS);
                        showError(PARALLELOGRAM_ERROR_TEXT);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    heightProgram10.getStyleClass().add(ERROR_FIELD_CLASS);
                    showError(PARALLELOGRAM_ERROR_TEXT);
                    return;
                }
                result = BigDecimal.valueOf(back1)
                        .multiply(BigDecimal.valueOf(height));
        }
        back1Program10.getStyleClass().remove(ERROR_FIELD_CLASS);
        base2Program10.getStyleClass().remove(ERROR_FIELD_CLASS);
        back3Program10.getStyleClass().remove(ERROR_FIELD_CLASS);
        base1Program10.getStyleClass().remove(ERROR_FIELD_CLASS);
        base2Program10.getStyleClass().remove(ERROR_FIELD_CLASS);
        diagonal1Program10.getStyleClass().remove(ERROR_FIELD_CLASS);
        diagonal2Program10.getStyleClass().remove(ERROR_FIELD_CLASS);
        radiusProgram10.getStyleClass().remove(ERROR_FIELD_CLASS);
        heightProgram10.getStyleClass().remove(ERROR_FIELD_CLASS);
        resultProgram10.setText(
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
