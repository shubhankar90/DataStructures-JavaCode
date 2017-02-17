import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.function.BiPredicate;

/**
 * TODO: This is your first major task.
 * 
 * This class implements a generic unbalanced binary search tree (BST).
 */

public class BinarySearchTree<K> implements Tree<K> {

	/**
	 * A Node is a Location, which means that it can be the return value of a
	 * search on the tree.
	 */

	class Node implements Location<K> {
		protected K data;
		protected Node left, right;
		protected Node parent; // the parent of this node
		protected int height; // the height of the subtree rooted at this node
		protected boolean dirty; // true iff the key in this node has been
									// removed

		/**
		 * Constructs a leaf node with the given key.
		 */
		public Node(K key) {
			this(key, null, null);
		}

		/**
		 * 
		 * 
		 * Constructs a new node with the given values for fields.
		 */
		public Node(K data, Node left, Node right) {
			this.data = data;
			this.left = left;
			this.right = right;
			this.height = 1;
		}

		
		/**
		 * Return true iff this node is a leaf in the tree.
		 */
		protected boolean isLeaf() {
			return left == null && right == null;
		}

		/**
		 * 
		 * 
		 * Performs a local update on the height of this node. Assumes that the
		 * heights in the child nodes are correct. This function *must* run in
		 * O(1) time.
		 */
		protected void fixHeight() {
			if ((this.left == null) & (this.right == null))
				height = 1;
			else if ((this.left != null) & (this.right == null))
				height = this.left.height + 1;
			else if ((this.left == null) & (this.right != null))
				height = this.right.height + 1;
			else
				height = Math.max(this.left.height, this.right.height) + 1;
		}

	
		/**
		 * 
		 * 
		 * Returns the data in this node.
		 */
		public K get() {
			return this.data;
		}

		/**
		 * 
		 * 
		 * Returns the location of the node containing the inorder predecessor
		 * of this node.
		 */
		public Node getBefore() {
			if (this.left != null) {
				//searches for maximum value on the left side below the node
				return findBelowBefore(this.left);
			} else if (this.parent != null) {
				//searches for maximum value on the left side obove the node
				return findUpBefore(this);
			} else
				return null;
		}

		/**
		 *helper for searching for maximum value on the left side below the node 
		 */
		public Node findUpBefore(Node root) {
			if (root.parent.right != null) {
				if (root.parent.right.equals(root))
					return root.parent;
				else if (root.parent.parent != null)
					return findUpBefore(root.parent);
				else
					return null;
			} else if (root.parent.parent != null)
				return findUpBefore(root.parent);
			else
				return null;
		}

		/**
		 *helper for searching for maximum value on the left side above the node 
		 */
		public Node findBelowBefore(Node root) {

			if (root.right == null)
				return root;
			else
				return findBelowBefore(root.right);
		}

		/**
		 *
		 * 
		 * Returns the location of the node containing the inorder successor of
		 * this node.
		 */
		public Node getAfter() {
			ArrayList<K> element = new ArrayList<K>();
			if (this.right != null)
				//searches for minimum value on the right side below the node
				return findBelowAfter(this.right, element);
			else if (this.parent != null)
				//searches for minimum value on the right side below the node
				return findUpAfter(this);
			else
				return null;

		}

		/**
		 *helper for searching for minimum value on the right side obove the node 
		 */
		public Node findUpAfter(Node root) {
			if (root.parent.left != null) {
				if (root.parent.left.equals(root))
					return root.parent;
				else if (root.parent.parent != null)
					return findUpAfter(root.parent);
				else
					return null;
			} else if (root.parent.parent != null)
				return findUpAfter(root.parent);
			else
				return null;
		}

		/**
		 *helper for searching for minimum value on the right side below the node 
		 */
		public Node findBelowAfter(Node root, ArrayList<K> element) {
			if (root.left == null)
				return root;
			else
				return findBelowAfter(root.left, element);
		}
	}

	protected Node root;
	protected int n;
	protected BiPredicate<K, K> lessThan;

	/**
	 * Constructs an empty BST, where the data is to be organized according to
	 * the lessThan relation.
	 */
	public BinarySearchTree(BiPredicate<K, K> lessThan) {
		this.lessThan = lessThan;
		this.n = 0;
	}

	/**
	 * 
	 * Looks up the key in this tree and, if found, returns the (possibly dirty)
	 * location containing the key.
	 */
	public Node search(K key) {
		return containsHelper(key, root);
	}

	/**
	 * 
	 * Search helper for finding a node(possibly dirty)
	 */
	private Node containsHelper(K key, Node p) {
		if (p == null)
			return null;
		if (key.equals(p.data))
			return p;
		if (lessThan.test(key, p.data))
			return containsHelper(key, p.left);
		return containsHelper(key, p.right);
	}

