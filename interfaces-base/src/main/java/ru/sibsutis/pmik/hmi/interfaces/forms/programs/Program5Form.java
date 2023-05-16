package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Контроллер формы программы по варианту 5.
 */
@SuppressWarnings("unused")
public class Program5Form extends AbstractProgramForm {

    private static final String HEADER = "Счастливый билет";

    private static final String ERROR_FIELD_CLASS = "error-field";

    private static final String ERROR_TEXT = "Введите целое положительное число";

    private static final String ERROR_FIELD_TEXT_FILL = "-fx-text-fill: #d35244;";

    private static final String ERROR_FIELD_TEXT_FILL_DISABLED ="-fx-text-fill: black;";

    private static final String TICKETS_IMAGE_PATH = "/images/tickets.png";

    private volatile boolean isCanceled = false;

    private ExecutorService executorService;

    /**
     * Первая цифра билета.
     */
    @FXML
    TextField digit1;

    /**
     * Вторая цифра билета.
     */
    @FXML
    TextField digit2;

    /**
     * Третья цифра билета.
     */
    @FXML
    TextField digit3;

    /**
     * Четвертая цифра билета.
     */
    @FXML
    TextField digit4;

    /**
     * Пятая цифра билета.
     */
    @FXML
    TextField digit5;

    /**
     * Шестая цифра билета.
     */
    @FXML
    TextField digit6;

    /**
     * Поиск счастливых билетов.
     */
    @FXML
    Button searchProgram5;

    /**
     * Изображение пачки билетов.
     */
    @FXML
    ImageView tickets;

    /**
     * Сообщение об ошибке ввода.
     */
    @FXML
    Label errorInputProgram5;

    /**
     * Метка поля вывода результата поиска.
     */
    @FXML
    Label resultLabelProgram5;

    /**
     * Поле вывода результата поиска билетов, начинающихся на 0.
     */
    @FXML
    ComboBox<String> resultProgram5_0;

    /**
     * Поле вывода результата поиска билетов, начинающихся на 1.
     */
    @FXML
    ComboBox<String> resultProgram5_1;

    /**
     * Поле вывода результата поиска билетов, начинающихся на 2.
     */
    @FXML
    ComboBox<String> resultProgram5_2;

    /**
     * Поле вывода результата поиска билетов, начинающихся на 3.
     */
    @FXML
    ComboBox<String> resultProgram5_3;

    /**
     * Поле вывода результата поиска билетов, начинающихся на 4.
     */
    @FXML
    ComboBox<String> resultProgram5_4;

    /**
     * Поле вывода результата поиска билетов, начинающихся на 5.
     */
    @FXML
    ComboBox<String> resultProgram5_5;

    /**
     * Поле вывода результата поиска билетов, начинающихся на 6.
     */
    @FXML
    ComboBox<String> resultProgram5_6;

    /**
     * Поле вывода результата поиска билетов, начинающихся на 7.
     */
    @FXML
    ComboBox<String> resultProgram5_7;

    /**
     * Поле вывода результата поиска билетов, начинающихся на 8.
     */
    @FXML
    ComboBox<String> resultProgram5_8;

    /**
     * Поле вывода результата поиска билетов, начинающихся на 9.
     */
    @FXML
    ComboBox<String> resultProgram5_9;

    /**
     * Кнопка "Отменить".
     */
    @FXML
    Button cancelProgram5;

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

        // Устанавливаем изображение на кнопку поиска
        URL ticketsImagePath =
                Objects.requireNonNull(getClass().getResource(TICKETS_IMAGE_PATH));
        Image ticketsImage = new Image(ticketsImagePath.toString(), true);
        tickets.setImage(ticketsImage);

        // Устанавливаем обработчики ввода цифр билета
        digit1.textProperty().addListener(
                (observable, oldValue, newValue) -> checkAndReset(newValue, digit1));
        digit2.textProperty().addListener(
                (observable, oldValue, newValue) -> checkAndReset(newValue, digit2));
        digit3.textProperty().addListener(
                (observable, oldValue, newValue) -> checkAndReset(newValue, digit3));
        digit4.textProperty().addListener(
                (observable, oldValue, newValue) -> checkAndReset(newValue, digit4));
        digit5.textProperty().addListener(
                (observable, oldValue, newValue) -> checkAndReset(newValue, digit5));
        digit6.textProperty().addListener(
                (observable, oldValue, newValue) -> checkAndReset(newValue, digit6));

