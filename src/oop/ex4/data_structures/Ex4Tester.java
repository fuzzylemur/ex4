package oop.ex4.data_structures;


import java.util.SortedSet;
import java.util.TreeSet;

public class Ex4Tester {


    public static void main(String[] args) {


        AvlTree tree = new AvlTree();


        for (int i=0;i<500;i++) {
            tree.add(i);
        }
        AvlTree copy = new AvlTree(tree);
        copy.delete(20);
        tree.delete(30);
        for (int i=0;i<1000;i++) {
            if (tree.contains(i) != copy.contains(i))
                System.out.println(i);

        }

    }
}
