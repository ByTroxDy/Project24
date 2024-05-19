package controllers;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class TableCellFactory {

    public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> createCellFactory() {
        return col -> new TableCell<S, T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || item.toString().isEmpty()) {
                    if (!empty) {
                        setText("NULL");
                    } else {
                        setText(null);
                    }
                } else {
                    setText(item.toString());
                }
            }
        };
    }
}
