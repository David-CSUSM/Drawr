package com.thebinarybandits.drawr.controllers;

import com.thebinarybandits.drawr.encoder.GifManager;
import com.thebinarybandits.drawr.pixelcanvas.PixelCanvas;
import com.thebinarybandits.drawr.pixelcanvas.PixelImage;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class AppController {

    private PixelCanvas canvas;

    @FXML private Parent tools;
    @FXML private ToolsController toolsController;

    @FXML
    public void initialize() {
        canvas = PixelCanvas.getInstance();
        canvas.initialize();
    }

    /**
     * Runs when the user clicks the 'new' menu button.
     * Creates a new project.
     */
    @FXML
    void newProject() {
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

    /**
     * Runs when the user clicks the 'open' menu button.
     * Opens a project from a .drawr file.
     */
    @FXML
    void openProject() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
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
    void saveAsGIF() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
    void saveAsPNG() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
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

            Canvas flattenImage = new Canvas(canvas.getSize(), canvas.getSize());
            GraphicsContext imageGraphics = flattenImage.getGraphicsContext2D();
            ArrayList<PixelImage> images = canvas.getLayers();

            for (PixelImage image : images) {
                imageGraphics.drawImage(image, 0, 0);
            }

            WritableImage png = flattenImage.snapshot(null, null);

            BufferedImage test = SwingFXUtils.fromFXImage(png, null);
            ImageIO.write(test, "png", selectedFile);
        }
    }

    @FXML
    void saveAsSpriteSheet() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
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

            int length = canvas.getSize() * canvas.getLayers().size();
            int height = canvas.getSize();
            Canvas combineImage = new Canvas(length, height);
            GraphicsContext imageGraphics = combineImage.getGraphicsContext2D();
            ArrayList<PixelImage> images = canvas.getLayers();

            int counter = 0;
            for (PixelImage image : images) {
                imageGraphics.drawImage(image, counter * canvas.getSize(), 0);
                ++counter;
            }

            WritableImage spriteSheet = combineImage.snapshot(null, null);

            BufferedImage test = SwingFXUtils.fromFXImage(spriteSheet, null);
            ImageIO.write(test, "png", selectedFile);
        }
    }

    /**
     * Runs when the user clicks the 'save' menu button.
     * Saves the project as a .drawr file.
     */
    @FXML
    void saveProject() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
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

    /**
     * Runs when the user clicks the 'undo' menu button.
     * Undo the project.
     */
    @FXML
    void undo() {
        canvas.undo();
    }

    /**
     * Runs when the user clicks the 'redo' menu button.
     * Redo the project.
     */
    @FXML
    void redo() {
        canvas.redo();
    }

    /**
     * Runs when the user clicks the 'clear' menu button.
     * Erases the entire active image
     */
    @FXML
    void clear() {
        canvas.clear();
    }

}
