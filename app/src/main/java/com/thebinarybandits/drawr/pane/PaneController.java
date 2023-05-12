package com.thebinarybandits.drawr.pane;

import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import com.thebinarybandits.drawr.pixelcanvas.PixelView;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PaneController {
    @FXML
    private Pane pane;
    private PixelCanvas canvas;
    private int mouseDragDraws = 0;

    @FXML
    public void initialize() {
        canvas = PixelCanvas.getInstance();

        Binding<PixelView> view = Bindings.createObjectBinding(() -> canvas.getCanvasView());

        pane.getChildren().add(view.getValue());
        pane.getChildren().add(new Grid(canvas.getViewSize(), canvas.getScale()));
    }

    @FXML
    void keyPressed(KeyEvent event) {
        if (event.getText().equals("f")) {
            canvas.clear();
        }
    }

    @FXML
    void mouseDragged(MouseEvent event) {
        pane.requestFocus();

        int scaledX = (int) event.getX() / canvas.getScale();
        int scaledY = (int) event.getY() / canvas.getScale();

        boolean inBoundsHorizontal = scaledX >= 0 && scaledX < canvas.getSize();
        boolean inBoundsVertical = scaledY >= 0 && scaledY < canvas.getSize();

        if (inBoundsHorizontal && inBoundsVertical) {
            if (canvas.draw(scaledX, scaledY))
                mouseDragDraws++;
            System.out.println("mouseDrag int is: " + mouseDragDraws);
        }
    }

    @FXML
    void mouseReleased(MouseEvent event) {
        System.out.println("I am in mouseReleased!");
        while (mouseDragDraws > 0) {
            canvas.discardUndo();
            System.out.println("mouseDrag int is: " + mouseDragDraws);
            mouseDragDraws--;
        }
        System.out.println("mouseDrag int is: " + mouseDragDraws);
    }

    @FXML
    void mousePressed(MouseEvent event) {
        pane.requestFocus();

        int scaledX = (int) event.getX() / canvas.getScale();
        int scaledY = (int) event.getY() / canvas.getScale();

        canvas.draw(scaledX, scaledY);
    }
}
