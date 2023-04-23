package com.thebinarybandits.drawr.pixelcanvas;

import java.util.ArrayList;

// singleton pattern
public class PixelCanvas {
    private static volatile PixelCanvas instance;
    private final LayerFactory factory;
    private final ArrayList<Layer> layers;
    private int layerIndex;

    private PixelCanvas() {
        factory = new LayerFactory();
        layers = new ArrayList<>();

        layers.add(factory.getNewLayer());
        layerIndex = 0;
    }

    private PixelCanvas(int size) {
        factory = new LayerFactory();
        layers = new ArrayList<>();

        layers.add(factory.getNewLayer(size));
        layerIndex = 0;
    }

    public static PixelCanvas getInstance() {
        PixelCanvas result = instance;
        if (result == null) {
            synchronized (PixelCanvas.class) {
                result = instance;
                if (result == null) {
                    instance = result = new PixelCanvas();
                }
            }
        }

        return result;
    }

    public static PixelCanvas getInstance(int size) {
        PixelCanvas result = instance;
        if (result == null) {
            synchronized (PixelCanvas.class) {
                result = instance;
                if (result == null) {
                    instance = result = new PixelCanvas(size);
                }
            }
        }

        return result;
    }

    public void createNewLayer() {
        int size = (int) layers.get(0).getImage().getWidth();
        layers.add(factory.getNewLayer(size));
        ++layerIndex;
    }

    public Layer getActiveLayer() {
        return layers.get(layerIndex);
    }

    public Layer getNextLayer() {
        if (layerIndex + 1 >= layers.size()) return null;

        return layers.get(++layerIndex);
    }

    public Layer getPreviousLayer() {
        if (layerIndex - 1 < 0) return null;
        return layers.get(--layerIndex);
    }
}
