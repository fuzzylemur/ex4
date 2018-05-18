package oop.ex4.data_structures;

public class AvlTree extends BinaryTree {

    private static final int LEFT = Node.LEFT;
    private static final int RIGHT = Node.RIGHT;
    private static final int ADD = 3;
    private static final int DELETE = 4;
    private static final int MAX_BALANCE_FACTOR = 2;

    public AvlTree() {
        super();
    }

    public AvlTree(int[] data) {

        super();
        if (data == null)
            return;
        for (int value : data) {
            add(value);
        }
    }

    public AvlTree(AvlTree tree) {

        super();
        for (Integer value : tree) {
            add(value);
        }
    }

    public boolean add(int newValue) {

        if (size == 0) {                                        // adding first node
            root = new Node(newValue, null);
            size ++;
            return true;
        }

        if (recursiveHelper(ADD, newValue, root)) {
            size ++;
            return true;
        }

        return false;
    }

    public boolean delete(int toDelete) {

        if (root == null)                                       // delete from empty tree
            return false;

        else if (toDelete == root.value() && size == 1) {       // delete last node
            root = null;
            size --;
            return true;
        }

        else if (recursiveHelper(DELETE, toDelete, root)) {
            size --;
            return true;
        }

        return false;
    }

    private Node deleteHelper(Node node) {

        if (node.left() == null && node.right() == null) {              // no children

            node.unlinkUp();
            return null;
        }

        else if (node.left() == null || node.right() == null) {         // one child

            Node child;
            if (node.left() != null) {
                child = node.left();
                node.unlinkDown(LEFT);
            }
            else {
                child = node.right();
                node.unlinkDown(RIGHT);
            }

            if (node.parent() == null)
                root = child;

            else if (node.parent().right() == node)
                node.parent().link(RIGHT, child);
            else
                node.parent().link(LEFT, child);

            return null;
        }

        else {                                                        // two children
            Node nextNode = successor(node);
            Node nextNodeParent = nextNode.parent();
            node.value(nextNode.value());
            deleteHelper(nextNode);

            return nextNodeParent;
        }
    }

    // ADD returns null if value already in tree, and the added node if added successfully
    // CONTAINS returns null if value not in tree, Node with value if found
    // DELETE returns null if value not in tree, some node ? if deleted successfully

    private boolean recursiveHelper(int mode, int searchValue, Node curNode) {

        if (curNode.value() == searchValue) {       // if value already in tree
            if (mode == ADD)
                return false;

            else {                                      // DELETE
                Node tempNode;
                tempNode = deleteHelper(curNode);

                if (tempNode != null) {                 // deletion of node with two kids
                    while (tempNode != curNode) {
                        tempNode.updateHeight();
                        tempNode = tempNode.parent();
                    }
                }
                curNode.updateHeight();
                avlBalance(curNode);
                return true;
            }
        }

        Node nextNode = curNode.left();            // choose sides
        int side = LEFT;
        if (curNode.value() < searchValue) {
            nextNode = curNode.right();
            side = RIGHT;
        }

        if (nextNode == null) {                     // see if child is null
            if (mode == DELETE)
                return false;
            else {
                curNode.link(side, (new Node(searchValue, curNode)));
                curNode.updateHeight();
                return true;
            }

        } else {                                       // if there is child, continue with it
            boolean result;
            result = recursiveHelper(mode, searchValue, nextNode);
            curNode.updateHeight();
            avlBalance(curNode);
            return result;
        }
    }

    private void rotate(int direction, Node root) {

        Node rootParent = root.parent();
        Node pivot;
        Node pivotChild;
        int rootSide = 0;

        if (rootParent != null) {               // find side of root node
            rootSide = LEFT;
            if (rootParent.right() == root)
                rootSide = RIGHT;
        }

        int reverseDirection = LEFT;            // determine directions
        if (direction == LEFT)
            reverseDirection = RIGHT;

        pivot = root.child(reverseDirection);
        pivotChild = pivot.child(direction);
        root.unlinkDown(reverseDirection);
        pivot.unlinkDown(direction);
        root.link(reverseDirection, pivotChild);
        pivot.link(direction, root);

        if (rootSide == 0)                      // connect root back to tree (if possible)
            this.root = pivot;
        else
            rootParent.link(rootSide, pivot);

        root.updateHeight();
        pivot.updateHeight();
    }

    private void avlBalance(Node node) {

        int balanceFactor = node.balanceFactor();

        if (balanceFactor == MAX_BALANCE_FACTOR) {
            if (node.left().balanceFactor() == 1 - MAX_BALANCE_FACTOR) {         // LR
                rotate(LEFT, node.left());
            }
            rotate(RIGHT, node);                             // LL
        }
        else if (balanceFactor == - MAX_BALANCE_FACTOR) {
            if (node.right().balanceFactor() == MAX_BALANCE_FACTOR - 1) {         // RL
                rotate(RIGHT, node.right());
            }
            rotate(LEFT, node);                              // RR
        }
    }

    public static int findMaxNodes(int h){

        return (int)Math.pow(2, h+1) - 1;
    }

    public static int findMinNodes(int h){

        double phi = (1 + Math.sqrt(5))/2;
        double fiboHplusTwo = (Math.pow(phi, h+3) - Math.pow((-1/phi), h+3)) / Math.sqrt(5);

        return (int)fiboHplusTwo - 1;
    }
}

