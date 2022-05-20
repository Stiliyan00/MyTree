/**
 * @author Stiliyan Iliev
 * @version 1.0
 * Package for working with tree based data structures with only 2 nodes.
 */
package lights.digital.masterclass.tree.twonode;

import lights.digital.masterclass.tree.Traversal;
import lights.digital.masterclass.tree.Tree;
import lights.digital.masterclass.tree.balanced.redblack.color.Color;
import lights.digital.masterclass.tree.exceptions.NodeNotChildOfItsParentException;
import lights.digital.masterclass.tree.print.PrintableNode;
import lights.digital.masterclass.tree.print.pretty.PrettyPrintable;
import lights.digital.masterclass.tree.twonode.binary.BinaryTree;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * A summary recursive implementation of all methods of the
 * Tree class, which are the same for all 2 node trees.
 *
 * @param <T> The data type in every node, which should be at least a class
 *            implementing the interface Comparable.
 */
public abstract class AbstractBinaryTree<T extends Comparable<T>>
        implements Tree<T>, PrettyPrintable {

    /**
     * A reference to the root of the current tree.
     */
    protected BinaryTreeNode<T> root;

    /**
     * The number of elements in the current tree.
     */
    protected int size = 0;

    /**
     * A log4j logger for logging information about thrown exceptions,
     * added new elements to the tree and erased ones.
     */
    protected Logger logger = Logger.getLogger(BinaryTree.class);

    /**
     * Simple method which checks whether a given argument
     * value is valid (is not null).
     *
     * @param argument     The argument which should be validated.
     * @param argumentName The name of the argument.
     * @param methodName   The method which is using this argument.
     */
    protected void validationOfArgumentInMethods(final T argument,
                                                 final String argumentName,
                                                 final String methodName) {
        if (argument == null) {
            logger.error("The value of " + argumentName
                    + " in method " + methodName
                    + "is null!");
            throw new IllegalArgumentException("The value of argument "
                    + argumentName + " in method "
                    + methodName + " cannot be null!");
        }
    }

    /**
     * Simple implementation of every node in the Binary based tree.
     *
     * @param <T> The data type in every node, which should be at least a class
     *            implementing the interface Comparable.
     */
    protected static class BinaryTreeNode<T extends Comparable<T>>
            implements PrintableNode {

        /**
         * The data in the current node.
         */
        public T data;
        /**
         * A reference to the left subtree.
         */
        public BinaryTreeNode<T> leftChild;
        /**
         * A reference to the right subtree.
         */
        public BinaryTreeNode<T> rightChild;

        /**
         * A reference to the parent of the current node.
         */
        public BinaryTreeNode<T> parent;

        /**
         * The color of the current tree which will be used in the
         * implementation of the RedBlackTree.
         */
        public Color color;

        public BinaryTreeNode(final T data) {
            this(data, null, null);
        }

        public BinaryTreeNode(final T data, final BinaryTreeNode<T> leftChild,
                              final BinaryTreeNode<T> rightChild) {
            this.data = data;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.parent = null;
            this.color = Color.BLACK;
        }

        /**
         * @return True if the current node has a left and right subtree
         * being null.
         */
        public boolean isLeaf() {
            return leftChild == null && rightChild == null;
        }

        /**
         * @return True if the current node is the root of the tree.
         */
        public boolean isRoot() {
            return parent == null;
        }

        // Here is the implementation of the PrettyPrintable methods:
        @Override
        public PrintableNode getLeft() {
            return this.leftChild;
        }

        @Override
        public PrintableNode getRight() {
            return this.rightChild;
        }

        @Override
        public String getText() {
            return data.toString() + " " + color;
        }
    }

    @Override
    public void clear() {
        this.size = 0;
        this.root = null;

        logger.warn("Cleared the whole tree.");
    }

    @Override
    public boolean empty() {
        return this.root == null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void prettyPrint() {

        List<List<String>> lines = new ArrayList<>();

        List<PrintableNode> level = new ArrayList<>();
        List<PrintableNode> next = new ArrayList<>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<>();

            nn = 0;

            for (PrintableNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.getText();
                    line.add(aa);
                    if (aa.length() > widest) {
                        widest = aa.length();
                    }

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null) {
                        nn++;
                    }
                    if (n.getRight() != null) {
                        nn++;
                    }
                }
            }

            if (widest % 2 == 1) {
                widest++;
            }

            lines.add(line);

            List<PrintableNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }

    @Override
    public StringBuilder text(Traversal traversal) {
        if (traversal == null) {
            logger.error("The value of argument traversal in method text is null.");
            throw new IllegalArgumentException("The value of argument traversal " +
                    "in method text in class BinaryTree should not be null!");
        }

        StringBuilder result = new StringBuilder();

        switch (traversal) {
            case INORDER -> printInorderTraversal(this.root, result);
            case PREORDER -> printPreorderTraversal(this.root, result);
            case POSTORDER -> printPostorderTraversal(this.root, result);
        }

        return result;
    }

    /**
     * Makes a string representation of {@code currentNode} after
     * an inorder traversal and stores it in
     * result.
     *
     * @param currentNode A reference to the tree we are traversing.
     * @param result      The string representation ot {@code currentNode}
     */
    private void printInorderTraversal(BinaryTreeNode<T> currentNode,
                                       StringBuilder result) {
        if (currentNode == null) {
            return;
        }

        printInorderTraversal(currentNode.leftChild, result);
        result.append(currentNode.data);
        result.append(" ");
        printInorderTraversal(currentNode.rightChild, result);
    }

    /**
     * Makes a string representation of {@code currentNode} after
     * a postorder traversal and stores it in
     * result.
     *
     * @param currentNode A reference to the tree we are traversing.
     * @param result      The string representation ot {@code currentNode}
     */
    private void printPostorderTraversal(BinaryTreeNode<T> currentNode, StringBuilder result) {
        if (currentNode == null) {
            return;
        }

        printInorderTraversal(currentNode.leftChild, result);
        printInorderTraversal(currentNode.rightChild, result);
        result.append(currentNode.data);
        result.append(" ");
    }

    /**
     * Makes a string representation of {@code currentNode} after
     * a preorder traversal and stores it in
     * result.
     *
     * @param currentNode A reference to the tree we are traversing.
     * @param result      The string representation ot {@code currentNode}
     */

    private void printPreorderTraversal(BinaryTreeNode<T> currentNode, StringBuilder result) {
        if (currentNode == null) {
            return;
        }

        result.append(currentNode.data);
        result.append(" ");
        printInorderTraversal(currentNode.leftChild, result);
        printInorderTraversal(currentNode.rightChild, result);
    }


    /**
     * A helper method to the getAllElements method.
     *
     * @param currentBinaryTreeNode         A reference to the tree we are checking.
     * @param collectionOfAllElementsInTree The collection in which should be stored
     *                                      the result.
     */
    private void safeGetAllElements(BinaryTreeNode<T> currentBinaryTreeNode,
                                    Collection<T> collectionOfAllElementsInTree) {
        if (currentBinaryTreeNode == null) {
            return;
        }

        collectionOfAllElementsInTree.add(currentBinaryTreeNode.data);
        safeGetAllElements(currentBinaryTreeNode.leftChild, collectionOfAllElementsInTree);
        safeGetAllElements(currentBinaryTreeNode.rightChild, collectionOfAllElementsInTree);
    }

    /**
     * A helper method to the height without argument.
     */
    private int height(BinaryTreeNode<T> currentNode) {
        if (currentNode == null)
            return 0;
        else {
            int leftHeight = height(currentNode.leftChild);
            int rightHeight = height(currentNode.rightChild);

            if (leftHeight > rightHeight)
                return (leftHeight + 1);
            else
                return (rightHeight + 1);
        }
    }

    /**
     * A helper method to the contains method, which guaranteed takes only
     * valid arguments.
     *
     * @param currentRoot A reference to the tree we are checking.
     * @param key         The value we are searching.
     * @return Whether {@code key} is in {@code currentRoot} or not.
     */
    private boolean safeContainsKey(BinaryTreeNode<T> currentRoot, T key) {
        if (currentRoot == null) {
            return false;
        }

        if (key == currentRoot.data) {
            return true;
        } else if (currentRoot.data.compareTo(key) > 0) {
            return safeContainsKey(currentRoot.leftChild, key);
        } else {
            return safeContainsKey(currentRoot.rightChild, key);
        }
    }


    @Override
    public int height() {
        return height(this.root);
    }

    @Override
    public Collection<T> getAllElements() {
        Collection<T> collectionOfAllElementsInTree = new ArrayList<>();

        safeGetAllElements(root, collectionOfAllElementsInTree);

        return collectionOfAllElementsInTree;
    }


    @Override
    public boolean contains(T key) {
        validationOfArgumentInMethods(key, "key", "contains");

        return safeContainsKey(this.root, key);
    }

    /**
     * Insertion method which takes only guaranteed valid arguments.
     *
     * @param currentNode The node to which should be added {@code value}.
     * @param value       The value which is to be added.
     * @return The {@code currentNode} after adding {@code value} to it.
     * @throws NodeNotChildOfItsParentException If the current node
     *                                          becomes not a
     *                                          child of its parent.
     */
    protected abstract BinaryTreeNode<T> safeInsert(BinaryTreeNode<T> currentNode,
                                                    final T value);
}
