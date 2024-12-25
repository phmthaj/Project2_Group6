package oop.dsai.project.tree.balanced;

import java.util.ArrayList;

import oop.dsai.project.gui.ScreenController;
import oop.dsai.project.shape.Circle;
import oop.dsai.project.tree.exception.TreeException;
import oop.dsai.project.tree.generic.GenericTree;
import oop.dsai.project.tree.generic.Node;

public class BalancedTree extends GenericTree {

	private int limitDistance;
	private int minLeafDepth;
	private int maxLeafDepth;

	public BalancedTree() {
		super();
		this.limitDistance = 1; // Default value
		this.minLeafDepth = 1; // this is initialize for
		this.maxLeafDepth = 1; // a tree with only root
	}

	public BalancedTree(Node root) {
		super(root);
		this.limitDistance = 1; // Default value
		this.minLeafDepth = 1; // this is initialize for
		this.maxLeafDepth = 1; // a tree with only root
	}

	public BalancedTree(Circle rootValue) {
		super(rootValue);
		this.limitDistance = 1; // Default value
		this.minLeafDepth = 1;
		this.maxLeafDepth = 1;
	}

	public int getLimitDistance() {
		return limitDistance;
	}

	public void setLimitDistance(int limitDistance) {
		this.limitDistance = limitDistance;
	}

	public int getMinLeafDepth() {
		return minLeafDepth;
	}

	public int getMaxLeafDepth() {
		return maxLeafDepth;
	}


	public ArrayList<Node> insertNode(Integer parentValue, Node newNode) throws TreeException  {
				ArrayList<Node> searchNodeList = super.insertNode(parentValue, newNode);
				this.updateMaxMin(this.root);
				if (this.maxLeafDepth - this.minLeafDepth > this.limitDistance) {
					searchNodeList.get(searchNodeList.size()-2).getChildren().remove(newNode);
					this.updateMaxMin(this.root);
					ScreenController.static_result.appendText("\nInsert failed: imbalanced");
					ScreenController.static_process.appendText("\nTree is imbalanced, cancel insert");
					throw new TreeException("Cannot insert because the new node makes the tree become imbalanced.");
				} else {
					return searchNodeList;
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
				foundDeleteNodeList.remove(foundNode);
				Node parentNode = getParentNode(root, value);
				Integer indexOfDeleteNode = parentNode.getChildren().indexOf(foundNode);
				if (foundNode.getNbChildren()==0) {
					parentNode.getChildren().remove(foundNode);
					this.updateMaxMin(this.root);
					if(this.maxLeafDepth-this.minLeafDepth>this.limitDistance) {
						parentNode.getChildren().add(indexOfDeleteNode, foundNode);
						this.updateMaxMin(this.root);
						ScreenController.static_result.appendText("\nDelete failed: imbalanced");
						ScreenController.static_process.appendText("\nTree is imbalanced, cancel delete");
						throw new TreeException("Cannot delete because it make the tree imbalance");
					}
					return foundDeleteNodeList;
				} else {
					parentNode.getChildren().remove(foundNode);
					for (int i = 0; i < foundNode.getChildren().size(); i++) {
						parentNode.getChildren().add(indexOfDeleteNode + i, foundNode.getChildren().get(i));
						foundDeleteNodeList.add(foundNode.getChildren().get(i));
					}
					this.updateDepth(this.root);
					this.updateMaxMin(this.root);
					if(this.maxLeafDepth-this.minLeafDepth>this.limitDistance) {
						for (int i = 0; i < foundNode.getChildren().size(); i++) {
							parentNode.getChildren().remove(foundNode.getChildren().get(i));
							foundDeleteNodeList.remove(foundNode.getChildren().get(i));
						}
						parentNode.getChildren().add(indexOfDeleteNode, foundNode);
						this.updateDepth(this.root);
						this.updateMaxMin(this.root);
						ScreenController.static_result.appendText("\nDelete failed: imbalanced");
						ScreenController.static_process.appendText("\nTree is imbalanced, cancel delete");
						throw new TreeException("Cannot delete because it make the tree imbalance");
					}
					return foundDeleteNodeList;
				}
			}
		} else {
			ScreenController.static_result.appendText("\nDelete failed: cannot find node value " + value +"  to delete");
			ScreenController.static_process.appendText("\n->Cannot find node value " + value + " to delete");
			throw new TreeException("Cannot find node with value " + value);
		}
	}

	public void updateMaxMin(Node root) {
		if (root == this.root) {
			if (root.getNbChildren() == 0) {
				this.minLeafDepth = 1;
				this.maxLeafDepth = 1;
				return;
			} else {
				this.minLeafDepth = 999999;
				this.maxLeafDepth = -1;
			}
		}
		if (root.getNbChildren() == 0 && root != this.root) {
			if (root.getDepth() < this.minLeafDepth)
				this.minLeafDepth = root.getDepth();
			if (root.getDepth() > this.maxLeafDepth)
				this.maxLeafDepth = root.getDepth();
		}
		for (Node child : root.getChildren())
			updateMaxMin(child);
	}

	public BalancedTree cloneTree() {
		Node newRootNode = root.cloneNode(root);
		BalancedTree newTree = new BalancedTree(newRootNode);
		return newTree;
	}

}
