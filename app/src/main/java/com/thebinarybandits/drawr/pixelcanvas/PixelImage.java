package com.thebinarybandits.drawr.pixelcanvas;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PixelImage extends WritableImage {
    private final PixelWriter imageWriter;

    public PixelImage(int size) {
        super(size, size);
        imageWriter = this.getPixelWriter();
    }

    public void draw(int x, int y, Color color) {
        imageWriter.setColor(x, y, color);
    }

    public void erase(int x, int y) {
        imageWriter.setArgb(x, y, 0);
    }

    public void clear() {
        for (int row = 0; row < this.getWidth(); ++row) {
            for (int column = 0; column < this.getHeight(); ++column) {
                imageWriter.setArgb(row, column, 0);
            }
        }
    }

    public String[][] getImageData() {
        String[][] imageArray = new String[16][16];

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                imageArray[x][y] = this.getPixelData(x, y).toString();
            }
        }

        return imageArray;
    }

    public void setImageData(String[][] colorStrings) {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                this.draw(x, y, Color.valueOf(colorStrings[x][y]));
            }
        }
    }

    public Color getPixelData(int x, int y) {
        return this.getPixelReader().getColor(x, y);
    }
}
