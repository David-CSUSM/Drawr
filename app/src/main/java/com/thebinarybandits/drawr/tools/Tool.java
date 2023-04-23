package com.thebinarybandits.drawr.tools;

import com.thebinarybandits.drawr.pixelcanvas.Layer;
import javafx.scene.paint.Color;

public interface Tool {
    public void useTool(Layer panel, int x, int y, Color color);
}
