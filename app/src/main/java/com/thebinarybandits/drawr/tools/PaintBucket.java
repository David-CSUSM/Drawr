package com.thebinarybandits.drawr.tools;

import com.thebinarybandits.drawr.pixelcanvas.PixelImage;
import javafx.scene.paint.Color;

/**
 * 
 */
public class PaintBucket implements Tool{

    /**
     * Inherited from Tool. Fills squares of the same color around a square.
     * 
     * @param panel  the panel to be drawn on
     * @param x  the x coordinate of the sqaure that will be filled
     * @param y  the y coordinate of the sqaure that will be filled
     * @param color  the color that will be used to fill the panel
     * @param CANVAS_SIZE  size of the grid on the panel. Default is 16x16
     */
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

    /**
     * Recursive function for coloring adjacent sqaures that are the same color as the square first clicked on.
     * 
     * @param panel  the panel to be drawn on
     * @param x  the x coordinate of the sqaure that will be filled
     * @param y  the y coordinate of the sqaure that will be filled
     * @param paintbucketColor  the color that will be used to fill the panel
     * @param firstPaneColor  the color of the square first clicked on
     * @param CANVAS_SIZE  size of the grid on the panel. Default is 16x16
     */
    private void recursivePaint(PixelImage panel, int x, int y, Color paintbucketColor, Color firstPaneColor, int CANVAS_SIZE) {
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
