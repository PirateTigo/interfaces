package ru.sibsutis.pmik.hmi.interfaces.help;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс управления справочной информацией.
 */
public class HelpManager {

    /**
     * Структура меню справочной информации.
     */
    final private Map<String, Map<String, String>> menuStructure =
            new LinkedHashMap<>();

    /**
     * Конструктор экземпляра.
     */
    public HelpManager() {
        Map<String, String> theme1 = new LinkedHashMap<>();
        theme1.put("Глава1", null);
        theme1.put("Глава2", null);
        theme1.put("Глава3", null);
        menuStructure.put("Тема1", theme1);

        Map<String, String> theme2 = new LinkedHashMap<>();
        theme2.put("Глава1", null);
        theme2.put("Глава2", null);
        theme2.put("Глава3", null);
        menuStructure.put("Тема2", theme2);
    }

    /**
     * Получает структуру меню справочной информации.
     */
    public Map<String, Map<String, String>> getMenuStructure() {
        return menuStructure;
    }

}
