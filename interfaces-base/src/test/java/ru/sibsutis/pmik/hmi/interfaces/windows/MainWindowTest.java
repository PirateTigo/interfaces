package ru.sibsutis.pmik.hmi.interfaces.windows;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Isolated;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.Start;
import ru.sibsutis.pmik.hmi.interfaces.InterfacesTest;
import ru.sibsutis.pmik.hmi.interfaces.forms.MainForm;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Модульные тесты визуальных компонентов основного окна приложения.
 */
@Isolated
public class MainWindowTest extends InterfacesTest {

    public static final int WAITING_TIMEOUT = 5;

    public static final String MAIN_MENU_SELECTOR = "#mainMenu";
    private static final String VARIANT_CHOICE_SELECTOR = "#variantChoice";
    private static final String THEORY_SELECTOR = "#theory";
    private static final String HELP_SELECTOR = "#help";
    public static final String HELP_CONTENT_SELECTOR = "#helpContent";
    private static final String PROGRAM_CONTENT_SELECTOR = "#programContent";

    private static FxRobot robot;

    @BeforeAll
    static void setUpClass() {
        robot = new FxRobot();
    }

    @SuppressWarnings("unused")
    @Start
    protected void start(Stage stage) throws IOException {
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
        MenuBar menuBar = (MenuBar) windowScene.lookup(MAIN_MENU_SELECTOR);
        ToolBar buttons = (ToolBar) windowScene.lookup("#buttons");
        HBox studentBox = (HBox) windowScene.lookup("#studentBox");
        ImageView studentView = (ImageView) windowScene.lookup("#studentView");
        Button variantChoice = (Button) windowScene.lookup(VARIANT_CHOICE_SELECTOR);
        ImageView variantChoiceView = (ImageView) windowScene.lookup("#variantChoiceView");
        Button theory = (Button) windowScene.lookup(THEORY_SELECTOR);
        ImageView theoryView = (ImageView) windowScene.lookup("#theoryView");
        Button help = (Button) windowScene.lookup(HELP_SELECTOR);
        ImageView helpView = (ImageView) windowScene.lookup("#helpView");
        AnchorPane content = (AnchorPane) windowScene.lookup("#content");
        HBox programContent = (HBox) windowScene.lookup(PROGRAM_CONTENT_SELECTOR);
        Label variantLabel = (Label) windowScene.lookup("#variantLabel");

        // assert
        Assertions.assertNotNull(root);
        Assertions.assertNotNull(menuBar);
        Assertions.assertNotNull(buttons);
        Assertions.assertNotNull(studentBox);
        Assertions.assertNotNull(studentView);
        Assertions.assertNotNull(variantChoice);
        Assertions.assertNotNull(variantChoiceView);
        Assertions.assertNotNull(theory);
        Assertions.assertNotNull(theoryView);
        Assertions.assertNotNull(help);
        Assertions.assertNotNull(helpView);
        Assertions.assertNotNull(content);
        Assertions.assertNotNull(programContent);
        Assertions.assertNotNull(variantLabel);
    }

    /**
     * Проверяем, что главное меню содержит корректную структуру.
     */
    @Test
    void givenMainForm_whenShowed_thenMainMenuIsCorrect() {
        // arrange
        MenuBar menuBar = (MenuBar) windowScene.lookup(MAIN_MENU_SELECTOR);
        String expectedMenu1Text = "Файл";
        String expectedMenu2Text = "Справка";
        String expectedMenuItem11Text = "Выбор варианта";
        String expectedMenuItem12Text = "Анализ программы";
        String expectedMenuItem14Text = "Выход";
        String expectedMenuItem21Text = "Теория";
        String expectedMenuItem22Text = "Об авторе";

        // act
        ObservableList<Menu> menus = menuBar.getMenus();
        Menu menu1 = menus.get(0);
        Menu menu2 = menus.get(1);
        String actualMenu1Text = menu1.getText();
        String actualMenu2Text = menu2.getText();

        ObservableList<MenuItem> menu1Items = menu1.getItems();
        MenuItem item11 = menu1Items.get(0);
        MenuItem item12 = menu1Items.get(1);
        MenuItem item13 = menu1Items.get(2);
        MenuItem item14 = menu1Items.get(3);
        String actualMenuItem11Text = item11.getText();
        String actualMenuItem12Text = item12.getText();
        boolean isMenuItem13Separator =
                item13.getClass().isAssignableFrom(SeparatorMenuItem.class);
        String actualMenuItem14Text = item14.getText();

        ObservableList<MenuItem> menu2Items = menu2.getItems();
        MenuItem item21 = menu2Items.get(0);
        MenuItem item22 = menu2Items.get(1);
        String actualMenuItem21Text = item21.getText();
        String actualMenuItem22Text = item22.getText();

        // assert
        Assertions.assertEquals(expectedMenu1Text, actualMenu1Text);
        Assertions.assertEquals(expectedMenu2Text, actualMenu2Text);
        Assertions.assertEquals(expectedMenuItem11Text, actualMenuItem11Text);
        Assertions.assertEquals(expectedMenuItem12Text, actualMenuItem12Text);
        Assertions.assertEquals(expectedMenuItem14Text, actualMenuItem14Text);
        Assertions.assertEquals(expectedMenuItem21Text, actualMenuItem21Text);
        Assertions.assertEquals(expectedMenuItem22Text, actualMenuItem22Text);
        Assertions.assertTrue(isMenuItem13Separator);
    }

