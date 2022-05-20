/**
 * @author Stiliyan Iliev
 * @version 1.0
 * Package for abstract factory creation pattern.
 */
package lights.digital.masterclass.tree.factory;

import lights.digital.masterclass.tree.Tree;
import lights.digital.masterclass.tree.balanced.btree.BTree;
import lights.digital.masterclass.tree.twonode.binary.BinaryTree;
import lights.digital.masterclass.tree.balanced.redblack.RedBlackTree;


public interface TreeFactory<T extends Comparable<T>> {

    static <T extends Comparable<T>> Tree<T> getInstance(TreeType treeType) {
        return switch (treeType) {
            case B_TREE -> new BTree<>();
//            case TT_TREE -> new TTTree<>();
            case RB_TREE -> new RedBlackTree<>();
            default -> new BinaryTree<>();
        };
    }

    static <T extends Comparable<T>> Tree<T> getInstance(TreeType treeType, T initialRootValue) {
        return switch (treeType) {
            case B_TREE -> new BTree<>(initialRootValue);
//            case TT_TREE -> new TTTree<>(initialRootValue);
            case RB_TREE -> new RedBlackTree<>(initialRootValue);
            default -> new BinaryTree<>(initialRootValue);
        };
    }
}
