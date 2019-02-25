/**
 * 泛型中使用通配符有两种形式：子类型限定<? extends xxx>和超类型限定<? super xxx>。
 * @param <T>
 */
public class BinarySearchTree<T extends  Comparable<? super T>> {
    private BTreeNode<T> root;

    public BinarySearchTree() {
        root = null;
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }
    public boolean contains(T x) {
        return contains(x, root);
    }

    public T findMin() throws UnderflowException {
        if (isEmpty()) throw new UnderflowException();
        return findMin(root).element;
    }
    public T findMax() throws UnderflowException {
        if (isEmpty()) throw new UnderflowException();
        return findMax(root).element;
    }

    public void insert(T x) {
        root=insert(x,root);
    }

    public void remove(T x) {
        root=remove(x,root);
    }

    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty Tree");
        }
        else printTree(root);
    }

    /**
     * 中序遍历
     * @param root
     */
    private void printTree(BTreeNode<T> root) {
        if (root != null) {
            printTree(root.left);
            System.out.println(root.element);
            printTree(root.right);
        }
    }

    /**
     * 删除值为x的节点。有几种情况
     * 1.删除结点为叶子节点
     * 2.删除结点有一个儿子
     * 3.删除节点有两个儿子
     * （删除策略：用其右子树的最小节点值代替该节点的值并递归删除被用来代替的最小值节点）
     * 因为右子树中最小的节点不会有左子树 第二次remove就比较容易
     * @param x
     * @param root
     * @return
     */
    private BTreeNode<T> remove(T x, BTreeNode<T> root) {
        if(root==null) return root;
        int compareRes = x.compareTo(root.element);
        if(compareRes<0) root.left=remove(x, root.left);
        else if(compareRes>0) root.right=remove(x, root.right);
        else
            if(root.left!=null&&root.right!=null)//左右孩子均不为空
            {
                root.element = findMin(root.right).element;
                root.right = remove(root.element, root.right);
            }
            else
                root = root.left != null ? root.left : root.right;//用左子树或者右子树替代
        return root;
    }

    /**
     * 往root为节点的树插入x值
     * @param x
     * @param root
     * @return
     */
    private BTreeNode<T> insert(T x, BTreeNode<T> root) {
        if (root==null) return new BTreeNode<>(x, null, null);
        int compareRes = x.compareTo(root.element);
        if(compareRes<0) //小于root.element 往左子树插
            root.left = insert(x, root.left);
        else if(compareRes>0) //大于root.element 往右子树插
            root.right = insert(x, root.right);
        else ;//Duplicate
        return root;
    }


    /**
     * 非递归实现
     * @param root 当前节点
     * @return 返回最大项对应的那个节点
     */
    private BTreeNode<T> findMax(BTreeNode<T> root) {
        if(root!=null){
            while (root.right!=null)//还有右子树
                root = root.right;//往右走
        }
        return root;
    }

    /**
     *递归实现
     * @param root 当前节点
     * @return 返回最小项对应的那个节点
     */
    private BTreeNode<T> findMin(BTreeNode<T> root) {
        if(root==null) return null;
        else if(root.left==null) return root;
        else return findMin(root.left);
    }

    /**
     *
     * @param x 要寻找的值
     * @param root 子树根节点
     * @return 查到返回true 反之返回false
     */
    private boolean contains(T x, BTreeNode<T> root) {
        if(root==null) return false;
        int compareRes = x.compareTo(root.element);//比较结果
        if (compareRes<0) //小于root的值 去root的左子树找
            return contains(x, root.left);
        else if (compareRes>0) //大于root的值 去root的右子树找
            return contains(x, root.right);
        else return true;//相等
    }
}
