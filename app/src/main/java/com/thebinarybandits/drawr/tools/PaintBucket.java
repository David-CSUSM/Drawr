package com.thebinarybandits.drawr.tools;

import com.thebinarybandits.drawr.pixelcanvas.Layer;
import javafx.scene.paint.Color;

public class PaintBucket implements Tool{
    public void useTool(Layer panel, int x, int y, Color color, int scale, int CANVAS_SIZE) {
        // color this square if it is different from color
        if (!color.equals((panel.getPixelData(x, y)))) {
            panel.draw(x, y, color);
            // recursivley color all adjacent squares that are not color
            if (x + 1 < CANVAS_SIZE)
                this.useTool(panel, x + 1, y, color, scale, CANVAS_SIZE);
            if (x - 1 >= 0)
                this.useTool(panel, x - 1, y, color, scale, CANVAS_SIZE);
            if (y + 1 < CANVAS_SIZE)
                this.useTool(panel, x, y + 1, color, scale, CANVAS_SIZE);
            if (y - 1 >= 0)
                this.useTool(panel, x, y - 1, color, scale, CANVAS_SIZE);
        }
    }
}
