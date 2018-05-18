package oop.ex4.data_structures;
import java.util.Iterator;

public abstract class BinaryTree implements Iterable<Integer> {

    protected Node root;
    protected int size;

    private class BinaryTreeIterator implements Iterator<Integer> {

        private Node curNode;

        private BinaryTreeIterator(Node root) {
            curNode = getMin(root);
        }

        public Integer next() {
            Node node = curNode;
            curNode = successor(curNode);
            return node.value();
        }

        public boolean hasNext() {
            return curNode != null;
        }

    }

    public BinaryTree() {}

    public abstract boolean add(int newValue);

    public abstract boolean delete(int toDelete);

    public int contains(int searchVal) {

        Node curRoot = root;
        int depth = 0;

        while (curRoot != null){
            if (curRoot.value() == searchVal)
                return depth;
            else if (curRoot.value() < searchVal)
                curRoot = curRoot.right();
            else if (curRoot.value() > searchVal)
                curRoot = curRoot.left();

            depth++;
        }
        return -1;
    }

    private Node getMin(Node node){

        Node curNode = node;
        while (curNode != null && curNode.left() != null){
            curNode = curNode.left();
        }
        return curNode;
    }

    protected Node successor(Node node){

        if (node.right() != null){
            return getMin(node.right());

        } else {

            Node curParent = node.parent();
            while (curParent != null && curParent.left() != node) {
                node = curParent;
                curParent = curParent.parent();
            }
            return curParent;
        }
    }

    public Iterator<Integer> iterator(){
        return new BinaryTreeIterator(root);
    }

    public int size() {
        return size;
    }

}
