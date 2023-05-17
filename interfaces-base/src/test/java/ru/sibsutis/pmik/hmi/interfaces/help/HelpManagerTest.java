package ru.sibsutis.pmik.hmi.interfaces.help;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Isolated;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Модульные тесты класса управления справочной информацией.
 */
@Isolated
public class HelpManagerTest {

    /**
     * Проверяем, что структура справочной информации может быть получена.
     */
    @Test
    void givenHelpManager_whenGetMenuStructure_thenStructureIsGot() throws URISyntaxException, IOException {
        // arrange
        HelpManager helpManager = new HelpManager();
        String expectedTheme1 = "CWT-анализ";
        String expectedSubTheme11 = "Описание технологии";
        String expectedSubTheme12 = "Что даёт CWT-анализ";
        String expectedSubTheme13 = "Типичные ошибки и рекомендации";
        String expectedTheme2 = "Анализ GOMS";
        String expectedSubTheme21 = "Описание";

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
    }

}