    /**
     * Проверяем, что при нажатии на кнопку "Вариант" отображается предупреждающее
     * диалоговое окно.
     */
    @Test
    void givenMainWindow_whenVariantChoicePressed_thenDialogShowed() throws ExecutionException, InterruptedException {
        // arrange
        Button variantChoice = (Button) windowScene.lookup(VARIANT_CHOICE_SELECTOR);

        // act
        Platform.runLater(variantChoice::fire);

        // assert
        Assertions.assertTrue(checkDialogByTitle("Запрос информации"));
    }

    /**
     * Проверяем, что при нажатии на кнопку меню "Файл->Выбор варианта" отображается
     * предупреждающее диалоговое окно.
     */
    @Test
    void givenMainWindow_whenMenuVariantChoosingPressed_thenDialogShowed() throws ExecutionException, InterruptedException {
        // arrange
        MenuBar menuBar = (MenuBar) windowScene.lookup(MAIN_MENU_SELECTOR);
        Menu menu1 = menuBar.getMenus().get(0);
        MenuItem item11 = menu1.getItems().get(0);

        // act
        Platform.runLater(item11::fire);

        // assert
        Assertions.assertTrue(checkDialogByTitle("Запрос информации"));
    }

    /**
     * Проверяем, что приложение завершается при нажатии на кнопку выхода.
     */
    @Test
    void givenMainWindow_whenMenuButtonExitPressed_thenApplicationClosed() {
        try (MockedStatic<Platform> platformMock = Mockito.mockStatic(Platform.class)) {
            // arrange
            MenuBar menuBar = (MenuBar) windowScene.lookup(MAIN_MENU_SELECTOR);
            Menu menu1 = menuBar.getMenus().get(0);
            MenuItem item13 = menu1.getItems().get(2);
            platformMock.when(Platform::exit).thenAnswer((Answer<Void>) invocation -> null);

            Platform.runLater(() -> {
                // act
                item13.fire();

                // assert
                platformMock.verify(Platform::exit);
            });
        }
    }

