package ru.sibsutis.pmik.hmi.interfaces.help;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Модульные тесты класса управления справочной информацией.
 */
public class HelpManagerTest {

    /**
     * Проверяем, что структура справочной информации может быть получена.
     */
    @Test
    void givenHelpManager_whenGetMenuStructure_thenStructureIsGot() {
        // arrange
        HelpManager helpManager = new HelpManager();
        String expectedTheme1 = "Тема1";
        String expectedSubTheme11 = "Глава1";
        String expectedSubTheme12 = "Глава2";
        String expectedSubTheme13 = "Глава3";
        String expectedTheme2 = "Тема2";
        String expectedSubTheme21 = "Глава1";
        String expectedSubTheme22 = "Глава2";
        String expectedSubTheme23 = "Глава3";

        // act
        Map<String, Map<String, String>> helpStructure =
                helpManager.getMenuStructure();
        
        // assert
        Assertions.assertTrue(helpStructure.containsKey(expectedTheme1));
        Assertions.assertTrue(helpStructure.containsKey(expectedTheme2));
        Assertions.assertTrue(helpStructure.get(expectedTheme1).containsKey(expectedSubTheme11));
        Assertions.assertTrue(helpStructure.get(expectedTheme1).containsKey(expectedSubTheme12));
        Assertions.assertTrue(helpStructure.get(expectedTheme1).containsKey(expectedSubTheme13));
        Assertions.assertTrue(helpStructure.get(expectedTheme2).containsKey(expectedSubTheme21));
        Assertions.assertTrue(helpStructure.get(expectedTheme2).containsKey(expectedSubTheme22));
        Assertions.assertTrue(helpStructure.get(expectedTheme2).containsKey(expectedSubTheme23));
    }

}
