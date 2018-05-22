package oop.ex4.data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Abstract class representing a binary tree data structure of int values,
 * with no duplicate values, and organized as a binary search tree.
 * Class implements 'Iterator' through a nested class for a binary tree iterator,
 * and general data members and methods for such a data structure.
 */
abstract class BinarySearchTree implements Iterable<Integer> {

    /* Constant for left side, used as a parameter in some methods */
    static final boolean LEFT = Node.LEFT;
    /* Constant for right side, used as a parameter in some methods */
    static final boolean RIGHT = Node.RIGHT;
    /* Root node of this tree */
    Node treeRoot;
    /* size of tree (number of nodes) */
    int size;

    /*
     * Default constructor
     */
    BinarySearchTree(){

        treeRoot = null;
        size = 0;
    }

    /*
     * Copy constructor for a binary tree
     * creates a new BinarySearchTree object with the same values as the original tree
     * not necessarily in the same structure
     * @param toCopy BinarySearchTree to copy
     */
    BinarySearchTree(BinarySearchTree toCopy) {

        if (toCopy != null) {
            for (int value : toCopy)
                add(value);
        }
    }

    /*
     * Abstract method to add a new value to the tree
     * @param newValue int value to add
     * @return true if added successfully, false if value already exists in tree
     */
    abstract boolean add(int newValue);

    /*
     * Abstract method for deleting a value from the tree
     * @param toDelete int value to delete
     * @return true if deleted successfully, false if value does not exist in tree
     */
    abstract boolean delete(int toDelete);

    /*
     * Get the size of the tree (number of nodes)
     * @return int size of the tree
     */
    int size() {
        return size;
    }

    /**
     * Checks whether an int value is in the tree
     * @param searchVal int value to look for
     * @return int depth of node that holds searchVal (root is depth 0), -1 if value not in tree
     */
    public int contains(int searchVal) {

        Node curNode = treeRoot;
        int depth = 0;

        while (curNode != null){

            if (curNode.value() == searchVal)
                return depth;

            else if (curNode.value() < searchVal)
                curNode = curNode.child(RIGHT);

            else if (curNode.value() > searchVal)
                curNode = curNode.child(LEFT);

            depth ++;
        }
        return -1;
    }

    /*
     * Get the pointer to the minimal node (the leftmost) in the sub-tree of a given root node.
     * @param root root of sub-tree for which to get the minimal node
     * @return pointer to minimal node
     */
    private Node getMin(Node root){

        Node curNode = root;
        while (curNode != null && curNode.child(LEFT) != null){
            curNode = curNode.child(LEFT);
        }
        return curNode;
    }

    /*
     * Find the successor node (node with next highest value) to a given node
     * @param node node to find successor to
     * @return node with next highest value
     */
    Node successor(Node node){

        if (node.child(RIGHT) != null){
            return getMin(node.child(RIGHT));

        } else {

            Node curParent = node.parent();
            while (curParent != null && curParent.child(LEFT) != node) {
                node = curParent;
                curParent = curParent.parent();
            }
            return curParent;
        }
    }

    /*
     * A nested class for implementing a binary tree Iterator,
     * iterating over the tree node int values in ascending order
     */
    private class BinarySearchTreeIterator implements Iterator<Integer> {

        /* pointer to current node */
        private Node curNode;

        /*
         * Constructor for a new binary tree iterator.
         * sets current node to the minimal value.
         */
        private BinarySearchTreeIterator() {
            curNode = getMin(treeRoot);
        }

        /**
         * Gets the next value in the tree
         * @return next int value (ascending order)
         */
        public Integer next() throws NoSuchElementException{
            if (curNode == null)
                throw new NoSuchElementException();

            Node node = curNode;
            curNode = successor(curNode);
            return node.value();
        }

        /**
         * Checks if there is a next value
         * @return true if there is next, false otherwise
         */
        public boolean hasNext() {
            return curNode != null;
        }
    }

    /**
     * Returns an iterator for the Avl Tree.
     * The returned iterator iterates over the tree nodes in an ascending order,
     * and does NOT implement the remove() method.
     * @return an iterator for the Avl Tree
     */
    public Iterator<Integer> iterator(){
        return new BinarySearchTreeIterator();
    }
}
