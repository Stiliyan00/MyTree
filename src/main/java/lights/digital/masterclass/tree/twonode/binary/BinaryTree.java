/**
 * @author Stiliyan Iliev
 * @version 1.0
 * Package for working with binary trees.
 */
package lights.digital.masterclass.tree.twonode.binary;

import lights.digital.masterclass.tree.twonode.AbstractBinaryTree;


/**
 * A simple implementation of a binary search tree.
 */
public class BinaryTree<T extends Comparable<T>> extends AbstractBinaryTree<T> {

    /**
     * Adding the right subtree of a given tree to the left subtree.
     */
    protected BinaryTreeNode<T> mergeBinaryTrees(BinaryTreeNode<T> leftSubtree,
                                                 BinaryTreeNode<T> rightSubtree) {
        if (leftSubtree == null) {
            return rightSubtree;
        }

        if (rightSubtree == null) {
            return leftSubtree;
        }

        safeInsert(leftSubtree, rightSubtree.data);
        mergeBinaryTrees(leftSubtree, rightSubtree.leftChild);
        mergeBinaryTrees(leftSubtree, rightSubtree.rightChild);

        return leftSubtree;
    }

    /**
     * A helper method to the insert method, which guaranteed takes only
     * valid arguments.
     *
     * @param currentNode A reference to the tree we are checking.
     * @param value       The value we are inserting to {@code currentNode}.
     * @return {@code currentNode} after the insertion of {@code value}.
     */
    protected BinaryTreeNode<T> safeInsert(BinaryTreeNode<T> currentNode, T value) {
        if (currentNode == null) {
            return new BinaryTreeNode<>(value);
        }

        if (currentNode.data.compareTo(value) > 0) {
            currentNode.leftChild = safeInsert(currentNode.leftChild, value);
        }

        if (currentNode.data.compareTo(value) < 0) {
            currentNode.rightChild = safeInsert(currentNode.rightChild, value);
        }

        return currentNode;
    }

    /**
     * A helper method to the erase method, which guaranteed takes only
     * valid arguments.
     *
     * @param currentNode A reference to the tree we are checking.
     * @param key         The value we are removing.
     * @return {@code currentNode} after the removal of {@code key}.
     */
    private BinaryTreeNode<T> safeErase(BinaryTreeNode<T> currentNode, T key) {

        if (currentNode == null) {
            return null;
        }

        if (currentNode.data == key) {
            this.size--;
            return mergeBinaryTrees(currentNode.leftChild, currentNode.rightChild);
        } else if (currentNode.data.compareTo(key) > 0) {
            currentNode.leftChild = safeErase(currentNode.leftChild, key);
        } else {
            currentNode.rightChild = safeErase(currentNode.rightChild, key);
        }

        return currentNode;
    }

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(T initialRootValue) {
        this.size++;
        this.root = new BinaryTreeNode<>(initialRootValue);
    }

    @Override
    public void insert(T value) {

        /// Validating the input value
        validationOfArgumentInMethods(value, "value", "insert");

        /// inserting the valid value to the current tree
        this.root = safeInsert(this.root, value);

        /// If the insertion is valid we should notify the logger with this info
        logger.info("Added the value: " + value + " to the current tree.");
        /// If the value is already in the tree the message will be the same.

        this.size += 1;
    }

    @Override
    public void erase(T key) {

        int sizeBeforeRemoval = this.size;

        validationOfArgumentInMethods(key, "key", "erase");

        root = safeErase(this.root, key);

        /// If the program has worked properly we should notify the logger for
        /// the removal of the certain value.
        logger.info("Removed value: " + key + "from current tree.");

        /// If we are trying to remove a non-containing value we should warn the logger
        if (sizeBeforeRemoval == this.size) {
            logger.warn("Trying to remove a non-containing value from the current tree.");
        }
    }
}
