package oop.dsai.project.gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.util.Duration;
import oop.dsai.project.shape.Line;
import oop.dsai.project.tree.balanced.BalancedTree;
import oop.dsai.project.tree.balancedbinary.BalancedBinaryTree;
import oop.dsai.project.tree.binary.BinaryTree;
import oop.dsai.project.tree.exception.TreeException;
import oop.dsai.project.tree.generic.GenericTree;
import oop.dsai.project.tree.generic.Node;

import java.util.ArrayList;
import java.util.Stack;

public class GraphicTree extends Canvas {

    private GenericTree genericTree; // type1
    private BinaryTree binaryTree; // type2
    private BalancedTree balancedTree; // type3
    private BalancedBinaryTree balancedBinaryTree; // type4
    private GenericTree mainTree;
    private Stack<GenericTree> stackGenTree1 = new Stack<>();
    private Stack<GenericTree> stackGenTree2 = new Stack<>();
    private Stack<BinaryTree> stackBinTree1 = new Stack<>();
    private Stack<BinaryTree> stackBinTree2 = new Stack<>();
    private Stack<BalancedTree> stackBalTree1 = new Stack<>();
    private Stack<BalancedTree> stackBalTree2 = new Stack<>();
    private Stack<BalancedBinaryTree> stackBalBinTree1 = new Stack<>();
    private Stack<BalancedBinaryTree> stackBalBinTree2 = new Stack<>();

    private static final Integer numberLayer = 8;
    private ArrayList<Node> nodeList = new ArrayList<>();
    private ArrayList<String> nodeList_result = new ArrayList<String>();
    public static Timeline timeline = new Timeline();

    public void switchTree(Integer type) {
        if (type == 1) {
            this.mainTree = this.genericTree;
        } else if (type == 2) {
            this.mainTree = this.binaryTree;
        } else if (type == 3) {
            this.mainTree = this.balancedTree;
        } else {
            this.mainTree = this.balancedBinaryTree;
        }
    }

    public void setLimit(Integer limit, Integer type) {
        if (type == 3) {
            this.balancedTree.setLimitDistance(limit);
        } else {
            this.balancedBinaryTree.setLimitDistance(limit);
        }
    }

    public void createEmptyTree(Integer type) {
        if (type == 1) {
            this.genericTree = new GenericTree();
            this.mainTree = this.genericTree;
        } else if (type == 2) {
            this.binaryTree = new BinaryTree();
            this.mainTree = this.binaryTree;
        } else if (type == 3) {
            this.balancedTree = new BalancedTree();
            this.mainTree = this.balancedTree;
        } else {
            this.balancedBinaryTree = new BalancedBinaryTree();
            this.mainTree = this.balancedBinaryTree;
        }
    }

    public GraphicTree() throws TreeException {
        this.genericTree = new GenericTree();
        this.binaryTree = new BinaryTree();
        this.balancedTree = new BalancedTree();
        this.balancedBinaryTree = new BalancedBinaryTree();
        this.mainTree = new GenericTree();
        widthProperty().addListener(evt -> drawTree());
        heightProperty().addListener(evt -> drawTree());
    }

    public void drawTree() {
        GraphicsContext gc = this.getGraphicsContext2D();
        clear();
        if (this.mainTree.root != null) {
            drawLines(gc, this.mainTree.root, 0, this.getWidth(), 0, this.getHeight() / numberLayer);
            drawCircles(gc, this.mainTree.root, 0, this.getWidth(), 0, this.getHeight() / numberLayer);
        }
    }

    public void drawCircles(GraphicsContext gc, Node root, double minWidth, double maxWidth, double minHeight,
                            double maxHeight) {
        Point2D point = new Point2D((minWidth + maxWidth) / 2, (minHeight + maxHeight) / 2);
        root.getRootCircle().setPoint(point);
        root.getRootCircle().draw(gc);
        for (int i = 0; i < root.getNbChildren(); i++) {
            drawCircles(gc, root.getChildren().get(i), minWidth + i * (maxWidth - minWidth) / root.getNbChildren(),
                    minWidth + (i + 1) * (maxWidth - minWidth) / root.getNbChildren(), maxHeight,
                    2 * maxHeight - minHeight);
        }
    }

