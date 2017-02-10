/**
 * Tree interface and BinarySearchTree class from lec4b.
 *                          ^^^^^^
 * 
 * TODO: Pre-Lecture Exercise for lec5a.
 * Note: This is a team effort. Every member of your team is expected to 
 *       make non-trivial contributions towards your solution.
 * 
 * Make the following modifications:
 * (1) Add javadoc style comments to all methods.
 * (2) Implement BinarySearchTree.contains() so that it runs in O(h) time,
 *     where h is the height of the tree.
 * (3) Modify Tree and BinarySearchTree so that they are generic for any 
 *     Comparable type object.
 * (4) [challenge] Try to implement a sensible BinarySearchTree.toString()
 *      method. Recall that an inorder traversal of a BST yields a sorted 
 *      sequence.
 * (5) Test thoroughly in main(). Be sure to include tests on non-integer 
 *     data.
 * 
 * @author <insert your team name here (e.g., red1, red2, ..., green1, ...)>
 * @author <insert the name of your Scribe here>
 */

public class BinarySearchTree<T extends Comparable<T>> implements Tree<T> {
  
  class Node<T> {
    T data;
    Node<T> left, right;
    
    Node(T key) {
      this(key, null, null);
    }
    
    Node(T data, Node<T> left, Node<T> right) {
      this.data = data;
      this.left = left;
      this.right = right;
    }
    
    boolean isLeaf() {
      return left == null && right == null;
    }
  }
  
  Node<T> root;
  int n;
  
  public void insert(T key) {
    n++;
    root = insertHelper(key, root);
  }
  
  private Node<T> insertHelper(T key, Node<T> p) {
    if (p == null)
      return new Node<T>(key);
    if (key.compareTo( p.data)<0)
      p.left = insertHelper(key, p.left);
    else
      p.right = insertHelper(key, p.right);
    return p;
  }
  
  public boolean contains(T key) {
	  Node<T> start = new Node<T>(root.data,root.left,root.right);
	 return containsHelper(key, start);
	 }
  
  public boolean containsHelper(T key, Node<T> start){
	  
	  if (start==null)
		  return false;
	  else if (start.data==key)
		  return true;
	  else if (start.data.compareTo(key)<0){
		  start = start.right;
		  return containsHelper(key, start);
	  }
	  else {
		  start = start.left;
		  return containsHelper(key, start);
		  }
  }
    
  public int size() {
    return n;
  }
  
  public static void main(String... args) { 
    int[] a = new int[] { 3, 9, 7, 2, 1, 5, 6, 4, 8 };
    Tree<Integer> bst = new BinarySearchTree<Integer>();
    assert bst.isEmpty();
    for (int key : a)
      bst.insert(key);
    /**       3
     *      /   \
     *     2     9
     *    /     /
     *   1     7
     *       /   \
     *      5     8
     *     / \
     *    4   6
     */
    assert !bst.isEmpty();
    assert bst.size() == a.length;
    for (int key : a)
      assert bst.contains(key); 
    System.out.println(bst.contains(10));
  }
}

interface Tree<T extends Comparable<T>> {
  void insert(T key);
  default void remove(T key) {
    throw new UnsupportedOperationException();
  }
  boolean contains(T key);
  int size();
  default boolean isEmpty() {
    return size() == 0;
  }
}
