package com.thebinarybandits.drawr;

import com.thebinarybandits.drawr.encoder.GifManager;
import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import com.thebinarybandits.drawr.tools.ToolsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.Parent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.ArrayList;

public class AppController {
    @FXML
    private Parent tools;
    private PixelCanvas canvas;
    @FXML
    private ToolsController toolsController;

    @FXML
    public void initialize() {
        canvas = PixelCanvas.getInstance();
        canvas.initialize();
    }

    @FXML
    void newProject(ActionEvent event) {
        // popup window to alert user that creating a new project will cause unsaved changes to be lost
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("New Project");
        alert.setHeaderText("Are you sure you want to create a new project?");
        alert.setContentText("All unsaved changes will be lost.");
        java.util.Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // user clicked OK, reset the PixelCanvas
            canvas.reset();
            canvas.initialize();
            toolsController.reset();
        }
    }


    @FXML
    void openProject(ActionEvent event) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a File");

        // Set file extension filter to only show '.drawr' files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Drawr Files (*.drawr)", "drawr");
        fileChooser.setFileFilter(filter);

        // Show the dialog and wait for user response
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Check if selected file is a '.drawr' file
            if (!selectedFile.getName().endsWith(".drawr")) {
                // Display an error message if selected file is not a '.drawr' file
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please choose a '.drawr' file.");
                alert.showAndWait();
                return;
            }

            FileInputStream fileIn = new FileInputStream(selectedFile.toString());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<String[][]> layersArrayList = (ArrayList<String[][]>) in.readObject();
            in.close();

            // if we allow users to change canvas size
            // then we would have to initialize the canvas based on the file's canvas size
            canvas.reset();
            canvas.initialize();
            canvas.initLayersData(layersArrayList);
            toolsController.reset();
        }
    }

    @FXML
    void saveAsGIF(ActionEvent event) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();

        // Set file extension filter to only show '.gif' files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("GIF Files (*.gif)", "gif");
        fileChooser.setFileFilter(filter);

        // Show the dialog and wait for user response
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Add '.gif' extension if not already present
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.endsWith(".gif")) {
                selectedFile = new File(filePath + ".gif");
            }

            GifManager gifmanager = new GifManager(canvas.getLayers());
            gifmanager.start(selectedFile.toString());


        }
    }

    @FXML
    void saveAsPNG(ActionEvent event) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();

        // Set file extension filter to only show '.png' files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Files (*.png)", "png");
        fileChooser.setFileFilter(filter);

        // Show the dialog and wait for user response
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Add '.png' extension if not already present
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.endsWith(".png")) {
                selectedFile = new File(filePath + ".png");
            }



        }
    }

    @FXML
    void saveAsSpritesheet(ActionEvent event) {

    }

    @FXML
    void saveProject(ActionEvent event) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();

        // Set file extension filter to only show '.drawr' files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Drawr Files (*.drawr)", "drawr");
        fileChooser.setFileFilter(filter);

        // Show the dialog and wait for user response
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Add '.drawr' extension if not already present
            String filePath = selectedFile.getAbsolutePath();
            if (!filePath.endsWith(".drawr")) {
                selectedFile = new File(filePath + ".drawr");
            }

            ArrayList<String[][]> layersArrayList = canvas.getLayersData();

            FileOutputStream fileOut = new FileOutputStream(selectedFile.toString());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(layersArrayList);
            out.close();
            fileOut.close();
        }
    }

    @FXML
    void undo(ActionEvent event) {
        canvas.undo();
    }

    @FXML
    void redo(ActionEvent event) {
        canvas.redo();
    }

    @FXML
    void clear(ActionEvent event) {
        canvas.clear();
    }
}