    public void drawLines(GraphicsContext gc, Node root, double minWidth, double maxWidth, double minHeight,
                          double maxHeight) {
        Point2D linePoint1; // Point_1
        Point2D linePoint2; // Point_2
        Line newLine = new Line(); // Blank line
        linePoint1 = new Point2D((minWidth + maxWidth) / 2, (minHeight + maxHeight) / 2);
        if (root.getNbChildren() != 0) {
            for (int i = 0; i < root.getNbChildren(); i++) {
                linePoint2 = new Point2D(
                        (2 * minWidth + (2 * i + 1) * (maxWidth - minWidth) / root.getNbChildren()) / 2,
                        (3 * maxHeight - minHeight) / 2);
                newLine.setPoint(linePoint1, linePoint2);
                newLine.draw(gc);// Draw the line

                drawLines(gc, root.getChildren().get(i), minWidth + i * (maxWidth - minWidth) / root.getNbChildren(),
                        minWidth + (i + 1) * (maxWidth - minWidth) / root.getNbChildren(), maxHeight,
                        2 * maxHeight - minHeight);
            }
        }
    }

    public void drawHighlightSequence(ArrayList<Node> ar) {
        KeyFrame kf = new KeyFrame(Duration.ZERO, evt -> {
            ar.get(0).getRootCircle().setHighlighter(true);
            drawTree();
        });
        timeline.getKeyFrames().add(kf);
        for (Integer index = 1; index < ar.size(); index++) {
            final Integer i = index;
            KeyFrame kf1 = new KeyFrame(Duration.millis(500 * i), evt -> {
                ar.get(i - 1).getRootCircle().setHighlighter(false);
                ar.get(i).getRootCircle().setHighlighter(true);
                drawTree();
            });
            timeline.getKeyFrames().add(kf1);
        }

        KeyFrame kf2 = new KeyFrame(Duration.millis(ar.size() * 500), evt -> {
            ar.get(ar.size() - 1).getRootCircle().setHighlighter(false);
            drawTree();
        });
        timeline.getKeyFrames().add(kf2);
    }

    public void insert(Integer parentValue, Integer keyValue) throws TreeException {
        timeline.getKeyFrames().clear();
        nodeList.clear();
        nodeList.add(this.mainTree.root);
        nodeList = this.mainTree.searchNode(nodeList, parentValue);
        this.drawHighlightSequence(nodeList);
        final int numTime = nodeList.size(); // so lan de chay xong search
        ScreenController.static_process.appendText("\nDrawing process");
        if (nodeList.get(nodeList.size() - 1).getDepth() == 8) {
            Alert er2 = new Alert(AlertType.INFORMATION, "The maximum tree's depth of this program is "
                    + numberLayer.toString() + "! You cannot insert more", ButtonType.OK);
            er2.setTitle("Error");
            er2.setHeaderText("");
            er2.show();
            timeline.playFromStart();
        } else {
            ScreenController.static_process.appendText("\nSearching parent node with value " + parentValue);
            KeyFrame kf = new KeyFrame(Duration.millis(500 * numTime), evt -> {
                try {
                    nodeList = this.mainTree.insertNode(parentValue, new Node(keyValue));
                    pushCloneTree();
                } catch (TreeException e) {
                    Alert er2 = new Alert(AlertType.INFORMATION, e.getMessage(), ButtonType.OK);
                    er2.setTitle("Error");
                    er2.setHeaderText("");
                    er2.show();
                }
                nodeList.get(nodeList.size() - 1).getRootCircle().setHighlighter(true);
                drawTree();
            });
            timeline.getKeyFrames().add(kf);
            KeyFrame kf1 = new KeyFrame(Duration.millis(500 * (numTime + 1)), evt -> {
                nodeList.get(nodeList.size() - 1).getRootCircle().setHighlighter(false);
                drawTree();
            });

            timeline.getKeyFrames().add(kf1);
            timeline.playFromStart();
        }
    }

