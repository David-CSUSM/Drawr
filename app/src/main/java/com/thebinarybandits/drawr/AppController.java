package com.thebinarybandits.drawr;

import com.thebinarybandits.drawr.layers.LayersController;
import com.thebinarybandits.drawr.pane.PaneController;
import com.thebinarybandits.drawr.tools.ToolsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;

public class AppController {
    @FXML
    private Parent tools;
    @FXML
    private Parent pane;
    @FXML
    private Parent layers;
    @FXML
    private ToolsController toolsController;
    @FXML
    private LayersController layersController;
    @FXML
    private PaneController paneController;

    @FXML
    public void initialize() {
        paneController.setLayersController(layersController);
        layersController.setPaneController(paneController);
        paneController.setToolsController(toolsController);
    }

    @FXML
    void newProject(ActionEvent event) {

    }

    @FXML
    void openProject(ActionEvent event) {

    }

    @FXML
    void saveProject(ActionEvent event) {

    }

    @FXML
    void saveProjectAs(ActionEvent event) {

    }
}