    /**
     * Проверяем, что после нажатия кнопки "Теория" отображается справочная
     * информация.
     */
    @Test
    void givenMainWindow_whenTheoryButtonPressed_thenHelpShowed() throws ExecutionException, InterruptedException {
        // arrange
        Button theory = (Button) windowScene.lookup(THEORY_SELECTOR);

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            // act
            theory.fire();
            completableFuture.complete("completed");
        });
        completableFuture.get();
        HBox programContent = (HBox) windowScene.lookup(PROGRAM_CONTENT_SELECTOR);
        HBox helpContent = (HBox) windowScene.lookup(HELP_CONTENT_SELECTOR);

        // assert
        Assertions.assertFalse(programContent.getParent().isVisible());
        Assertions.assertNotNull(helpContent);
        Assertions.assertTrue(theory.getStyleClass().contains("button-pressed"));
    }

    /**
     * Проверяем, что после нажатия кнопки "Справка->Теория" отображается справочная
     * информация.
     */
    @Test
    void givenMainWindow_whenMenuTheoryButtonPressed_thenHelpShowed() throws ExecutionException, InterruptedException {
        // arrange
        MenuBar menuBar = (MenuBar) windowScene.lookup(MAIN_MENU_SELECTOR);
        Menu menu2 = menuBar.getMenus().get(1);
        MenuItem item21 = menu2.getItems().get(0);
        Button theory = (Button) windowScene.lookup(THEORY_SELECTOR);

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            // act
            item21.fire();
            completableFuture.complete("completed");
        });
        completableFuture.get();
        HBox programContent = (HBox) windowScene.lookup(PROGRAM_CONTENT_SELECTOR);
        HBox helpContent = (HBox) windowScene.lookup(HELP_CONTENT_SELECTOR);

        // assert
        Assertions.assertFalse(programContent.getParent().isVisible());
        Assertions.assertNotNull(helpContent);
        Assertions.assertTrue(theory.getStyleClass().contains("button-pressed"));
    }

    /**
     * Проверяем, что повторное нажатие на кнопку "Теория" возвращает отображение
     * содержимого рабочей области.
     */
    @Test
    void givenMainWindow_whenTheoryButtonPressedTwice_thenProgramContentIsShowed() throws ExecutionException, InterruptedException {
        // arrange
        Button theory = (Button) windowScene.lookup(THEORY_SELECTOR);
        CompletableFuture<String> completableFuture1 = new CompletableFuture<>();
        Platform.runLater(() -> {
            theory.fire();
            completableFuture1.complete("completed");
        });
        completableFuture1.get();
        CompletableFuture<String> completableFuture2 = new CompletableFuture<>();
        Platform.runLater(() -> {
            theory.fire();
            completableFuture2.complete("completed");
        });
        completableFuture2.get();
        HBox programContent = (HBox) windowScene.lookup(PROGRAM_CONTENT_SELECTOR);
        HBox helpContent = (HBox) windowScene.lookup(HELP_CONTENT_SELECTOR);

        // assert
        Assertions.assertTrue(programContent.getParent().isVisible());
        Assertions.assertNotNull(helpContent);
        Assertions.assertFalse(helpContent.getParent().isVisible());
        Assertions.assertFalse(theory.getStyleClass().contains("button-pressed"));
    }

    /**
     * Проверяем, что после нажатия кнопки "Файл->Анализ программы" отображается
     * анализируемая программа.
     */
    @Test
    void givenMainWindow_whenMenuBackToProgramAnalysisButtonPressed_thenProgramContentShowed() throws ExecutionException, InterruptedException {
        // arrange
        MenuBar menuBar = (MenuBar) windowScene.lookup(MAIN_MENU_SELECTOR);
        Menu menu1 = menuBar.getMenus().get(0);
        MenuItem item12 = menu1.getItems().get(1);
        Button theory = (Button) windowScene.lookup(THEORY_SELECTOR);

        // act
        CompletableFuture<String> completableFuture1 = new CompletableFuture<>();
        Platform.runLater(() -> {
            theory.fire();
            completableFuture1.complete("completed");
        });
        completableFuture1.get();
        CompletableFuture<String> completableFuture2 = new CompletableFuture<>();
        Platform.runLater(() -> {
            item12.fire();
            completableFuture2.complete("completed");
        });
        completableFuture2.get();
        HBox programContent = (HBox) windowScene.lookup(PROGRAM_CONTENT_SELECTOR);
        HBox helpContent = (HBox) windowScene.lookup(HELP_CONTENT_SELECTOR);

        // assert
        Assertions.assertTrue(programContent.getParent().isVisible());
        Assertions.assertNotNull(helpContent);
        Assertions.assertFalse(helpContent.getParent().isVisible());
        Assertions.assertFalse(theory.getStyleClass().contains("button-pressed"));
    }

    /**
     * Проверяем, что после нажатия кнопки "Помощь" отображается справочная
     * информация по описанию текущего варианта программы.
     */
    @Test
    void givenMainWindow_whenHelpButtonPressed_thenHelpShowed() {
        // arrange
        Button theory = (Button) windowScene.lookup(THEORY_SELECTOR);
        Button help = (Button) windowScene.lookup(HELP_SELECTOR);
        MainForm mainForm = (MainForm) controller;
        int expectedVariant = 5;
        String expectedVariantName = "Вариант 6";

        Platform.runLater(() -> {
            // act
            mainForm.setVariant(expectedVariant);
            help.fire();
            HBox programContent = (HBox) windowScene.lookup(PROGRAM_CONTENT_SELECTOR);
            HBox helpContent = (HBox) windowScene.lookup(HELP_CONTENT_SELECTOR);
            Accordion helpMenu = (Accordion) windowScene.lookup("#helpMenu");
            LinkedList<TitledPane> panes = new LinkedList<>(helpMenu.getPanes());
            String actualVariantName = panes.getLast().getText();

            // assert
            Assertions.assertFalse(programContent.getParent().isVisible());
            Assertions.assertNotNull(helpContent);
            Assertions.assertTrue(theory.getStyleClass().contains("button-pressed"));
            Assertions.assertEquals(expectedVariantName, actualVariantName);
        });
    }

    /**
     * Проверяем корректность всплывающей подсказки кнопки "Вариант".
     */
    @Test
    void givenMainWindow_whenHoverOnVariantChoosingButton_thenTooltipShowed() {
        // arrange

        // act
        robot.moveTo(VARIANT_CHOICE_SELECTOR);

        // assert
        Awaitility.await()
                .atMost(Duration.ofSeconds(WAITING_TIMEOUT))
                .until(() -> checkTipIsShowed(
                        "Выбор варианта анализируемой программы",
                        VARIANT_CHOICE_SELECTOR
                ));
    }

    /**
     * Проверяем корректность всплывающей подсказки кнопки "Теория".
     */
    @Test
    void givenMainWindow_whenHoverOnVariantTheory_thenTooltipShowed() {
        // arrange

        // act
        robot.moveTo(THEORY_SELECTOR);

        // assert
        Awaitility.await()
                .atMost(Duration.ofSeconds(WAITING_TIMEOUT))
                .until(() -> checkTipIsShowed(
                        "Теория по проведению анализа интерфейса",
                        THEORY_SELECTOR
                ));
    }

    /**
     * Проверяем корректность всплывающей подсказки кнопки "Теория" после активации.
     */
    @Test
    void givenMainWindow_whenHoverOnVariantTheoryAfterActivation_thenTooltipShowed() throws ExecutionException, InterruptedException {
        // arrange
        Button theory = (Button) windowScene.lookup(THEORY_SELECTOR);
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        Platform.runLater(() -> {
            theory.fire();
            completableFuture.complete("completed");
        });
        completableFuture.get();

        // act
        robot.moveTo(THEORY_SELECTOR);

        // assert
        Awaitility.await()
                .atMost(Duration.ofSeconds(WAITING_TIMEOUT))
                .until(() -> checkTipIsShowed(
                        "Возвращение к анализу программы",
                        THEORY_SELECTOR
                ));
    }

    /**
     * Проверяем корректность всплывающей подсказки кнопки "Помощь".
     */
    @Test
    void givenMainWindow_whenHoverOnVariantHelp_thenTooltipShowed() {
        // arrange

        // act
        robot.moveTo(HELP_SELECTOR);

        // assert
        Awaitility.await()
                .atMost(Duration.ofSeconds(WAITING_TIMEOUT))
                .until(() -> checkTipIsShowed(
                        "Описание функциональных возможностей анализируемой программы",
                        HELP_SELECTOR
                ));
    }

    /**
     * Проверяет, что всплывающая подсказка отображается.
     *
     * @param tip Текст подсказки.
     * @return Признак отображения.
     */
    private boolean checkTipIsShowed(String tip, String selector) {
        Control node = (Control) windowScene.lookup(selector);
        if (node != null) {
            Tooltip tooltip = node.getTooltip();
            return tooltip.getText().equals(tip) && tooltip.isActivated();
        } else {
            return false;
        }
    }

    /**
     * Проверяет, что открыто диалоговое окно.
     *
     * @param title Заголовок окна.
     * @return Признак того, что окно открыто.
     */
    private boolean checkDialogByTitle(String title) throws ExecutionException, InterruptedException {
        CompletableFuture<Boolean> isDialogClosingTrySuccess = new CompletableFuture<>();
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                Awaitility.await()
                        .atMost(Duration.ofSeconds(WAITING_TIMEOUT))
                        .until(() -> {
                            CompletableFuture<Boolean> isDialogClosed = new CompletableFuture<>();
                            Platform.runLater(() -> {
                                Stage stage = (Stage) Stage.getWindows()
                                        .stream()
                                        .filter(Window::isShowing)
                                        .filter(window -> ((Stage) window).getTitle().equals(title))
                                        .findAny()
                                        .orElse(null);

                                if (stage != null) {
                                    // after
                                    stage.close();
                                    isDialogClosed.complete(true);
                                } else {
                                    isDialogClosed.complete(false);
                                }
                            });
                            return isDialogClosed.get();
                        });
            } catch (Throwable ex) {
                ex.printStackTrace();
                isDialogClosingTrySuccess.complete(false);
                return;
            }
            isDialogClosingTrySuccess.complete(true);
        });
        return isDialogClosingTrySuccess.get();
    }

}
