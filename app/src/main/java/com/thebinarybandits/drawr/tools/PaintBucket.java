package com.thebinarybandits.drawr.tools;

import com.thebinarybandits.drawr.pixelcanvas.PixelImage;
import javafx.scene.paint.Color;

public class PaintBucket implements Tool{
    public void useTool(PixelImage panel, int x, int y, Color color, int CANVAS_SIZE) {
        Color paneColor = panel.getPixelData(x, y);
        panel.draw(x, y, color);
        
        // recursively color squares that are the same color as panecolor
        if (x + 1 < CANVAS_SIZE)
            recursivePaint(panel, x + 1, y, color, paneColor, CANVAS_SIZE);
        if (x - 1 >= 0)
            recursivePaint(panel, x - 1, y, color, paneColor, CANVAS_SIZE);
        if (y + 1 < CANVAS_SIZE)
            recursivePaint(panel, x, y + 1, color, paneColor, CANVAS_SIZE);
        if (y - 1 >= 0)
            recursivePaint(panel, x, y - 1, color, paneColor, CANVAS_SIZE);
    }

    // recursive function for coloring adjacent sqaures that are the same color as the square first clicked on
    public void recursivePaint(PixelImage panel, int x, int y, Color paintbucketColor, Color firstPaneColor, int CANVAS_SIZE) {
        if (firstPaneColor.equals(panel.getPixelData(x, y))) {
            panel.draw(x, y, paintbucketColor);

            if (x + 1 < CANVAS_SIZE)
                recursivePaint(panel, x + 1, y, paintbucketColor, firstPaneColor, CANVAS_SIZE);
            if (x - 1 >= 0)
                recursivePaint(panel, x - 1, y, paintbucketColor, firstPaneColor, CANVAS_SIZE);
            if (y + 1 < CANVAS_SIZE)
                recursivePaint(panel, x, y + 1, paintbucketColor, firstPaneColor, CANVAS_SIZE);
            if (y - 1 >= 0)
                recursivePaint(panel, x, y - 1, paintbucketColor, firstPaneColor, CANVAS_SIZE);
        }
    }
}
