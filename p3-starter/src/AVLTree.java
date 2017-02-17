import java.util.function.BiPredicate;

/**
 * TODO: This is your second major task.
 *
 * This class implements a generic height-balanced binary search tree, using the
 * AVL algorithm. Beyond the constructor, only the insert() method needs to be
 * implemented. All other methods are unchanged.
 */

public class AVLTree<K> extends BinarySearchTree<K> {

	/**
	 * Creates an empty AVL tree as a BST organized according to the lessThan
	 * predicate.
	 */
	public AVLTree(BiPredicate<K, K> lessThan) {
		super(lessThan);
	}

	/**
	 * 
	 * Inserts the given key into this AVL tree such that the ordering property
	 * for a BST and the balancing property for an AVL tree are maintained.
	 */

	public Node insert(K key) {
		Node q = super.insert(key);

		fixups(q);
		return q;
	}

	/**
	 * right rotate method
	 */
	private Node rightRotate(Node y) {
		String parentDirection = null;
		Node parent = null;
		//checking for direction of parent
		if (y.parent != null) {
			parent = y.parent;
			if (parent.left == null)
				parentDirection = "right";
			else if (parent.left.data.equals(y.data))
				parentDirection = "left";
			else
				parentDirection = "right";

		}

		Node x = y.left;
		Node T2 = x.right;

		// Perform rotation
		x.right = y;
		y.left = T2;
		if (parent != null) {
			if (parentDirection == "left")
				parent.left = x;
			else
				parent.right = x;
		}
		x.parent = parent;
		if (T2 != null)
			T2.parent = y;
		y.parent = x;

		// Update heights
		y.fixHeight();
		Node returnNode = x;
		while (x != null) {
			x.fixHeight();
			x = x.parent;
		}
		return returnNode;
	}

	/**
	 * Left rotate function
	 */
	private Node leftRotate(Node x) {
		String parentDirection = null;
		Node parent = null;
		if (x.parent != null) {
			parent = x.parent;
			if (parent.left == null)
				parentDirection = "right";
			else if (parent.left.data.equals(x.data))
				parentDirection = "left";
			else
				parentDirection = "right";
		}
		Node y = x.right;
		Node T2 = y.left;

		// Perform rotation
		y.left = x;
		x.right = T2;
		if (parent != null) {
			if (parentDirection == "left")
				parent.left = y;
			else
				parent.right = y;
		}
		y.parent = parent;
		if (T2 != null)
			T2.parent = x;
		x.parent = y;
		
		// Update heights
		x.fixHeight();
		Node returnNode = y;
		while (y != null) {
			y.fixHeight();
			y = y.parent;
		}
		return returnNode;
	}

	/**
	 * fix the tree for balancing
	 */
	private void fixups(Node q) {
		K insertData = q.data;
		int balance = 0;
		Node old = q;
		q = q.parent;
		balance = getBalance(q);
		while ((q != null) & (Math.abs(balance) <= 1)) {
			balance = getBalance(q);
			q = q.parent;
			old = old.parent;
		}
		if (balance < -1) {
			if (lessThan.test(insertData, old.right.data)) {
				old = old.right;
				old = rightRotate(old);
				if (super.root.data.equals(old.parent.data)) {
					super.root = leftRotate(old.parent);
					super.root.parent = null;
				} else
					leftRotate(old.parent);
				} else {
				if (super.root.data.equals(old.data)) {
					super.root = leftRotate(old);
					super.root.parent = null;
				} else
					leftRotate(old);
			}

		} else if (balance > 1) {
			if (!lessThan.test(insertData, old.left.data)) {
				old = old.left;
				old = leftRotate(old);
				if (super.root.data.equals(old.parent.data)) {
					super.root = rightRotate(old.parent);
					super.root.parent = null;
				} else
					rightRotate(old.parent);
				} else {
				if (super.root.data.equals(old.data)) {
					super.root = rightRotate(old);
					super.root.parent = null;
				} else
					rightRotate(old);

			}
		}
	}

	/**
	 * find balance at a node
	 */
	int getBalance(Node N) {
		if (N == null)
			return 0;
		int leftHeight = 0;
		int rightHeight = 0;
		if (N.left != null)
			leftHeight = N.left.height;
		if (N.right != null)
			rightHeight = N.right.height;
		return (leftHeight - rightHeight);
	}

}
