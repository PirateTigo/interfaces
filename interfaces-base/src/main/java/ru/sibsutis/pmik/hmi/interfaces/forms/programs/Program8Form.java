package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * Контроллер формы программы по варианту 8.
 */
@SuppressWarnings("unused")
public class Program8Form extends AbstractProgramForm {

    private static final String HEADER = "Шифр Цезаря";

    private static final String ERROR_FIELD_CLASS = "error-field";

    private static final String CIPHER_KEY_ERROR_TEXT = "Введите положительное целое число";

    private static final String ENCODE_ERROR_TEXT = "Введите текст сообщения";

    private static final String DECODE_ERROR_TEXT = "Введите текст зашифрованного сообщения";

    /**
     * Название поля ввода ключа.
     */
    @FXML
    Label cipherKeyLabel;

    /**
     * Поле ввода ключа.
     */
    @FXML
    TextField cipherKey;

    /**
     * Ошибка ввода ключа.
     */
    @FXML
    Label cipherKeyError;

    /**
     * Название поля ввода сообщения.
     */
    @FXML
    Label messageLabel;

    /**
     * Поле ввода сообщения.
     */
    @FXML
    TextArea message;

    /**
     * Ошибка ввода сообщения.
     */
    @FXML
    Label messageError;

    /**
     * Кнопка "Зашифровать".
     */
    @FXML
    Button toEncode;

    /**
     * Кнопка "Расшифровать".
     */
    @FXML
    Button toDecode;

    /**
     * Поле ввода зашифрованного сообщения
     */
    @FXML
    TextArea cipher;

    /**
     * Ошибка ввода зашифрованного сообщения.
     */
    @FXML
    Label cipherError;

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    protected void initialize() {
        super.initialize();

        // Устанавливаем обработчик ввода кода
        cipherKey.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    cipherKeyError.setText("");
                    cipherKey.getStyleClass().remove(ERROR_FIELD_CLASS);
                });

        // Устанавливаем обработчик ввода сообщения
        message.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    messageError.setText("");
                    message.getStyleClass().remove(ERROR_FIELD_CLASS);
                });

        // Устанавливаем обработчик кнопки "Зашифровать"
        toEncode.setOnAction(event -> {
            int code = getCode();
            if (code <= 0) {
                return;
            }

            String messageText = message.getText();
            if (messageText.isBlank()) {
                messageError.setText(ENCODE_ERROR_TEXT);
                message.getStyleClass().add(ERROR_FIELD_CLASS);
                return;
            }

            cipher.clear();
            cipherError.setText("");
            cipher.getStyleClass().remove(ERROR_FIELD_CLASS);
            doEncode(messageText, code);
        });

        // Устанавливаем обработчик кнопки "Расшифровать"
        toDecode.setOnAction(event -> {
            int code = getCode();
            if (code <= 0) {
                return;
            }

            String cipherText = cipher.getText();
            if (cipherText.isBlank()) {
                cipherError.setText(ENCODE_ERROR_TEXT);
                cipher.getStyleClass().add(ERROR_FIELD_CLASS);
                return;
            }

            message.clear();
            messageError.setText("");
            message.getStyleClass().remove(ERROR_FIELD_CLASS);
            doDecode(cipherText, code);
        });

        // Устанавливаем шрифты
        setFont(cipherKeyLabel, 16, false, false);
        setFont(cipherKey, 16, false, false);
        setFont(cipherKeyError, 16, false, false);
        setFont(messageLabel, 16, false, false);
        setFont(message, 16, false, false);
        setFont(messageError, 16, false, false);
        setFont(toDecode, 16, true, false);
        setFont(toEncode, 16, true, false);
        setFont(cipher, 16, false, false);
        setFont(cipherError, 16, false, false);
    }

    @Override
    protected String getHeader() {
        return HEADER;
    }

    /**
     * Зашифровать сообщение.
     */
    private void doEncode(String messageText, int code) {
        StringBuilder cipherText = new StringBuilder();
        for (final Character character: messageText.toCharArray()) {
            cipherText.append(((Character)((char)((int)character + code))));
        }
        cipher.setText(cipherText.toString());
    }

    /**
     * Расшифровать сообщение.
     */
    private void doDecode(String cipherText, int code) {
        StringBuilder messageText = new StringBuilder();
        for (final Character character: cipherText.toCharArray()) {
            messageText.append(((Character)((char)((int)character - code))));
        }
        message.setText(messageText.toString());
    }

    /**
     * Получает введенный код.
     *
     * @return Код шифра Цезаря.
     */
    private int getCode() {
        int code = -1;
        try {
            code = Integer.parseInt(cipherKey.getText());
            if (code <= 0) {
                cipherKeyError.setText(CIPHER_KEY_ERROR_TEXT);
                cipherKey.getStyleClass().add(ERROR_FIELD_CLASS);
                return -1;
            }
            cipherKeyError.setText("");
            cipherKey.getStyleClass().remove(ERROR_FIELD_CLASS);
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            cipherKeyError.setText(CIPHER_KEY_ERROR_TEXT);
            cipherKey.getStyleClass().add(ERROR_FIELD_CLASS);
        }
        return code;
    }

}
