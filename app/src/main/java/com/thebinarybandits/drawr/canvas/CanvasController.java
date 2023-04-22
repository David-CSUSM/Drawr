package com.thebinarybandits.drawr.canvas;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class CanvasController {
    @FXML
    private Canvas canvasView;
    private GraphicsContext onCanvasView;
    private CanvasModel canvas;
    private int CANVAS_SIZE;
    private int CANVAS_SCALE;

    @FXML
    public void initialize() {
        canvasView.setFocusTraversable(true);

        onCanvasView = canvasView.getGraphicsContext2D();
        onCanvasView.setImageSmoothing(false);
        onCanvasView.setLineWidth(0.2);

        CANVAS_SIZE = 16;
        CANVAS_SCALE = (int) canvasView.getWidth() / CANVAS_SIZE;

        canvas = new CanvasModel(CANVAS_SIZE);

        overlayGrid();
    }

    @FXML
    void keyPressed(KeyEvent event) {
        if (event.getText().equals("f")) {
            canvas.clear();
            clearView();
            overlayGrid();
        }
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        int scaledX = (int) event.getX() / CANVAS_SCALE;
        int scaledY = (int) event.getY() / CANVAS_SCALE;

        boolean inBoundsHorizontal = scaledX >= 0 && scaledX < CANVAS_SIZE;
        boolean inBoundsVertical = scaledY >= 0 && scaledY < CANVAS_SIZE;

        if (inBoundsHorizontal && inBoundsVertical) {
            updateView(scaledX, scaledY, Color.SALMON);
        }
    }

    @FXML
    void mousePressed(MouseEvent event) {
        int scaledX = (int) event.getX() / CANVAS_SCALE;
        int scaledY = (int) event.getY() / CANVAS_SCALE;

        updateView(scaledX, scaledY, Color.SALMON);
    }

    private void overlayGrid() {
        for (int row = 0; row <= canvasView.getWidth(); row += CANVAS_SCALE) {
            onCanvasView.strokeLine(0, row, canvasView.getWidth(), row);
        }
        for (int column = 0; column <= canvasView.getHeight(); column += CANVAS_SCALE) {
            onCanvasView.strokeLine(column, 0, column, canvasView.getHeight());
        }
    }

    private void updateView(int scaledX, int scaledY, Color color) {
        canvas.draw(scaledX, scaledY, color);
        clearView();
        onCanvasView.drawImage(canvas.getCanvas(), 0, 0, canvasView.getWidth(), canvasView.getHeight());
        overlayGrid();
    }

    private void clearView() {
        onCanvasView.clearRect(0, 0, canvasView.getHeight(), canvasView.getWidth());
    }
}
