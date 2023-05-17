package controller;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;

public class CheckBoxTableCell<S> extends TableCell<S, Boolean> {
    private final CheckBox checkBox;

    public CheckBoxTableCell() {
        checkBox = new CheckBox();
        checkBox.setAlignment(Pos.CENTER);
        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (isEditing()) {
                commitEdit(newValue);
            }
        });
        setGraphic(checkBox);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setEditable(true);
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (isEmpty()) {
            return;
        }
        checkBox.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        checkBox.setSelected(getItem());
    }

    @Override
    public void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            checkBox.setSelected(item);
            setGraphic(checkBox);
        }
    }
}
