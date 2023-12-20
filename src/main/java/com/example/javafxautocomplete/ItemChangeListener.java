package com.example.javafxautocomplete;

import java.util.List;

public interface ItemChangeListener<T extends AutoCompleteNode> {
    void onChange(T selectedItem);

    List<T> loadItems(String text);
}
