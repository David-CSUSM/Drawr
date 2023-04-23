package com.thebinarybandits.drawr.pixelcanvasviewer;

public class PixelViewerFactory {
    public PixelViewer getNewPixelViewer() {
        return new PixelViewer();
    }

    public PixelViewer getNewPixelViewer(int size) {
        return new PixelViewer(size);
    }
}
