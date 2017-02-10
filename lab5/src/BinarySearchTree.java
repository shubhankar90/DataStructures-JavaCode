/**
 * Starter code for lab5. This is an implementation of BinarySearchTree 
 * for int data.
 * 
 * Implement the remove() method using the algorithm described by your AI.
 *
 * @author Shubhankar Mitra
 */

public class BinarySearchTree implements Tree {

  class Node {
    int data;
    Node left, right;

    Node(int key) {
      this(key, null, null);
    }

    Node(int data, Node left, Node right) {
      this.data = data;
      this.left = left;
      this.right = right;
    }

    boolean isLeaf() {
      return left == null && right == null;
    }
  }

  Node root;
  int n;

  /**
   * TODO
   * 
   * Removes the key from this tree. Must run in O(h) time, where h
   * is the height of the tree.
   */
  public void remove(int key) {
      Node prev = root;
	  Node p = root;
	  while (p.data!=key){
		  if (key>p.data){
			  prev = p;
			  p = p.right;
		  }
		  else{
			  prev = p;
			  p = p.left;
		  }
	  }
	  if ((p.right==null)&(p.left==null)){
		  if (prev.right.data==key)
			  prev.right = null;
		  else
			  prev.left = null;
			  
	  }else if ((p.right==null)&(p.left!=null)){
		  if (prev.right.data==key)
			  prev.right = p.left;
		  else
			  prev.left = p.left;
			  
	  }else if ((p.right!=null)&(p.left==null)){
		  if (prev.right.data==key)
			  prev.right = p.right;
		  else
			  prev.left = p.right;
			  
	  }else if ((p.right!=null)&(p.left!=null)){
		  Node temp = p.right;
		  Node prevMaxLeft = prevMaxLeft(p.left);
		  System.out.println(prevMaxLeft.data);
		  if (prev.right.data==key){
			  prev.right = prevMaxLeft;
			  prevMaxLeft.right = temp; 
		  }else{
			  prev.left = prevMaxLeft;
			  prevMaxLeft.right = temp; 
		  }
			  
	  }
	  
	  
  }
  
  public Node prevMaxLeft(Node p){
	  Node prev = p;
	  
	  while (p.right!=null){
		  prev = p;
		  p = p.right;
	  }
	  return prev;
  }
  
  
	
  
  /**
   * Inserts the key into this tree. Runs in O(h) time, where h is
   * the height of the tree.
   */
  public void insert(int key) {
    n++;
    root = insertHelper(key, root);
  }

  private Node insertHelper(int key, Node p) {
    if (p == null) 
      p = new Node(key);
    else if (key < p.data)
      p.left = insertHelper(key, p.left);
    else 
      // if keys are unique, it must be the case that key > p.data
      p.right = insertHelper(key, p.right);
    return p;
  }

  /**
   * Returns true iff key is in this tree. Runs in O(h) time, where h is
   * the height of the tree.
   */
  public boolean contains(int key) {
    return containsHelper(key, root);
  }

  private boolean containsHelper(int key, Node p) {
    if (p == null)
      return false;
    if (key == p.data)
      return true;
    if (key < p.data)
      return containsHelper(key, p.left);
    return containsHelper(key, p.right);
  }

  /**
   * Returns the number of keys in this tree.
   */
  public int size() {
    return n;
  }

  /**
   * Testing.
   */
  public static void main(String... args) {
    int[] a = new int[] { 3, 9, 7, 2, 1, 5, 6, 4, 8 };
    int[] b = new int[] { 1, 6, 4, 5, 8, 9, 7, 2 };
    Tree bst = new BinarySearchTree();
    assert bst.isEmpty();
    for (int key : a)
      bst.insert(key);
    assert bst.size() == a.length;
    for (int key : a)
      assert bst.contains(key);
    bst.remove(3);
    for (int key : b)
      assert bst.contains(key);
    assert !bst.contains(3);
    int n = bst.size();
    for (int key : b) {
      assert bst.contains(key);
      bst.remove(key);
      assert !bst.contains(key);
      n--;
      assert n == bst.size();
    }
    assert bst.isEmpty();
    System.out.println("Passed all the basic tests...");

    /**
     * TODO: As a challenge, arrange things so that attempts to remove
     * key that are not in the tree are simply ignored (and do no harm).
     */
    for (int key : a)
      bst.insert(key);
    n = bst.size();
    for (int key : a) {
      bst.remove(-key);
      assert n == bst.size();
    }
    System.out.println("Passed challenge tests...");
  }
}

interface Tree {
  void insert(int key);
  default void remove(int key) {
    throw new UnsupportedOperationException();
  }
  boolean contains(int key);
  int size();
  default boolean isEmpty() {
    return size() == 0;
  }
}