	/**
	 * 
	 * Returns the height of this tree. Runs in O(1) time!
	 */
	public int height() {
		if (root != null)
			return root.height;
		else
			return 0;
	}

	/**
	 * 
	 * Clears all the keys from this tree. Runs in O(1) time!
	 */
	public void clear() {
		this.root.left = null;
		this.root.right = null;
		this.n = 0;
		this.root.height = 0;
		this.root = null;
	}

	/**
	 * Returns the number of keys in this tree.
	 */
	public int size() {
		return n;
	}

	/**
	 *
	 * 
	 * Inserts the given key into this BST, as a leaf, where the path to the
	 * leaf is determined by the predicate provided to the tree at construction
	 * time. The parent pointer of the new node and the heights in all node
	 * along the path to the root are adjusted accordingly.
	 * 
	 * Note: we assume that all keys are unique. Thus, if the given key is
	 * already present in the tree, nothing happens.
	 * 
	 * Returns the location where the insert occurred (i.e., the leaf node
	 * containing the key).
	 */
	public Node insert(K key) {
		Object[] element = new Object[1];
		if (n == 0) {
			root = insertHelper(key, this.root, element);
			n++;

			return root;
		}
		else {
			n++;
			insertHelper(key, this.root, element);
			return (Node) element[0];
		}

	}

	/**
	 * 
	 * helper for insert
	 */
	private Node insertHelper(K key, Node p, Object[] addedNode) {
		if (p == null) {
			p = new Node(key);
			addedNode[0] = p;
			

		} else if (key.equals(p.data)) {
			if (p.dirty) {
				p.dirty = false;
				addedNode[0] = p;
			} else {
				n--;
				addedNode[0] = p;
			}
			
		} else if (lessThan.test(key, p.data)) {

			
			p.left = insertHelper(key, p.left, addedNode);
			p.left.parent = p;
			p.fixHeight();
			
		} else {
			p.right = insertHelper(key, p.right, addedNode);
			p.right.parent = p;
			p.fixHeight();
			}
		return p;
	}

	/**
	 * 
	 * 
	 * Returns true iff the given key is in this BST and it is not dirty.
	 */
	public boolean contains(K key) {
		Node p = search(key);
		if (p != null) {
			if (!p.dirty)
				return true;
			else
				return false;
		}

		else
			return false;
	}

	/**
	 * 
	 * 
	 * Removes the key from this BST. If the key is not in the tree, nothing
	 * happens. Implement the removal using lazy deletion.
	 */
	public void remove(K key) {
		Node loc = search(key);
		if (loc != null) {
			if (!loc.dirty) {
				n--;
				loc.dirty = true;
			}

		}
	}

	/**
	 * 
	 * 
	 * Clears out all dirty nodes from this BST.
	 * 
	 * Use the following algorithm: (1) Let ks be the list of keys in this tree.
	 * (2) Clear this tree. (2) For each key in ks, insert it into this tree.
	 */
	public void rebuild() {
		List<K> ks = inOrderIter(root);
		this.clear();
		for (K key : ks)
			this.insert(key);
	}

	/**
	 * 
	 * 
	 * Returns a sorted list of all the keys in this tree.
	 */
	public List<K> keys() {

		return inOrderIter(this.root);
	}

	/**
	 * 
	 * 
	 * Returns a textual representation of this BST.
	 */
	public String toString() {
		showTree(this.root);
		return "";
	}

	public int height(Node root) {

		return root.height;
	}

	public void showTree(Node root) {
		Queue<Node> q = new LinkedList<Node>();
		int levelNodes = 0;
		if (root == null)
			return;
		q.add(root);
		while (!q.isEmpty()) {
			levelNodes = q.size();
			while (levelNodes > 0) {
				Node n = (Node) q.remove();
				System.out.print(" " + n.data);
				if (n.left != null)
					q.add(n.left);
				if (n.right != null)
					q.add(n.right);
				levelNodes--;
			}
			System.out.println("");
		}
	}

	/**
	 * 
	 * helper function to return non-dirty keys inorder
	 */
	public ArrayList<K> inOrderIter(Node root) {
		ArrayList<K> arr = new ArrayList<K>();
		if (root == null)
			return arr;

		Stack<Node> s = new Stack<Node>();

		Node currentNode = root;

		while (!s.empty() || currentNode != null) {

			if (currentNode != null) {
				s.push(currentNode);
				currentNode = currentNode.left;
			} else {
				Node n = s.pop();
				if (!n.dirty) {
					
					arr.add(n.data);
				}
				currentNode = n.right;
			}
		}
		return arr;
	}

	public static void main(String[] args) {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>((Integer x, Integer y) -> x < y);
		System.out.println(bst.isEmpty());
		BinarySearchTree<Integer>.Node p;
		p = bst.new Node(5);
		assertNull(p.left);
		assertNull(p.right);
		assertNull(p.parent);
	}
}