    public void search(Integer searchValue) {
        String result ;
        timeline.getKeyFrames().clear();
        nodeList.clear();
        nodeList.add(this.mainTree.root);
        nodeList = this.mainTree.searchNode(nodeList, searchValue);
        final int numTime = nodeList.size(); // so lan de chay xong search
        this.drawHighlightSequence(nodeList);
        KeyFrame kf = new KeyFrame(Duration.millis(500 * numTime), evt -> {
            if (nodeList.get(nodeList.size() - 1).getRootCircle().getSearchKey() != searchValue) {
                Alert er2 = new Alert(AlertType.INFORMATION, "\nCannot find the node with inserted key", ButtonType.OK);
                er2.setTitle("Error");
                er2.setHeaderText("");
                er2.show();
                ScreenController.static_process.appendText("\nCannot find the node with value " + searchValue);
                ScreenController.static_result.appendText("\nCannot find the node with value " + searchValue);
            } else {
                ScreenController.static_process.appendText("\n->Found the node with value " + searchValue);
                ScreenController.static_result.appendText("\n->Found the node with value " + searchValue);
            }
        });
        ScreenController.static_process.appendText("\nDrawing process");
        timeline.getKeyFrames().add(kf);
        timeline.playFromStart();
    }

    public void delete(Integer deleteValue) {
        timeline.getKeyFrames().clear();
        nodeList.clear();
        nodeList.add(this.mainTree.root);
        nodeList = this.mainTree.searchNode(nodeList, deleteValue);
        this.drawHighlightSequence(nodeList);
        final int numTime = nodeList.size(); // so lan de chay xong search
        Node deleteNode = nodeList.get(nodeList.size() - 1);
        ScreenController.static_process.appendText("\nDrawing process");
        KeyFrame kf = new KeyFrame(Duration.millis(500 * numTime), evt -> {
            try {
                nodeList = this.mainTree.deleteNode(deleteValue);
                pushCloneTree();
            } catch (TreeException e) {
                Alert er2 = new Alert(AlertType.INFORMATION, e.getMessage(), ButtonType.OK);
                er2.setTitle("Error");
                er2.setHeaderText("");
                er2.show();
            }
            drawTree();
        });
        timeline.getKeyFrames().add(kf);
        if (deleteNode.getChildren().size() > 0 && deleteNode != mainTree.root) {
            ArrayList<Node> childrenList = deleteNode.getChildren();
            KeyFrame kf1 = new KeyFrame(Duration.millis(500 * (numTime + 1)), evt -> {
                for (Node temp : childrenList)
                    temp.getRootCircle().setHighlighter(true);
                drawTree();
            });
            KeyFrame kf2 = new KeyFrame(Duration.millis(500 * (numTime + 2)), evt -> {
                for (Node temp : childrenList)
                    temp.getRootCircle().setHighlighter(false);
                drawTree();
            });
            timeline.getKeyFrames().add(kf1);
            timeline.getKeyFrames().add(kf2);
        }
        timeline.playFromStart();

    }

