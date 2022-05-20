package lights.digital.masterclass.tree.twonode;

import lights.digital.masterclass.tree.Traversal;
import lights.digital.masterclass.tree.factory.TreeFactory;
import lights.digital.masterclass.tree.factory.TreeType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractBinaryTreeTest {

    private final AbstractBinaryTree<Integer> abstractBinaryTree =
            (AbstractBinaryTree<Integer>) TreeFactory.getInstance(TreeType.BINARY_TREE, 10);

    @BeforeEach
    void setUp() {
        abstractBinaryTree.insert(9);
        abstractBinaryTree.insert(8);
        abstractBinaryTree.insert(7);
        abstractBinaryTree.insert(13);
        abstractBinaryTree.insert(11);
        abstractBinaryTree.insert(19);
    }

    @AfterEach
    void tearDown() {
        abstractBinaryTree.clear();
    }

    @Test
    public void containsWithNullKeyShouldThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class,
                () -> abstractBinaryTree.contains(null),
                "The method contains in class abstractBinaryTree " +
                        "should Throw exception with null value for " +
                        "argument key!");
    }

    @Test
    public void containsWithNonContainingKeyShouldReturnFalseTest() {
        assertFalse(abstractBinaryTree.contains(30),
                "The element 30 is not in the current Tree " +
                        "and the method contain should " +
                        "return false!");
    }

    @Test
    public void containsWithKeyBeingRootShouldReturnTrueTest() {
        assertTrue(abstractBinaryTree.contains(10),
                "the element 10 is the root of the current " +
                        "Tree and the method contains should " +
                        "return true");
    }

    @Test
    public void containsWithKeyBeingLeafShouldReturnTrueTest() {
        assertTrue(abstractBinaryTree.contains(7),
                "The element 7 is in the current Tree and " +
                        "the method contains should return true!");
    }

    @Test
    public void containsWithKeyInTreeShouldReturnTrueTest() {
        assertTrue(abstractBinaryTree.contains(13),
                "The element 13 is in the current Tree and " +
                        "the method contains should return true!");
    }


    ///I do not know how to test if there is a memory leak after this clear
    ///method:
    @Test
    public void clearWithNonEmptyTreeShouldMakeCurrentTreeEmptyTest() {
       // abstractBinaryTree.prettyPrint();

        abstractBinaryTree.clear();

        assertTrue(abstractBinaryTree.empty());
    }

    @Test
    public void emptyWithNonEmptyTreeTest() {
        assertFalse(abstractBinaryTree.empty());
    }

    @Test
    public void emptyWithOneNodeTreeTest() {
        /// Removing all elements from the current binary tree:
        abstractBinaryTree.clear();
        // Adding a single node to the current binary tree:
        abstractBinaryTree.insert(1);

        // Asserting that the method empty works:
        // (Here we take for granted that the methods clear and insert work):
        assertFalse(abstractBinaryTree.empty());
    }

    @Test
    public void emptyWithEmptyTreeTest() {
        // Clearing all elements from current binary tree:
        abstractBinaryTree.clear();

        assertTrue(abstractBinaryTree.empty());
    }


    @Test
    public void sizeWithEmptyTreeShouldReturnZeroTest() {
        // Clearing all element from current binary tree:
        abstractBinaryTree.clear();

        assertEquals(0, abstractBinaryTree.size());
    }

    @Test
    public void sizeWithOneNodeTreeShouldReturnOneTest() {
        // Clearing all element from current binary tree:
        abstractBinaryTree.clear();

        // Adding a single node to the current tree:
        abstractBinaryTree.insert(10);

        assertEquals(1, abstractBinaryTree.size());
    }

    @Test
    public void sizeWiTthSevenNodeTreeShouldReturnSevenTest() {
        assertEquals(7, abstractBinaryTree.size());
    }

    @Test
    public void heightWithEmptyTreeShouldReturnZeroTest() {
        // Clearing the current binary tree:
        abstractBinaryTree.clear();

        assertEquals(0, abstractBinaryTree.height());
    }

    @Test
    public void heightWithOneNodeTreeShouldReturnOneTest() {
        abstractBinaryTree.clear();

        abstractBinaryTree.insert(1);

        assertEquals(1, abstractBinaryTree.height());
    }

    @Test
    public void heightWithCurrentSevenNodeTreeShouldReturnFour() {
        assertEquals(4, abstractBinaryTree.height());
    }

    @Test
    public void textWithNullTraversalValueShouldThrowExceptionTest() {
        assertThrows(IllegalArgumentException.class,
                () -> abstractBinaryTree.text(null),
                "The method text in class binary tree should throw an " +
                        "Illegal argument exception if the traversal argument " +
                        "is null!");
    }

    @Test
    public void textWithEmptyTreeShouldReturnEmptyStringBuilderTest() {
        // An empty string buffer which is the expected result of the method
        // text when the current tree is empty:
        StringBuilder emptyStringBuilderBuffer = new StringBuilder();

        // Making the current tree empty:
        abstractBinaryTree.clear();

        assertEquals(emptyStringBuilderBuffer.toString(),
                abstractBinaryTree.text(Traversal.INORDER).toString());
    }

    @Test
    public void textWithOneNodeTreeShouldBeSameInAllTraversalsTest() {
        StringBuilder textRepresentationOfOneNodeTree = new StringBuilder();

        // Making the current binary tree empty:
        abstractBinaryTree.clear();

        // Adding the new root to the tree:
        abstractBinaryTree.insert(1);

        // Adding the excepted node in the string representation of the current tree:
        textRepresentationOfOneNodeTree.append(1);
        textRepresentationOfOneNodeTree.append(" ");


        //Asserting the equality between the text representation ot the current
        // tree and the result of method text() with argument: Inorder, Postorder, Preorder:
        assertEquals(textRepresentationOfOneNodeTree.toString(), abstractBinaryTree.text(Traversal.INORDER).toString());
        assertEquals(textRepresentationOfOneNodeTree.toString(), abstractBinaryTree.text(Traversal.PREORDER).toString());
        assertEquals(textRepresentationOfOneNodeTree.toString(), abstractBinaryTree.text(Traversal.POSTORDER).toString());
    }

    @Test
    public void textWithSevenNodeTreeInorderTraversalTest() {
        StringBuilder textRepresentationOfSevenNodeTree = new StringBuilder();

        textRepresentationOfSevenNodeTree.append("7 8 9 10 11 13 19 ");

        assertEquals(textRepresentationOfSevenNodeTree.toString(),
                abstractBinaryTree.text(Traversal.INORDER).toString());
    }

    @Test
    public void textWithSevenNodeTreePreorderTraversalTest() {
        StringBuilder textRepresentationOfSevenNodeTree = new StringBuilder();

        textRepresentationOfSevenNodeTree.append("10 7 8 9 11 13 19 ");

        assertEquals(textRepresentationOfSevenNodeTree.toString(),
                abstractBinaryTree.text(Traversal.PREORDER).toString());
    }

    @Test
    public void textWithSevenNodeTreePostorderTraversalTest() {
        StringBuilder textRepresentationOfSevenNodeTree = new StringBuilder();

        textRepresentationOfSevenNodeTree.append("7 8 9 11 13 19 10 ");

        assertEquals(textRepresentationOfSevenNodeTree.toString(),
                abstractBinaryTree.text(Traversal.POSTORDER).toString());
    }

    @Test
    public void getAllElementsWithEmptyTreeShouldReturnEmptyCollectionTest() {
        abstractBinaryTree.clear();

        assertEquals(0, abstractBinaryTree.getAllElements().size());
    }

    @Test
    public void getAllElementsWithOneNodeArrayShouldReturnCollectionWithSizeOneTest() {
        abstractBinaryTree.clear();

        abstractBinaryTree.insert(1);

        Collection<Integer> collectionOfCurrentTreeElements = new ArrayList<>();

        collectionOfCurrentTreeElements.add(1);

        // Checking if the excepted collecting is the same as the actual:
        assertEquals(collectionOfCurrentTreeElements.size(), abstractBinaryTree.getAllElements().size());
        assertTrue(collectionOfCurrentTreeElements.containsAll(abstractBinaryTree.getAllElements()));
    }

    @Test
    public void getAllElementsWithSevenNodeTreeShouldReturnCollectionWithSizeSevenTest() {
        // Creating the expected collection:
        Collection<Integer> collectionOfCurrentTreeElements = new ArrayList<>();

        // Adding the excepted elements to this collection:
        collectionOfCurrentTreeElements.add(10);
        collectionOfCurrentTreeElements.add(9);
        collectionOfCurrentTreeElements.add(8);
        collectionOfCurrentTreeElements.add(7);
        collectionOfCurrentTreeElements.add(13);
        collectionOfCurrentTreeElements.add(11);
        collectionOfCurrentTreeElements.add(19);


        // Checking if the excepted collecting is the same as the actual:
        assertEquals(collectionOfCurrentTreeElements.size(), abstractBinaryTree.getAllElements().size());
        assertTrue(collectionOfCurrentTreeElements.containsAll(abstractBinaryTree.getAllElements()));
    }

}