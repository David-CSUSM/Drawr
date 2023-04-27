package com.thebinarybandits.drawr;

import java.io.*;
import javax.swing.*;

import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.WritableImage;
//import javafx.embed.swing.SwingFXUtils;
import java.util.ArrayList;

public class AppController {
    private PixelCanvas canvas;

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
        }
    }


    @FXML
    void openProject(ActionEvent event) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose a File");

        // Show the dialog and wait for user response
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile(); // need to enfore choosing only '.drawr' file

            FileInputStream fileIn = new FileInputStream(selectedFile.toString());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<String[][]> layersArrayList = (ArrayList<String[][]>) in.readObject();
            in.close();

            // if we allow users to change canvas size
            // then we would have to initialize the canvas based on the file's canvas size
            canvas.reset();
            canvas.initialize();
            canvas.setLayersData(layersArrayList);
        }
    }

    @FXML
    void saveProjectAs(ActionEvent event) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();
        // Show the dialog and wait for user response
        int result = fileChooser.showSaveDialog(null);

        WritableImage canvasImage = canvas.getImage();
        WritableImage writableImage = new WritableImage((int) canvasImage.getWidth(), (int) canvasImage.getHeight());

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
    void saveProject(ActionEvent event) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser fileChooser = new JFileChooser();

        // Show the dialog and wait for user response
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            ArrayList<String[][]> layersArrayList = canvas.getLayersData();

            FileOutputStream fileOut = new FileOutputStream(selectedFile.toString()); // need to enfore saving only as '.drawr' file
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(layersArrayList);
            out.close();
            fileOut.close();
        }
    }
}
