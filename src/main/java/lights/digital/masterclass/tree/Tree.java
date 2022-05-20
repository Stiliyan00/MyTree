/**
 * @author Stiliyan Iliev
 * @version 1.0
 * Package for working with tree based data structures.
 */
package lights.digital.masterclass.tree;


import lights.digital.masterclass.tree.exceptions.NodeNotChildOfItsParentException;
import lights.digital.masterclass.tree.print.pretty.PrettyPrintable;

import java.util.Collection;

public interface Tree<T extends Comparable<T>> extends PrettyPrintable {

    /**
     *
     * Checks if {@code key} is in the current tree.
     *
     * @param key The value which will be checked whether is
     *            in the current tree or not.
     * @throws IllegalArgumentException If {@code key} is null.
     * @return True if{@code key} is in the current tree
     *              else it returns false.
     */
    boolean contains(T key);

    /**
     *
     *  Adding a new value into the current tree.
     *  If the element is in the current tree, it will not be added a second time.
     *
     * @param value The value which is going to be added the current tree.
     * @throws IllegalArgumentException If {@code value} is null.
     */
    void insert(T value);

    /**
     *
     *  Removing a certain value from the tree.
     *
     * @param key The certain value which is to be removed.
     * @throws IllegalArgumentException If {@code value} is null.
     * @throws NodeNotChildOfItsParentException if the current node
     *                                          becomes not a
     *                                          child of its parent.
     */
    void erase(T key);

    /**
     * Removing all element from the current tree and making it null.
     */
    void clear();

    /**
     *
     * @return True if the current tree has no elements (size == 0)
     *              else false.
     */
    boolean empty();

    /**
     * @return The number of elements (nodes) in the current tree.
     */
    int size();

    /**
     * @return The height of the current tree.
     */
    int height();

    /**
     * @return A mutable collection containing all element (nodes) of the tree.
     */
    Collection<T> getAllElements();


    /**
     * @param traversal An enum showing the needed traversal order.
     * @return  A text representation of the tree as a
     *          sequence of the vertexes in
     *          if the wanted traversal order.
     */
    StringBuilder text(Traversal traversal);
}