    public void update(Integer oldValue, Integer newValue) throws TreeException {
        timeline.getKeyFrames().clear();
        nodeList.clear();
        nodeList.add(this.mainTree.root);
        nodeList = this.mainTree.searchNode(nodeList, oldValue);
        final int numTime = nodeList.size(); // so lan de chay xong search
        this.drawHighlightSequence(nodeList);
        KeyFrame kf = new KeyFrame(Duration.millis(500 * numTime), evt -> {
            try {
                nodeList = this.mainTree.updateValueOfNode(oldValue, newValue);
                pushCloneTree();
            } catch (TreeException e) {
                Alert er2 = new Alert(AlertType.INFORMATION, e.getMessage(), ButtonType.OK);
                er2.setTitle("Error");
                er2.setHeaderText("");
                er2.show();
            }
            nodeList.get(nodeList.size() - 1).getRootCircle().setHighlighter(true);
            drawTree();
        });
        timeline.getKeyFrames().add(kf);
        KeyFrame kf1 = new KeyFrame(Duration.millis(500 * (numTime + 1)), evt -> {
            nodeList.get(nodeList.size() - 1).getRootCircle().setHighlighter(false);
            drawTree();
        });
        timeline.getKeyFrames().add(kf1);
        timeline.playFromStart();
    }

    public boolean isEmpty() {
        return this.mainTree.root == null;
    }

    public boolean isEmptyForBalanced() {
        return this.mainTree.root == null && ((this.balancedTree.getLimitDistance() == 1
                && this.mainTree == this.balancedTree)
                || (this.balancedBinaryTree.getLimitDistance() == 1 && this.mainTree == this.balancedBinaryTree));
    }

    public void setRoot(Integer key) {
        Node temp = new Node(key);
        temp.setDepth(1);
        this.mainTree.root = temp;
        this.drawTree();
        pushCloneTree();
    }

