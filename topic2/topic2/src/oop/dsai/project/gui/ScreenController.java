package oop.dsai.project.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import oop.dsai.project.tree.exception.TreeException;

import java.io.IOException;

public class ScreenController {
    @FXML
    private TextField inputNodeKey;
    @FXML
    private TextArea resultArea;
    @FXML
    private TextArea textArea;
    @FXML
    private TextArea pseudoArea;
    @FXML
    private Label currentTreeName;

    @FXML
    private BorderPane screenContainer;

    @FXML
    private Button pauseBtn;

    @FXML
    private Button resumeBtn;

    @FXML
    public  TextArea processArea;

    @FXML
    public Button sampleBtn;

    public static TextArea static_process;
    public static TextArea static_result;
    public static TextArea static_pseudo;

    public static Label staticLabel;

    private GraphicTree graphicTree;

    public void initialize() {
        staticLabel = currentTreeName;
        static_process = processArea;
        static_result = resultArea;
        static_pseudo = pseudoArea;
        try {
            graphicTree = new GraphicTree();
        } catch (TreeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        screenContainer.setCenter(graphicTree);
        pauseBtn.setVisible(false);
        resumeBtn.setVisible(false);

        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());
    }

    public Label getCurrentTreeName() {
        return currentTreeName;
    }

    public void setCurrentTreeName(Label currentTreeName) {
        this.currentTreeName = currentTreeName;
    }

    public void transferData(GraphicTree tree) {
        this.graphicTree = tree;
    }

    public void switchTree(Integer type) {
        this.graphicTree.switchTree(type);
        this.graphicTree.drawTree();
    }

    public void samplePressed(ActionEvent event) throws TreeException {
        screenContainer.setCenter(graphicTree);
        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());

