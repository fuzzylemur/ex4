import java.util.Iterator;

public class BinaryTreeIterator implements Iterator<Integer> {

    private Node curNode;

    BinaryTreeIterator(Node root) {
        curNode = getMin(root);
    }

    private Node getMin(Node node){
        Node curNode = node;
        while (curNode != null && curNode.getLeftSon() != null){
            curNode = curNode.getLeftSon();
        }
        return curNode;
    }

    private Node successor(Node node){
        if (node.getRightSon() != null){
            return getMin(node.getRightSon());
        } else {
            Node curParent = node.getParent();
            while (curParent.getParent().getLeftSon() != curParent) {
                curParent = curParent.getParent();
                if (curParent == null)
                    break;
            }
            return curParent;
        }
    }

    public Integer next() {
        curNode = successor(curNode);
        return curNode.getValue();
    }

    public boolean hasNext() {
        return curNode != null && successor(curNode) != null;
    }

}
