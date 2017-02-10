import java.util.Iterator;

import DoublyLinkedList.Node;

public class Deque<T> implements DequeInt<T> {

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
	
	DoublyLinkedList<T> queue;
	
	public Deque(){
		queue = new DoublyLinkedList<T>();
	}
	
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void push(T x) {
		n++;
		  Node curr = new Node(x, queue, queue.next);
		  head.next = curr;
		  curr.next.prev = curr;
		
	}

	@Override
	public T pop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void inject(T x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T eject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void add(T x) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T remove(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T get(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean contains(T x) {
		// TODO Auto-generated method stub
		return false;
	}

	

}

/**
 * If you want to call yourself a List, then implement this interface:
 */
interface DequeInt<T> extends List<T> {
	void push(T x); //Insert item x on the front end of the deque.
	T pop(); //Remove the front item from the deque and return it.
	void inject(T x); //Insert item x on the rear end of the deque.
	T eject(); //Remove the rear item from the deque and return it.
	int size();
	default boolean isEmpty() {
		return size() == 0;
  }
}