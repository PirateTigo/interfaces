package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Контроллер формы программы по варианту 3.
 */
@SuppressWarnings("unused")
public class Program3Form extends AbstractProgramForm {

    private static final String HEADER = "Следующее простое число";

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
    TextField inputFieldProgram3;

    /**
     * Описатель поля ввода.
     */
    @FXML
    Label inputFieldLabelProgram3;

    /**
     * Сообщение об ошибке ввода.
     */
    @FXML
    Label errorInputProgram3;

    /**
     * Кнопка "Отменить".
     */
    @FXML
    Button cancelProgram3;

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
        inputFieldProgram3.setOnKeyPressed(event -> {
            if (Objects.requireNonNull(event.getCode()) == KeyCode.ENTER) {
                startWait();
                executorService.submit(this::calculate);
            }
        });

        // Устанавливаем обработчик для отмены процесса вычислений.
        cancelProgram3.setOnAction(event -> isCanceled = true);

        // Устанавливаем обработчик сброса полей после ввода
        inputFieldProgram3.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    inputFieldProgram3.getStyleClass().remove(ERROR_FIELD_CLASS);
                    errorInputProgram3.setText("");
                });

        // Устанавливаем шрифты
        setFont(inputFieldProgram3, 16, false, false);
        setFont(errorInputProgram3, 16, false, false);
        setFont(inputFieldLabelProgram3, 16, false, false);
    }

    @Override
    protected String getHeader() {
        return HEADER;
    }

    /**
     * Вычисляет следующее простое число.
     */
    private void calculate() {
        BigInteger n;

        // Проверяем корректность ввода
        try {
            n = new BigInteger(inputFieldProgram3.getText());
            if (n.compareTo(BigInteger.ONE) < 0) {
                Platform.runLater(() -> {
                    stopWait();
                    inputFieldProgram3.getStyleClass().add(ERROR_FIELD_CLASS);
                    errorInputProgram3.setText(ERROR_TEXT);
                });
                return;
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            Platform.runLater(() -> {
                stopWait();
                inputFieldProgram3.getStyleClass().add(ERROR_FIELD_CLASS);
                errorInputProgram3.setText(ERROR_TEXT);
            });
            return;
        }

        do {
            if (isCanceled) {
                // Останавливаем расчеты, если пользователь не хочет ждать
                Platform.runLater(this::stopWait);
                return;
            }
            n = n.add(BigInteger.ONE);
        } while (!n.isProbablePrime(100));

        // Выводим результат
        final BigInteger finalN = n;
        Platform.runLater(() -> {
            stopWait();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Результат");
            alert.setHeaderText("Следующее простое число после "
                    + inputFieldProgram3.getText() + ":");
            alert.setContentText(finalN.toString());
            alert.initOwner(parentForm.getMainStage());
            alert.showAndWait();
            inputFieldProgram3.setText(finalN.toString());
        });
    }

    /**
     * Блокирует интерфейс на время расчетов.
     */
    private void startWait() {
        isCanceled = false;
        errorInputProgram3.setStyle(ERROR_FIELD_TEXT_FILL_DISABLED);
        errorInputProgram3.setText("Подождите...");
        inputFieldProgram3.setDisable(true);
        inputFieldLabelProgram3.setDisable(true);
        cancelProgram3.setVisible(true);
    }

    /**
     * Разблокирует интерфейс после произведенных расчетов.
     */
    private void stopWait() {
        errorInputProgram3.setStyle(ERROR_FIELD_TEXT_FILL);
        errorInputProgram3.setText("");
        inputFieldProgram3.setDisable(false);
        inputFieldLabelProgram3.setDisable(false);
        cancelProgram3.setVisible(false);
    }

}
