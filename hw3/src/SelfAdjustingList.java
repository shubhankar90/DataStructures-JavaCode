import java.util.Iterator;



/**
 * hw3: Problem 5 starter code.
 */

public class SelfAdjustingList<T> extends DoublyLinkedList<T> {

  // TODO: New code goes here.
	  /**
	   * Inserts the value x at the beginning of this list. 
	   */
	  public void add(T x) {  
	    // TODO: This must run in O(1) time.
		  n++;
		  Node curr = new Node(x, head, head.next);
		  head.next = curr;
		  curr.next.prev = curr;
	  }
	  
	  public T find(int i){
		  if (i < 0 || i >= size())
		      throw new IndexOutOfBoundsException();
		    // TODO: Don't forget to skip over the headnode.
		    Node curr = head.next;
		    for (int j=0; j<i; j++){
		    	curr = curr.next;
		    }
		    curr.prev.next = curr.next;
			curr.next.prev = curr.prev;
			curr.prev = head;
			curr.next = head.next;
			head.next = curr;
			curr.next.prev = curr;
		    return curr.data;
	  }

  /**
   * Simple testing to get you started. Add more tests of your own!
   */
  public static void main(String... args) {
    SelfAdjustingList<Integer> xs = new SelfAdjustingList<>();
    for (int x = 1; x <= 10; x++)
      xs.add(x);
    for (int i = 0; i < xs.size(); i++)
      assert 10 - i == xs.get(i);
    for (int i = 0; i < xs.size(); i++) {
      int x = xs.get(i);
      assert x == xs.find(i);
    }
    for (int i = 0; i < xs.size(); i++) {
      int x = xs.find(i);
      assert x == xs.get(0);
    }
    System.out.println("All tests passed...");
  }
}
