package com.thebinarybandits.drawr.layers;

import com.thebinarybandits.drawr.pane.Grid;
import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import com.thebinarybandits.drawr.pixelcanvas.PixelView;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class LayersController {
    @FXML
    private Pane pane;
    private PixelCanvas canvas;

    @FXML
    public void initialize() {
        canvas = PixelCanvas.getInstance();

        Grid grid = new Grid((int) pane.getPrefWidth(), (int) pane.getPrefWidth() / canvas.getSize());

        Binding<PixelView> view = Bindings.createObjectBinding(() -> canvas.getLayerView());

        pane.getChildren().add(grid);
        pane.getChildren().add(view.getValue());
        grid.toFront();
    }

    @FXML
    void createLayer(ActionEvent event) {
        canvas.createLayer();
    }

    @FXML
    void moveBackward(ActionEvent event) {
        canvas.changeLayer(PixelCanvas.Direction.BACK);
    }

    @FXML
    void moveForward(ActionEvent event) {
        canvas.changeLayer(PixelCanvas.Direction.FORWARD);
    }
}
