package lights.digital.masterclass.tree.balanced.btree;

import lights.digital.masterclass.tree.Traversal;
import lights.digital.masterclass.tree.Tree;

import java.util.Collection;

public class BTree<T extends Comparable<T>> implements Tree<T> {

    public BTree() {

    }

    public BTree(T initialRootValue) {

    }

    @Override
    public boolean contains(T key) {
        return false;
    }

    @Override
    public void insert(T value) {

    }

    @Override
    public void erase(T key) {

    }

    @Override
    public void clear() {

    }

    @Override
    public boolean empty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int height() {
        return 0;
    }

    @Override
    public Collection<T> getAllElements() {
        return null;
    }

    @Override
    public StringBuilder text(Traversal traversal) {


        return null;
    }

    @Override
    public void prettyPrint() {

    }
}
