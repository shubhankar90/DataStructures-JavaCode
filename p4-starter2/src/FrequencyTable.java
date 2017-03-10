import java.util.HashMap;

/**
 * TODO: Complete the implementation of this class.
 */

public class FrequencyTable extends HashMap<Character, Integer> {
  /**
   * Constructs an empty table.
   */
	
  public FrequencyTable() {
    super();
  }
    
  /**
   * TODO: Make use of get() and put().
   * 
   * Constructs a table of character counts from the given text string.
   */
  public FrequencyTable(String text) {
	  char ch;
	  for (int i = 0; i < text.length(); i++){
		     ch = text.charAt(i); 
		     put(ch,get(ch)+1);
		    	 
		    //Process char
		}
    
  }
  
  /**
   * TODO
   * 
   * Returns the count associated with the given character. In the case that
   * there is no association of ch in the map, return 0.
   */
  @Override
  public Integer get(Object ch) {
	  return containsKey(ch) ? super.get(ch) : 0;
  }
}
