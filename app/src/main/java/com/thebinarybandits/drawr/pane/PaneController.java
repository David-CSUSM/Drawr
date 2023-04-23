package com.thebinarybandits.drawr.pane;

import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import com.thebinarybandits.drawr.pixelcanvasviewer.PixelCanvasViewer;
import com.thebinarybandits.drawr.tools.Tool;
import com.thebinarybandits.drawr.tools.Pen;
import com.thebinarybandits.drawr.tools.Eraser;
import com.thebinarybandits.drawr.tools.PaintBucket;

import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleGroup;

public class PaneController {

    @FXML
    private Pane pane;
    private int CANVAS_SIZE;
    private int VIEW_SIZE;
    private int SCALE;

    private PixelCanvas canvas;
    private PixelCanvasViewer view;

    private Tool activeTool;

    @FXML
    private ToggleGroup Tools;

    @FXML
    public void initialize() {
        canvas = PixelCanvas.getInstance();
        view = PixelCanvasViewer.getInstance();

        pane.getChildren().add(view.getActiveView().getView());

        CANVAS_SIZE = (int) canvas.getActiveLayer().getImage().getWidth();
        VIEW_SIZE = (int) view.getActiveView().getView().getWidth();
        SCALE = VIEW_SIZE / CANVAS_SIZE;

        overlayGrid();

        activeTool = new Pen();
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
            // canvas.getActiveLayer().draw(scaledX, scaledY, Color.SALMON);
            activeTool.useTool(canvas.getActiveLayer(), scaledX, scaledY, Color.SALMON, SCALE, CANVAS_SIZE);
            view.getActiveView().update(canvas.getActiveLayer().getImage());
            overlayGrid();
        }
    }

    @FXML
    void mousePressed(MouseEvent event) {
        int scaledX = (int) event.getX() / SCALE;
        int scaledY = (int) event.getY() / SCALE;

        // canvas.getActiveLayer().draw(scaledX, scaledY, Color.SALMON);
        activeTool.useTool(canvas.getActiveLayer(), scaledX, scaledY, Color.SALMON, SCALE, CANVAS_SIZE);
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

    @FXML
    void selectEraser(ActionEvent event) {
        activeTool = new Eraser();
    }

    @FXML
    void selectPen(ActionEvent event) {
        activeTool = new Pen();
    }

    @FXML
    void selectPaintBucket(ActionEvent event) {
        activeTool = new PaintBucket();
    }
}
