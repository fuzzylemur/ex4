package oop.ex4.data_structures;

public class Ex4Tester {


    public static void main(String[] args) {


        AvlTree tree = new AvlTree();

        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(6);
        tree.add(0);
        tree.delete(2);
        System.out.println(tree.contains(1));
    }
}

       // for (int j=0; j<1; j++) {

            //for (int i = 0; i < 10; i++) {
             //   tree.add(i);
            //}

          //  for (int i = 0; i < 15; i++) {
            //    int x = (i * 7919) % 30;
              //  System.out.println(tree.add(x));
            //}

            //System.out.println(tree.size());
            //System.out.println("root: " + tree.root.value() + " height : " + tree.root.height());


            //for (int i = 0; i < 100000; i++) {
             //   if (tree.contains(i) == -1)
             //       System.out.println(i + "!!!");
            //}

            //for (int i = 100000; i >= 0; i--) {
             //   tree.delete(i);
              //  tree.delete(i);
            //}

            //System.out.println(tree.size());
            //System.out.println(tree.root);

        //System.out.println("root: " + tree.root.value() + " height : " + tree.root.height());

       // for (Integer value : tree)
         //   System.out.println(value);


        /*

        int LEFT = 0;
        int RIGHT = 1;

        Node node0 = new Node(0);
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        node0.parent(node1);
        node1.left(node0);
        node1.parent(node2);
        node2.left(node1);
        node2.parent(node3);
        node3.left(node2);

        node0.updateHeight();
        node1.updateHeight();
        node2.updateHeight();
        node3.updateHeight();

        node0.printNode();
        node1.printNode();
        node2.printNode();
        node3.printNode();

        System.out.println("Rotate 2 right");
        node1.rotate(RIGHT, node2);

        node0.printNode();
        node1.printNode();
        node2.printNode();
        node3.printNode();

        System.out.println("Rotate 3 right");
        node1.rotate(RIGHT, node3);

        node0.printNode();
        node1.printNode();
        node2.printNode();
        node3.printNode();
        */


