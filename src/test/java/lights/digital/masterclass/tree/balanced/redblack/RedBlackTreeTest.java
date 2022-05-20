package lights.digital.masterclass.tree.balanced.redblack;

import lights.digital.masterclass.tree.Tree;
import lights.digital.masterclass.tree.exceptions.NodeNotChildOfItsParentException;
import lights.digital.masterclass.tree.factory.TreeFactory;
import lights.digital.masterclass.tree.factory.TreeType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class RedBlackTreeTest {

    private final Tree<Integer> redBlackTree = TreeFactory.getInstance(TreeType.RB_TREE, 10);

    @BeforeEach
    public void setUp() throws NodeNotChildOfItsParentException {
        redBlackTree.insert(9);
        redBlackTree.insert(8);
        redBlackTree.insert(7);
        redBlackTree.insert(13);
        redBlackTree.insert(11);
        redBlackTree.insert(19);
    }

    @AfterEach
    public void tearDown() {
        redBlackTree.clear();
    }

    @Test
    public void defaultConstructorTest() {
        RedBlackTree<Integer> localTemporaryRedBlackTree = new RedBlackTree<>();
    }

    @Test
    public void insertWithNullArgumentShouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> redBlackTree.insert(null));
    }

    @Test
    public void insertSingleValueInEmptyTreeShouldMakeItRootTest() {
        // Clearing all elements from tree, which were added in @BeforeEach scope:
        redBlackTree.clear();

        // Adding the initial value:
        redBlackTree.insert(10);

        assertEquals(1, redBlackTree.size());
        assertTrue(redBlackTree.contains(10));
    }

    @Test
    public void insertWithAlreadyContainingKeyShouldNotChangeTree() {
        int heightOfTreeBeforeInsertion = redBlackTree.height();

        Collection<Integer> treeElementsBeforeInsertion = redBlackTree.getAllElements();

        // Inserting an already containing value in the red-black tree:
        redBlackTree.insert(7);

        // Checking is the elements in the list before the insertion and after it
        // are the same.
        assertEquals(7, redBlackTree.getAllElements().size());
        assertTrue(treeElementsBeforeInsertion.containsAll(redBlackTree.getAllElements()));


        // Checking if the height of the tree before and after the insertion is
        // the same.
        assertEquals(heightOfTreeBeforeInsertion, redBlackTree.height());
    }

    @Test
    public void insertNewNode19ShouldMakeTreeNotPerfectlyBalancedTest() {
        // erasing all values added in the @BeforeEach state:
        redBlackTree.clear();

        // inserting the first 5 values:
        redBlackTree.insert(10);
        redBlackTree.insert(9);
        redBlackTree.insert(15);
        redBlackTree.insert(13);
        redBlackTree.insert(12);




        // Inserting the new value 19 which should make the tree not perfectly balanced:
        redBlackTree.insert(19);
//
//        Red-black trees are balanced, but not necessarily perfectly.
//        To be precise, properties of red-black tree guarantee that
//        the longest path to the leaf (implicit, not shown in your picture)
//        is at most twice as long as the shortest.


        assertEquals(4 ,redBlackTree.height());
        assertEquals(6, redBlackTree.size());
    }

    @Test
    public void insertSingleValueToSingleNodeTreeTest() {
        // Clearing all elements from tree, which were added in @BeforeEach scope:
        redBlackTree.clear();

        redBlackTree.insert(10);
        redBlackTree.insert(11);
    }


    //Parent Node Is Red, Uncle Node Is Black,
    // Inserted Node Is "Outer Grandchild"
    @Test
    public void insertNodeWithRedParentBlackUncleShouldBalanceNodeContainingParentAndGrandparentTest() {
        redBlackTree.clear();

        redBlackTree.insert(17);
        redBlackTree.insert(9);
        redBlackTree.insert(19);
        redBlackTree.insert(71);


        // The current tree is:
//                    17 BLACK
//            ┌───────────┴───────────┐
//         9 BLACK                19 BLACK
//                                    └─────┐
//                                        71 RED
         //                                                                  ----------
        // Inserting the new node with data 171, which will become child of |  71 RED  |
        //                                                                   ----------
        // and should balance the node containing 19, 71, 171

        redBlackTree.insert(171);

        Collection<Integer> expectedElementsToBeInTree = new ArrayList<>();

        expectedElementsToBeInTree.add(17);
        expectedElementsToBeInTree.add(9);
        expectedElementsToBeInTree.add(71);
        expectedElementsToBeInTree.add(19);
        expectedElementsToBeInTree.add(171);


        assertEquals(3 ,redBlackTree.height());
        assertEquals(expectedElementsToBeInTree.size(), redBlackTree.getAllElements().size());
        assertTrue(expectedElementsToBeInTree.containsAll(redBlackTree.getAllElements()));
    }

    // Parent Node Is Red, Uncle Node Is Black,
    // Inserted Node Is "Inner Grandchild"
    @Test
    public void insertNodeWithBlackUncleShouldNotChangeHeightTest() {

        int heightBeforeInsertion = redBlackTree.height();

        redBlackTree.insert(12);

        assertEquals(heightBeforeInsertion, redBlackTree.height());
        assertTrue(redBlackTree.contains(12));
    }


    @Test
    public void erase() {

    }
}