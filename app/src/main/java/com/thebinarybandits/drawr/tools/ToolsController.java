package com.thebinarybandits.drawr.tools;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class ToolsController {

    @FXML
    private ToggleGroup Tools;

    @FXML
    private ColorPicker colorPicker;

    private Tool activeTool = new Pen();

    private Color selectedColor = Color.BLACK;

    @FXML
    void eraserSelected(ActionEvent event) {
        activeTool = new Eraser();
    }

    @FXML
    void paintBucketSelected(ActionEvent event) {
        activeTool = new PaintBucket();
    }

    @FXML
    void penSelected(ActionEvent event) {
        activeTool = new Pen();
    }

    @FXML
    void updateColor(ActionEvent event) {
        selectedColor = colorPicker.getValue();
    }

    public Tool getActiveTool() {
        return activeTool;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }
}

