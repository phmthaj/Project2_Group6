
package oop.dsai.project.tree.generic;

import oop.dsai.project.shape.Circle;
import oop.dsai.project.tree.exception.TreeException;
import oop.dsai.project.gui.ScreenController;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import static java.awt.Color.RED;


public class GenericTree {
	public Node root;
	public String status;

	public GenericTree() {
		this.root = null;
	}

	public GenericTree(Node root) {
		this.root = root;
		this.root.setDepth(1);
	}

	public GenericTree(Circle rootValue) {
		this.root.setRootCircle(rootValue);
		this.root.setDepth(1);
	}

	public GenericTree createTree() {
		return new GenericTree();
	}

	public ArrayList<Node> insertNode(Integer parentValue, Node newNode) throws TreeException {

		boolean isNodeInTree = isInTree(root, newNode.getRootCircle().getSearchKey());

		if (!isNodeInTree) {
			boolean isParentInTree = isInTree(root, parentValue);
			if (isParentInTree) {
				ArrayList<Node> searchParentNodeList = new ArrayList<>();
				searchParentNodeList.add(root);
				ArrayList<Node> searchNodeList = searchNode(searchParentNodeList, parentValue);
				searchNodeList.get(searchNodeList.size() - 1).addChild(newNode);
				searchNodeList.add(newNode);
				this.updateDepth(this.root);
				ScreenController.static_process.appendText("\n->Inserted node " + newNode.getRootCircle().getSearchKey()+" successfully");
				ScreenController.static_result.appendText("\nInserted node " + newNode.getRootCircle().getSearchKey()+" into node "+ parentValue +" successfully");
				return searchNodeList;
			} else {
				ScreenController.static_process.appendText("\n->Cannot find parent value");
				ScreenController.static_result.appendText("\nInserted node " + newNode.getRootCircle().getSearchKey()+" into node "+ parentValue +" failed");
				throw new TreeException("Cannot find node with value " + parentValue);
			}
		} else {
			ScreenController.static_process.appendText("\n->Node with inputing value " + newNode.getRootCircle().getSearchKey() + " already exists in the tree.");
			ScreenController.static_result.appendText("\nInserted node " + newNode.getRootCircle().getSearchKey()+" into node "+ parentValue +" failed");
			throw new TreeException("Node with value " + newNode.getRootCircle().getSearchKey() + " already exists in the tree.");
		}

    }