        // Устанавливаем обработку кнопки поиска
        searchProgram5.setOnAction(event -> {
            startWait();
            executorService.shutdownNow();
            executorService = Executors.newSingleThreadExecutor();
            executorService.submit(this::search);
        });

        // Устанавливаем обработчик для отмены процесса вычислений.
        cancelProgram5.setOnAction(event -> isCanceled = true);

        // Устанавливаем шрифты
        setFont(digit1, 16, false, false);
        setFont(digit2, 16, false, false);
        setFont(digit3, 16, false, false);
        setFont(digit4, 16, false, false);
        setFont(digit5, 16, false, false);
        setFont(digit6, 16, false, false);
        setFont(errorInputProgram5, 16, false, false);
        setFont(resultLabelProgram5, 16, false, false);
    }

    @Override
    protected String getHeader() {
        return HEADER;
    }

    /**
     * Выполняет поиск счастливых билетов.
     */
    private void search() {
        // Проверяем корректность ввода
        int[] digits = new int[6];

        for (int i = 0; i < 6; i++) {
            TextField currentDigit = null;
            try {
                Field digit = this.getClass().getDeclaredField("digit" + (i + 1));
                digit.setAccessible(true);
                currentDigit = (TextField) digit.get(this);
                digits[i] = Integer.parseInt(currentDigit.getText());
                digit.setAccessible(false);
            } catch (NumberFormatException ex) {
                if (currentDigit != null) {
                    final TextField finalCurrentDigit = currentDigit;
                    Platform.runLater(() -> {
                        stopWait();
                        finalCurrentDigit.getStyleClass().add(ERROR_FIELD_CLASS);
                        errorInputProgram5.setText(ERROR_TEXT);
                    });
                }
                return;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
                return;
            }
        }

        List<String> happyTickets_0 = new LinkedList<>();
        List<String> happyTickets_1 = new LinkedList<>();
        List<String> happyTickets_2 = new LinkedList<>();
        List<String> happyTickets_3 = new LinkedList<>();
        List<String> happyTickets_4 = new LinkedList<>();
        List<String> happyTickets_5 = new LinkedList<>();
        List<String> happyTickets_6 = new LinkedList<>();
        List<String> happyTickets_7 = new LinkedList<>();
        List<String> happyTickets_8 = new LinkedList<>();
        List<String> happyTickets_9 = new LinkedList<>();
        int maximum = 0;
        for (int i = 0; i < 6; i++) {
            maximum += Math.pow(10, i) * digits[5 - i];
        }
        for (int i1 = 0; i1 < 10; i1++) {
            for (int i2 = 0; i2 < 10; i2++) {
                for (int i3 = 0; i3 < 10; i3++) {
                    for (int i4 = 0; i4 < 10; i4++) {
                        for (int i5 = 0; i5 < 10; i5++) {
                            for (int i6 = 0; i6 < 10; i6++) {
                                if (isCanceled) {
                                    // Останавливаем расчеты, если пользователь не хочет ждать
                                    Platform.runLater(this::stopWait);
                                    return;
                                }
                                if (i1 + i2 + i3 == i4 + i5 + i6) {
                                    String currentValue = String.valueOf(i1) +
                                            i2 + i3 + i4 + i5 + i6;
                                    switch (i1) {
                                        case 0:
                                            happyTickets_0.add(currentValue);
                                            break;
                                        case 1:
                                            happyTickets_1.add(currentValue);
                                            break;
                                        case 2:
                                            happyTickets_2.add(currentValue);
                                            break;
                                        case 3:
                                            happyTickets_3.add(currentValue);
                                            break;
                                        case 4:
                                            happyTickets_4.add(currentValue);
                                            break;
                                        case 5:
                                            happyTickets_5.add(currentValue);
                                            break;
                                        case 6:
                                            happyTickets_6.add(currentValue);
                                            break;
                                        case 7:
                                            happyTickets_7.add(currentValue);
                                            break;
                                        case 8:
                                            happyTickets_8.add(currentValue);
                                            break;
                                        case 9:
                                            happyTickets_9.add(currentValue);
                                            break;
                                        default:
                                            throw new IllegalStateException("Неизвестная ошибка");
                                    }
                                }

                                int currentNumber = i1 * 100000
                                        + i2 * 10000
                                        + i3 * 1000
                                        + i4 * 100
                                        + i5 * 10
                                        + i6;
                                if (currentNumber == maximum) {
                                    // Выводим результат
                                    Platform.runLater(() -> {
                                        resultProgram5_0.setItems(FXCollections.observableList(happyTickets_0));
                                        resultProgram5_1.setItems(FXCollections.observableList(happyTickets_1));
                                        resultProgram5_2.setItems(FXCollections.observableList(happyTickets_2));
                                        resultProgram5_3.setItems(FXCollections.observableList(happyTickets_3));
                                        resultProgram5_4.setItems(FXCollections.observableList(happyTickets_4));
                                        resultProgram5_5.setItems(FXCollections.observableList(happyTickets_5));
                                        resultProgram5_6.setItems(FXCollections.observableList(happyTickets_6));
                                        resultProgram5_7.setItems(FXCollections.observableList(happyTickets_7));
                                        resultProgram5_8.setItems(FXCollections.observableList(happyTickets_8));
                                        resultProgram5_9.setItems(FXCollections.observableList(happyTickets_9));
                                        stopWait();
                                    });
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Обработчик поля ввода.
     *
     * @param newValue Новое значение поля.
     */
    private void checkAndReset(String newValue, TextField digit) {
        if (newValue.length() <= 1) {
            errorInputProgram5.setText("");
            digit.getStyleClass().remove(ERROR_FIELD_CLASS);
        }
    }

    /**
     * Блокирует интерфейс на время расчетов.
     */
    private void startWait() {
        isCanceled = false;
        resultProgram5_0.getItems().clear();
        resultProgram5_1.getItems().clear();
        resultProgram5_2.getItems().clear();
        resultProgram5_3.getItems().clear();
        resultProgram5_4.getItems().clear();
        resultProgram5_5.getItems().clear();
        resultProgram5_6.getItems().clear();
        resultProgram5_7.getItems().clear();
        resultProgram5_8.getItems().clear();
        resultProgram5_9.getItems().clear();
        errorInputProgram5.setStyle(ERROR_FIELD_TEXT_FILL_DISABLED);
        errorInputProgram5.setText("Подождите...");
        digit1.setDisable(true);
        digit2.setDisable(true);
        digit3.setDisable(true);
        digit4.setDisable(true);
        digit5.setDisable(true);
        digit6.setDisable(true);
        searchProgram5.setDisable(true);
        resultLabelProgram5.setDisable(true);
        resultProgram5_0.setDisable(true);
        resultProgram5_1.setDisable(true);
        resultProgram5_2.setDisable(true);
        resultProgram5_3.setDisable(true);
        resultProgram5_4.setDisable(true);
        resultProgram5_5.setDisable(true);
        resultProgram5_6.setDisable(true);
        resultProgram5_7.setDisable(true);
        resultProgram5_8.setDisable(true);
        resultProgram5_9.setDisable(true);
        cancelProgram5.setVisible(true);
    }

    /**
     * Разблокирует интерфейс после произведенных расчетов.
     */
    private void stopWait() {
        errorInputProgram5.setStyle(ERROR_FIELD_TEXT_FILL);
        errorInputProgram5.setText("");
        digit1.setDisable(false);
        digit2.setDisable(false);
        digit3.setDisable(false);
        digit4.setDisable(false);
        digit5.setDisable(false);
        digit6.setDisable(false);
        searchProgram5.setDisable(false);
        resultLabelProgram5.setDisable(false);
        resultProgram5_0.setDisable(false);
        resultProgram5_1.setDisable(false);
        resultProgram5_2.setDisable(false);
        resultProgram5_3.setDisable(false);
        resultProgram5_4.setDisable(false);
        resultProgram5_5.setDisable(false);
        resultProgram5_6.setDisable(false);
        resultProgram5_7.setDisable(false);
        resultProgram5_8.setDisable(false);
        resultProgram5_9.setDisable(false);
        cancelProgram5.setVisible(false);
    }

}
