package com.thebinarybandits.drawr.tools;

import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;

public class ToolsController {
    @FXML
    private ColorPicker colorPicker;
    private final ToolFactory factory = new ToolFactory();
    private final PixelCanvas canvas = PixelCanvas.getInstance();
    @FXML
    void eraserSelected(ActionEvent event) {
        canvas.setTool(factory.getTool("Eraser"));
    }

    @FXML
    void paintBucketSelected(ActionEvent event) {
        canvas.setTool(factory.getTool("PaintBucket"));
    }

    @FXML
    void penSelected(ActionEvent event) {
        canvas.setTool(factory.getTool("Pen"));
    }

    @FXML
    void updateColor(ActionEvent event) {
        canvas.setColor(colorPicker.getValue());
    }
}

