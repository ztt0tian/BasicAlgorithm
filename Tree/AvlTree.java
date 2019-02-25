public class AvlTree<T extends Comparable<? super T>> {
    private AvlNode<T> root;
    private static final int ALLOW_IMBALANCE = 1;//允许的最大高度差
    public AvlTree() {
        root = null;
    }

    public void insert(T x) {
        root = insert(x, root);
    }

    public void remove(T x) {
        root = remove(x, root);
    }

    public T findMin() throws UnderflowException {
        if (isEmpty()) throw new UnderflowException();
        return findMin(root).element;
    }

    public T findMax() throws UnderflowException {
        if (isEmpty()) throw new UnderflowException();
        return findMax(root).element;
    }

    public boolean contains(T x) {
        return contains(x, root);
    }

    public void makeEmpty() {
        root = null;
    }

    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty Tree");
        } else printTree(root);
    }

    /**
     * 中序遍历
     *
     * @param root
     */
    private void printTree(AvlNode<T> root) {
        if (root != null) {
            printTree(root.left);
            System.out.println(root.element);
            printTree(root.right);
        }
    }

    private boolean contains(T x, AvlNode<T> root) {
        while (root != null) {
            int compareRes = x.compareTo(root.element);
            if (compareRes < 0) root = root.left;
            else if (compareRes > 0) root = root.right;
            else return true;
        }
        return false;
    }

    private AvlNode<T> findMax(AvlNode<T> root) {
        if (root == null) {
            return null;
        }
        while (root.right != null) {
            root = root.right;
        }
        return root;
    }

    private AvlNode<T> findMin(AvlNode<T> root) {
        if (root == null) {
            return null;
        }
        while (root.left != null) {
            root = root.left;
        }
        return root;
    }

    private boolean isEmpty() {
        return root == null ? true : false;
    }

    /**
     * 删除AVL的某一个结点 先按二叉查找树删除 最后再平衡删除后的树
     * @param x
     * @param root
     * @return
     */
    private AvlNode<T> remove(T x, AvlNode<T> root) {
        if(root==null) return root;
        int compareRes = x.compareTo(root.element);
        if(compareRes<0) root.left = remove(x, root.left);
        else if(compareRes>0) root.right = remove(x, root.right);
        else if (root.left != null && root.right != null) {
            root.element = findMin(root.right).element;
            root.right = remove(root.element, root.right);
        }
        else
            root = (root.left != null) ? root.left : root.right;
        return balance(root);
    }

    private AvlNode<T> insert(T x, AvlNode<T> root) {
        if (root == null) return new AvlNode<>(x, null, null);
        int compareRes = x.compareTo(root.element);
        if (compareRes < 0) root.left = insert(x, root.left);
        else if (compareRes > 0) root.right = insert(x, root.right);
        else ;//Duplicate
        return balance(root);//先按二叉查找树 插入 再去平衡他
    }



    /**
     *  失衡情形
     *  1.对左儿子的左子树进行插入
     *  2.对左儿子的右子树进行插入
     *  3.对右儿子的左子树进行插入
     *  4.对右儿子的左子树进行插入
     * @param root
     * @return
     */
    private AvlNode<T> balance(AvlNode<T> root) {
        if(root==null) return root;
        //左子树过深
        if (height(root.left) - height(root.right) > ALLOW_IMBALANCE) {
            //进一步判断左儿子（失衡节点的左儿子）的的左右子树高度关系
            if (height(root.left.left) >= height(root.left.right)) {
                root = rotateWithLeftChild(root);//情形1 对左子树单旋转
            } else root = doubleWithLeftChild(root);//情形2 对左子树双旋转
        }
        //右子树过深
        else if (height(root.right) - height(root.left) > ALLOW_IMBALANCE) {
            //进一步判断右儿子（失衡节点的右儿子）的的左右子树高度关系
            if (height(root.right.right) >= height(root.right.left)) {
                root = rotateWithRightChild(root);//情形3 对右子树单旋转
            } else root = doubleWithRightChild(root);//情形4 对右子树双旋转
        }
        //平衡之后更新平衡节点的高度
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        return root;
    }

    /**
     * 双旋转左子树 ：先对失衡结点的左子树进行右单旋转（更新失衡结点的左子树），再对整个失衡节点进行左单旋转
     * 适用于情形2
     * @param root
     * @return
     */
    private AvlNode<T> doubleWithLeftChild(AvlNode<T> root) {
        root.left = rotateWithRightChild(root.left);
        return rotateWithLeftChild(root);
    }
    /**
     * 双旋转右子树 ：先对失衡结点的右子树进行左单旋转（更新失衡结点的右子树），再对整个失衡节点进行右单旋转
     * 适用于情形3
     * @param root
     * @return
     */
    private AvlNode<T> doubleWithRightChild(AvlNode<T> root) {
        root.right = rotateWithLeftChild(root.right);
        return rotateWithRightChild(root);
    }

    /**
     * 单旋转左子树 适用于情形1
     * @param root
     * @return
     */
    private AvlNode<T> rotateWithLeftChild(AvlNode<T> root) {
        AvlNode<T> newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;
        //更新高度节点
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        newRoot.height = Math.max(height(newRoot.left), root.height) + 1;
        return newRoot;
    }
    /**
     * 单旋转右子树 适用于情形4
     * @param root
     * @return
     */
    private AvlNode<T> rotateWithRightChild(AvlNode<T> root) {
        AvlNode<T> newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;
        //更新节点高度
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        newRoot.height = Math.max(height(newRoot.right), root.height) + 1;
        return newRoot;
    }

    private int height(AvlNode<T> avlNode) {
        return avlNode == null ? -1 : avlNode.height;
    }

    public void checkBalance(){
        checkBalance(root);
    }

    private int checkBalance(AvlNode<T> root) {
        if(root==null) return -1;//空节点 平衡结果返回-1
        if (root != null) {
            int h_left = checkBalance(root.left);
            int h_right = checkBalance(root.right);
            if (Math.abs(height(root.left) - height(root.right))>1||height(root.left)!=h_left||height(root.right)!=h_right) {
                System.out.println("不平衡");
            }
        }
        return height(root);//如若平衡会返回该节点的高度

    }

    private static class AvlNode<T> {
        public AvlNode(T element) {
            this(element, null, null);
        }

        public AvlNode(T et, AvlNode<T> lt, AvlNode<T> rt) {
            element = et;
            left = lt;
            right = rt;
        }

        T element;
        AvlNode<T> left;
        AvlNode<T> right;
        int height;//高度
    }

    public static void main(String[] args) {
        AvlTree<Integer> avlTree = new AvlTree<>();
        final int SMALL = 40;
        final int NUMS = 200;  // must be even
        final int GAP  =   37;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
        {
            System.out.println( "INSERT: " + i );
            avlTree.insert( i );
            if( NUMS < SMALL )
                avlTree.checkBalance( );
        }
        avlTree.checkBalance();
    }
}
