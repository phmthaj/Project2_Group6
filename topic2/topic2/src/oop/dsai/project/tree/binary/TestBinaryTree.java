package oop.dsai.project.tree.binary;

import oop.dsai.project.tree.exception.TreeException;
import oop.dsai.project.tree.generic.Node;

import java.util.ArrayList;

/*								Generic Tree
 * 									4
 * 							/				\			
 * 							2				1			
 * 						/		\		/		\	
 * 						5		6		3		7
 */

public class TestBinaryTree {
    public static void main(String[] args) throws TreeException {
        BinaryTree tempTree = new BinaryTree(new Node(4));
        Integer rootValue = tempTree.root.getRootCircle().getSearchKey();
        try {
            ArrayList<Node> listNodes = tempTree.insertNode(rootValue, new Node(2));
            listNodes = tempTree.insertNode(rootValue, new Node(1));
            listNodes = tempTree.insertNode(1, new Node(3));
            listNodes = tempTree.insertNode(1, new Node(7));
            listNodes = tempTree.insertNode(2, new Node(5));
            listNodes = tempTree.insertNode(2, new Node(6));

            ArrayList<Node> searchNode = new ArrayList<>();
            searchNode.add(tempTree.root);
            searchNode = tempTree.searchNode(searchNode, 5);
            System.out.println("Search list of 5:");
            for (Node index : searchNode) {
                System.out.print(index.getRootCircle().getSearchKey() + "  ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        System.out.println("\nBFS Traversal");
        ArrayList<Node> bfsList = tempTree.traverseBFS();
        for (Node i :bfsList) {
            System.out.print(i.getRootCircle().getSearchKey() + "  ");
        }

        System.out.println("\nPreorder Traversal");
        ArrayList<Node> preOrderList = tempTree.traversePreOrder();
        for (Node i : preOrderList) {
            System.out.print(i.getRootCircle().getSearchKey() + "  ");
        }

        System.out.println("\nPostorder Traversal");
        ArrayList<Node> postOrderList = tempTree.traversePostOrder();
        for (Node i : postOrderList) {
            System.out.print(i.getRootCircle().getSearchKey() + "  ");
        }

        System.out.println();

        //Insert, Search, Traverse => OK

        //Test update node extends from Generic => OK
//        try {
//            ArrayList<Node> listNodes = tempTree.updateValueOfNode(5, 15);
//            System.out.println("Update node 5 -> 15");
//            for (Node index : listNodes) {
//                System.out.print(index.rootCircle.getSearchKey() + "  ");
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println(e.getStackTrace());
//        }
//
//        //After updating
//        System.out.println("\nPreorder Traversal");
//        preOrderList = tempTree.traversePreOrder();
//        for (Node i : preOrderList) {
//            System.out.print(i.rootCircle.getSearchKey() + "  ");
//        }
//
//        System.out.println("\nPostorder Traversal");
//        postOrderList = tempTree.traversePostOrder();
//        for (Node i : postOrderList) {
//            System.out.print(i.rootCircle.getSearchKey() + "  ");
//        }

        System.out.println();

        //Test delete
        System.out.println("Delete node 2: ");
        try {
            ArrayList<Node> listNodes = tempTree.deleteNode(2);
            listNodes.forEach(t -> System.out.print(t.getRootCircle().getSearchKey() + " "));
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getStackTrace());
        }
        
        System.out.println("\nPreorder Traversal");
        preOrderList = tempTree.traversePreOrder();
        for (Node i : preOrderList) {
            System.out.print(i.getRootCircle().getSearchKey() + "  ");
        }

        System.out.println("\nPostorder Traversal");
        postOrderList = tempTree.traversePostOrder();
        for (Node i : postOrderList) {
            System.out.print(i.getRootCircle().getSearchKey() + "  ");
        }
    }

}
