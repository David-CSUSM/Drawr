package com.thebinarybandits.drawr.tools;

public class ToolFactory {
    public Tool getTool(String tool) {
        if (tool == null) {
            return null;
        }
        if (tool.equalsIgnoreCase("Pen")) {
            return new Pen();

        } else if (tool.equalsIgnoreCase("Eraser")) {
            return new Eraser();

        } else if (tool.equalsIgnoreCase("PaintBucket")) {
            return new PaintBucket();
        }

        return null;
    }
}
