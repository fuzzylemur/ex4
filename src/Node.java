public class Node {

    private int value;
    private int height;
    private Node leftSon;
    private Node rightSon;
    private Node parent;

    Node() {

    }

    Node(int value) {
        this.value = value;
        this.height = 1;

    }

    Node(int value, Node parent) {
        this.value = value;
        this.parent = parent;
        this.height = 1;
    }

    int getValue() {
        return value;
    }

    int getHeight() {
        return height;
    }

    Node getLeftSon() {
        return leftSon;
    }

    Node getRightSon() {
        return rightSon;
    }

    Node getParent() {
        return parent;
    }

    int getBalanceFactor() {

        int leftHeight = 0;
        if (leftSon != null)
            leftHeight = leftSon.getHeight();

        int rightHeight = 0;
        if (rightSon != null)
            rightHeight = rightSon.getHeight();

        return leftHeight - rightHeight;
    }


    void heightPlus() {
        this.height++;
    }

    void heightMinus() {
        this.height--;
    }

    void setRightSon(Node rightSon) {
        this.rightSon = rightSon;
    }

    void setParent(Node parent) {
        this.parent = parent;
    }

    void setLeftSon(Node leftSon) {
        this.leftSon = leftSon;
    }
}

