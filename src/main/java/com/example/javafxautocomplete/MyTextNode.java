package com.example.javafxautocomplete;

import javafx.scene.Node;
import javafx.scene.text.Text;

import java.util.StringJoiner;

public class MyTextNode implements AutoCompleteNode {
    private final String text;

    public MyTextNode(String text) {
        this.text = text;
    }

    @Override
    public Node getNode() {
        return new Text(this.getName());
    }

    @Override
    public String getName() {
        return this.text;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MyTextNode.class.getSimpleName() + "[", "]")
                .add("text='" + text + "'")
                .toString();
    }
}
