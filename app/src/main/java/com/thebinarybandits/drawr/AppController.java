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
import com.thebinarybandits.drawr.pixelcanvasviewer.PixelViewer;
import com.thebinarybandits.drawr.tools.ToolsController; 
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
//import javafx.embed.swing.SwingFXUtils; 
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.UIManager.*;

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
            // user clicked OK, reset the PixelCanvas & PixelCanvasViewer
            PixelCanvas.getInstance().resetLayersAndIndex();
            PixelCanvasViewer.getInstance().resetViewersAndIndex();
            PixelCanvas.getInstance().getActiveLayer();
            PixelCanvasViewer.getInstance().getActiveView();
            layersController.updateLayerView();
            paneController.addPixelViewer(layersController.getPixelCanvasViewerView().getActiveView().getView());
            
        } else {
            // user clicked Cancel, do nothing
        }
    }
    

    @FXML
    void openProject(ActionEvent event) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        FileInputStream fileIn = new FileInputStream("test.drawr");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        ArrayList<String[][]> layersArrayList = (ArrayList<String[][]>) in.readObject();
        in.close();
        fileIn.close();

        PixelCanvas.getInstance().resetLayersAndIndex();
        PixelCanvasViewer.getInstance().resetViewersAndIndex();

        PixelCanvas.getInstance().setLayersData(layersArrayList);
        PixelCanvasViewer.getInstance().setViewersData();

        layersController.updateLayerView();
        paneController.addPixelViewer(layersController.getPixelCanvasViewerView().getActiveView().getView());
    }

    @FXML
    void saveProjectAs(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {;
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    
        JFileChooser fileChooser = new JFileChooser();
        // Show the dialog and wait for user response
        int result = fileChooser.showSaveDialog(null);

        WritableImage canvasImage = PixelCanvas.getInstance().getActiveLayer().getImage();

        javafx.scene.image.WritableImage writableImage = new javafx.scene.image.WritableImage(
            (int) canvasImage.getWidth(),
            (int) canvasImage.getHeight()
    );

      //  BufferedImage buffedImage = SwingFXUtils.fromFXImage(canvasImage, null);
    
        if (result == JFileChooser.APPROVE_OPTION) {
            // Get the selected file
            File selectedFile = fileChooser.getSelectedFile();
    
            // Create a BufferedImage object of the same size as the pixel canvas
            //BufferedImage image = new BufferedImage(PixelCanvas.getInstance().getHeight(),PixelCanvas.getInstance().getHeight(), 0);
                //todo
    
            // Get the Graphics object of the BufferedImage
            //todo
    
            // Paint the pixel canvas onto the BufferedImage
            //todo
    
            //try {
                // Write the BufferedImage to the selected file
               // ImageIO.write(image, "png", selectedFile);
           // } //catch (IOException ex) {
              //  ex.printStackTrace();
           // }
        }
    }

    @FXML
    void saveProject(ActionEvent event) throws IOException{
        ArrayList<String[][]> layersArrayList = new ArrayList<String[][]>();
        layersArrayList = PixelCanvas.getInstance().getLayersData();

        FileOutputStream fileOut = new FileOutputStream("test.drawr");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(layersArrayList);
        out.close();
        fileOut.close();
    }
}
