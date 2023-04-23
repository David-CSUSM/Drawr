package com.thebinarybandits.drawr.pane;

import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import com.thebinarybandits.drawr.pixelcanvasviewer.PixelCanvasViewer;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class PaneController {

    @FXML
    private Pane pane;
    private int CANVAS_SIZE;
    private int VIEW_SIZE;
    private int SCALE;

    private PixelCanvas canvas;
    private PixelCanvasViewer view;

    @FXML
    public void initialize() {
        canvas = PixelCanvas.getInstance();
        view = PixelCanvasViewer.getInstance();

        pane.getChildren().add(view.getActiveView().getView());

        CANVAS_SIZE = (int) canvas.getActiveLayer().getImage().getWidth();
        VIEW_SIZE = (int) view.getActiveView().getView().getWidth();
        SCALE = VIEW_SIZE / CANVAS_SIZE;

        overlayGrid();
    }

    @FXML
    void keyPressed(KeyEvent event) {
        if (event.getText().equals("f")) {
            canvas.getActiveLayer().clear();
            view.getActiveView().clear();
            overlayGrid();
        }
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        int scaledX = (int) event.getX() / SCALE;
        int scaledY = (int) event.getY() / SCALE;

        boolean inBoundsHorizontal = scaledX >= 0 && scaledX < CANVAS_SIZE;
        boolean inBoundsVertical = scaledY >= 0 && scaledY < CANVAS_SIZE;

        if (inBoundsHorizontal && inBoundsVertical) {
            canvas.getActiveLayer().draw(scaledX, scaledY, Color.SALMON);
            view.getActiveView().update(canvas.getActiveLayer().getImage());
            overlayGrid();
        }
    }

    @FXML
    void mousePressed(MouseEvent event) {
        int scaledX = (int) event.getX() / SCALE;
        int scaledY = (int) event.getY() / SCALE;

        canvas.getActiveLayer().draw(scaledX, scaledY, Color.SALMON);
        view.getActiveView().update(canvas.getActiveLayer().getImage());
        overlayGrid();
    }

    private void overlayGrid() {
        GraphicsContext activeViewGraphics = view.getActiveView().getGraphics();

        for (int row = 0; row <= VIEW_SIZE; row += SCALE) {
            activeViewGraphics.strokeLine(0, row, VIEW_SIZE, row);
        }
        for (int column = 0; column <= VIEW_SIZE; column += SCALE) {
            activeViewGraphics.strokeLine(column, 0, column, VIEW_SIZE);
        }
    }
}
