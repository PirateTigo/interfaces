package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Контроллер формы программы по варианту 1.
 */
@SuppressWarnings("unused")
public class Program1Form extends AbstractProgramForm {

    private static final String HEADER = "Число π до k-й цифры после точки";

    private static final String ERROR_FIELD_CLASS = "error-field";

    private static final String ERROR_TEXT =
            "Введите целое положительное число не больше 2000";

    private static final String ERROR_FIELD_TEXT_FILL = "-fx-text-fill: #d35244;";

    private static final String ERROR_FIELD_TEXT_FILL_DISABLED ="-fx-text-fill: black;";

    private ExecutorService executorService;

    /**
     * Поле ввода числа k.
     */
    @FXML
    TextField inputPiPrecisionField;

    /**
     * Поле вывода ошибки.
     */
    @FXML
    Label errorPi;

    /**
     * Поле вывода значения числа пи.
     */
    @FXML
    TextArea outputPiField;

    @Override
    public void close() {
        executorService.shutdown();
    }

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    protected void initialize() {
        super.initialize();

        executorService = Executors.newSingleThreadExecutor();

        // Устанавливаем обработчик для ввода значения числа пи
        inputPiPrecisionField.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                startWait();
                executorService.submit(this::calculatePi);
            }
        });

        // Устанавливаем обработчик сброса полей после ввода
        inputPiPrecisionField.textProperty().addListener(
                (observable, oldValue, newValue) -> {
            inputPiPrecisionField.getStyleClass().remove(ERROR_FIELD_CLASS);
            errorPi.setText("");
            outputPiField.clear();
        });

        // Устанавливаем шрифты
        setFont(inputPiPrecisionField, 16, false);
        setFont(errorPi, 16, false);
        setFont(outputPiField, 16, false);
    }

    @Override
    protected String getHeader() {
        return HEADER;
    }

    /**
     * Вычисляет число пи.
     * <p>
     * Для вычисления используется
     * <a href="https://en.wikipedia.org/wiki/Chudnovsky_algorithm">
     *     алгоритм Чудновского
 *     </a>.
     */
    private void calculatePi() {
        // Устанавливаем точность
        int precision;
        try {
            precision = Integer.parseInt(inputPiPrecisionField.getText());
            if (precision <= 0 || precision > 2000) {
                Platform.runLater(() -> {
                    stopWait();
                    inputPiPrecisionField.getStyleClass().add(ERROR_FIELD_CLASS);
                    errorPi.setText(ERROR_TEXT);
                });
                return;
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            Platform.runLater(() -> {
                stopWait();
                inputPiPrecisionField.getStyleClass().add(ERROR_FIELD_CLASS);
                errorPi.setText(ERROR_TEXT);
            });
            return;
        }
        MathContext mc = new MathContext(precision + 3);

        // Устанавливаем значения констант алгоритма Чудновского
        MathContext forC = new MathContext(precision * 10);
        BigDecimal c = new BigDecimal(426880, forC)
                .multiply(new BigDecimal(10005).sqrt(forC), forC);
        BigDecimal l = new BigDecimal("13591409", mc);
        BigDecimal x = new BigDecimal("1", mc);
        BigDecimal m = new BigDecimal("1", mc);
        BigDecimal k = new BigDecimal("-6", mc);
        BigDecimal q = new BigDecimal("1", mc);
        BigDecimal term = BigDecimal.ZERO;
        BigDecimal prev;

        // Выполняем итерационное приближение к указанной точности числа
        do {
            if (term.equals(BigDecimal.ZERO)) {
                prev = BigDecimal.ZERO;
            } else {
                prev = term.pow(-1, mc).multiply(c, mc);
            }
            term = term.add(m.multiply(l).divide(x, mc));
            l = l.add(new BigDecimal("545140134", mc), mc);
            x = x.multiply(new BigDecimal("-262537412640768000", mc), mc);
            k = k.add(new BigDecimal(12, mc), mc);
            m = m.multiply(
                    (k.pow(3).subtract(k.multiply(new BigDecimal(16, mc), mc), mc))
                            .divide(q.pow(3), mc)
            );
            q = q.add(BigDecimal.ONE);
        } while (!compare(
                prev,
                term.pow(-1, mc).multiply(c, mc),
                precision
        ));

        // Выводим результат
        MathContext finalContext = new MathContext(precision + 1);
        String result = term.pow(-1, finalContext)
                .multiply(c, finalContext)
                .toString();
        Platform.runLater(() -> {
            outputPiField.setText(result);
            stopWait();
        });
    }

    /**
     * Сравнивает два числа с указанной точностью.
     *
     * @param first Первое число.
     * @param second Второе число.
     * @param precision Точность.
     * @return Признак равенства.
     */
    private boolean compare(BigDecimal first, BigDecimal second, int precision) {
        String leftStr = first.toString();
        String rightStr = second.toString();

        boolean isEquals = true;

        for (int i = 0; i < leftStr.length() && i < rightStr.length(); i++) {
            if (i > precision + 2) break;
            if (leftStr.charAt(i) != rightStr.charAt(i)) {
                isEquals = false;
                break;
            }
        }

        return isEquals;
    }

    /**
     * Блокирует интерфейс на время расчетов числа.
     */
    private void startWait() {
        errorPi.setStyle(ERROR_FIELD_TEXT_FILL_DISABLED);
        errorPi.setText("Подождите...");
        inputPiPrecisionField.setDisable(true);
        outputPiField.setDisable(true);
    }

    /**
     * Разблокирует интерфейс после произведенных расчетов.
     */
    private void stopWait() {
        errorPi.setStyle(ERROR_FIELD_TEXT_FILL);
        errorPi.setText("");
        inputPiPrecisionField.setDisable(false);
        outputPiField.setDisable(false);
    }

}
