

public class BinarySearchTreeTest {
    public static void main(String[] args) throws UnderflowException {
        BTreeNode<Integer> root = new BTreeNode<>(5);
        BinarySearchTree tree = new BinarySearchTree();
        //tree.findMax();
        System.out.println(tree.isEmpty());
        tree.insert(6);
        tree.insert(2);
        tree.insert(1);
        tree.insert(4);
        tree.insert(3);
        tree.insert(8);
        tree.printTree();
        System.out.println(tree.findMax());
        System.out.println(tree.findMin());
        tree.remove(2);
        tree.printTree();
    }
}