    public void clear() {
        getGraphicsContext2D().clearRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void preorderList() {
        timeline.getKeyFrames().clear();
        nodeList.clear();
        nodeList = this.mainTree.traversePreOrder();
        this.drawHighlightSequence(nodeList);
        timeline.playFromStart();

        for (Node i :nodeList) {
            nodeList_result.add(i.getRootCircle().getSearchKey() + "  ");
        }
        ScreenController.static_result.appendText("\nPreorder Traverse result is: \n" + nodeList_result);
        nodeList_result.clear();
    }

    public void postorderList() {
        timeline.getKeyFrames().clear();
        nodeList.clear();
        nodeList = this.mainTree.traversePostOrder();
        this.drawHighlightSequence(nodeList);
        timeline.playFromStart();

        for (Node i :nodeList) {
            nodeList_result.add(i.getRootCircle().getSearchKey() + "  ");
        }
        ScreenController.static_result.appendText("\nPostorder Traverse result is: \n" + nodeList_result);
        nodeList_result.clear();
    }

    public void bfsList() {
        timeline.getKeyFrames().clear();
        nodeList.clear();
        nodeList = this.mainTree.traverseBFS();
        System.out.println(nodeList);
        this.drawHighlightSequence(nodeList);
        timeline.playFromStart();
        //print result
        for (Node i :nodeList) {
            System.out.print(i.getRootCircle().getSearchKey() + "  ");
            nodeList_result.add(i.getRootCircle().getSearchKey() + "  ");
        }
        ScreenController.static_result.appendText("\nBFS Traverse result is: \n" + nodeList_result);
        nodeList_result.clear();
    }

    public void pushCloneTree() {
        try {
            if (mainTree instanceof BalancedBinaryTree) {
                BalancedBinaryTree sameTree = (BalancedBinaryTree) mainTree;
                BalancedBinaryTree copyTree = sameTree.cloneTree();
                stackBalBinTree1.push(copyTree);
            } else if (mainTree instanceof BalancedTree) {
                BalancedTree sameTree = (BalancedTree) mainTree;
                BalancedTree copyTree = sameTree.cloneTree();
                stackBalTree1.push(copyTree);
            } else if (mainTree instanceof BinaryTree) {
                BinaryTree sameTree = (BinaryTree) mainTree;
                BinaryTree copyTree = sameTree.cloneTree();
                stackBinTree1.push(copyTree);
            } else {
                GenericTree copyTree = mainTree.cloneTree();
                this.stackGenTree1.push(copyTree);
            }
        } catch (Exception e) {
            Alert er2 = new Alert(AlertType.INFORMATION, e.getMessage(), ButtonType.OK);
            er2.setTitle("Error");
            er2.setHeaderText("");
            er2.show();
        }
    }

    public void undo() {

        if (mainTree instanceof BalancedBinaryTree) {
            BalancedBinaryTree tempTree = stackBalBinTree1.pop();
            ScreenController.static_process.appendText("\nPop stack");
            stackBalBinTree2.push(tempTree);
            ScreenController.static_process.appendText("\nPush stack");
            this.balancedBinaryTree = stackBalBinTree1.peek();
            this.mainTree = stackBalBinTree1.peek();
            ScreenController.static_process.appendText("\nPeek stack");
        } else if (mainTree instanceof BalancedTree) {
            BalancedTree tempTree = stackBalTree1.pop();
            ScreenController.static_process.appendText("\nPop stack");
            stackBalTree2.push(tempTree);
            ScreenController.static_process.appendText("\nPush stack");
            this.balancedTree = stackBalTree1.peek();
            this.mainTree = stackBalTree1.peek();
            ScreenController.static_process.appendText("\nPeek stack");
        } else if (mainTree instanceof BinaryTree) {
            BinaryTree tempTree = stackBinTree1.pop();
            ScreenController.static_process.appendText("\nPop stack");
            stackBinTree2.push(tempTree);
            ScreenController.static_process.appendText("\nPush stack");
            this.binaryTree = stackBinTree1.peek();
            this.mainTree = stackBinTree1.peek();
            ScreenController.static_process.appendText("\nPeek stack");
        } else {
            GenericTree tempTree = stackGenTree1.pop();
            ScreenController.static_process.appendText("\nPop stack");
            stackGenTree2.push(tempTree);
            ScreenController.static_process.appendText("\nPush stack");
            this.genericTree = stackGenTree1.peek();
            this.mainTree = stackGenTree1.peek();
            ScreenController.static_process.appendText("\nPeek stack");
        }
        ScreenController.static_result.appendText("\nUndo successfully");
        drawTree();

    }

    public void redo() {

        if (mainTree instanceof BalancedBinaryTree) {
            BalancedBinaryTree tempTree = stackBalBinTree2.pop();
            ScreenController.static_process.appendText("\nPop stack");
            stackBalBinTree1.push(tempTree);
            ScreenController.static_process.appendText("\nPush stack");
            this.balancedBinaryTree = stackBalBinTree1.peek();
            this.mainTree = stackBalBinTree1.peek();
            ScreenController.static_process.appendText("\nPeek stack");
        } else if (mainTree instanceof BalancedTree) {
            BalancedTree tempTree = stackBalTree2.pop();
            ScreenController.static_process.appendText("\nPop stack");
            stackBalTree1.push(tempTree);
            ScreenController.static_process.appendText("\nPush stack");
            this.balancedTree = stackBalTree1.peek();
            this.mainTree = stackBalTree1.peek();
            ScreenController.static_process.appendText("\nPeek stack");
        } else if (mainTree instanceof BinaryTree) {
            BinaryTree tempTree = stackBinTree2.pop();
            ScreenController.static_process.appendText("\nPop stack");
            stackBinTree1.push(tempTree);
            ScreenController.static_process.appendText("\nPush stack");
            this.binaryTree = stackBinTree1.peek();
            this.mainTree = stackBinTree1.peek();
            ScreenController.static_process.appendText("\nPeek stack");
        } else {
            GenericTree tempTree = stackGenTree2.pop();
            ScreenController.static_process.appendText("\nPop stack");
            stackGenTree1.push(tempTree);
            ScreenController.static_process.appendText("\nPush stack");
            this.genericTree = stackGenTree1.peek();
            this.mainTree = stackGenTree1.peek();
            ScreenController.static_process.appendText("\nPeek stack");
        }
        drawTree();
        ScreenController.static_result.appendText("\nRedo successfully");
    }

}