	public Node getParentNode(Node node, Integer key) {
		ArrayList<Node> foundNodeList = new ArrayList<>();
		foundNodeList.add(root);
		if (node.getChildren().contains(searchNode(foundNodeList, key).get(foundNodeList.size() - 1))) {
			return node;
		}

		for (Node child : node.getChildren()) {
			Node foundNode = getParentNode(child, key);
			if (foundNode != null) {
				return foundNode;
			}
		}

		return null;
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
				Node parentNode = getParentNode(root, value);
				foundDeleteNodeList.remove(foundNode);
				ScreenController.static_process.appendText("\n->Found node with key value in tree");
				ScreenController.static_process.appendText("\nDeleted node " + value + " successfully");
				ScreenController.static_result.appendText(("\nDeleted node " + value + " successfully"));
				if (foundNode.getChildren().size() == 0) {
					parentNode.getChildren().remove(foundNode);
					this.updateDepth(this.root);
					return foundDeleteNodeList;
				} else {
					Integer indexOfDeteleNode = parentNode.getChildren().indexOf(foundNode);
					for (int i = 0; i < foundNode.getChildren().size(); i++) {
						parentNode.getChildren().add(indexOfDeteleNode + i, foundNode.getChildren().get(i));
						foundDeleteNodeList.add(foundNode.getChildren().get(i));
					}
					parentNode.getChildren().remove(foundNode);
					foundNode = null;
					this.updateDepth(this.root);
					return foundDeleteNodeList;
				}
			}
		} else {
			ScreenController.static_result.appendText("\nDelete failed : cannot find node with value");
			ScreenController.static_process.appendText("\n->Cannot find node with value " + value);
			throw new TreeException("Cannot find node with value " + value);
		}
    }
	                                                 // old        //new
	public ArrayList<Node> updateValueOfNode(Integer currentValue, Integer newValue) throws TreeException {
		boolean isNewValueInTree = isInTree(root, newValue);
		if (!isNewValueInTree) {
			boolean isUpdateInTree = isInTree(root, currentValue);
			ScreenController.static_process.appendText("\nFind node to update");
			if (isUpdateInTree) {
				ArrayList<Node> foundUpdateNodeList = new ArrayList<>();
				foundUpdateNodeList.add(root);
				foundUpdateNodeList = searchNode(foundUpdateNodeList, currentValue);
				foundUpdateNodeList.get(foundUpdateNodeList.size() - 1).getRootCircle().setSearchKey(newValue);
				ScreenController.static_process.appendText("\nFound node " + currentValue + " in tree");
				ScreenController.static_result.appendText("\n-> Update node " + currentValue + " to " + newValue + " successfully");
				ScreenController.static_result.appendText("\nUpdate node " + currentValue + " to " + newValue + " successfully");
				return foundUpdateNodeList;
			} else {
				ScreenController.static_process.appendText("\nCannot find node with key value in tree");
				ScreenController.static_result.appendText("\nUpdate failed : cannot find node with key value in tree");
				throw new TreeException("Cannot find node with value " + currentValue);
			}
		} else {
			ScreenController.static_process.appendText("\nNode with key value is already in tree");
			ScreenController.static_result.appendText("\nUpdate failed : new value " + newValue + " already exists in the tree.");
			throw new TreeException("New value " + newValue + " already exists in the tree.");
		}
    }


	private class Pair {
		private Node node;
		private int state;

		private Pair(Node node, Integer state) {
			this.node = node;
			this.state = state;
		}
	}

	public ArrayList<Node> traversePreOrder() {
		Stack<Pair> stack = new Stack<>();
		stack.push(new Pair(root, -1));

		ArrayList<Node> preOrderList = new ArrayList<>();
		while (stack.size() > 0) {
			Pair top = stack.peek();
			if (top.state == -1) {
				preOrderList.add(top.node);
				top.state++;
			} else if (top.state == top.node.getChildren().size()) {
				stack.pop();
			} else {
				Pair cp = new Pair(top.node.getChildren().get(top.state), -1);
				stack.push(cp);
				top.state++;
			}
		}
		return preOrderList;
	}

	public ArrayList<Node> traversePostOrder() {
		Stack<Pair> stack = new Stack<>();
		stack.push(new Pair(root, -1));

		ArrayList<Node> postOrderList = new ArrayList<>();
		while (stack.size() > 0) {
			Pair top = stack.peek();
			if (top.state == -1) {
				top.state++;
			} else if (top.state == top.node.getChildren().size()) {
				postOrderList.add(top.node);
				stack.pop();
			} else {
				Pair cp = new Pair(top.node.getChildren().get(top.state), -1);
				stack.push(cp);
				top.state++;
			}
		}
		return postOrderList;
	}
	public ArrayList<Node> traverseBFS() {
		ArrayList<Node> bfsOrderList = new ArrayList<>();
		if (root == null) {
			return bfsOrderList;
		}

		Queue<Node> queue = new LinkedList<>();
		queue.add(root);

		while (!queue.isEmpty()) {
			Node currentNode = queue.poll();
			bfsOrderList.add(currentNode);

			for (Node child : currentNode.getChildren()) {
				queue.add(child);
			}
		}

		return bfsOrderList;
	}

	public boolean isInTree(Node node, Integer key) {
		if (node.getRootCircle().getSearchKey() == key) {
			return true;
		}
		for (Node child : node.getChildren()) {
			boolean found = isInTree(child, key);
			if (found) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Node> searchNode(ArrayList<Node> listNodes, Integer key) {
		// listNodes contains 1 Node: root
		if (listNodes.get(listNodes.size() - 1).getRootCircle().getSearchKey() == key) {
			return listNodes;
		}

		for (Node child : listNodes.get(listNodes.size() - 1).getChildren()) {
			listNodes.add(child);
			ArrayList<Node> foundListNodes = searchNode(listNodes, key);
			if (foundListNodes.get(listNodes.size() - 1).getRootCircle().getSearchKey() == key) {
				return foundListNodes;
			}
		}
		return listNodes;
	}

	public void updateDepth(Node root) {
		if (root == this.root) {
			this.root.setDepth(1);
		}
		for (Node child : root.getChildren()) {
			child.setDepth(root.getDepth() + 1);
			updateDepth(child);
		}
	}

	public GenericTree cloneTree() {
		Node newRootNode = root.cloneNode(root);
		GenericTree newTree = new GenericTree(newRootNode);
		return newTree;
	}

}
