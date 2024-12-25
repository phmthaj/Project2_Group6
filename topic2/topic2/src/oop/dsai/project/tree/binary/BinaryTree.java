package oop.dsai.project.tree.binary;

import oop.dsai.project.shape.Circle;
import oop.dsai.project.tree.exception.TreeException;
import oop.dsai.project.tree.generic.GenericTree;
import oop.dsai.project.tree.generic.Node;
import oop.dsai.project.gui.ScreenController;
import java.util.ArrayList;

public class BinaryTree extends GenericTree {
	final int MAX_NB_CHILDREN = 2;

	public BinaryTree() {
		super();
	}

	public BinaryTree(Node root) {
		super(root);
	}

	public BinaryTree(Circle rootValue) {
		super(rootValue);
	}

	public BinaryTree createBinaryTree() {
		return new BinaryTree();
	}

	@Override
	public ArrayList<Node> insertNode(Integer parentValue, Node newNode) throws TreeException {
		boolean isNodeInTree = isInTree(root, newNode.getRootCircle().getSearchKey());
		ScreenController.static_process.appendText("");
		if (!isNodeInTree) {
			boolean isParentInTree = isInTree(root, parentValue);
			if (isParentInTree) {
				ArrayList<Node> searchParentNodeList = new ArrayList<>();
				searchParentNodeList.add(root);
				ArrayList<Node> searchNodeList = searchNode(searchParentNodeList, parentValue);
				Node insertNode = searchNodeList.get(searchNodeList.size() - 1);

				if (insertNode.getChildren().size() < this.MAX_NB_CHILDREN) {
					return super.insertNode(parentValue, newNode);
				} else{
					ScreenController.static_result.appendText("\nInsert failed: max children is 2 " + parentValue);
					ScreenController.static_process.appendText("\n->Cannot insert more nodes for node value" + parentValue +" :max children is 2 " );
					throw new TreeException("Only can input max 2 nodes!!");
				}
			} else {
				ScreenController.static_result.appendText("\nInsert failed: Cannot find parent node with value " + parentValue);
				ScreenController.static_process.appendText("\n->Cannot found node with value" + parentValue + " in tree");
				throw new TreeException("Cannot find node with value " + parentValue);
			}
		} else {
			ScreenController.static_result.appendText("\nInsert failed: node with value" + newNode.getRootCircle().getSearchKey() + " already exists in the tree");
			ScreenController.static_process.appendText("\n->Found node with value" + newNode.getRootCircle().getSearchKey() + " in tree");
			throw new TreeException("Node with value " + newNode.getRootCircle().getSearchKey() + " already exists in the tree");
		}
	}

	public Node getDeepestNode() {
		Node temp = root;
		while (temp.getChildren().get(temp.getChildren().size() - 1) != null) {
			temp = temp.getChildren().get(temp.getChildren().size() - 1);
		}
		if (temp.getChildren().get(0) != null) {
			temp.getChildren().remove(0);
			return temp.getChildren().get(0);
		} else {
			temp.getChildren().remove(temp.getChildren().size() - 1);
			return temp;
		}
	}

	public ArrayList<Node> deleteNode(Integer value) throws TreeException {
		boolean isInTree = isInTree(root, value);
		if (isInTree) {
			if (value == root.getRootCircle().getSearchKey()) {
				ScreenController.static_process.appendText("\n->Found key value is root node");
				ScreenController.static_result.appendText("\nDelete node " + " failed");
				throw new TreeException("Cannot delete root node. You can create new tree to clear old tree.");
			} else {
				ScreenController.static_process.appendText("\nFind node with key value in tree");
				ArrayList<Node> foundDeleteNodeList = new ArrayList<>();
				foundDeleteNodeList.add(root);
				foundDeleteNodeList = searchNode(foundDeleteNodeList, value);
				Node foundNode = foundDeleteNodeList.get(foundDeleteNodeList.size() - 1);
				ScreenController.static_process.appendText("\n->Found node with key value in tree");
				ScreenController.static_process.appendText("\nDeleted node " + value + " successfully");
				ScreenController.static_result.appendText(("\nDeleted node " + value + " successfully"));
				if(foundNode.getChildren().size() == 2) {  
					// replace the deleted node with its first child
					// the second child is now the child of the first child
					Node parentNode = getParentNode(root, value);
					foundDeleteNodeList.remove(foundNode);
					Integer indexOfDeteleNode = parentNode.getChildren().indexOf(foundNode);
					Node firstChildOfDeleteNode = foundNode.getChildren().get(0);
					Node secondChildOfDeleteNode = foundNode.getChildren().get(1);
					firstChildOfDeleteNode.setDepth(foundNode.getDepth());
					
					parentNode.getChildren().add(indexOfDeteleNode, firstChildOfDeleteNode);
					firstChildOfDeleteNode.getChildren().add(secondChildOfDeleteNode);
					foundDeleteNodeList.add(firstChildOfDeleteNode);
					foundDeleteNodeList.add(secondChildOfDeleteNode);
					parentNode.getChildren().remove(foundNode);
					this.updateDepth(this.root);
				} 
				else {
					foundDeleteNodeList = super.deleteNode(value);
				}
				return foundDeleteNodeList;
			}
		} else {
			ScreenController.static_result.appendText("\nDelete failed : cannot find node with value");
			ScreenController.static_process.appendText("\n->Cannot find node with value " + value);
			throw new TreeException("Cannot find node with value " + value);
		}
	}
	
	public BinaryTree cloneTree() {
		Node newRootNode = root.cloneNode(root);
		BinaryTree newTree = new BinaryTree(newRootNode);
		return newTree;
	}
	
}
