package lights.digital.masterclass.tree.print.pretty;


/**
 * A functional interface allowing all class which implement it to
 * be printed as a graph representation.
 */
@FunctionalInterface
public interface PrettyPrintable {
    void prettyPrint();
}
