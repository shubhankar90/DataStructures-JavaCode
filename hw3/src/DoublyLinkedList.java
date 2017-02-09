/**
 * hw3: Problem 1 starter code.
 * 
 * Hallmarks of a DoublyLinkedList:
 * - headnode (also called a dummy node)
 * - backward pointers to the previous node in the list
 * - circularity: last node points forward to the headnode and the
 *                headnode points backward to the last node
 */

import java.util.Iterator;

public class DoublyLinkedList<T> implements List<T> {

  /**
   * Node is a pair containing a data field and a pointers to
   * the previous and next nodes in the list.
   */
  class Node {
    T data;
    Node next, prev;

    Node(T data) {
      this(data, null, null);
    }

    Node(T data, Node prev, Node next) {
      this.data = data;
      this.prev = prev;
      this.next = next;
    }
  }

  Node head;  // always points to the headnode for this list
  int n;      // the number of nodes in this list, initially 0
  

  /**
   * Creates the empty list. 
   */
  public DoublyLinkedList() {
    // TODO: Create the headnode.
    // Note that the prev and next fields in the headnode should 
    // point back to the headnode.
	  head = new Node(null);
	  head.next = head;
	  head.prev = head;
	  
  }

  /**
   * Inserts the value x at the end of this list. 
   */
  public void add(T x) {  
    // TODO: This must run in O(1) time.
	  n++;
	  Node last = head.prev;
	  Node curr =
	  new Node(x, last, head);
	  last.next = curr;
	  head.prev = curr;
  }

  /**
   * Removes the element at index i from this list.
   * @return the data in the removed node.
   * @throw IndexOutOfBoundsException iff i is out of range for this list.
   */
  public T remove(int i) {
    if (i < 0 || i >= size())
      throw new IndexOutOfBoundsException();
    // TODO: Don't forget to skip over the headnode.
    n--;
    Node prev = head;
    for (int j=0; j<i; j++){
    	prev = prev.next;
    }
    T remData = prev.next.data;
    prev.next = prev.next.next;
    prev.next.prev = prev; 
    return remData;
  }
  
  /**
   * Returns the i-th element from this list, where i is a zero-based index.
   * @throw IndexOutOfBoundsException iff i is out of range for this list.
   */
  public T get(int i) {
    if (i < 0 || i >= size())
      throw new IndexOutOfBoundsException();
    // TODO: Don't forget to skip over the headnode.
    Node curr = head.next;
    for (int j=0; j<i; j++){
    	curr = curr.next;
    }
    return curr.data;
  }

  /**
   * Returns true iff the value x appears somewhere in this list.
   */
  public boolean contains(T x) {
    // TODO: Don't forget to skip over the headnode.
	Node curr = head;
	for (int j = 0; j < size(); j++) {
		curr = curr.next;
		if (curr.data==x)
			return true;
	}
    return false;
  }

  /**
   * Returns the number of elements in this list.
   */
  public int size() {
    return n;
  }

  /**
   * Returns an iterator for this list.
   */
  public Iterator<T> iterator() {
    return new Iterator<T>() {
    	Node curr = head;
    	
      public boolean hasNext() {
        if (curr.next.data!=null)
        	return true;
        return false;
      }

      public T next() {
        // TODO	
    	  curr = curr.next;
        return curr.data;
      }
      
      public void remove() {
	// TODO: This must run in O(1) time.
    	n--;
    	Node prev = curr.prev;
    	prev.next = prev.next.next;
	    prev.next.prev = prev; 
	    return;
      }
    };
  }

  /**
   * Returns a string representing this list (resembling a Racket list).
   */
  public String toString() {
    if (isEmpty())
      return "()";    
    Iterator<T> it = iterator();
    StringBuilder ans = new StringBuilder("(").append(it.next());
    while (it.hasNext())
      ans.append(" ").append(it.next());
    return ans.append(")").toString();
  }

  /**
   * Simple testing to get you started. Add more tests of your own!
   */
  public static void main(String... args) {
    DoublyLinkedList<Integer> xs = new DoublyLinkedList<>();
    int[] a = new int[] { 4, 3, 6, 5, 7, 8 };
    for (int x : a)
      xs.add(x);
    assert 6 == xs.size();
    for (int i = 0; i < a.length; i++)
      assert xs.get(i) == a[i];
    assert !xs.contains(null);
    for (int x : a)
      assert xs.contains(x);
    assert "(4 3 6 5 7 8)".equals(xs.toString());
    assert xs.remove(0) == 4;
    assert xs.remove(1) == 6;
    assert 4 == xs.size();
    assert "(3 5 7 8)".equals(xs.toString());
    while (!xs.isEmpty())
      xs.remove(xs.size() - 1);
    assert 0 == xs.size();
    for (int x : a)
      xs.add(x);
    for (int x : xs)
      assert xs.contains(x);
    Iterator<Integer> it = xs.iterator();
    while (it.hasNext())
      if (it.next() % 2 == 0)
        it.remove();
    assert 3 == xs.size();
    assert "(3 5 7)".equals(xs.toString());
    System.out.println("All tests passed...");
  }
}

/**
 * If you want to call yourself a List, then implement this interface:
 */
interface List<T> extends Iterable<T> {
  void add(T x);  // simple add
  T remove(int i);
  T get(int i);
  boolean contains(T x);
  int size();
  default boolean isEmpty() {
    return size() == 0;
  }
}
