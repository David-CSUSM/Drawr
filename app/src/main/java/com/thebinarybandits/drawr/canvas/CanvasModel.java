package com.thebinarybandits.drawr.canvas;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class CanvasModel {
    private WritableImage canvas;
    private PixelWriter onCanvas;
    private int CANVAS_SIZE;

    public CanvasModel(int size) {
        CANVAS_SIZE = size;

        canvas = new WritableImage(CANVAS_SIZE, CANVAS_SIZE);
        onCanvas = canvas.getPixelWriter();
    }

    public WritableImage getCanvas() {
        return canvas;
    }

    public void draw(int x, int y, Color color) {
        onCanvas.setColor(x, y, color);
    }

    public void clear() {
        for (int row = 0; row < CANVAS_SIZE; ++row) {
            for (int column = 0; column < CANVAS_SIZE; ++column) {
                onCanvas.setArgb(row, column, 0);
            }
        }
    }
}
