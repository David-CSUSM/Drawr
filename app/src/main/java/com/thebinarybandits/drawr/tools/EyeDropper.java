package com.thebinarybandits.drawr.tools;

import com.thebinarybandits.drawr.pixelcanvas.PixelImage;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;

public class EyeDropper implements Tool{
    private ColorPicker colorPicker;
    private PixelCanvas canvas;

    public EyeDropper(ColorPicker colorPicker, PixelCanvas canvas) {
        this.colorPicker = colorPicker;
        this.canvas = canvas;
    }

    public void useTool(PixelImage panel, int x, int y, Color color, int CANVAS_SIZE) {
        colorPicker.setValue(panel.getPixelData(x, y));
        canvas.setColor(colorPicker.getValue());
    }
}
