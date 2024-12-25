package oop.dsai.project.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import oop.dsai.project.tree.exception.TreeException;

import java.io.IOException;

import static oop.dsai.project.gui.ScreenController.staticLabel;

public class ScreenMenuController {

    @FXML
    private Button exitButton;
    private GraphicTree graphicTree;

    public void initialize() {
        try {
            graphicTree = new GraphicTree();
        } catch (TreeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void transferData(GraphicTree tree) {
        this.graphicTree = tree;
    }

    @FXML
    public void selectBalancedBinaryTree(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLGraphicTree.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ScreenController controller = loader.getController();
        controller.transferData(this.graphicTree);
        controller.switchTree(4);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.restoreDraw();
        staticLabel.setText("Balanced Binary Tree");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void selectBalancedTree(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLGraphicTree.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ScreenController controller = loader.getController();
        controller.transferData(this.graphicTree);
        controller.switchTree(3);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.restoreDraw();
        staticLabel.setText("Balanced Tree");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void selectBinaryTree(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLGraphicTree.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ScreenController controller = loader.getController();
        controller.transferData(this.graphicTree);
        controller.switchTree(2);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.restoreDraw();
        staticLabel.setText("Binary Tree");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void selectGenericTree(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("FXMLGraphicTree.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ScreenController controller = loader.getController();
        controller.transferData(this.graphicTree);
        controller.switchTree(1);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        controller.restoreDraw();
        staticLabel.setText("Generic Tree");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void selectHelpMenu(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION,
                "Aims: Visualization of Tree Data Structures" +
                        "\n\nHow to visualize a tree:\n" +
                        "Step 1: Choose your type of tree\n" +
                        "Step 2: Choose operations\n" +
                        "Step 3: Wait and enjoy your work\n", ButtonType.OK);
        alert.setTitle("Help Menu");
        alert.setHeaderText("Orientation");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> alert.close());
    }

    @FXML
    public void pressExitButton(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to quit?", ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle("Exit");
        alert.setHeaderText("");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        } else {
        }
    }
}
