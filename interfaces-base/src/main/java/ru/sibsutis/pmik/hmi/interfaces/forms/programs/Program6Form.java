package ru.sibsutis.pmik.hmi.interfaces.forms.programs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

/**
 * Контроллер формы программы по варианту 6.
 */
@SuppressWarnings("unused")
public class Program6Form extends AbstractProgramForm {

    private static final String HEADER = "Симулятор подбрасывания монеты";

    private static final String COIN_IMAGE_PATH = "/images/coin.png";

    private int eagle = 0;
    private int tails = 0;

    private int count = 0;

    private Random random;

    /**
     * Изображение монеты.
     */
    @FXML
    ImageView coin;

    /**
     * Метка "Успех".
     */
    @FXML
    Label successLabel;

    /**
     * Поле вывода процента выпадения орла.
     */
    @FXML
    Label success;

    /**
     * Метка "Неудача".
     */
    @FXML
    Label failLabel;

    /**
     * Поле вывода процента выпадения решки.
     */
    @FXML
    Label fail;

    /**
     * Кнопка сброса программы в начальное состояние.
     */
    @FXML
    Button resetProgram6;

    /**
     * Вызывается автоматически после загрузки формы.
     */
    @FXML
    protected void initialize() {
        super.initialize();

        random = new Random(42);

        // Устанавливаем изображение монеты
        URL coinImagePath =
                Objects.requireNonNull(getClass().getResource(COIN_IMAGE_PATH));
        Image coinImage = new Image(coinImagePath.toString(), true);
        coin.setImage(coinImage);

        // Устанавливаем обработчик для кликов по монете
        coin.setOnMouseClicked(event -> calculate());

        // Устанавливаем обработчик кнопки сброса
        resetProgram6.setOnAction(event -> {
            success.setText("");
            fail.setText("");
            count = 0;
            eagle = 0;
            tails = 0;
        });

        // Устанавливаем шрифты
        setFont(successLabel, 28, false, false);
        setFont(success, 28, false, false);
        setFont(failLabel, 28, false, false);
        setFont(fail, 28, false, false);
        setFont(resetProgram6, 20, true, false);
    }

    @Override
    protected String getHeader() {
        return HEADER;
    }

    /**
     * Выполняет симуляцию "подброса" монеты.
     */
    private void calculate() {
        count++;
        if (random.nextBoolean()) {
            eagle++;
        } else {
            tails++;
        }
        MathContext mc = new MathContext(2);
        BigDecimal bigDecimal = new BigDecimal(eagle * 100.0 / count, mc);
        success.setText(bigDecimal.toPlainString() + " %");
        bigDecimal = new BigDecimal(tails * 100.0 / count, mc);
        fail.setText(bigDecimal.toPlainString() + " %");
    }

}
