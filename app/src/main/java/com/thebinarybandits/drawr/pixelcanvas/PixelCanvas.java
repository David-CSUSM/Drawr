package com.thebinarybandits.drawr.pixelcanvas;

import com.thebinarybandits.drawr.tools.Tool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;

// singleton pattern
public class PixelCanvas {
    private static volatile PixelCanvas instance;
    private int size;
    private int viewSize;
    private Tool tool;
    private Color color;
    private final ArrayList<PixelImage> layers;
    private final PixelView canvasView;
    private final ObservableList<PixelView> layerViews;
    private int index;

    public enum Direction {UP, DOWN}

    private PixelCanvas() {
        size = 16;
        viewSize = 640;
        tool = null;
        color = Color.BLACK;
        layers = new ArrayList<>();
        canvasView = new PixelView(viewSize);
        layerViews = FXCollections.observableArrayList();
        index = -1;
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

    public void initialize() {
        ++index;
        layers.add(new PixelImage(size));
        layerViews.add(new PixelView(240));
    }

    public void reset() {
        size = 16;
        viewSize = 640;
        tool = null;
        color = Color.BLACK;
        layers.clear();
        canvasView.clear();
        layerViews.clear();
        index = -1;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setViewSize(int viewSize) {
        this.viewSize = viewSize;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSize() {
        return size;
    }

    public int getViewSize() {
        return viewSize;
    }

    public int getScale() {
        return viewSize / size;
    }

    public PixelImage getImage() {
        return layers.get(index);
    }

    public PixelView getCanvasView() {
        return canvasView;
    }

    public PixelView getLayerView() {
        return layerViews.get(index);
    }

    public ArrayList<PixelImage> getLayers() {
        return layers;
    }

    public ObservableList<PixelView> getLayerViews() {
        return layerViews;
    }

    public void draw(int x, int y) {
        if (tool != null && layers.size() > 0) {
            tool.useTool(layers.get(index), x, y, color, size);
            canvasView.update(getImage());

            getLayerView().update(getImage());
        }
    }

    public void clear() {
        layers.get(index).clear();
        canvasView.clear();
        layerViews.get(index).clear();
    }

    public void createLayer() {
        if (index == layers.size() - 1) {
            ++index;
            layers.add(new PixelImage(size));
            layerViews.add(new PixelView(240));

        } else {
            ++index;
            layers.add(index, new PixelImage(size));
            layerViews.add(index, new PixelView(240));
        }

        canvasView.update(getImage());
    }

    public void deleteLayer() {
        if (layers.size() - 1 == 0) return;

        layers.remove(getImage());
        layerViews.remove(getLayerView());

        canvasView.update(getImage());
    }

    public void changeLayer(int index) {
        this.index = index;

        // only update the view if the canvas is initialized
        if (index >= 0) {
            canvasView.update(getImage());
        }
    }

    public void swapLayer(Direction location) {
        if (location == Direction.UP && index - 1 >= 0) {
            PixelImage currentImage = layers.get(index);
            layers.remove(currentImage);
            layers.add(index - 1, currentImage);

            Collections.swap(layerViews, index, index - 1);

        } else if (location == Direction.DOWN && index + 1 <= layerViews.size() - 1) {
            PixelImage nextImage = layers.get(index + 1);
            layers.remove(nextImage);
            layers.add(index, nextImage);

            Collections.swap(layerViews, index, index + 1);
        }
    }

    public ArrayList<String[][]> getLayersData() {
        ArrayList<String[][]> layersArrayList = new ArrayList<>();

        for (PixelImage layer : layers) {
            layersArrayList.add(layer.getImageData());
        }

        return layersArrayList;
    }

    public void setLayersData(ArrayList<String[][]> layersArrayList) {
        getImage().setImageData(layersArrayList.get(0));
        getCanvasView().update(getImage());
        getLayerView().update(getImage());

        for (int i = 1; i < layersArrayList.size(); i++) {
            createLayer();
            getImage().setImageData(layersArrayList.get(i));
            getCanvasView().update(getImage());
            getLayerView().update(getImage());
        }
    }
}
