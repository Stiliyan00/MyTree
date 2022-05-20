package lights.digital.masterclass.tree.print;

/** Unified interface for nodes that can be printed */
public interface PrintableNode {

    /** Get left child */
    PrintableNode getLeft();


    /** Get right child */
    PrintableNode getRight();


    /** Get text to be printed */
    String getText();
}
