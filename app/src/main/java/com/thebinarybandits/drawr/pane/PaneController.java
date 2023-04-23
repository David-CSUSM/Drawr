package com.thebinarybandits.drawr.pane;

import com.thebinarybandits.drawr.layers.LayersController;
import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import com.thebinarybandits.drawr.pixelcanvasviewer.PixelCanvasViewer;
import com.thebinarybandits.drawr.tools.Tool;
import com.thebinarybandits.drawr.tools.Pen;
import com.thebinarybandits.drawr.tools.Eraser;
import com.thebinarybandits.drawr.tools.PaintBucket;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
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
    private Canvas grid;
    private LayersController layersController;

    private PixelCanvas canvas;
    private PixelCanvasViewer view;
    private int CANVAS_SIZE;
    private int VIEW_SIZE;
    private int SCALE;


    private Tool activeTool;

    @FXML
    private ToggleGroup Tools;

    @FXML
    public void initialize() {
        canvas = PixelCanvas.getInstance();
        view = PixelCanvasViewer.getInstance();

        CANVAS_SIZE = (int) canvas.getActiveLayer().getImage().getWidth();
        VIEW_SIZE = (int) view.getActiveView().getView().getWidth();
        SCALE = VIEW_SIZE / CANVAS_SIZE;

        grid = new Canvas(VIEW_SIZE, VIEW_SIZE);

        pane.getChildren().add(grid);
        pane.getChildren().add(view.getActiveView().getView());
        grid.toFront();

        overlayGrid();

        activeTool = new Pen();
    }

    public void setLayersController(LayersController controller) {
        layersController = controller;
    }

    public void addPixelViewer(Canvas view) {
        pane.getChildren().add(view);
        grid.toFront();
    }

    @FXML
    void keyPressed(KeyEvent event) {
        if (event.getText().equals("f")) {
            canvas.getActiveLayer().clear();
            view.getActiveView().clear();

            layersController.clearLayerView();
            layersController.overlayGrid();
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
            activeTool.useTool(canvas.getActiveLayer(), scaledX, scaledY, Color.SALMON, CANVAS_SIZE);
            view.getActiveView().update(canvas.getActiveLayer().getImage());

            layersController.updateLayerView();
        }
    }

    @FXML
    void mousePressed(MouseEvent event) {
        int scaledX = (int) event.getX() / SCALE;
        int scaledY = (int) event.getY() / SCALE;

        // canvas.getActiveLayer().draw(scaledX, scaledY, Color.SALMON);
        activeTool.useTool(canvas.getActiveLayer(), scaledX, scaledY, Color.SALMON, CANVAS_SIZE);
        view.getActiveView().update(canvas.getActiveLayer().getImage());

        layersController.updateLayerView();
    }

    public void overlayGrid() {
        GraphicsContext gridGraphics = grid.getGraphicsContext2D();
        gridGraphics.setLineWidth(0.2);

        for (int row = 0; row <= VIEW_SIZE; row += SCALE) {
            gridGraphics.strokeLine(0, row, VIEW_SIZE, row);
        }
        for (int column = 0; column <= VIEW_SIZE; column += SCALE) {
            gridGraphics.strokeLine(column, 0, column, VIEW_SIZE);
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