        if (staticLabel.getText() == "Generic Tree") {
            graphicTree.createEmptyTree(1);
            graphicTree.drawTree();
            if (graphicTree.isEmpty()) {
                graphicTree.setRoot(1);
            }
        }
        graphicTree.insert(1,3);
        graphicTree.insert(1, 4);
    }

    @FXML
    public void createPressed(ActionEvent event) {
        processArea.clear();
        pseudoArea.clear();
        screenContainer.setCenter(graphicTree);
        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());

        if (!graphicTree.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Do you want to create new tree? This will delete the old tree.", ButtonType.OK, ButtonType.CANCEL);
            alert.setTitle("Exit");
            alert.setHeaderText("");
            if (alert.showAndWait().get() != ButtonType.OK) {
                return;
            }
        }
        Alert success = new Alert(AlertType.INFORMATION, "Successfully create a new empty tree!", ButtonType.OK);
        success.setTitle("");
        success.setHeaderText("");
        Alert fail = new Alert(AlertType.INFORMATION, "Please input a POSITIVE INTEGER!", ButtonType.OK);
        fail.setTitle("Error");
        fail.setHeaderText("");
        pseudoArea.appendText("Get tree_label from Menu then create a new empty tree by \n\n.createEmtyTree() \n.DrawTree()\n");
        if (staticLabel.getText() == "Generic Tree") {
            this.graphicTree.createEmptyTree(1);
            this.graphicTree.drawTree();
            success.show();
            textArea.appendText("\nCreate a new Generic Tree");
            resultArea.appendText("\nNew Generic Tree is created successfully");
        } else if (staticLabel.getText() == "Binary Tree") {
            this.graphicTree.createEmptyTree(2);
            this.graphicTree.drawTree();
            success.show();
            textArea.appendText("\nCreate Binary Tree");
            resultArea.appendText("\nNew Binary Tree is created successfully");
        } else if (staticLabel.getText() == "Balanced Tree") {
            pseudoArea.appendText("\nIf label is Balanced Tree, user input distance limit");
            processArea.appendText("\nInput distance limit");
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Input the distance limit");
            Button okButton = (Button) td.getDialogPane().lookupButton(ButtonType.OK);
            EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent evt) {
                    try {
                        Integer temp = Integer.parseInt(td.getEditor().getText().trim());
                        if (temp > 0) {
                            graphicTree.createEmptyTree(3);
                            graphicTree.setLimit(temp, 3);
                            graphicTree.drawTree();
                            success.show();
                            textArea.appendText("\nCreate Balanced Tree");
                            resultArea.appendText("\nNew Balanced Tree is created successfully with distance " + temp );
                        } else {
                            throw new NumberFormatException();
                        }
                    } catch (Exception e) {
                        fail.show();
                        textArea.appendText("\nCreate Balanced Tree");
                        resultArea.appendText("\nERROR!: Input must be Integer");
                        Platform.runLater(() -> textArea.selectRange(0, 6));
                    }
                }
            };
            okButton.setOnAction(event1);
            td.showAndWait();
        } else {
            processArea.setText("Input distance limit");
            pseudoArea.appendText("\nIf label is Balanced Tree, user input distance limit");
            TextInputDialog td = new TextInputDialog();
            td.setHeaderText("Input the distance limit");
            Button okButton = (Button) td.getDialogPane().lookupButton(ButtonType.OK);
            EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent evt) {
                    try {
                        Integer temp = Integer.parseInt(td.getEditor().getText().trim());
                        if (temp > 0) {
                            graphicTree.createEmptyTree(4);
                            graphicTree.setLimit(temp, 4);
                            graphicTree.drawTree();
                            success.show();
                            textArea.appendText("\nCreate Balanced Binary Tree");
                            resultArea.appendText("\nNew Balanced Binary Tree is created successfully with distance " + temp);
                        } else {
                            throw new NumberFormatException();
                        }
                    } catch (Exception e) {
                        fail.show();
                        textArea.appendText("\nCreate Balanced Binary Tree");
                        resultArea.appendText("\nERROR!: Input must be Integer");
                        Platform.runLater(() -> textArea.selectRange(0, 6));
                    }
                }
            };
            okButton.setOnAction(event1);
            td.showAndWait();
        }

    }

    @FXML
    public void insertPressed(ActionEvent event) {
        processArea.clear();
        pseudoArea.clear();
        screenContainer.setCenter(graphicTree);

        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());
        processArea.appendText("Check if tree exist");
        processArea.appendText("\nCheck input value");
        pseudoArea.appendText("\n1.Check if tree status and input value is correct format");
        pseudoArea.appendText("\n2.If tree is exist, then input parent value, if tree is empty then set root node");
        pseudoArea.appendText("\n3.Search for parent value then insert ");
        if (graphicTree.isEmptyForBalanced()) {
            Alert er1 = new Alert(AlertType.INFORMATION, "You must create a tree and set the limit first",
                    ButtonType.OK);
            er1.setTitle("Error");
            er1.setHeaderText("");
            er1.show();
            Platform.runLater(() -> textArea.selectRange(0, 6));
        } else {
            try {
                processArea.appendText("\nTree is existed");
                processArea.appendText("\nCheck new node value: valid");
                Integer num = Integer.parseInt(this.inputNodeKey.getText().trim());
                if (graphicTree.isEmpty()) {
                    graphicTree.setRoot(num);
                    textArea.appendText("\nInsert root " + num);
                    return;
                }
                TextInputDialog td = new TextInputDialog();
                processArea.appendText("\nInput parent");
                td.setHeaderText("Input the parent node's key you want to insert into");
                Button okButton = (Button) td.getDialogPane().lookupButton(ButtonType.OK);
                EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent evt) {
                        try {
                            Integer temp = Integer.parseInt(td.getEditor().getText().trim());
                            pauseBtn.setVisible(true);
                            graphicTree.insert(temp, num);
                            textArea.appendText("\nInsert node " + num + " into node " + temp);
                        } catch (TreeException e) {
                            Alert er2 = new Alert(AlertType.INFORMATION, e.getMessage(), ButtonType.OK);
                            er2.setTitle("Error");
                            er2.setHeaderText("");
                            er2.show();
                            resultArea.appendText("\nERROR!");
                        } catch (NumberFormatException e2) {
                            Alert er1 = new Alert(AlertType.INFORMATION, "Key must be an INTEGER!", ButtonType.OK);
                            er1.setTitle("Error");
                            er1.setHeaderText("");
                            er1.show();
                            Platform.runLater(() -> textArea.selectRange(0, 6));
                            resultArea.appendText("\nInsert failed because invalid input");
                        }
                    }
                };
                okButton.setOnAction(event1);
                td.showAndWait();
            } catch (NumberFormatException e1) {
                Alert er1 = new Alert(AlertType.INFORMATION, "Key must be an INTEGER!", ButtonType.OK);
                er1.setTitle("Error");
                er1.setHeaderText("");
                er1.show();
                Platform.runLater(() -> textArea.selectRange(0, 6));
                resultArea.appendText("\nInsert failed because invalid input");
            }
        }
        graphicTree.timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evt) {
                pauseBtn.setVisible(false);
            }

        });
    }

    public void restoreDraw() {
        screenContainer.setCenter(graphicTree);

        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());
        graphicTree.drawTree();
    }

    @FXML
    public void deletePressed(ActionEvent event) {
        pseudoArea.clear();
        processArea.appendText("Check if tree exist");
        processArea.appendText("\nCheck key value");
        pseudoArea.appendText("\n1.Check if tree status and key value is correct format");
        pseudoArea.appendText("\n2.If tree is exist, search the node with key value");
        pseudoArea.appendText("\n3.Delete node with key value if existed ");
        screenContainer.setCenter(graphicTree);
        static_process.clear();
        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());
        processArea.appendText("Check if tree exist");
        processArea.appendText("\nCheck input value");
        if (graphicTree.isEmptyForBalanced()) {
            Alert er1 = new Alert(AlertType.INFORMATION, "You must create a tree and set the limit first",
                    ButtonType.OK);
            er1.setTitle("Error");
            er1.setHeaderText("");
            er1.show();
            Platform.runLater(() -> textArea.selectRange(0, 6));
            processArea.appendText("\nTree is not existed");
            resultArea.appendText("\nDelete failed because tree does not exist");
        } else {
            if (graphicTree.isEmpty()) {
                Alert er1 = new Alert(AlertType.INFORMATION, "The tree is empty now!", ButtonType.OK);
                er1.setTitle("Error");
                er1.setHeaderText("");
                er1.show();
                textArea.appendText("\nERROR!: Empty Tree");
                resultArea.appendText("Delete failed because tree is empty");
                Platform.runLater(() -> textArea.selectRange(0, 6));
            } else {
                try {
                    processArea.appendText("\n-> Tree exist");
                    processArea.appendText("\n->Input value: valid");
                    Integer num = Integer.parseInt(this.inputNodeKey.getText().trim());
                    pauseBtn.setVisible(true);
                    graphicTree.delete(num);
                    textArea.appendText("\nDelete node " + num);
                } catch (NumberFormatException e1) {
                    Alert er1 = new Alert(AlertType.INFORMATION, "Key must be an INTEGER!", ButtonType.OK);
                    er1.setTitle("Error");
                    er1.setHeaderText("");
                    er1.show();
                    textArea.appendText("\nERROR!: Input must be Integer");
                    resultArea.appendText(("Delete failed because invalid input"));
                    Platform.runLater(() -> textArea.selectRange(0, 6));
                }
            }
        }
        graphicTree.timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evt) {
                pauseBtn.setVisible(false);
            }

        });
    }

    @FXML
    public void updatePressed(ActionEvent event) {
        screenContainer.setCenter(graphicTree);
        pseudoArea.clear();
        processArea.clear();
        processArea.appendText("Check if tree exist");
        processArea.appendText("\nCheck input value");
        processArea.appendText("Check if tree exist");
        processArea.appendText("\nCheck key value");
        pseudoArea.appendText("\n1.Check if tree status and key value is correct format");
        pseudoArea.appendText("\n2.If tree is exist, search the node with key value");
        pseudoArea.appendText("\n3.Update node with key value if existed ");
        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());

        if (graphicTree.isEmptyForBalanced()) {
            Alert er1 = new Alert(AlertType.INFORMATION, "You must create a tree and set the limit first",
                    ButtonType.OK);
            er1.setTitle("Error");
            er1.setHeaderText("");
            er1.show();
            Platform.runLater(() -> textArea.selectRange(0, 6));
            processArea.appendText("\nTree is not existed");
            resultArea.appendText("\nUpdate failed because tree does not exist");
        } else {
            try {
                Integer num = Integer.parseInt(this.inputNodeKey.getText().trim());
                if (graphicTree.isEmpty()) {
                    graphicTree.setRoot(num);
                    return;
                }
                TextInputDialog td = new TextInputDialog();
                td.setHeaderText("Input the value you want to update to node with value " + num);
                Button okButton = (Button) td.getDialogPane().lookupButton(ButtonType.OK);
                EventHandler<ActionEvent> event1 = evt -> {
                    try {
                        processArea.appendText("\n-> Tree exist");
                        processArea.appendText("\n->Input value: valid");
                        Integer temp = Integer.parseInt(td.getEditor().getText().trim());
                        pauseBtn.setVisible(true);
                        graphicTree.update(num, temp);
                        textArea.appendText("\nUpdate node " + num + " become " + temp);
                    } catch (TreeException e) {
                        Alert er2 = new Alert(AlertType.INFORMATION, e.getMessage(), ButtonType.OK);
                        er2.setTitle("Error");
                        er2.setHeaderText("");
                        er2.show();
                    } catch (NumberFormatException e2) {
                        Alert er1 = new Alert(AlertType.INFORMATION, "Key must be an INTEGER!", ButtonType.OK);
                        er1.setTitle("Error");
                        er1.setHeaderText("");
                        er1.show();
                        textArea.appendText("\nERROR!: Input must be Integer");
                        Platform.runLater(() -> textArea.selectRange(0, 6));
                        processArea.appendText("\nInput is not valid");
                        resultArea.appendText("\nUpdate failed because invalid input");
                    }
                };
                okButton.setOnAction(event1);
                td.showAndWait();
            } catch (NumberFormatException e1) {
                Alert er1 = new Alert(AlertType.INFORMATION, "Key must be an INTEGER!", ButtonType.OK);
                er1.setTitle("Error");
                er1.setHeaderText("");
                er1.show();
                processArea.appendText("\nInput is not valid");
                resultArea.appendText("\nUpdate failed because invalid input");
                Platform.runLater(() -> textArea.selectRange(0, 6));
            }
        }
        graphicTree.timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evt) {
                pauseBtn.setVisible(false);
            }

        });
    }

    @FXML
    public void searchPressed(ActionEvent event) {
        pseudoArea.clear();
        processArea.appendText("Check if tree exist");
        processArea.appendText("\nCheck key value");
        pseudoArea.appendText("\n1.Check if tree status and key value is correct format");
        pseudoArea.appendText("\n2.If tree is exist, search the node with key value");
        pseudoArea.appendText("\n3.Draw the traverse line ");
        screenContainer.setCenter(graphicTree);
        processArea.clear();
        processArea.appendText("Check if tree exist");
        processArea.appendText("\nCheck input value");
        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());
        if (graphicTree.isEmptyForBalanced()) {
            Alert er1 = new Alert(AlertType.INFORMATION, "You must create a tree and set the limit first",
                    ButtonType.OK);
            er1.setTitle("Error");
            er1.setHeaderText("");
            er1.show();
            Platform.runLater(() -> textArea.selectRange(0, 6));
            processArea.appendText("\nTree is not existed");
            resultArea.appendText("\nSearch failed because tree does not exist");
        } else {
            if (graphicTree.isEmpty()) {
                Alert er1 = new Alert(AlertType.INFORMATION, "The tree is empty now!", ButtonType.OK);
                er1.setTitle("Error");
                er1.setHeaderText("");
                er1.show();
                Platform.runLater(() -> textArea.selectRange(0, 6));
                processArea.appendText("\nTree is empty");
                resultArea.appendText("\nSearch failed because tree is empty");
            } else {
                try {
                    processArea.appendText("\n-> Tree exist");
                    processArea.appendText("\n->Input value: valid");
                    Integer num = Integer.parseInt(this.inputNodeKey.getText().trim());
                    pauseBtn.setVisible(true);
                    graphicTree.search(num);
                    textArea.appendText("\nSearch for node " + num);
                } catch (NumberFormatException e1) {
                    Alert er1 = new Alert(AlertType.INFORMATION, "Key must be an INTEGER!", ButtonType.OK);
                    er1.setTitle("Error");
                    er1.setHeaderText("");
                    er1.show();
                    textArea.appendText("\nERROR!: Input must be Integer");
                    Platform.runLater(() -> textArea.selectRange(0, 6));
                    processArea.appendText("\nInput is not valid");
                    resultArea.appendText("\nSearch failed because invalid input");
                }
            }
        }
        graphicTree.timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evt) {
                pauseBtn.setVisible(false);
            }

        });
    }

    @FXML
    public void preorderPressed(ActionEvent event) {
        pseudoArea.clear();
        textArea.appendText("\nTraverse preorder");
        pseudoArea.appendText("\n1. Create a stack then push root into stack with state -1 (visit first time)" +
                "\n2. Create a ArrayList to store result" +
                "\n3. If stack.size() > 0, create a top variable to traverse:" +
                "\t\n3.1: if top.state == -1, List.add(top.node) , state++" +
                "\t\n3.2: if top.state == top.node.getChildren().size(). stack.pop (visted all child node)" +
                "\t\n3.3: Child node = top.node.getChildren.get(top.state) -> perform step 3.1");
        screenContainer.setCenter(graphicTree);
        processArea.clear();
        processArea.appendText("Check if tree exist");
        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());

        try {
            processArea.appendText("->Tree exist");
            pauseBtn.setVisible(true);
            graphicTree.preorderList();
            textArea.appendText("\nTraverse preorder");
            processArea.appendText("\nPerform preorder traverse");
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!!");
            alert.setHeaderText("");
            alert.setContentText("The tree is empty");
            alert.showAndWait();
            Platform.runLater(() -> textArea.selectRange(0, 6));
            processArea.appendText("\n->Tree is empty");
            resultArea.appendText("\nTraverse failed because tree is empty");
        }
        graphicTree.timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evt) {
                pauseBtn.setVisible(false);
            }

        });
    }

    @FXML
    public void postorderPressed(ActionEvent event) {
        pseudoArea.clear();
        textArea.appendText("\nTraverse postorder");
        pseudoArea.appendText("\\n1. Create a stack then push root into stack with state -1 (visit first time)" +
                "\\n2. Create a ArrayList to store result" +
                "\\n3. If stack.size() > 0, create a top variable to traverse:" +
                "\t\n3.1: if top.state == -1, state++\n" +
                "\t\n3.2: if top.state == top.node.getChildren().size(), list.add(top), stack.pop (visted all child node)\n" +
                "\t\n3.3: Child node = top.node.getChildren.get(top.state) -> perform step 3.1");
        screenContainer.setCenter(graphicTree);
        processArea.clear();
        processArea.appendText("Check if tree exist");
        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());

        try {
            processArea.appendText("->Tree exist");
            pauseBtn.setVisible(true);
            graphicTree.postorderList();
            textArea.appendText("\nTraverse postorder");
            processArea.appendText("\nPerform postorder traverse");
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!!");
            alert.setHeaderText("");
            alert.setContentText("The tree is empty");
            textArea.appendText("\nERROR!: Empty tree");
            alert.showAndWait();
            Platform.runLater(() -> textArea.selectRange(0, 6));
            processArea.appendText("\n->Tree is empty");
            resultArea.appendText("\nTraverse failed because tree is empty");
        }
        graphicTree.timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evt) {
                pauseBtn.setVisible(false);
            }

        });
    }

    @FXML
    public void backPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ScreenMenu.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        ScreenMenuController controller = loader.getController();
        controller.transferData(this.graphicTree);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void resumePressed(ActionEvent event) {
        pseudoArea.clear();
        pseudoArea.appendText("\ngraphicTree.timeline.play();");
        graphicTree.timeline.play();
        processArea.appendText("Resume");
        Platform.runLater(() -> textArea.selectRange(0, 6));
        resumeBtn.setVisible(false);

    }

    @FXML
    public void pausePressed(ActionEvent event) {
        pseudoArea.clear();
        pseudoArea.appendText("\ngraphicTree.timeline.pause();");
        graphicTree.timeline.pause();
        processArea.appendText("Pause");
        Platform.runLater(() -> textArea.selectRange(0, 5));
        resumeBtn.setVisible(true);
    }

    @FXML
    void undoPressed(ActionEvent event) {
        processArea.clear();
        pseudoArea.clear();
        pseudoArea.appendText("\nTree_type tempTree = stack_Tree_type.pop();\n" +
                "            stack_Tree_type.push(tempTree);\n" +
                "            this.Tree_type = stack_Tree_type.peek();\n" +
                "            this.mainTree = stack_Tree_type.peek();\n" );
        screenContainer.setCenter(graphicTree);
        processArea.appendText("\nUndo");
        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());

        if (graphicTree.isEmpty()) {
            Alert er1 = new Alert(AlertType.INFORMATION, "The tree is empty now!", ButtonType.OK);
            er1.setTitle("Error");
            er1.setHeaderText("");
            er1.show();
            textArea.appendText("\nERROR!: Empty tree");
            Platform.runLater(() -> textArea.selectRange(0, 6));
            return;
        } else {
            try {
                graphicTree.undo();
                textArea.appendText("\nUndo");
                Platform.runLater(() -> textArea.selectRange(0, 4));
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning!!");
                alert.setHeaderText("");
                alert.setContentText("Cannot undo to empty tree. You can create tree to get new empty tree. ");
                textArea.appendText("\nERROR!: Empty tree");
                Platform.runLater(() -> textArea.selectRange(0, 6));
                alert.showAndWait();
            }
        }
    }

    @FXML
    void redoPressed(ActionEvent event) {
        pseudoArea.clear();
        pseudoArea.appendText("\nTree_type tempTree = stack_Tree_type_2.pop();\n" +
                "            stack_Tree_type.push(tempTree);\n" +
                "            this.Tree_type = stack_Tree_type.peek();\n" +
                "            this.mainTree = stack_Tree_type.peek();\n" );
        processArea.clear();
        screenContainer.setCenter(graphicTree);
        processArea.appendText("\nRedo");
        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());

        if (graphicTree.isEmpty()) {
            Alert er1 = new Alert(AlertType.INFORMATION, "The tree is empty now!", ButtonType.OK);
            er1.setTitle("Error");
            er1.setHeaderText("");
            er1.show();
            textArea.appendText("\nERROR!: Empty tree");
            Platform.runLater(() -> textArea.selectRange(0, 6));
        } else {
            try {
                graphicTree.redo();
                textArea.appendText("\nRedo");
                Platform.runLater(() -> textArea.selectRange(0, 4));
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error!!");
                alert.setHeaderText("");
                alert.setContentText("No operation left to redo.");
                textArea.appendText("\nERROR!: Empty tree");
                Platform.runLater(() -> textArea.selectRange(0, 6));
                alert.showAndWait();
            }
        }
    }

    public void bfsPressed(ActionEvent actionEvent) {
        textArea.appendText("\nTraverse BFS");
        pseudoArea.clear();
        pseudoArea.appendText("1. Create ArrayList store nodeList as result\n" +
                "2. Create Queue to store node traverse and add root node\n" +
                "3. \tNode currentNode = queue.poll();\n" +
                "\tbfsOrderList.add(currentNode);\n" +
                "4.For each child in currentNode queue.add(child)");
        screenContainer.setCenter(graphicTree);
        graphicTree.widthProperty().bind(screenContainer.widthProperty());
        graphicTree.heightProperty().bind(screenContainer.heightProperty());

        try {
            pauseBtn.setVisible(true);
            graphicTree.bfsList();
            textArea.appendText("\nTraverse BFS");
            processArea.appendText("Perform bfs traverse");
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error!!");
            alert.setHeaderText("");
            alert.setContentText("The tree is empty");
            textArea.appendText("\nERROR!: Empty tree");
            alert.showAndWait();
            Platform.runLater(() -> textArea.selectRange(0, 6));
            processArea.appendText("\n->Tree is empty");
            resultArea.appendText("\nTraverse failed because tree is empty");
        }
        graphicTree.timeline.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent evt) {
                pauseBtn.setVisible(false);
            }

        });
    }
}
