package com.thebinarybandits.drawr.tools;

import com.thebinarybandits.drawr.pixelcanvas.Layer;
import javafx.scene.paint.Color;

public class Eraser implements Tool {
    public void useTool(Layer panel, int x, int y, Color color, int scale, int CANVAS_SIZE) {
        panel.erase(x, y);
    }
}
