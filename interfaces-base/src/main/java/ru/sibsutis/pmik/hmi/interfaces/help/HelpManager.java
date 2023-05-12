package ru.sibsutis.pmik.hmi.interfaces.help;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Класс управления справочной информацией.
 */
public class HelpManager {

    private static final String CONTENTS_PATH = "/help/contents.md";

    private static final Pattern linePattern = Pattern.compile("^\\[(.*)]\\((.*)\\)");

    /**
     * Структура меню справочной информации.
     */
    final private Map<String, Map<String, String>> menuStructure =
            new LinkedHashMap<>();

    /**
     * Конструктор экземпляра.
     */
    public HelpManager() throws URISyntaxException, IOException {
        load();
    }

    /**
     * Получает структуру меню справочной информации.
     */
    public Map<String, Map<String, String>> getMenuStructure() {
        return menuStructure;
    }

    /**
     * Загружает структуру меню из файла {@value CONTENTS_PATH}.
     */
    private void load() throws URISyntaxException, IOException {
        menuStructure.clear();
        Class<?> clazz = getClass();
        URL contentsURL =
                Objects.requireNonNull(clazz.getResource(CONTENTS_PATH));
        Path contentsPath = Paths.get(contentsURL.toURI());
        AtomicReference<String> currentTheme = new AtomicReference<>();
        try (Stream<String> lines = Files.lines(contentsPath, StandardCharsets.UTF_8)) {
            lines.forEach(line -> {
                String trimmedLine = line.trim();
                if (trimmedLine.charAt(0) == '#') {
                    if (trimmedLine.charAt(1) == '#') {
                        // Читаем следующую главу
                        String restOfString = trimmedLine.substring(2).trim();
                        Matcher matcher = linePattern.matcher(restOfString);
                        if (matcher.find()) {
                            String chapter = matcher.group(1);
                            String path = matcher.group(2);
                            menuStructure.get(currentTheme.get()).put(chapter, path);
                        }
                    } else {
                        // Читаем следующую тему
                        currentTheme.set(trimmedLine.substring(1).trim());
                        menuStructure.put(currentTheme.get(), new LinkedHashMap<>());
                    }
                }
            });
        }
    }

}
