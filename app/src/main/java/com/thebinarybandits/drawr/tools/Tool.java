package com.thebinarybandits.drawr.tools;

import com.thebinarybandits.drawr.pixelcanvas.PixelImage;
import javafx.scene.paint.Color;

public interface Tool {
    public void useTool(PixelImage panel, int x, int y, Color color, int CANVAS_SIZE);
}
