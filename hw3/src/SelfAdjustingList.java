/**
 * hw3: Problem 5 starter code.
 */

public class SelfAdjustingList<T> extends DoublyLinkedList<T> {

  // TODO: New code goes here.
  
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
