package com.thebinarybandits.drawr.pane;

import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;

import com.thebinarybandits.drawr.pixelcanvas.PixelImage;
import com.thebinarybandits.drawr.pixelcanvas.PixelView;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PaneController {
    @FXML
    private Pane pane;
    private Grid grid;
    private PixelCanvas canvas;

    @FXML
    public void initialize() {
        canvas = PixelCanvas.getInstance();

        grid = new Grid(canvas.getViewSize(), canvas.getScale());

        ObservableList<PixelView> views = canvas.getViews();

        views.addListener((ListChangeListener<PixelView>) change -> {
            change.next();

            if (change.wasAdded()) {
                Binding<PixelView> view = Bindings.createObjectBinding(() -> canvas.getView());
                pane.getChildren().add(view.getValue());
                grid.toFront();
            }

            if (change.wasRemoved()) {
                pane.getChildren().clear();
                pane.getChildren().add(grid);
            }
        });

        pane.setFocusTraversable(true);

        pane.getChildren().add(grid);
        grid.toFront();
    }

    @FXML
    void keyPressed(KeyEvent event) {
        if (event.getText().equals("f")) {
            canvas.clear();
        }
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        int scaledX = (int) event.getX() / canvas.getScale();
        int scaledY = (int) event.getY() / canvas.getScale();

        boolean inBoundsHorizontal = scaledX >= 0 && scaledX < canvas.getSize();
        boolean inBoundsVertical = scaledY >= 0 && scaledY < canvas.getSize();

        if (inBoundsHorizontal && inBoundsVertical) {
            canvas.draw(scaledX, scaledY);
        }
    }

    @FXML
    void mousePressed(MouseEvent event) {
        int scaledX = (int) event.getX() / canvas.getScale();
        int scaledY = (int) event.getY() / canvas.getScale();

        canvas.draw(scaledX, scaledY);
    }
}
