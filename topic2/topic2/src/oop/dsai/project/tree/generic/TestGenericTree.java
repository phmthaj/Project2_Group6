
package oop.dsai.project.tree.generic;

import java.util.ArrayList;
import java.util.Stack;

/*								Generic Tree
 * 									4
 * 			/				/				\			\
 * 			8				1				2			10
 * 		/		\		/		\		/	|	\	
 * 		9		16		14		6		7	5	3
 */

public class TestGenericTree {
	public static void main(String args[]) {
		GenericTree tempTree = new GenericTree(new Node(4));
		Stack<GenericTree> stackGenericTrees = new Stack<>();
		GenericTree newTree = new GenericTree();
		Integer rootValue = tempTree.root.getRootCircle().getSearchKey();
		try {
			ArrayList<Node> listNodes = tempTree.insertNode(rootValue, new Node(8));
			listNodes = tempTree.insertNode(rootValue, new Node(1));
			listNodes = tempTree.insertNode(rootValue, new Node(2));

			GenericTree copyTree = tempTree.cloneTree();
			stackGenericTrees.push(copyTree);
			System.out.println("\nPreorder Traversal");
			ArrayList<Node> preOrderList = copyTree.traversePreOrder();
			for (Node i : preOrderList) {
				System.out.print(i.getRootCircle().getSearchKey() + "  ");
			}

			newTree = tempTree.cloneTree();
			System.out.println("\nPreorder Traversal nodeTree");
			preOrderList = newTree.traversePreOrder();
			for (Node j : preOrderList) {
				System.out.print(j.getRootCircle().getSearchKey() + "  ");
			}
			System.out.println();
			
			listNodes = tempTree.insertNode(8, new Node(9));
			listNodes = tempTree.insertNode(1, new Node(14));

			copyTree = tempTree.cloneTree();
			stackGenericTrees.push(copyTree);

			System.out.println("\nPreorder Traversal");
			preOrderList = copyTree.traversePreOrder();
			for (Node i : preOrderList) {
				System.out.print(i.getRootCircle().getSearchKey() + "  ");
			}
			
//			try {
//				copyTree = (GenericTree) tempTree.clone();
//				stackGenericTrees.push(copyTree);
//				System.out.println("\nPreorder Traversal");
//				preOrderList = copyTree.traversePreOrder();
//				for (Node j : preOrderList) {
//					System.out.print(j.getRootCircle().getSearchKey() + "  ");
//				}
//				System.out.println();
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//				System.out.println(e.getStackTrace());
//			}

			listNodes = tempTree.insertNode(1, new Node(6));
			listNodes = tempTree.insertNode(2, new Node(7));

			copyTree = tempTree.cloneTree();
			stackGenericTrees.push(copyTree);
			System.out.println("\nPreorder Traversal");
			preOrderList = copyTree.traversePreOrder();
			for (Node i : preOrderList) {
				System.out.print(i.getRootCircle().getSearchKey() + "  ");
			}
//			try {
//				GenericTree copyTree = (GenericTree) tempTree.clone();
//				stackGenericTrees.push(copyTree);
//				System.out.println("\nPreorder Traversal");
//				ArrayList<Node> preOrderList = copyTree.traversePreOrder();
//				for (Node i : preOrderList) {
//					System.out.print(i.getRootCircle().getSearchKey() + "  ");
//				}
//				System.out.println();
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//				System.out.println(e.getStackTrace());
//			}

			listNodes = tempTree.insertNode(2, new Node(5));

//			try {
//				GenericTree copyTree = (GenericTree) tempTree.clone();
//
//				System.out.println("\nPreorder Traversal");
//				ArrayList<Node> preOrderList = copyTree.traversePreOrder();
//				for (Node i : preOrderList) {
//					System.out.print(i.getRootCircle().getSearchKey() + "  ");
//				}
//
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//				System.out.println(e.getStackTrace());
//			}

			listNodes = tempTree.insertNode(2, new Node(3));

			listNodes = tempTree.insertNode(rootValue, new Node(10));
			listNodes = tempTree.insertNode(8, new Node(16));

//			ArrayList<Node> searchNode = new ArrayList<>();
//			searchNode.add(tempTree.root);
//			searchNode = tempTree.searchNode(searchNode, 6);
//			System.out.println("Search list of 6:");
//			for (Node index : searchNode) {
//				System.out.print(index.getRootCircle().getSearchKey() + "  ");
//			}
			
//			System.out.println("\nPreorder Traversal nodeTree");
//			preOrderList = newTree.traversePreOrder();
//			for (Node j : preOrderList) {
//				System.out.print(j.getRootCircle().getSearchKey() + "  ");
//			}
//			System.out.println();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		
		System.out.println("\nPrint Stack");
		for (GenericTree temp : stackGenericTrees) {
			ArrayList<Node> preOrderList = temp.traversePreOrder();
			for (Node i : preOrderList) {
				System.out.print(i.getRootCircle().getSearchKey() + "  ");
			}
			System.out.println();
		}

		
		
		System.out.println("\nPreorder Traversal");
		ArrayList<Node> preOrderList = tempTree.traversePreOrder();
		for (Node i : preOrderList) {
			System.out.print(i.getRootCircle().getSearchKey() + "  ");
		}

//		System.out.println("\nPostorder Traversal");
//		ArrayList<Node> postOrderList = tempTree.traversePostOrder();
//		for (Node i : postOrderList) {
//			System.out.print(i.getRootCircle().getSearchKey() + "  ");
//		}
//
//		System.out.println("\n");
//
//		try { // Update node 16 -> 164
//			ArrayList<Node> listNodes = tempTree.updateValueOfNode(16, 164);
//			System.out.println("Update list of 16->164");
//			for (Node index : listNodes) {
//				System.out.print(index.getRootCircle().getSearchKey() + "  ");
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			System.out.println(e.getStackTrace());
//		}
//
//		System.out.println("\nPreorder Traversal");
//		preOrderList = tempTree.traversePreOrder();
//		for (Node i : preOrderList) {
//			System.out.print(i.getRootCircle().getSearchKey() + "  ");
//		}
//
//		System.out.println("\nPostorder Traversal");
//		postOrderList = tempTree.traversePostOrder();
//		for (Node i : postOrderList) {
//			System.out.print(i.getRootCircle().getSearchKey() + "  ");
//		}
//
//		System.out.println("\nAfter deleting node 5, deleteList:");
//		try {
//			ArrayList<Node> listNodes = tempTree.deleteNode(5);
//			for (Node index : listNodes) {
//				System.out.print(index.getRootCircle().getSearchKey() + "  ");
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			System.out.println(e.getStackTrace());
//		}
//
//		System.out.println("\nPreorder Traversal");
//		preOrderList = tempTree.traversePreOrder();
//		for (Node i : preOrderList) {
//			System.out.print(i.getRootCircle().getSearchKey() + "  ");
//		}
//
//		System.out.println("\nPostorder Traversal");
//		postOrderList = tempTree.traversePostOrder();
//		for (Node i : postOrderList) {
//			System.out.print(i.getRootCircle().getSearchKey() + "  ");
//		}
//
////		Node parentNode = tempTree.getParentNode(tempTree.root,2);
////		System.out.print(parentNode.rootCircle.getSearchKey());
//
//		System.out.println("\nAfter deleting node 2, deleteList:");
//		try {
//			ArrayList<Node> listNodes = tempTree.insertNode(2, new Node(5));
//			listNodes = tempTree.deleteNode(2);
//			for (Node index : listNodes) {
//				System.out.print(index.getRootCircle().getSearchKey() + "  ");
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			System.out.println(e.getStackTrace());
//		}
//
//		System.out.println("\nPreorder Traversal");
//		preOrderList = tempTree.traversePreOrder();
//		for (Node i : preOrderList) {
//			System.out.print(i.getRootCircle().getSearchKey() + "  ");
//		}
//
//		System.out.println("\nPostorder Traversal");
//		postOrderList = tempTree.traversePostOrder();
//		for (Node i : postOrderList) {
//			System.out.print(i.getRootCircle().getSearchKey() + "  ");
//		}
	}
}
