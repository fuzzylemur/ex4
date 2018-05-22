package oop.ex4.data_structures;

/**
 * Sub-class of BinarySearchTree, implementing a balanced search tree (AVL).
 * Balance is maintained so that for each node in the tree, the difference
 * between the heights of its two sub-trees is no larger then the maximum balance
 * factor (2 by default).
 */
public class AvlTree extends BinarySearchTree {

    /* Constant for 'add' mode */
    private static final int ADD = 3;
    /* Constant for 'delete' mode */
    private static final int DELETE = 4;
    /* Constant for maximal balance factor */
    private static final int MAX_BALANCE_FACTOR = 2;

    /** Default constructor */
    public AvlTree() {
        super();
    }

    /**
     * A constructor that builds the tree by adding the elements in the input array
     * one-by-one If the same values appears twice (or more) in the list, it is ignored.
     * @param data int values to add to tree
     */
    public AvlTree(int[] data) {
        super();
        if (data == null)
            return;
        for (int value : data) {
            add(value);
        }
    }

    /**
     * A copy-constructor that builds the tree from existing tree
     * @param tree tree to be copied
     */
    public AvlTree(AvlTree tree) {
        super(tree);
    }

    /**
     * Add a new node with key newValue into the tree.
     * @param newValue new value to add to the tree
     * @return false iff newValue already exist in the tree
     */
    public boolean add(int newValue) {

        if (size == 0) {                                        // adding first node
            treeRoot = new Node(newValue, null);
            size ++;
            return true;
        }

        if (recursiveHelper(ADD, newValue, treeRoot)) {         // adding all the next ones
            size ++;
            return true;
        }

        return false;
    }

    /**
     * Remove a node from the tree, if it exists
     * @param toDelete value to delete
     * @return true iff toDelete found and deleted
     */
    public boolean delete(int toDelete) {

        if (treeRoot == null)                                       // delete from empty tree
            return false;

        if (toDelete == treeRoot.value() && size == 1) {       // delete last node
            treeRoot = null;
            size --;
            return true;
        }

        if (recursiveHelper(DELETE, toDelete, treeRoot)) {
            size --;
            return true;
        }

        return false;
    }

    /*
     * Helper function for deleting a node, handling three different scenarios, node to delete has:
     * 1.no children
     * 2.one child
     * 3.two children.
     * @param node the node to delete
     * @return null if deletion was done in cases 1 or 2
     *          parent of deleted node if in case 3
     */
    private Node deleteHelper(Node node) {

        if (node.child(LEFT) == null && node.child(RIGHT) == null) {         // no children
            node.unlinkUp();
            return null;
        }

        if (node.child(LEFT) == null || node.child(RIGHT) == null) {         // one child
            Node newChild;
            if (node.child(LEFT) != null) {
                newChild = node.child(LEFT);
                node.unlinkDown(LEFT);
            }
            else {
                newChild = node.child(RIGHT);
                node.unlinkDown(RIGHT);
            }
            if (node.parent() == null)
                treeRoot = newChild;
            else
                node.parent().link(node.getSide(), newChild);

            return null;
        }

        else {                                                              // two children
            Node nextNode = successor(node);
            Node nextNodeParent = nextNode.parent();
            node.setValue(nextNode.value());
            deleteHelper(nextNode);

            return nextNodeParent;
        }
    }

    /*
     * A recursive helper function for all operation that need to be done by recursion
     * through the tree (adding and deleting nodes).
     * @param mode class constant for function mode (ADD/DELETE)
     * @param searchValue value to add/delete from tree
     * @param curNode pointer to current node in recursion
     * @return ADD: false if value already in tree, true if added successfully
     *          DELETE: false if value not in tree, true if deleted successfully
     */
    private boolean recursiveHelper(int mode, int searchValue, Node curNode) {

        if (curNode.value() == searchValue) {       // if value already in tree
            if (mode == ADD)
                return false;

            else {                                      // deleting a node
                Node tempNode;
                tempNode = deleteHelper(curNode);

                if (tempNode != null) {                     // delete a node with two children,
                    while (tempNode != curNode) {           // inner loop for updating nodes between
                        tempNode.updateHeight();            // successor node and deleted node
                        avlBalance(tempNode);
                        tempNode = tempNode.parent();
                    }
                }
                curNode.updateHeight();
                avlBalance(curNode);
                return true;
            }
        }

        boolean side = LEFT;                        // else choose the next node based on value
        if (curNode.value() < searchValue)
            side = RIGHT;
        Node nextNode = curNode.child(side);

        if (nextNode == null) {                     // see if next node is null
            if (mode == DELETE)
                return false;
            else {
                curNode.link(side, (new Node(searchValue, curNode)));
                curNode.updateHeight();
                return true;
            }

        } else {                                    // if there is child, continue recursion
            boolean result;
            result = recursiveHelper(mode, searchValue, nextNode);
            curNode.updateHeight();
            avlBalance(curNode);
            return result;
        }
    }

    /*
     * Preform an AVL rotation on the given node, in the chosen direction
     * @param direction boolean class constant LEFT / RIGHT
     * @param root node to rotate around (not to be confused with treeRoot)
     */
    private void rotate(boolean direction, Node root) {

        Node rootParent = root.parent();
        Node pivot;
        Node pivotChild;

        boolean rootSide = false;                   // find side of root node relative to its parent
        if (rootParent != null)
            rootSide = root.getSide();

        boolean reverseDirection = !direction;      // determine reverse direction

        pivot = root.child(reverseDirection);
        pivotChild = pivot.child(direction);

        root.unlinkDown(reverseDirection);          // update pointers (by linking/un-linking nodes)
        pivot.unlinkDown(direction);
        root.link(reverseDirection, pivotChild);
        pivot.link(direction, root);

        if (rootParent != null)                     // connect rotated section back to tree
            rootParent.link(rootSide, pivot);
        else
            this.treeRoot = pivot;                  // in case root was the treeRoot

        root.updateHeight();                        // update heights
        pivot.updateHeight();
    }

    /*
     * Balance a node in AVL tree.
     * Check balance factor and rotate nodes accordingly if needed
     * @param node node to balance (assumes all its child nodes are balanced)
     */
    private void avlBalance(Node node) {

        int balanceFactor = node.balanceFactor();

        if (balanceFactor == MAX_BALANCE_FACTOR) {
            if (node.child(LEFT).balanceFactor() == 1 - MAX_BALANCE_FACTOR) {         // LR
                rotate(LEFT, node.child(LEFT));
            }
            rotate(RIGHT, node);                                                      // LL
        }
        else if (balanceFactor == - MAX_BALANCE_FACTOR) {
            if (node.child(RIGHT).balanceFactor() == MAX_BALANCE_FACTOR - 1) {        // RL
                rotate(RIGHT, node.child(RIGHT));
            }
            rotate(LEFT, node);                                                       // RR
        }
    }

    /**
     * A method that calculates the maximum number of nodes in an AVL tree of height h
     * @param h height of the tree (a non-negative number).
     * @return maximum number of nodes in tree of height h
     */
    public static int findMaxNodes(int h){

        return (int)Math.pow(2, h+1) - 1;
    }

    /**
     * A method that calculates the minimum numbers of nodes in an AVL tree of height h
     * @param h height of the tree (a non-negative number).
     * @return minimum number of nodes in tree of height h
     */
    public static int findMinNodes(int h){

        double phi = (1 + Math.sqrt(5))/2;
        double fibo = (Math.pow(phi, h+3) - Math.pow((-1/phi), h+3)) / Math.sqrt(5);

        return (int)fibo - 1;
    }
}

