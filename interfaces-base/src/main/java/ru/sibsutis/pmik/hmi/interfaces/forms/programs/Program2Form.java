package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Контроллер формы программы по варианту 2.
 */
@SuppressWarnings("unused")
public class Program2Form extends AbstractProgramForm {

    private static final String HEADER = "Разложение на произведение простых чисел";

    private static final String ERROR_FIELD_CLASS = "error-field";

    private static final String ERROR_TEXT = "Введите целое положительное число";

    private static final String ERROR_FIELD_TEXT_FILL = "-fx-text-fill: #d35244;";

    private static final String ERROR_FIELD_TEXT_FILL_DISABLED ="-fx-text-fill: black;";

    private volatile boolean isCanceled = false;

    private ExecutorService executorService;

    /**
     * Поле ввода исходного числа.
     */
    @FXML
    TextField inputFieldProgram2;

    /**
     * Кнопка "ОК".
     */
    @FXML
    Button okButtonProgram2;

    /**
     * Сообщение об ошибке ввода.
     */
    @FXML
    Label errorInputProgram2;

    /**
     * Поле вывода результата разложения.
     */
    @FXML
    TextArea outputFieldProgram2;

    /**
     * Кнопка "Отменить".
     */
    @FXML
    Button cancelProgram2;

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

        // Устанавливаем обработчик для ввода исходного числа и начала расчетов
        okButtonProgram2.setOnAction(event -> {
            startWait();
            executorService.submit(this::calculate);
        });

        // Устанавливаем обработчик для отмены процесса вычислений.
        cancelProgram2.setOnAction(event -> isCanceled = true);

        // Устанавливаем обработчик сброса полей после ввода
        inputFieldProgram2.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    inputFieldProgram2.getStyleClass().remove(ERROR_FIELD_CLASS);
                    errorInputProgram2.setText("");
                    outputFieldProgram2.clear();
                });

        // Устанавливаем шрифты
        setFont(header, 24, false, false);
        setFont(inputFieldProgram2, 16, false, false);
        setFont(errorInputProgram2, 16, false, false);
        setFont(okButtonProgram2, 16, true, false);
    }

    @Override
    protected String getHeader() {
        return HEADER;
    }

    /**
     * Вычисляет разложение числа на произведение простых множителей.
     */
    private void calculate() {

        BigInteger n, two = new BigInteger("2"), div = new BigInteger("2");

        // Проверяем корректность ввода
        try {
            n = new BigInteger(inputFieldProgram2.getText());
            if (n.compareTo(BigInteger.ONE) < 0) {
                Platform.runLater(() -> {
                    stopWait();
                    inputFieldProgram2.getStyleClass().add(ERROR_FIELD_CLASS);
                    errorInputProgram2.setText(ERROR_TEXT);
                });
                return;
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            Platform.runLater(() -> {
                stopWait();
                inputFieldProgram2.getStyleClass().add(ERROR_FIELD_CLASS);
                errorInputProgram2.setText(ERROR_TEXT);
            });
            return;
        }

        StringBuilder answer = new StringBuilder("1");

        while (n.compareTo(BigInteger.ONE) > 0) {
            if (isCanceled) {
                // Останавливаем расчеты, если пользователь не хочет ждать
                Platform.runLater(this::stopWait);
                return;
            }
            while (n.remainder(div).compareTo(BigInteger.ZERO) == 0) {
                answer.append(" * ").append(div);
                n = n.divide(div);
            }
            if (div.compareTo(two) == 0) {
                div = div.add(BigInteger.ONE);
            } else {
                div = div.add(two);
            }
        }

        // Выводим результат
        Platform.runLater(() -> {
            outputFieldProgram2.setText(answer.toString());
            stopWait();
        });
    }

    /**
     * Блокирует интерфейс на время расчетов.
     */
    private void startWait() {
        isCanceled = false;
        errorInputProgram2.setStyle(ERROR_FIELD_TEXT_FILL_DISABLED);
        errorInputProgram2.setText("Подождите...");
        inputFieldProgram2.setDisable(true);
        okButtonProgram2.setDisable(true);
        outputFieldProgram2.setDisable(true);
        cancelProgram2.setVisible(true);
    }

    /**
     * Разблокирует интерфейс после произведенных расчетов.
     */
    private void stopWait() {
        errorInputProgram2.setStyle(ERROR_FIELD_TEXT_FILL);
        errorInputProgram2.setText("");
        inputFieldProgram2.setDisable(false);
        okButtonProgram2.setDisable(false);
        outputFieldProgram2.setDisable(false);
        cancelProgram2.setVisible(false);
    }

}
