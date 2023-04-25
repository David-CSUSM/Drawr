package com.thebinarybandits.drawr;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import com.google.common.base.Optional;
import com.thebinarybandits.drawr.layers.LayersController;
import com.thebinarybandits.drawr.pane.PaneController;
import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import com.thebinarybandits.drawr.pixelcanvasviewer.PixelCanvasViewer;
import com.thebinarybandits.drawr.tools.ToolsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

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

        //popup window to alert user that creating a new project will cause unsaved changes to be lost
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("New Project");
        alert.setHeaderText("Are you sure you want to create a new project?");
        alert.setContentText("All unsaved changes will be lost.");
        java.util.Optional<ButtonType> result = alert.showAndWait();


        if (result.isPresent() && result.get() == ButtonType.OK){
            // user clicked OK, reset the canvas
            //TODO
        } else {
            // user clicked Cancel, do nothing
        }
    }
    

    @FXML
    void openProject(ActionEvent event) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a File");
    
        // Show the dialog and wait for user response
        int result = fileChooser.showOpenDialog(null);
    
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                // Get the selected file
                File selectedFile = fileChooser.getSelectedFile();
    
                // Load the pixel art image
                BufferedImage image = ImageIO.read(selectedFile);
    
                // Process the pixel art image
                //TODO
    
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void saveProject(ActionEvent event) {

    }

    @FXML
    void saveProjectAs(ActionEvent event) {

    }
}
