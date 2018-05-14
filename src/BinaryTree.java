import java.util.Iterator;

public abstract class BinaryTree implements Iterable<Integer> {

    protected Node root;
    private BinaryTreeIterator myIterator;
    protected int size;


    public BinaryTree(){
        myIterator = new BinaryTreeIterator(root);
    }

    public abstract boolean add(int newValue);

    public abstract boolean delete(int toDelete);

    public int contains(int searchVal){
        Node curRoot = root;
        int depth = 0;

        while (curRoot != null){
            if (curRoot.getValue() == searchVal)
                return depth;
            else if (curRoot.getValue() < searchVal)
                curRoot = curRoot.getRightSon();
            else if (curRoot.getValue() > searchVal)
                curRoot = curRoot.getLeftSon();

            depth++;
        }
        return -1;
    }

    public Iterator<Integer> iterator(){
        return myIterator;
    }

    public int size() {
        return size;
    }

}
