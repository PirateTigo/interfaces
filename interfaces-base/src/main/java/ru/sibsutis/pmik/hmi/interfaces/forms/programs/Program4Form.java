package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Контроллер формы программы по варианту 4.
 */
@SuppressWarnings("unused")
public class Program4Form extends AbstractProgramForm {

    private static final String HEADER = "Комплексные числа";

    private static final String ERROR_FIELD_CLASS = "error-field";

    private static final String ERROR_TEXT = "Введите вещественное число";

    private static final String ERROR_FIELD_TEXT_FILL = "-fx-text-fill: #d35244;";

    private static final String ERROR_FIELD_TEXT_FILL_DISABLED ="-fx-text-fill: black;";

    /**
     * Метка действительной части первого комплексного числа.
     */
    @FXML
    Label complex1Item1Label;

    /**
     * Действительная часть первого комплексного числа.
     */
    @FXML
    TextField complex1Item1;

    /**
     * Метка мнимой части первого комплексного числа.
     */
    @FXML
    Label complex1Item2Label;

    /**
     * Мнимая часть первого комплексного числа.
     */
    @FXML
    TextField complex1Item2;

    /**
     * Метка действительной части второго комплексного числа.
     */
    @FXML
    Label complex2Item1Label;

    /**
     * Действительная часть второго комплексного числа.
     */
    @FXML
    TextField complex2Item1;

    /**
     * Метка мнимой части второго комплексного числа.
     */
    @FXML
    Label complex2Item2Label;

    /**
     * Мнимая часть второго комплексного числа.
     */
    @FXML
    TextField complex2Item2;

    /**
     * Операция над комплексными числами.
     */
    @FXML
    ComboBox<String> operationProgram4;

    /**
     * Метка знака равенства.
     */
    @FXML
    Label equalsProgram4;

    /**
     * Поле вывода результата.
     */
    @FXML
    Label answerProgram4;

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    protected void initialize() {
        super.initialize();

        // Устанавливаем обработку события смены операции
        operationProgram4.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
                    answerProgram4.setStyle(ERROR_FIELD_TEXT_FILL_DISABLED);
                    calculate(newValue);
                });

        // Устанавливаем сброс полей
        complex1Item1.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    complex1Item1.getStyleClass().remove(ERROR_FIELD_CLASS);
                    answerProgram4.setText("");
                });
        complex1Item2.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    complex1Item2.getStyleClass().remove(ERROR_FIELD_CLASS);
                    answerProgram4.setText("");
                });
        complex2Item1.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    complex2Item1.getStyleClass().remove(ERROR_FIELD_CLASS);
                    answerProgram4.setText("");
                });
        complex2Item2.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    complex2Item2.getStyleClass().remove(ERROR_FIELD_CLASS);
                    answerProgram4.setText("");
                });

        // Устанавливаем шрифты
        setFont(complex1Item1, 16, false);
        setFont(complex1Item1Label, 16, false);
        setFont(complex1Item2, 16, false);
        setFont(complex1Item2Label, 16, false);
        setFont(complex2Item1, 16, false);
        setFont(complex2Item1Label, 16, false);
        setFont(complex2Item2, 16, false);
        setFont(complex2Item2Label, 16, false);
        setFont(equalsProgram4, 20, false);
        setFont(answerProgram4, 32, false);
    }

    @Override
    protected String getHeader() {
        return HEADER;
    }

    private void calculate(String operation) {
        // Проверяем корректность ввода
        double complex1Item1Value;
        try {
            complex1Item1Value = Double.parseDouble(complex1Item1.getText());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            complex1Item1.getStyleClass().add(ERROR_FIELD_CLASS);
            answerProgram4.setText(ERROR_TEXT);
            answerProgram4.setStyle(ERROR_FIELD_TEXT_FILL);
            return;
        }
        double complex1Item2Value;
        try {
            complex1Item2Value = Double.parseDouble(complex1Item2.getText());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            complex1Item2.getStyleClass().add(ERROR_FIELD_CLASS);
            answerProgram4.setText(ERROR_TEXT);
            answerProgram4.setStyle(ERROR_FIELD_TEXT_FILL);
            return;
        }
        double complex2Item1Value;
        try {
            complex2Item1Value = Double.parseDouble(complex2Item1.getText());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            complex2Item1.getStyleClass().add(ERROR_FIELD_CLASS);
            answerProgram4.setText(ERROR_TEXT);
            answerProgram4.setStyle(ERROR_FIELD_TEXT_FILL);
            return;
        }
        double complex2Item2Value;
        try {
            complex2Item2Value = Double.parseDouble(complex2Item2.getText());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            complex2Item2.getStyleClass().add(ERROR_FIELD_CLASS);
            answerProgram4.setText(ERROR_TEXT);
            answerProgram4.setStyle(ERROR_FIELD_TEXT_FILL);
            return;
        }

        String result;
        if ("Плюс".equals(operation)) {
            double real = complex1Item1Value + complex2Item1Value;
            double image = complex1Item2Value + complex2Item2Value;
            result = complexToString(real, image);
        } else if ("Минус".equals(operation)) {
            double real = complex1Item1Value - complex2Item1Value;
            double image = complex1Item2Value - complex2Item2Value;
            result = complexToString(real, image);
        } else if ("Умножить".equals(operation)) {
            // (a1 + i*b1)*(a2 + i*b2) = (a1*a2 - b1*b2) + i*(a1*b2 + a2*b1)
            double real = complex1Item1Value * complex2Item1Value
                    - complex2Item1Value * complex2Item2Value;
            double image = complex1Item1Value * complex2Item2Value
                    + complex2Item1Value * complex1Item2Value;
            result = complexToString(real, image);
        } else {
            // (a1 + i*b1) / (a2 + i*b2) =
            //           a1*a2+b1*b2         a2*b1-a1*b2
            //           -----------    + i* --------------
            //            a2*a2+b2*b2        a2*a2+b2*b2
            double denominator = complex2Item1Value * complex2Item1Value
                    + complex2Item2Value * complex2Item2Value;
            if (denominator == 0) {
                answerProgram4.setText("Деление на ноль");
                answerProgram4.setStyle(ERROR_FIELD_TEXT_FILL);
                return;
            }
            double real = (complex1Item1Value * complex2Item1Value
                            + complex1Item2Value * complex2Item2Value) / denominator;
            double image = (complex2Item1Value * complex1Item2Value
                            - complex1Item1Value * complex2Item2Value) / denominator;
            result = complexToString(real, image);
        }

        answerProgram4.setText(result);
    }

    /**
     * Записывает комплексное число в строку.
     *
     * @param real Действительная часть.
     * @param image Мнимая часть.
     * @return Комплексное число, записанное в строку.
     */
    private String complexToString(double real, double image) {
        StringBuilder result = new StringBuilder();
        MathContext mc = new MathContext(3);
        BigDecimal realBig = new BigDecimal(real, mc);
        BigDecimal imageBig = new BigDecimal(image, mc);
        result.append(realBig.toPlainString());
        if (image < 0) {
            result.append("-i•");
            result.append(imageBig.abs().toPlainString());
        } else {
            result.append("+i•");
            result.append(imageBig.toPlainString());
        }
        return result.toString();
    }

}
