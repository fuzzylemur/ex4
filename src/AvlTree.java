public class AvlTree extends BinaryTree {

    AvlTree(){
        super();
    }

    AvlTree(int[] data) {
        super();
        for (int value : data){
            add(value);
        }
    }

    AvlTree(AvlTree tree) {
        super();
        // deep copy
    }

    public boolean add(int newValue){

        if (contains(newValue) >= 0)
            return false;

        else
            addHelper(newValue, root);

        size++;
        return true;

    }

    private void addHelper(int newValue, Node curNode) {

        if (curNode == null) {
            root = new Node(newValue);
            return;
        }
        else if (newValue < curNode.getValue()) {
            if (curNode.getLeftSon() == null) {
                curNode.setLeftSon(new Node(newValue, curNode));
            }
            else
                addHelper(newValue, curNode.getLeftSon());

        } else if (newValue > curNode.getValue()) {
            if (curNode.getRightSon() == null) {
                curNode.setRightSon(new Node(newValue, curNode));
            }
            else
                addHelper(newValue, curNode.getRightSon());
        }

        treeJanitor(curNode, true);
    }

    public boolean delete(int toDelete){
        return true;
    }

    private void rotateRight(Node node){

        Node newParent = node.getParent().getParent();
        Node rightSon = node.getRightSon();
        node.setRightSon(node.getParent());
        node.getRightSon().setLeftSon(rightSon);
        node.setParent(newParent);
        if (newParent == null)
            root = node;

        node.heightPlus();
        node.getRightSon().heightMinus();

    }

    private void rotateLeft(Node node){
        System.out.println("left rotation");
        Node newParent = node.getParent().getParent();
        Node leftSon = node.getLeftSon();
        //if (leftSon != null)
        node.setLeftSon(node.getParent());
        node.getLeftSon().setRightSon(leftSon);
        node.setParent(newParent);
        if (newParent == null)
            root = node;

        //node.heightPlus();
        node.getLeftSon().heightMinus();

    }

    private void addUpdateHeight(Node node) {
        System.out.println("1");
        if (node.getRightSon() != null && node.getLeftSon() != null){
            if (Math.abs(node.getRightSon().getHeight() - node.getLeftSon().getHeight()) == 1)
                node.heightPlus();
        }

        else if (node.getRightSon() == null || node.getLeftSon() == null) {
           // System.out.println("2");
            node.heightPlus();
        }
    }

    private void treeJanitor(Node node, boolean add) {

        addUpdateHeight(node);

        avlBalance(node);
    }

    private void avlBalance(Node node) {

        int balanceFactor = node.getBalanceFactor();
        if (Math.abs(balanceFactor) == 2){

            if (balanceFactor == 2) {
                int leftFactor = node.getLeftSon().getBalanceFactor();
                if (leftFactor == -1) {                  // LR
                    rotateLeft(node.getLeftSon());
                }
                rotateRight(node.getLeftSon());         // LL

            }

            if (balanceFactor == -2) {
                int rightFactor = node.getRightSon().getBalanceFactor();
                System.out.println("rightFactor: " + rightFactor);
                if (rightFactor == 1) {           // RL
                    rotateRight(node.getRightSon());
                }
                rotateLeft(node.getRightSon());         // RR

            }
        }
    }

}
