package com.thebinarybandits.drawr.layers;

import com.thebinarybandits.drawr.pane.PaneController;
import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import com.thebinarybandits.drawr.pixelcanvasviewer.PixelCanvasViewer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

public class LayersController {

    @FXML
    private Canvas layerView;
    private GraphicsContext layerViewGraphics;
    private PaneController paneController;
    private PixelCanvas canvas;
    private PixelCanvasViewer view;
    private int SCALE;

    @FXML
    public void initialize() {
        canvas = PixelCanvas.getInstance();
        view = PixelCanvasViewer.getInstance();

        layerViewGraphics = layerView.getGraphicsContext2D();
        layerViewGraphics.setImageSmoothing(false);
        layerViewGraphics.setLineWidth(0.2);

        int canvasSize = (int) canvas.getActiveLayer().getImage().getWidth();
        SCALE = (int) layerView.getWidth() / canvasSize;

        overlayGrid();
    }

    public void setPaneController(PaneController controller) {
        paneController = controller;
    }

    @FXML
    void createLayer(ActionEvent event) {
        canvas.createNewLayer();
        view.createNewPixelViewer();
        paneController.addPixelViewer(view.getActiveView().getView());
        updateLayerView();
    }

    @FXML
    void moveBackward(ActionEvent event) {
        canvas.getPreviousLayer();
        view.getPreviousView();

        updateLayerView();
    }

    @FXML
    void moveForward(ActionEvent event) {
        canvas.getNextLayer();
        view.getNextView();

        updateLayerView();
    }

    public void updateLayerView() {
        clearLayerView();
        WritableImage image = canvas.getActiveLayer().getImage();
        layerViewGraphics.drawImage(image, 0, 0, layerView.getWidth(), layerView.getHeight());
        overlayGrid();
    }

    public void clearLayerView() {
        layerViewGraphics.clearRect(0, 0, layerView.getWidth(), layerView.getHeight());
    }

    public void overlayGrid() {
        for (int row = 0; row <= layerView.getWidth(); row += SCALE) {
            layerViewGraphics.strokeLine(0, row, layerView.getWidth(), row);
        }
        for (int column = 0; column <= layerView.getHeight(); column += SCALE) {
            layerViewGraphics.strokeLine(column, 0, column, layerView.getHeight());
        }
    }
}
