package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Модульные тесты визуальных компонентов основного окна приложения.
 */
public class MainWindowTest extends InterfacesTest {

    @SuppressWarnings("unused")
    @Start
    private void start(Stage stage) throws IOException {
        prepareMainWindow(stage);
    }

    /**
     * Проверяем, что окно содержит корректный заголовок.
     */
    @Test
    void givenMainWindow_whenShowed_thenHasCorrectTitle() {
        // arrange
        String expectedTitle = "Изучение правил построения интерфейсов";

        // act
        String actualTitle = windowStage.getTitle();

        // assert
        assertEquals(expectedTitle, actualTitle);
    }

    /**
     * Проверяем наличие в окне всех визуальных компонентов.
     */
    @Test
    void givenMainWindow_whenShowed_thenHasItems() {
        // arrange

        // act
        AnchorPane root = (AnchorPane) windowScene.lookup("#root");
        MenuBar menuBar = (MenuBar) windowScene.lookup("#mainMenu");
        HBox studentBox = (HBox) windowScene.lookup("#studentBox");
        ImageView studentView = (ImageView) windowScene.lookup("#studentView");
        Button variantChoice = (Button) windowScene.lookup("#variantChoice");
        ImageView variantChoiceView = (ImageView) windowScene.lookup("#variantChoiceView");
        Button theory = (Button) windowScene.lookup("#theory");
        ImageView theoryView = (ImageView) windowScene.lookup("#theoryView");
        Button help = (Button) windowScene.lookup("#help");
        ImageView helpView = (ImageView) windowScene.lookup("#helpView");
        Label variantLabel = (Label) windowScene.lookup("#variantLabel");

        // assert
        Assertions.assertNotNull(root);
        Assertions.assertNotNull(menuBar);
        Assertions.assertNotNull(studentBox);
        Assertions.assertNotNull(studentView);
        Assertions.assertNotNull(variantChoice);
        Assertions.assertNotNull(variantChoiceView);
        Assertions.assertNotNull(theory);
        Assertions.assertNotNull(theoryView);
        Assertions.assertNotNull(help);
        Assertions.assertNotNull(helpView);
        Assertions.assertNotNull(variantLabel);
    }

    /**
     * Проверяем, что главное меню содержит корректную структуру.
     */
    @Test
    void givenMainForm_whenShowed_thenMainMenuIsCorrect() {
        // arrange
        MenuBar menuBar = (MenuBar) windowScene.lookup("#mainMenu");
        String expectedMenu1Text = "Файл";
        String expectedMenu2Text = "Справка";
        String expectedMenuItem11Text = "Выбор варианта";
        String expectedMenuItem13Text = "Выход";
        String expectedMenuItem21Text = "Теория";
        String expectedMenuItem22Text = "Об авторе";

        // act
        Menu menu1 = menuBar.getMenus().get(0);
        String actualMenu1Text = menu1.getText();
        Menu menu2 = menuBar.getMenus().get(1);
        String actualMenu2Text = menu2.getText();
        MenuItem item11 = menu1.getItems().get(0);
        String actualMenuItem11Text = item11.getText();
        MenuItem item12 = menu1.getItems().get(1);
        boolean isMenuItem12Separator =
                item12.getClass().isAssignableFrom(SeparatorMenuItem.class);
        MenuItem item13 = menu1.getItems().get(2);
        String actualMenuItem13Text = item13.getText();
        MenuItem item21 = menu2.getItems().get(0);
        String actualMenuItem21Text = item21.getText();
        MenuItem item22 = menu2.getItems().get(1);
        String actualMenuItem22Text = item22.getText();

        // assert
        Assertions.assertEquals(expectedMenu1Text, actualMenu1Text);
        Assertions.assertEquals(expectedMenu2Text, actualMenu2Text);
        Assertions.assertEquals(expectedMenuItem11Text, actualMenuItem11Text);
        Assertions.assertEquals(expectedMenuItem13Text, actualMenuItem13Text);
        Assertions.assertEquals(expectedMenuItem21Text, actualMenuItem21Text);
        Assertions.assertEquals(expectedMenuItem22Text, actualMenuItem22Text);
        Assertions.assertTrue(isMenuItem12Separator);
    }

}
