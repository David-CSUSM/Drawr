package com.thebinarybandits.drawr.layers;

import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import com.thebinarybandits.drawr.pixelcanvas.PixelView;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class LayersController {
    @FXML
    private ImageView animationView;
    @FXML
    private VBox layersContainer;
    private PixelCanvas canvas;
    private SimpleIntegerProperty index;

    @FXML
    public void initialize() {
        canvas = PixelCanvas.getInstance();

        index = new SimpleIntegerProperty(-1);

        // listen to change layer
        index.addListener((observable, oldValue, newValue) -> canvas.changeLayer(newValue.intValue()));

        // listen for layer selection from user
        layersContainer.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            LayerCell parent = (LayerCell) event.getPickResult().getIntersectedNode().getParent();

            highlightSelection(parent);
            index.set(layersContainer.getChildren().indexOf(parent));
        });

        ObservableList<PixelView> views = canvas.getLayerViews();

        // listen for created or deleted layerCells
        views.addListener((ListChangeListener<PixelView>) change -> {
            change.next();

            if (change.wasReplaced()) {
                // swapping layers
                return;
            }

            if (change.wasAdded()) {
                // creating a new layerCell
                Binding<PixelView> layerView = Bindings.createObjectBinding(() -> canvas.getLayerView());

                LayerCell cell = new LayerCell(240, 240 / canvas.getSize());
                cell.setLayerView(layerView.getValue());

                // insert at the end of the list
                if (index.intValue() == views.size() - 1) {
                    index.set(index.intValue() + 1);
                    layersContainer.getChildren().add(cell);

                    highlightSelection(cell);

                    // insert at the next index
                } else {
                    index.set(index.intValue() + 1);
                    layersContainer.getChildren().add(index.intValue(), cell);

                    highlightSelection(cell);
                }
            }

            boolean newProject = (change.wasRemoved() && change.getRemovedSize() > 1) || (change.wasRemoved() && layersContainer.getChildren().size() - 1 == 0);

            if (newProject) {
                index.set(-1);
                layersContainer.getChildren().clear();

            } else if (change.wasRemoved()) {
                // prevent underflow if deleting from the middle or beginning
                if (index.intValue() - 1 >= 0) {
                    index.set(index.intValue() - 1);
                }

                layersContainer.getChildren().remove(change.getRemoved().get(0).getParent());
                highlightSelection(layersContainer.getChildren().get(index.intValue()));
            }
        });
    }

    void highlightSelection(Node cell) {
        for (Node child : layersContainer.getChildren()) {
            child.setStyle("-fx-border-color: #f4f4f4");
        }

        cell.setStyle("-fx-border-color: black");
    }

    @FXML
    void createLayer(ActionEvent event) {
        canvas.createLayer();
    }

    @FXML
    void deleteLayer(ActionEvent event) {
        canvas.deleteLayer();
    }

    @FXML
    void moveUp(ActionEvent event) {
        canvas.swapLayer(PixelCanvas.Direction.UP);

        if (index.intValue() - 1 >= 0) {
            Node currentCell = layersContainer.getChildren().get(index.intValue());
            layersContainer.getChildren().remove(currentCell);
            layersContainer.getChildren().add(index.intValue() - 1, currentCell);

            index.set(index.intValue() - 1);
        }
    }

    @FXML
    void moveDown(ActionEvent event) {
        canvas.swapLayer(PixelCanvas.Direction.DOWN);

        if (index.intValue() + 1 <= layersContainer.getChildren().size() - 1) {
            Node nextCell = layersContainer.getChildren().get(index.intValue() + 1);
            layersContainer.getChildren().remove(nextCell);
            layersContainer.getChildren().add(index.intValue(), nextCell);

            index.set(index.intValue() + 1);
        }
    }

    @FXML
    void previewAnimation(ActionEvent event) {
        System.out.println("clicked previewAnimation");
    }
}
