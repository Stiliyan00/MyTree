package lights.digital.masterclass.tree.exceptions;

public class NodeNotChildOfItsParentException extends RuntimeException {
    public NodeNotChildOfItsParentException(String message) {
        super(message);
    }
}
