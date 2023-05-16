package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import ru.sibsutis.pmik.hmi.interfaces.components.Autocomplete;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Контроллер формы программы по варианту 7.
 */
@SuppressWarnings("unused")
public class Program7Form extends AbstractProgramForm {

    private static final String HEADER = "Поле Чудес";

    private static final String ERROR_FIELD_CLASS = "error-field";

    private static final String ERROR_TEXT = "Введите от 1 до 20 символов русского алфавита и/или пробелов";

    private static final String SECRET_LETTER_CLASS = "secret-letter";

    private static final String NON_SECRET_LETTER_CLASS = "non-secret-letter";

    private Autocomplete phrases;

    /**
     * Исходные данные для игры.
     */
    @FXML
    HBox sourceData;

    /**
     * Кнопка запуска игры.
     */
    @FXML
    Button startProgram7;

    /**
     * Сообщение об ошибке ввода.
     */
    @FXML
    Label errorInputProgram7;

    /**
     * Загаданное слово.
     */
    @FXML
    HBox gameWord;

    /**
     * Кнопка сброса игры в начальное состояние.
     */
    @FXML
    Button resetProgram7;

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    protected void initialize() {
        super.initialize();

        // Устанавливаем выпадающий список и кнопку ввода новой фразы
        phrases = new Autocomplete();
        phrases.setPrefWidth(200);
        phrases.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    errorInputProgram7.setText("");
                    phrases.getStyleClass().remove(ERROR_FIELD_CLASS);
                });
        sourceData.getChildren().add(phrases);
        Button addPhrase = new Button("+");
        Pattern onlyRussianLetters = Pattern.compile("^[а-яA-Я ёЁ]{1,20}$");
        addPhrase.setOnAction(event -> {
            String text = phrases.getText();
            String clearedText = text.trim().replace(" +", " ");
            Matcher matcher = onlyRussianLetters.matcher(clearedText);
            if (matcher.find()) {
                phrases.addEntry(clearedText.toUpperCase(Locale.ROOT));
                phrases.clear();
            } else {
                phrases.getStyleClass().add(ERROR_FIELD_CLASS);
                errorInputProgram7.setText(ERROR_TEXT);
            }
        });
        sourceData.getChildren().add(addPhrase);

        Random random = new Random(42);

        // Устанавливаем обработчик кнопки запуска игры
        startProgram7.setOnAction(event -> {
            List<String> phraseEntries = phrases.getEntries();
            if (!phraseEntries.isEmpty()) {
                int entryNumber = Math.abs(random.nextInt()) % phraseEntries.size();
                String phrase = phraseEntries.get(entryNumber);
                for (final Character letter : phrase.toCharArray()) {
                    Label letterLabel = new Label(letter.toString());
                    setFont(letterLabel, 48, false, false);
                    if (!" ".equals(letter.toString())) {
                        letterLabel.getStyleClass().add(SECRET_LETTER_CLASS);
                    }
                    gameWord.getChildren().add(letterLabel);
                }
            }
        });

        // Устанавливаем обработчик нажатий клавиш клавиатуры
        programContent.setOnKeyPressed(keyEvent -> {
            for (final Node node: gameWord.getChildren()) {
                String text = ((Label) node).getText();
                if (!text.equals(" ")) {
                    if (keyEvent.getText().toUpperCase(Locale.ROOT).equals(text)) {
                        node.getStyleClass().remove(SECRET_LETTER_CLASS);
                        node.getStyleClass().add(NON_SECRET_LETTER_CLASS);
                    }
                }
            }
        });

        // Устанавливаем обработку кнопки сброса игры
        resetProgram7.setOnAction(event -> {
            gameWord.getChildren().clear();
            phrases.clearEntries();
            phrases.requestFocus();
        });

        // Устанавливаем шрифты
        setFont(header, 64, true, true);
        setFont(phrases, 16, false, false);
        setFont(addPhrase, 16, true, false);
        setFont(startProgram7, 16, true, false);
        setFont(errorInputProgram7, 16, false, false);
        setFont(resetProgram7, 16, true, false);
    }

    @Override
    protected String getHeader() {
        return HEADER;
    }

}
