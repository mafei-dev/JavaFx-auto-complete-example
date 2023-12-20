package com.example.javafxautocomplete;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.Objects;

public class AutoComplete<T extends AutoCompleteNode> extends TextField {

    private T selectedItem;
    private Node nextItem;
    private final ItemChangeListener<T> itemChangeListener;

    public AutoComplete(ItemChangeListener<T> itemChangeListener) {
        this.itemChangeListener = itemChangeListener;
        this.init();
    }


    public AutoComplete(ItemChangeListener<T> itemChangeListener, Node nextItem) {
        this.nextItem = nextItem;
        this.itemChangeListener = itemChangeListener;
        this.init();
    }

    private void init() {

        ContextMenu contextMenu = new ContextMenu();
        contextMenu.addEventFilter(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == javafx.scene.input.KeyCode.SPACE) {
                event.consume();
            }
        });

        contextMenu.setWidth(this.getWidth());
        this.setContextMenu(contextMenu);

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                contextMenu.hide();
            }
        });
        setActionEvent();

    }

    private void setActionEvent() {
        this.setOnKeyReleased(keyEvent -> {
            KeyCode code = keyEvent.getCode();
            if (code.isLetterKey() || code.isDigitKey() || code.isWhitespaceKey()) {
                this.getContextMenu().getItems().clear();
                this.getContextMenu().getItems().add(new MenuItem("Loading"));
                System.out.println("AutoComplete.setActionEvent");
                List<T> ts = this.itemChangeListener.loadItems(keyEvent.getText());
                this.getContextMenu().getItems().clear();
                if (ts.isEmpty()) {
                    this.getContextMenu().getItems().add(new MenuItem(null, new Label("No Item.")));
                    if (!this.getContextMenu().isShowing()) {
                        this.getContextMenu().show(this, Side.BOTTOM, 0, 0);
                    }
                } else {
                    this.loadItem(ts);
                }
            } else {
                keyEvent.consume();
            }

        });
    }


    public void loadItem(List<T> items) {

        this.getContextMenu().getItems().clear();
        items.forEach(t -> {
            t.getNode().setStyle("-fx-font-weight: bold;-fx-fill: red;");
            MenuItem menuItem = new MenuItem(null, t.getNode());
            menuItem.setOnAction(e -> setSelectedItem(t));
            this.getContextMenu().getItems().add(menuItem);
        });
        if (!this.getContextMenu().isShowing()) {
            this.getContextMenu().show(this, Side.BOTTOM, 0, 0);
        }
    }

    public T getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(T selectedItem) {
        this.selectedItem = selectedItem;
        this.setText(selectedItem.getName());
        if (Objects.isNull(this.nextItem)) {
            this.setFocused(true);
        } else {
            this.nextItem.requestFocus();
        }
        this.positionCaret(getText().length());
        this.itemChangeListener.onChange(selectedItem);
    }

}
