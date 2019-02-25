/**
 * 二叉树节点定义
 */
public class BTreeNode<T> {

    public BTreeNode() {
    }

    public BTreeNode(T element) {
        this(element, null, null);
    }

    public BTreeNode(T element, BTreeNode<T> left, BTreeNode<T> right) {
        this.element = element;
        this.left = left;
        this.right = right;
    }
    T element;
    BTreeNode<T> left;//左孩子
    BTreeNode<T> right;//右孩子
}
