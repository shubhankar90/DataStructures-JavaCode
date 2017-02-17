
public class Deque<T> extends DoublyLinkedList<T> implements DequeInt<T>{

	@Override
	public void push(T x) {
		n++;
		Node curr = new Node(x, head, head.next);
		head.next = curr;
		curr.next.prev = curr;
		
	}

	@Override
	public T pop() {
		// TODO Auto-generated method stub
		n--;
		T item = head.next.data;
		head.next = head.next.next;
		head.next.prev = head;
		return item;
	}

	@Override
	public void inject(T x) {
		// TODO Auto-generated method stub
		n++;
		Node newLast = new Node(x, head.prev, head);
		newLast.prev.next = newLast;
		head.prev = newLast;
		
		
		
	}

	@Override
	public T eject() {
		// TODO Auto-generated method stub
		n--;
		Node last = head.prev;
		head.prev = last.prev;
		head.prev.next = head;
		return last.data;
	}
	
	public static void main(String... args) {
		Deque<Integer> xs = new Deque<Integer>();
	    int[] a = new int[] { 4, 3, 6, 5, 7, 8 };
	    for (int x : a)
	      xs.push(x);
	    System.out.println(xs.toString());
	    //assert xs.toString()=="(8 7 5 6 3 4)";
	    Deque<Integer> xs2 = new Deque<Integer>();
	    for (int x : a)
	      xs2.inject(x);
	    System.out.println(xs2.toString());
	    //assert xs2.toString()=="(4 3 6 5 7 8)";
	    
	    assert xs2.pop()==4;
	    System.out.println(xs2.toString());
	    //assert xs2.toString()=="(3 6 5 7 8)";
	    
	    assert xs2.eject()==8;
	    System.out.println(xs2.toString());
	    //assert xs2.toString()=="(3 6 5 7)";
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

}