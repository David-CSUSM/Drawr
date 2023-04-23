package com.thebinarybandits.drawr.tools;

import com.thebinarybandits.drawr.pixelcanvas.Layer;
import javafx.scene.paint.Color;

public class Pen implements Tool {
    public void useTool(Layer panel, int x, int y, Color color) {
        panel.draw(x, y, color);
    }
}
