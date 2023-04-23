package com.thebinarybandits.drawr.tools;

import com.thebinarybandits.drawr.pixelcanvas.Layer;
import javafx.scene.paint.Color;

public class PaintBucket implements Tool{
    public void useTool(Layer panel, int x, int y, Color color, int scale, int CANVAS_SIZE) {
        // color this square if it is different from color
        if (!color.equals((panel.getPixelData(x, y))));
            panel.draw(x, y, color);
            // recursivley color all adjacent squares that are not color
            if (x + scale < CANVAS_SIZE)
                this.useTool(panel, x + scale, y, color, scale, CANVAS_SIZE);
            if (x - scale >= 0)
                this.useTool(panel, x - scale, y, color, scale, CANVAS_SIZE);
            if (y + scale < CANVAS_SIZE)
                this.useTool(panel, x, y + scale, color, scale, CANVAS_SIZE);
            if (y - scale >= 0)
                this.useTool(panel, x, y - scale, color, scale, CANVAS_SIZE);
    }
}
