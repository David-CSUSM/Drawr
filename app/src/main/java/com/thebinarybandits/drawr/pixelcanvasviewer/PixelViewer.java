package com.thebinarybandits.drawr.pixelcanvasviewer;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

public class PixelViewer {
    private final Canvas view;
    private final GraphicsContext viewGraphics;

    public PixelViewer() {
        view = new Canvas(640, 640);
        viewGraphics = view.getGraphicsContext2D();

        view.setFocusTraversable(true);

        viewGraphics.setImageSmoothing(false);
        viewGraphics.setLineWidth(0.2);
    }

    public PixelViewer(int size) {
        view = new Canvas(size, size);
        viewGraphics = view.getGraphicsContext2D();

        viewGraphics.setImageSmoothing(false);
    }

    public void update(WritableImage image) {
        clear();
        viewGraphics.drawImage(image, 0, 0, view.getWidth(), view.getHeight());
    }

    public void clear() {
        viewGraphics.clearRect(0, 0, view.getWidth(), view.getHeight());
    }

    public Canvas getView() {
        return view;
    }

    public GraphicsContext getGraphics() {
        return viewGraphics;
    }
}
