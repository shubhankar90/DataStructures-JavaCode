import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;



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
    else if (key%2 ==0)
      root = new Node(key, root, null);
    else
      root = new Node(key, null, root);
  }
  
  public String toString1(){
	  String res = "";
	  for (Integer item: levelOrderNodes(root))
		  System.out.println(item);
	  return "";
  }
  
  public ArrayList<Integer> levelOrderNodes(Node root) {
		Queue<Node> q = new LinkedList<Node>();
		int levelNodes = 0;
		ArrayList<Integer> retList = new ArrayList(); 
		if (root == null)
			return retList;
		q.add(root);
		while (!q.isEmpty()) {
			levelNodes = q.size();
			while (levelNodes > 0) {
				Node n = (Node) q.remove();
				retList.add(n.data);
				if (n.left != null)
					q.add(n.left);
				if (n.right != null)
					q.add(n.right);
				levelNodes--;
			}
//			System.out.println("");
		}
		return retList;
	}
  
  public void pruneLeaves(){
	  pruneLeavesHelper(root);
  }
  
  public Node pruneLeavesHelper(Node root) {
	  if (root==null)
		  return root;
	  else{
		  if (root.isLeaf()){
			  n--;
			  root = null;
			  
		  }else {
			  root.left = pruneLeavesHelper(root.left);
			  root.right = pruneLeavesHelper(root.right);
		  }
		  return root; 
	  }
	  
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
  
  public static void main(String... args) {
    int[] a = new int[] { 3, 9, 7, 2, 1, 5, 6, 4, 8 };
    BinaryTree tr = new BinaryTree();
    assert tr.isEmpty();
    for (int key : a)
      tr.insert(key);
    assert tr.size() == a.length;
    assert !tr.root.isLeaf();
    for (int key : a)
      assert tr.contains(key);
    try { 
      tr.remove(3);
    }
    catch (UnsupportedOperationException e) {
    }
    tr.toString1();
    tr.pruneLeaves();
    tr.toString1();
    tr.pruneLeaves();
    tr.toString1();
    BinaryTree tr1 = new BinaryTree();   
    assert (tr1.isEmpty());
    tr1.pruneLeaves();
    assert (tr1.isEmpty());
    for (int key : new int[] { 7,9,4 })
    	tr1.insert(key);
    tr1.pruneLeaves();
    
   
    assert (1==tr1.size());
    tr1.pruneLeaves();
    
    
    assert (0==tr1.size());
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

