/**
 * Tree interface and BinaryTree class from lec4b.
 */

public class BinaryTree implements Tree {
  
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
  
  public void insert(int key) {
    n++;
    if (root == null)
      root = new Node(key);
    else if (root.left == null)
      root.left = new Node(key);
    else if (root.right == null)
      root.right = new Node(key);
    else if (Math.random() < 0.5)
      root = new Node(key, root, null);
    else
      root = new Node(key, null, root);
  }
  
  public boolean contains(int key) {
    return containsHelper(key, root);
  }
  
  private boolean containsHelper(int key, Node p) {
    if (p == null)
      return false;
    if (p.data == key)
      return true;
    return containsHelper(key, p.left) || containsHelper(key, p.right);
  }
  
  public int size() {
    return n;
  }
//  
//  public int getHeight(BinaryTree tree) {
//	  if ( tree.root==null)
//		  return 0;
//	  return max(getHeight(tree.left),getHeight(tree.right));
//  }
  
  public int getHeight(Node root){
	    if(root == null){
	        return 0;
	    }
	    return Math.max(getHeight(root.left), getHeight(root.right)) + 1;
	}
  
  public static void main(String... args) {
    int[] a = new int[] { 3, 9, 7, 2, 1, 5, 6, 4, 8 };
    BinaryTree tr = new BinaryTree();
    assert tr.isEmpty();
    for (int key : a)
      tr.insert(key);
    System.out.println(tr.getHeight(tr.root));
    assert tr.size() == a.length;
    assert !tr.root.isLeaf();
    for (int key : a)
      assert tr.contains(key);
    try { 
      tr.remove(3);
    }
    catch (UnsupportedOperationException e) {
    }  
    System.out.println("Passed all tests...");
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

