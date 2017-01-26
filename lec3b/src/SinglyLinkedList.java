/**
 * Here is the List interface and SinglyLinkedList class from
 * Lecture 3a. Do the following:
 * 
 * (1) Add appropriate Javadoc style comments to all methods.
 * (2) Implement the remove() method.
 * (3) Implement the toString() method.
 * (4) Turn List into a generic interface and SinglyLinkedList into
 *     a generic class so that the structure can be used to hold
 *     any type T of data.
 * (5) Test thoroughly in main() with a variety of data types.
 *
 * We affirm that all members of our team contributed to this solution.
 * @author red6
 * @author Walter Crutchfield
 */

public class SinglyLinkedList<T> implements List<T> {

	/**
	 * Node is a nested class that is used hold a piece of data
	 * for an element in the list and the reference to the next
	 * node in the list
	 */
	class Node {
		T data; // data to be stored
		Node next; // reference to next node in the list

		Node(T data) {
			this(data, null);
		}

		Node(T data, Node next) {
			this.data = data;
			this.next = next;
		}
	}

	Node head; // head of the list, starts the linkage
	int n; // stores the length of the list

	/**
	 * This method adds the object x to the end of the list and
	 * consequently increments the size n
	 */
	public void add(T x) {
		n++;
		if (head == null) 
			head = new Node(x);
		else {
			Node p = head;
			while (p.next != null) // walks thru the list to find the last object
				p = p.next;
			p.next = new Node(x); // links new node made from x to last node
		}
	}

	/**
	 * Removes the node of the list at index i and returns its data and 
	 * reroutes its linkage appropriately
	 */
	public T remove(int i) {
		if (i < 0 || i >= size())
			throw new IndexOutOfBoundsException();
		n--;
		Node p = head;
		if (i == 0) {
			head = p.next;
			return p.data;
		}
		while (i > 1) { // gets to ith node in the list
			p = p.next;
			i--;
		}
		T removedElement = p.next.data;
		// following if-else reroutes reference to next node in the linkage
		if (p.next.next == null)
			p.next = null;
		else
			p.next = p.next.next;
		return removedElement;

	}

	/**
	 * Returns the data of the ith node in the list
	 */
	public T get(int i) {
		if (i < 0 || i >= size())
			throw new IndexOutOfBoundsException();
		Node p = head;
		while (i > 0) { // gets to ith node in list
			p = p.next;
			i--;
		}
		return p.data;
	}

	/**
	 * Returns true iff the data x is present somewhere in the list
	 */
	public boolean contains(T x) {
		Node p = head;
		while (p != null) // while there is still a node
			if (p.data == x) // if data x is found immediately return true
				return true;
			else
				p = p.next;
		return false;
	}

	/**
	 * Returns the number of nodes in the list
	 */
	public int size() {
		return n;
	}

	/**
	 * Returns true iff there is no data stored in the list
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns a string representation of the list
	 */
	public String toString() {
		if (head == null)
			return "()";
		else {
			String retVal = "";
			Node p = head;
			retVal = retVal+p.data+" ";
			while (p.next != null) { // walks thru list
				p = p.next;
				retVal = retVal+p.data+" ";
			}
			return "("+retVal.trim()+")"; 
		}
	}

	/**
	 * Tests
	 */
	public static void main(String... args) {
		//Testing for integer linked list
		List<Integer> xs = new SinglyLinkedList<Integer>();
		assert "()".equals(xs.toString());
		System.out.println("()".equals(xs.toString()));
		int[] a = new int[] { 7, 4, 6, 9, 2 };
		for (int x : a)
			xs.add(x);
		assert "(7 4 6 9 2)".equals(xs.toString());
		System.out.println(xs.toString());
		for (int x : a){
			assert xs.contains(x);
			System.out.println(xs.contains(x));
		}

		for (int i = 0; i < xs.size(); i++)
			assert a[i] == xs.get(i);
		assert "(7 4 6 9 2)".equals(xs.toString());
		System.out.println(xs.toString());
		xs.remove(3);
		System.out.println(xs.toString());
		assert "(7 4 6 2)".equals(xs.toString());
		while (!xs.isEmpty()){
			xs.remove(0);
			System.out.println(xs.size());
			System.out.println(xs.toString());
		}

		assert "()".equals(xs.toString());

		//Testing for String linked list
		List<String> xString = new SinglyLinkedList<String>();
		assert "()".equals(xString.toString());
		System.out.println("()".equals(xString.toString()));
		String[] aString = new String[] { "7", "4", "6", "9", "2" };
		for (String x : aString)
			xString.add(x);
		assert "(7 4 6 9 2)".equals(xString.toString());
		System.out.println(xString.toString());
		for (String x : aString){
			assert xString.contains(x);
			System.out.println(xString.contains(x));
		}

		for (int i = 0; i < xString.size(); i++)
			assert aString[i] == xString.get(i);
		assert "(7 4 6 9 2)".equals(xString.toString());
		System.out.println(xString.toString());
		xString.remove(3);
		System.out.println(xString.toString());
		assert "(7 4 6 2)".equals(xString.toString());
		while (!xString.isEmpty()){
			xString.remove(0);
			System.out.println(xString.size());
			System.out.println(xString.toString());
		}

		assert "()".equals(xString.toString());
	}
}

/**
 * Interface for SinglyLinkedList to implement
 */
interface List<T> {
	void add(T x);
	T remove(int i);
	T get(int i);
	boolean contains(T x);
	int size();
	boolean isEmpty();
}
