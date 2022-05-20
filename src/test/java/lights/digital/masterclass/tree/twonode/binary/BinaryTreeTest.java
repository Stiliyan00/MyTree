package lights.digital.masterclass.tree.twonode.binary;

import lights.digital.masterclass.tree.Tree;
import lights.digital.masterclass.tree.exceptions.NodeNotChildOfItsParentException;
import lights.digital.masterclass.tree.factory.TreeFactory;
import lights.digital.masterclass.tree.factory.TreeType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BinaryTreeTest {

    private final Tree<Integer> binaryTree = TreeFactory.getInstance(TreeType.BINARY_TREE, 10);

    @BeforeEach
    void setUp() throws NodeNotChildOfItsParentException {
        binaryTree.insert(9);
        binaryTree.insert(8);
        binaryTree.insert(7);
        binaryTree.insert(13);
        binaryTree.insert(11);
        binaryTree.insert(19);
    }

    @AfterEach
    void tearDown() {
        binaryTree.clear();
    }


    @Test
    public void insertWithNullValueShouldThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class,
                () -> binaryTree.insert(null),
                "The argument value in method insert in class " +
                        "BinaryTree cannot be null!");
    }

    @Test
    public void insertWithCurrentNodeNullShouldMakeNewTreeWithOnlyOneElementTest()
            throws NodeNotChildOfItsParentException {
        binaryTree.clear();

        //Inserting the value 1 should make binaryTree a one-node tree:
        binaryTree.insert(1);

        assertEquals(1, binaryTree.getAllElements().size());
        assertTrue(binaryTree.contains(1));
    }

    @Test
    public void insertWithAlreadyExistingValueShouldNotChangeTreeTest()
            throws NodeNotChildOfItsParentException {
        Collection<Integer> collectionOfElementsBeforeInsertingValueNine =
                binaryTree.getAllElements();

        //Here we are inserting a value which is already in the BinaryTree
        binaryTree.insert(9);

        Collection<Integer> collectionOfElementsAfterInsertingValueNine =
                binaryTree.getAllElements();


        //Here we are going to check whether the two collections have the same size:
        assertEquals(collectionOfElementsAfterInsertingValueNine.size(),
                collectionOfElementsBeforeInsertingValueNine.size(),
                "The size of the collection of all elements " +
                        "from the BinaryTree before and after the insertion " +
                        "ot the value 9 should be the same, because we already" +
                        " have the value 9 in it!");

        // Here we are going to check whether the 2 collections have the same elements:
        assertTrue(collectionOfElementsAfterInsertingValueNine
                        .containsAll(collectionOfElementsBeforeInsertingValueNine),
                "The collection before and after the insertion of " +
                        "the value 9 should not change the elements of either of " +
                        "the trees!");
    }

    @Test
    public void insertWithNonContainingValueShouldAddTheNewValueToTreeTest() throws NodeNotChildOfItsParentException {
        assertFalse(binaryTree.contains(30),
                "The value 30 should not be in the BinaryTree at " +
                        "this point!");

        binaryTree.insert(30);

        assertTrue(binaryTree.contains(30),
                "The value should have been added to the BinaryTree " +
                        "and should now be contained in it!");
    }

    @Test
    public void eraseWithNullArgumentValueShouldThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class,
                () -> binaryTree.erase(null),
                "The value ot the argument key in method " +
                        "erase in class BinaryTree should not be null!");
    }

    @Test
    public void eraseWithNullTreeShouldWorkTest() {
        BinaryTree<Integer> localBinaryTree = new BinaryTree<>();
        binaryTree.erase(10);
    }

    @Test
    public void eraseWithNonContainingValueShouldDoNothingTest() {
        //Saving all the current element in a collection, before we erase a certain element
        Collection<Integer> collectionOfAllElementsOfTreeBeforeErasingNine =
                binaryTree.getAllElements();

        //Trying to erase the element 9 which is still not in the tree
        binaryTree.erase(9);

        //Should be the exact same collection as collectionOfAllElementsOfTreeBeforeErasingNine
        Collection<Integer> collectionOfAllElementsOfTreeAfterErasingNine =
                binaryTree.getAllElements();

        assertEquals(collectionOfAllElementsOfTreeAfterErasingNine.size(),
                collectionOfAllElementsOfTreeBeforeErasingNine.size() - 1,
                "The size of the collection of all elements " +
                        "from the BinaryTree before and after the erasing " +
                        "ot the value 9 should be the same, because we already" +
                        " have the value 9 in it!");
        assertFalse(collectionOfAllElementsOfTreeAfterErasingNine
                        .containsAll(collectionOfAllElementsOfTreeBeforeErasingNine),
                "The collection before and after the insertion of " +
                        "the value 9 should not change the elements of either of " +
                        "the trees!");
    }

    @Test
    public void eraseWithValueBeingRootShouldRemoveRootAndMergeTheTwoSubtreesTest() {
        // The value 10 is the root of the current tree:
        assertTrue(binaryTree.contains(10));

        // Removing the value 10 from the tree and merging the two subtrees:
        binaryTree.erase(10);

        //Checking if removing the element 10 was successful:
        assertFalse(binaryTree.contains(10));

        // Making a list which will have all element of the tree after removing the element 10:
        List<Integer> listCopyOfCurrentTree = new ArrayList<>();

        listCopyOfCurrentTree.add(9);
        listCopyOfCurrentTree.add(8);
        listCopyOfCurrentTree.add(7);
        listCopyOfCurrentTree.add(13);
        listCopyOfCurrentTree.add(11);
        listCopyOfCurrentTree.add(19);

        // Checking if the current tree is what we are expecting:
        assertEquals(binaryTree.getAllElements().size(), listCopyOfCurrentTree.size());
        assertTrue(binaryTree.getAllElements().containsAll(listCopyOfCurrentTree));
    }

    @Test
    public void eraseWithElementInRightSubtreeShouldWork() {
        binaryTree.erase(13);

        List<Integer> listOfElementsExpectedToBeInTreeAfterErasing = new ArrayList<>();

        listOfElementsExpectedToBeInTreeAfterErasing.add(10);
        listOfElementsExpectedToBeInTreeAfterErasing.add(9);
        listOfElementsExpectedToBeInTreeAfterErasing.add(8);
        listOfElementsExpectedToBeInTreeAfterErasing.add(7);
        listOfElementsExpectedToBeInTreeAfterErasing.add(11);
        listOfElementsExpectedToBeInTreeAfterErasing.add(19);

        assertEquals(listOfElementsExpectedToBeInTreeAfterErasing.size(),
                binaryTree.size());

        assertTrue(binaryTree.getAllElements().containsAll(listOfElementsExpectedToBeInTreeAfterErasing));
    }
}



