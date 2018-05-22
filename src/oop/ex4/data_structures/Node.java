package oop.ex4.data_structures;

/*
 * Class representing a Node in a binary tree data structure.
 * Each Node object holds an int value, and can be connected to other Node objects to create a binary tree.
 * Class methods allow getting/setting Node values, and other operations that can be done in the context
 * of a binary tree (linking and un-linking nodes, determining the balance factor).
 */
class Node {

    /* Constant for left side, used as a parameter in some methods */
    static final boolean LEFT = true;
    /* Constant for right side, used as a parameter in some methods */
    static final boolean RIGHT = false;

    /* value of this node */
    private int value;
    /* height of this node (distance from furthest leaf in tree) */
    private int height;
    /* pointer to left child node */
    private Node leftChild;
    /* pointer to right child node */
    private Node rightChild;
    /* pointer to parent node **/
    private Node parent;


    /* default constructor */
    Node() {
    }

    /*
     * Constructor for creating a new node with given value and parent node
     * @param value int value to set in node
     * @param parent Node parent of this new node
     */
    Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
        this.height = 0;
    }

    /*
     * Get the value of a Node
     * @return int value of node
     */
    int value() {
        return value;
    }

    /*
     * Get height of Node (distance from furthest leaf in tree).
     * @return int height of node
     */
    int height() {
        return height;
    }

    /*
     * Get pointer to parent of this node
     * @return parent node
     */
    Node parent() {
        return parent;
    }

    /*
     * Get pointer to child of node on the given side (defined by class constants)
     * used for parameterized getting of child nodes.
     *
     * @param side boolean class constant LEFT / RIGHT
     * @return child node in the requested side
     */
    Node child(boolean side) {
        if (side == LEFT)
            return leftChild;
        else
            return rightChild;
    }

    /*
     * Get the side in which this node is attached to its parent node.
     * Assumes node has a non-null parent.
     * @return boolean class constant LEFT / RIGHT
     */
    boolean getSide() {
        if (parent().child(LEFT) == this)
            return LEFT;
        else
            return RIGHT;
    }

    /*
     * Set child node pointer in given side
     * @param side boolean class constant LEFT / RIGHT
     * @param child new child node
     */
    private void setChild(boolean side, Node child) {
        if (side == LEFT)
            leftChild = child;
        else
            rightChild = child;
    }

    /*
     * Set parent node pointer
     * @param parent node to set as parent
     */
    private void setParent(Node parent) {
        this.parent = parent;
    }

    /*
     * Set value to node
     * @param newValue int value to set
     */
    void setValue(int newValue) {
        this.value = newValue;
    }

    /*
     * Get heights of both child nodes (height is -1 for a null child)
     * @return int[] height of left child, height of right child
     */
    private int[] childHeights() {

        int leftHeight = -1;
        int rightHeight = -1;

        if (leftChild != null)
            leftHeight = leftChild.height();
        if (rightChild != null)
            rightHeight = rightChild.height();

        return new int[]{leftHeight, rightHeight};
    }

    /*
     * Calculate balance factor (difference between left child height and right child height)
     * @return int balance factor
     */
    int balanceFactor() {

        return childHeights()[0] - childHeights()[1];
    }

    /*
     * Update node height based on child node heights.
     * Assumes child node heights are correct.
     */
    void updateHeight() {

        int[] childHeights = childHeights();

        if (childHeights[0] > childHeights[1])
            height = childHeights[0] + 1;
        else
            height = childHeights[1] + 1;
    }

    /*
     * Link a child node on the given side (sets pointers in both child and parent)
     * @param side boolean class constant LEFT / RIGHT
     * @param child node to link to
     */
    void link(boolean side, Node child) {

        setChild(side, child);
        if (child != null)
            child.setParent(this);
    }

    /*
     * Unlink child node in the given side (sets pointers in both child and parent)
     * @param side boolean class constant LEFT / RIGHT
     */
    void unlinkDown(boolean side) {

        if (child(side) != null)
            child(side).setParent(null);
        setChild(side, null);
    }

    /*
     * Unlink this node from its parent (sets pointers in both child and parent)
     */
    void unlinkUp() {

        if (parent == null)
            return;

        if (parent.child(LEFT) == this) {
            parent.setChild(LEFT, null);
        } else {
            parent.setChild(RIGHT, null);
        }
        setParent(null);
    }
}

