package ru.sibsutis.pmik.hmi.interfaces.components;

import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.*;

/**
 * Поле ввода с выпадающим списком введенных элементов.
 */
public class Autocomplete extends TextField {

    /**
     * Введенные значения.
     */
    private final SortedSet<String> entries;

    /**
     * Выпадающий список, отображающий введенные значения.
     */
    private final ContextMenu entriesPopup;

    /**
     * Конструктор экземпляра поля ввода с выпадающим списком.
     */
    public Autocomplete() {
        super();
        entries = new TreeSet<>();
        entriesPopup = new ContextMenu();

        // Устанавливаем обработчик изменения поля ввода
        textProperty().addListener((observable, oldValue, newValue) ->
                reflectTextToPopup(getText()));

        // Устанавливаем обработчик изменения фокуса
        focusedProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue) {
                        reflectTextToPopup(getText());
                    } else {
                        entriesPopup.hide();
                    }
                });

        // Устанавливаем обработчик клика мыши
        setOnMouseClicked(event -> reflectTextToPopup(getText()));
    }

    /**
     * Добавляет элемент выпадающего списка.
     *
     * @param entry Новый элемент.
     */
    public void addEntry(String entry) {
        entries.add(entry);
    }

    /**
     * Очищает выпадающий список.
     */
    public void clearEntries() {
        entries.clear();
    }

    /**
     * Получает список элементов.
     */
    public List<String> getEntries() {
        List<String> result = new LinkedList<>(entries);
        return Collections.unmodifiableList(result);
    }

    /**
     * Заполняет выпадающий список найденными значениями.
     *
     * @param searchResult Найденные значения.
     */
    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();

        for (final String result : searchResult) {
            Label entryLabel = new Label(result);
            entryLabel.setFont(getFont());
            CustomMenuItem item = new CustomMenuItem(entryLabel, false);
            menuItems.add(item);
        }

        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);
    }

    /**
     * Отображает выпадающий список.
     *
     * @param items Элементы списка.
     */
    private void showPopup(List<String> items) {
        if (!items.isEmpty()) {
            populatePopup(items);
            if (!entriesPopup.isShowing()) {
                entriesPopup.show(this, Side.BOTTOM, 0, 0);
            }
        } else {
            entriesPopup.hide();
        }
    }

    /**
     * Отображает введенное значение на выпадающий список.
     *
     * @param text Введенное значение.
     */
    private void reflectTextToPopup(String text) {
        if (text.isEmpty()) {
            List<String> allEntries = new LinkedList<>(entries);
            populatePopup(allEntries);
            showPopup(allEntries);
        } else {
            LinkedList<String> searchResult = new LinkedList<>(
                    entries.subSet(text, text + Character.MAX_VALUE)
            );
            showPopup(searchResult);
        }
    }

}
