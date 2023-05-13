package com.thebinarybandits.drawr.tools;

import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.RadioButton;
import javafx.scene.paint.Color;

public class ToolsController {
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private RadioButton penButton;
    private final PixelCanvas canvas = PixelCanvas.getInstance();

    public void reset(){
        penButton.setSelected(true);
        canvas.setTool(new Pen());
        colorPicker.setValue(Color.BLACK);
        canvas.setColor(colorPicker.getValue());
    }

    @FXML
    void eraserSelected(ActionEvent event) {
        canvas.setTool(new Eraser());
    }

    @FXML
    void paintBucketSelected(ActionEvent event) {
        canvas.setTool(new PaintBucket());
    }

    @FXML
    void penSelected(ActionEvent event) {
        canvas.setTool(new Pen());
    }

    @FXML
    void eyeDropperSelected(ActionEvent event) {
        canvas.setTool(new EyeDropper(colorPicker, canvas));
    }

    @FXML
    void updateColor(ActionEvent event) {
        canvas.setColor(colorPicker.getValue());
    }
}

