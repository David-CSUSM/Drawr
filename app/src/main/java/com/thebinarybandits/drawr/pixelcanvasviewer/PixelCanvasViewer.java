package com.thebinarybandits.drawr.pixelcanvasviewer;

import java.util.ArrayList;

// singleton pattern
public class PixelCanvasViewer {
    private static volatile PixelCanvasViewer instance;
    private final PixelViewerFactory factory;
    private final ArrayList<PixelViewer> viewers;
    private int viewerIndex;

    private PixelCanvasViewer() {
        factory = new PixelViewerFactory();
        viewers = new ArrayList<>();

        viewers.add(factory.getNewPixelViewer());
        viewerIndex = 0;
    }

    private PixelCanvasViewer(int size) {
        factory = new PixelViewerFactory();
        viewers = new ArrayList<>();

        viewers.add(factory.getNewPixelViewer(size));
        viewerIndex = 0;
    }

    public static PixelCanvasViewer getInstance() {
        PixelCanvasViewer result = instance;
        if (result == null) {
            synchronized (PixelCanvasViewer.class) {
                result = instance;
                if (result == null) {
                    instance = result = new PixelCanvasViewer();
                }
            }
        }

        return result;
    }

    public static PixelCanvasViewer getInstance(int size) {
        PixelCanvasViewer result = instance;
        if (result == null) {
            synchronized (PixelCanvasViewer.class) {
                result = instance;
                if (result == null) {
                    instance = result = new PixelCanvasViewer(size);
                }
            }
        }

        return result;
    }

    public void resetViewersAndIndex(){
        for(PixelViewer viewer : viewers){
            viewer.clear();
        }
        viewers.clear();
        viewers.add(factory.getNewPixelViewer());
        viewerIndex = 0;
    }

    public void createNewPixelViewer() {
        int size = (int) viewers.get(0).getView().getWidth();
        viewers.add(factory.getNewPixelViewer(size));
        ++viewerIndex;
    }

    public PixelViewer getActiveView() {
        return viewers.get(viewerIndex);
    }

    public PixelViewer getNextView() {
        if (viewerIndex + 1 >= viewers.size()) return null;

        return viewers.get(++viewerIndex);
    }

    public PixelViewer getPreviousView() {
        if (viewerIndex - 1 < 0) return null;

        return viewers.get(--viewerIndex);
    }
}
