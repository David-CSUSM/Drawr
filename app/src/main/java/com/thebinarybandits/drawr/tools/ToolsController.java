package com.thebinarybandits.drawr.tools;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class ToolsController {

    @FXML
    private ToggleGroup Tools;

    private Tool activeTool = new Pen();

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

    public Tool getActiveTool() {
        return activeTool;
    }
}

