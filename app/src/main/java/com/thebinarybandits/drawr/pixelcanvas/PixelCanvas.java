package com.thebinarybandits.drawr.pixelcanvas;

import com.thebinarybandits.drawr.tools.Tool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.util.ArrayList;

// singleton pattern
public class PixelCanvas {
    private static volatile PixelCanvas instance;
    private int size;
    private int viewSize;
    private Tool tool;
    private Color color;
    private final ArrayList<PixelImage> layers;
    private final ObservableList<PixelView> views;
    private final PixelView layerView;
    private int index;
    public enum Direction { BACK, FORWARD }

    private PixelCanvas() {
        size = 16;
        viewSize = 640;
        tool = null;
        color = Color.BLACK;
        layers = new ArrayList<>();
        views = FXCollections.observableArrayList();
        layerView = new PixelView(240);
        index = 0;
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
        layers.add(new PixelImage(size));
        views.add(new PixelView(viewSize));
    }

    public void reset() {
        size = 16;
        viewSize = 640;
        tool = null;
        color = Color.BLACK;
        layers.clear();
        views.clear();
        layerView.clear();
        index = 0;
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

    public PixelView getView() {
        return views.get(index);
    }

    public PixelView getLayerView() {
        return layerView;
    }

    public ArrayList<PixelImage> getLayers() {
        return layers;
    }

    public ObservableList<PixelView> getViews() {
        return views;
    }

    public void draw(int x, int y) {
        if (tool != null && layers.size() > 0) {
            tool.useTool(layers.get(index), x, y, color, size);
            getView().update(getImage());

            layerView.update(getImage());
        }
    }

    public void clear() {
        layers.get(index).clear();
        views.get(index).clear();
        layerView.clear();
    }

    public void createLayer() {
        if (index == layers.size() - 1) {
            ++index;
            layers.add(new PixelImage(size));
            views.add(new PixelView(viewSize));

        } else {
            ++index;
            layers.add(index, new PixelImage(size));
            views.add(index, new PixelView(viewSize));
        }

        layerView.update(getImage());
    }

    public void changeLayer(int index) {
        boolean inBounds = index >= 0 && index < layers.size() - 1;

        if (inBounds) {
            this.index = index;
        }
    }

    public void changeLayer(Direction location) {
        if (location == Direction.BACK && index > 0) {
            --index;

        } else if (location == Direction.FORWARD && index < layers.size() - 1) {
            ++index;
        }

        layerView.update(getImage());
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
        getView().update(getImage());

        for (int i = 1; i < layersArrayList.size(); i++) {
            createLayer();
            getImage().setImageData(layersArrayList.get(i));
            getView().update(getImage());
        }

        layerView.update(getImage());
    }
}
