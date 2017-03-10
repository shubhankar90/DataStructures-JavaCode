import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Queue;

/**
 * TODO: Complete the implementation of this class.
 * 
 * A HuffmanTree represents a variable-length code such that the shorter the bit
 * pattern associated with a character, the more frequently that character
 * appears in the text to be encoded.
 */

public class HuffmanTree {

	class Node {
		protected char key;
		protected int priority;
		protected Node left, right;

		public Node(int priority, char key) {
			this(priority, key, null, null);
		}

		public Node(int priority, Node left, Node right) {
			this(priority, '\0', left, right);
		}

		public Node(int priority, char key, Node left, Node right) {
			this.key = key;
			this.priority = priority;
			this.left = left;
			this.right = right;
		}

		public boolean isLeaf() {
			return left == null && right == null;
		}
	}

	protected Node root;

	/**
	 * TODO
	 * 
	 * Creates a HuffmanTree from the given frequencies of letters in the
	 * alphabet using the algorithm described in lecture.
	 */
	public HuffmanTree(FrequencyTable charFreqs) {
		Comparator<Node> comparator = (x, y) -> {
			/**
			 * TODO: x and y are Nodes x comes before y if x's priority is less
			 * than y's priority
			 */
			return x.priority - y.priority;
		};
		PriorityQueue<Node> forest = new Heap<Node>(comparator);
		for (char ch : charFreqs.keySet()) {
			Node entry = new Node(charFreqs.get(ch), ch);
			forest.insert(entry);
		}
		while (forest.size() > 1) {
			Node n1 = forest.delete();
			Node n2 = forest.delete();
			Node newNode = new Node(n1.priority + n2.priority, n1, n2);
			forest.insert(newNode);
		}
		/**
		 * TODO: Complete the implementation of Huffman's Algorithm. Start by
		 * populating forest with leaves.
		 */
		root = forest.delete();
	}

	/**
	 * TODO
	 * 
	 * Returns the character associated with the prefix of bits.
	 * 
	 * @throws DecodeException
	 *             if bits does not match a character in the tree.
	 */
	public char decodeChar(String bits) {
		Node start = root;
		for (char ch : bits.toCharArray()) {
			if (ch == '0') {
				if (start.isLeaf())
					break;
				if (start.left == null)
					throw new DecodeException(bits);

				start = start.left;
			} else {
				if (start.isLeaf())
					break;
				if (start.right == null)
					throw new DecodeException(bits);
				start = start.right;
			}

		}
		return start.key;
	}

	/**
	 * TODO
	 * 
	 * Returns the bit string associated with the given character. Must search
	 * the tree for a leaf containing the character. Every left turn corresponds
	 * to a 0 in the code. Every right turn corresponds to a 1. This function is
	 * used by CodeBook to populate the map.
	 * 
	 * @throws EncodeException
	 *             if the character does not appear in the tree.
	 */
	public String lookup(char ch) {
		String res = lookupHelper(root, ch, "");
		if (res==null)
			throw new EncodeException(ch);
		else
			return res;
	}

	public String lookupHelper(Node root, char ch, String path) {
		if (root.isLeaf()) {
			if (root.key == ch) {
				return path;
			} else
				return null;
		} else {
			String left = lookupHelper(root.left, ch, path + "0");
			String right = lookupHelper(root.right, ch, path + "1");
			if (left != null)
				return left;
			else if (right != null)
				return right;
			else
				return null;

		}

	}
}
