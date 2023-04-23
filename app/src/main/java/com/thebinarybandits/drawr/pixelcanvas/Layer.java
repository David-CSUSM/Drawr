package com.thebinarybandits.drawr.pixelcanvas;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Layer {
    private final WritableImage image;
    private final PixelWriter imageWriter;

    public Layer() {
        image = new WritableImage(16, 16);
        imageWriter = image.getPixelWriter();
    }

    public Layer(int size) {
        image = new WritableImage(size, size);
        imageWriter = image.getPixelWriter();
    }

    public void draw(int x, int y, Color color) {
        imageWriter.setColor(x, y, color);
    }

    public void clear() {
        for (int row = 0; row < image.getWidth(); ++row) {
            for (int column = 0; column < image.getHeight(); ++column) {
                imageWriter.setArgb(row, column, 0);
            }
        }
    }

    public WritableImage getImage() {
        return image;
    }
}
