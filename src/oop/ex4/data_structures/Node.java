package oop.ex4.data_structures;

public class Node {

    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    private int value;
    private int height;
    private Node leftChild;
    private Node rightChild;
    private Node parent;

    public Node() {
    }

    public Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
        this.height = 0;
    }

    public int value() {
        return value;
    }

    private int height() {
        return height;
    }

    public Node left() {
        return leftChild;
    }

    public Node right() {
        return rightChild;
    }

    public Node parent() {
        return parent;
    }

    public Node child(int side) {
        if (side == LEFT)
            return leftChild;
        else
            return rightChild;
    }

    private void left(Node leftChild) {
        this.leftChild = leftChild;
    }

    private void right(Node rightChild) {
        this.rightChild = rightChild;
    }

    private void parent(Node parent) {
        this.parent = parent;
    }

    public void value(int newValue) {
        this.value = newValue;
    }

    private int[] childHeights() {

        int leftHeight = -1;
        int rightHeight = -1;

        if (leftChild != null)
            leftHeight = leftChild.height();
        if (rightChild != null)
            rightHeight = rightChild.height();

        return new int[]{leftHeight, rightHeight};
    }

    public int balanceFactor() {

        return childHeights()[0] - childHeights()[1];
    }

    public void updateHeight() {

        int[] childHeights = childHeights();

        if (childHeights[0] > childHeights[1])
            height = childHeights[0] + 1;
        else
            height = childHeights[1] + 1;
    }

    public void link(int side, Node child) {

        if (side == LEFT) {
            left(child);
            if (child != null)
                child.parent(this);

        } else {
            right(child);
            if (child != null)
                child.parent(this);
        }
    }

    public void unlinkDown(int side) {

        if (side == LEFT) {
            if (leftChild != null)
                leftChild.parent(null);
            left(null);

        } else {
            if (rightChild != null)
                rightChild.parent(null);
            right(null);
        }
    }

    public void unlinkUp() {

        if (parent != null) {
            if (parent.left() == this) {
                parent.left(null);

            } else {
                parent.right(null);
            }
            parent(null);
        }
    }

    void printNode() {

        int rightVal = -1;
        if (rightChild != null)
            rightVal = rightChild.value();

        int leftVal = -1;
        if (leftChild != null)
            leftVal = leftChild.value();

        int parentVal = -1;
        if (parent != null)
            parentVal = parent.value();

        System.out.println("value: "+value+"  parent: "+parentVal+"  left: "+leftVal+
                "  right: "+rightVal+"  height: "+height+"  BF: "+balanceFactor());
    }
}

