
package lights.digital.masterclass.tree.balanced.redblack;

import lights.digital.masterclass.tree.balanced.redblack.color.Color;
import lights.digital.masterclass.tree.exceptions.NodeNotChildOfItsParentException;
import lights.digital.masterclass.tree.twonode.AbstractBinaryTree;


import static lights.digital.masterclass.tree.balanced.redblack.color.Color.RED;


public class RedBlackTree<T extends Comparable<T>> extends AbstractBinaryTree<T> {

    private void replaceParentsChild(BinaryTreeNode<T> parent,
                                     BinaryTreeNode<T> oldChild,
                                     BinaryTreeNode<T> newChild) {

        if (parent == null) {
            root = newChild;
        } else if (parent.leftChild == oldChild) {
            parent.leftChild = newChild;
        } else if (parent.rightChild == oldChild) {
            parent.rightChild = newChild;
        } else {
            logger.error("The current node is not a child of its parent.");

            throw new NodeNotChildOfItsParentException("Node is not a child of its parent");
        }

        if (newChild != null) {
            newChild.parent = parent;
        }
    }

    private void rightRotation(BinaryTreeNode<T> currentNode)
            throws NodeNotChildOfItsParentException {

        BinaryTreeNode<T> parent = currentNode.parent;
        BinaryTreeNode<T> leftSubtree = currentNode.leftChild;

        currentNode.leftChild = leftSubtree.rightChild;
        if (leftSubtree.rightChild != null) {
            leftSubtree.rightChild.parent = currentNode;
        }

        leftSubtree.rightChild = currentNode;
        currentNode.parent = leftSubtree;

        replaceParentsChild(parent, currentNode, leftSubtree);
    }

    private void leftRotation(BinaryTreeNode<T> currentNode) throws NodeNotChildOfItsParentException {
        BinaryTreeNode<T> parent = currentNode.parent;
        BinaryTreeNode<T> rightSubtree = currentNode.rightChild;

        currentNode.rightChild = rightSubtree.leftChild;
        if (rightSubtree.leftChild != null) {
            rightSubtree.leftChild.parent = currentNode;
        }

        rightSubtree.leftChild = currentNode;
        currentNode.parent = rightSubtree;

        replaceParentsChild(parent, currentNode, rightSubtree);
    }

    @Override
    protected BinaryTreeNode<T> safeInsert(BinaryTreeNode<T> currentNode, T key)
            throws NodeNotChildOfItsParentException {

        BinaryTreeNode<T> parent = null;

        // Traverse the tree to the left or right depending on the key
        while (currentNode != null) {
            parent = currentNode;
            if (key.compareTo(currentNode.data) < 0) {
                currentNode = currentNode.leftChild;
            } else if (key.compareTo(currentNode.data) > 0) {
                currentNode = currentNode.rightChild;
            } else {
                /// This is the case the value is already in the tree and in this case
                /// the tree will remain the same.
                return null;
            }
        }

        // Insert new node:

        // Creating the new node:
        BinaryTreeNode<T> newNode = new BinaryTreeNode<>(key);

        // Setting the color of the new node to RED:
        newNode.color = RED;

        // Setting the child-parent connection between newNode and parent:
        if (parent == null) {
            currentNode = newNode;
        } else if (key.compareTo(parent.data) < 0) {
            parent.leftChild = newNode;
        } else {
            parent.rightChild = newNode;
        }
        newNode.parent = parent;

        balancingTreeAfterInsertion(newNode);

        return parent;
    }


    /// After inserting an element the tree may not still be balanced, so
    /// we have to balance it.
    private void balancingTreeAfterInsertion(BinaryTreeNode<T> node) throws NodeNotChildOfItsParentException {
        /// Saving a reference to the parent of the current node:
        BinaryTreeNode<T> parent = node.parent;

        if (parent == null) {
            node.color = Color.BLACK;
            return;
        }

        // Parent is black --> nothing to do
        if (parent.color == Color.BLACK) {
            return;
        }

        // From here on, parent is red
        BinaryTreeNode<T> grandparent = parent.parent;

        // Case 2:
        // Not having a grandparent means that parent is the root. If we enforce black roots
        // (rule 2), grandparent will never be null, and the following if-then block can be
        // removed.
        if (grandparent == null) {
            parent.color = Color.BLACK;
            return;
        }

        // Get the uncle of the current node:
        BinaryTreeNode<T> uncle = getUncle(parent);

        // Case 3: Uncle is red -> recolor parent, grandparent and uncle
        if (uncle != null && uncle.color == RED) {
            parent.color = Color.BLACK;
            grandparent.color = RED;
            uncle.color = Color.BLACK;

            balancingTreeAfterInsertion(grandparent);
        } else if (parent == grandparent.leftChild) {
            // Case 4a: Uncle is black and node is left->right "inner child"
            // of its grandparent
            if (node == parent.rightChild) {
                leftRotation(parent);

                // The parent point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step!!!!!
                parent = node;
            }

            // Case 5a: Uncle is black and node is left->left "outer child" of its grandparent
            rightRotation(grandparent);

            // Recolor original parent and grandparent
            parent.color = Color.BLACK;
            grandparent.color = RED;
        } else {
            // Case 4b: Uncle is black and node is right->left
            // "inner child" of its grandparent
            if (node == parent.leftChild) {
                rightRotation(parent);

                parent = node;
            }

            // Case 5b: Uncle is black and node is right->right
            // "outer child" of its grandparent
            leftRotation(grandparent);

            // Recolor original parent and grandparent
            parent.color = Color.BLACK;
            grandparent.color = RED;
        }
    }

    private BinaryTreeNode<T> getUncle(BinaryTreeNode<T> parent) throws NodeNotChildOfItsParentException {
        BinaryTreeNode<T> grandparent = parent.parent;
        if (grandparent.leftChild == parent) {
            return grandparent.rightChild;
        } else if (grandparent.rightChild == parent) {
            return grandparent.leftChild;
        } else {
            throw new NodeNotChildOfItsParentException("Parent is not a child of its grandparent");
        }
    }

    private void safeErase(BinaryTreeNode<T> currentNode, T key) {

    }

    /**
     * We are going to allow null value for the root to make it easy for clearing the
     * tree.
     */
    public RedBlackTree() {
        this.root = null;
    }

    public RedBlackTree(T initialRootValue) {
        if (initialRootValue == null) {
            throw new IllegalArgumentException("The data value in the root node " +
                    "cannot be null!");
        }

        this.size++;
        this.root = new BinaryTreeNode<>(initialRootValue);
        this.root.color = Color.BLACK;
    }

    @Override
    public void insert(T value) throws NodeNotChildOfItsParentException {
        validationOfArgumentInMethods(value, "value", "insert");

        // Spacial case for empty tree:
        if (this.root == null) {
            this.root = new BinaryTreeNode<T>(value);
        }

        // after the validation the real insertion (safeInsert) is called:
        safeInsert(this.root, value);

        // Notifying the logger that everything is ok.
        logger.info("Added the value: " + value + " to the current tree.");

        // Updating the size:
        this.size++;
    }

    @Override
    public void erase(T key) {

        int sizeBeforeRemoval = this.size;

        validationOfArgumentInMethods(key, "key", "erase");


        safeErase(this.root, key);
        this.size--;

        /// If the program has worked properly we should notify the logger for
        /// the removal of the certain value.
        logger.info("Removed value: " + key + "from current tree.");

        /// If we are trying to remove a non-containing value we should warn the logger
        if (sizeBeforeRemoval == this.size) {
            logger.warn("Trying to remove a non-containing value from the current tree.");
        }
    }
}
