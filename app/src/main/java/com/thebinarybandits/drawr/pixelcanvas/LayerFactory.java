package com.thebinarybandits.drawr.pixelcanvas;

public class LayerFactory {
    public Layer getNewLayer() {
        return new Layer(16);
    }

    public Layer getNewLayer(int size) {
        return new Layer(size);
    }
}
