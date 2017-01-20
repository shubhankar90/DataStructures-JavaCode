import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.util.Random;
//import java.lang.Math;

/**
 * @author (put your name here)
 * 
 * A ColorTable represents a dictionary of frequency counts, keyed on Color.
 * It is a simplification of Map<Color, Integer>. The size of the key space
 * can be reduced by limiting each Color to a certain number of bits per channel.
 */

/**
 * TODO
 * 
 * Implement this class, including whatever data members you need and all of the
 * public methods below. You may create any number of private methods if you find
 * them to be helpful. Replace all TODO comments with appropriate javadoc style 
 * comments. Be sure to document all data fields and helper methods you define.
 */

public class ColorTable {
  /**
   * Counts the number of collisions during an operation.
   */
  private static int numCollisions = 0;
  
  private long[][] hashArray;
  
  private int bitsPerChannel;
  
  private int initialCapacity;
  
  private int collisionStrategy;
  
  private double rehashThreshold;

  /**
   * Returns the number of collisions that occurred during the most recent get or
   * put operation.
   */
  public static int getNumCollisions() {
    return numCollisions;
  }

  /**
   * TODO
   * 
   * Constructs a color table with a starting capacity of initialCapacity. Keys in
   * the color key space are truncated to bitsPerChannel bits. The collision resolution
   * strategy is specified by passing either Constants.LINEAR or Constants.QUADRATIC for
   * the collisionStrategy parameter. The rehashThrehold specifies the maximum tolerable load 
   * factor before triggering a rehash.
   * 
   * @throws RuntimeException if initialCapacity is not in the range [1..Constants.MAX_CAPACITY]
   * @throws RuntimeException if bitsPerChannel is not in the range [1..8]
   * @throws RuntimeException if collisionStrategy is not one of Constants.LINEAR or Constants.QUADRATIC
   * @throws RuntimeException if rehashThreshold is not in the range (0.0..1.0] for a
   *                             linear strategy or (0.0..0.5) for a quadratic strategy
   */
  public ColorTable(int initialCapacity, int bitsPerChannel, int collisionStrategy, double rehashThreshold) { 
	  //int black = Util.pack(Color.BLACK, 6);
	  //System.out.println(black);
	  this.hashArray = new long[initialCapacity][2];
	  this.bitsPerChannel = bitsPerChannel;
	  this.initialCapacity = initialCapacity;
	  this.collisionStrategy = collisionStrategy;
	  this.rehashThreshold = rehashThreshold;
	  for (int i=0; i<initialCapacity; i++){
		  hashArray[i][0] = -1;
		  hashArray[i][1] = -1;
	  }
	  
	  
  }

  /**
   * TODO
   * 
   * Returns the number of bits per channel used by the colors in this table.
   */
  public int getBitsPerChannel() {
    return bitsPerChannel;
  }

  /**
   * TODO
   * 
   * Returns the frequency count associated with color. Note that colors not
   * explicitly represented in the table are assumed to be present with a
   * count of zero. Uses Util.pack() as the hash function.
   */
  public long get(Color color) {
	  int packedColor = Util.pack(color, bitsPerChannel);
	  int finIndex = lookup(packedColor);
	  if ((hashArray[finIndex][1] == -1) || (numCollisions>hashArray.length)){
		 return 0;
	  }else{
		 return hashArray[finIndex][1];
	  }
	  
  }

  
  private int lookup(int key) {
	  int initIndex = hash(key);
	  int collCount = 0;
	  int finIndex = initIndex;
	  if ((hashArray[initIndex][0] == key) || (hashArray[finIndex][0] == -1)){
		  finIndex = initIndex;
	  }else{
		  while ((hashArray[finIndex][0] != -1) & (hashArray[finIndex][0] != key)){
			  collCount++;
			  if (collisionStrategy == Constants.LINEAR){
				  finIndex = (initIndex + collCount)%hashArray.length;
			  }else{
				  finIndex = (initIndex + (collCount*collCount))%hashArray.length;
			  }
			  
		  } 
	  }
	  numCollisions = collCount;
	  return finIndex;
  }
  
  private int hash(int key) {
	  return key%hashArray.length;
  }
  /**
   * TODO
   * 
   * Associates the count with the color in this table. Do nothing if count is less than
   * or equal to zero. Uses Util.pack() as the hash function.
   */
  public void put(Color color, long count) {
	  if (count>0){
		  int packedColor = Util.pack(color, bitsPerChannel);
		  int finIndex = lookup(packedColor);
		  hashArray[finIndex][0] = packedColor;
		  hashArray[finIndex][1] = count;
	  }
	  if (getLoadFactor()>rehashThreshold){
		  rehash();
	  }
	  
	  
  }

  /**
   * TODO
   * 
   * Increments the frequency count associated with color. Note that colors not
   * explicitly represented in the table are assumed to be present with a
   * count of zero.
   */
  public void increment(Color color) {
	  int packedColor = Util.pack(color, bitsPerChannel);
	  int finIndex = lookup(packedColor);
	  if (hashArray[finIndex][0] == -1){		  
		  hashArray[finIndex][0] = packedColor;
		  hashArray[finIndex][1] = 1;
	  }else{
		  hashArray[finIndex][1]++;  
	  }
	  if (getLoadFactor()>rehashThreshold){
		  rehash();
	  }
  }

  /**
   * TODO
   * 
   * Returns the load factor for this table.
   */
  public double getLoadFactor() {
	  double capacity = getCapacity();
	  double size = getSize();

	  double loadFactor = (size)/capacity;

    return loadFactor;
  }

  /**
   * TODO
   * 
   * Returns the size of the internal array representing this table.
   */
  public int getCapacity() {
    return hashArray.length;
  }

  /**
   * TODO
   * 
   * Returns the number of key/value associations in this table.
   */
  public int getSize() {
	  int count = 0;
	  for (int i=0; i<hashArray.length; i++){
		  if (hashArray[i][0] != -1){
			  count++;  
		  }
	  }
    return count;
  }

  /**
   * TODO
   * 
   * Returns true iff this table is empty.
   */
  public boolean isEmpty() {
	  int count = 0;
	  for (int i=0; i<hashArray.length; i++){
		  if (hashArray[i][0] != -1){
			  count++;  
		  }
	  }
	  if (count==0){
		  return true;
	  }
	  else{
		  return false;
	  }
    
  }

  /**
   * TODO
   * 
   * Increases the size of the array to the smallest prime greater than double the 
   * current size that is of the form 4j + 3, and then moves all the key/value 
   * associations into the new array. 
   * 
   * Hints: 
   * -- Make use of Util.isPrime().
   * -- Multiplying a positive integer n by 2 could result in a negative number,
   *    corresponding to integer overflow. You should detect this possibility and
   *    crop the new size to Constants.MAX_CAPACITY.
   * 
   * @throws RuntimeException if the table is already at maximum capacity.
   */
  public void rehash() {
		    
	  int Capacity = getCapacity();
	  if (Capacity == Constants.MAX_CAPACITY){
		  throw new RuntimeException();
	  }
	  int newCapacity = findNewSize(Capacity);
	  //System.out.println("NewCap:"+newCapacity);
	  long[][] temp = hashArray;
	  this.hashArray = new long[newCapacity][2];
	  for (int i=0; i<hashArray.length; i++){
		  hashArray[i][0] = -1;
		  hashArray[i][1] = -1;
	  }
      for (int i=0; i<temp.length; i++){
    	  if (temp[i][0] != -1){
    		  int key = (int) temp[i][0];
    		  this.put(Util.unpack(key, bitsPerChannel), temp[i][1]);  
    	  }
    	  
      }
	    	  
	      
  }
  
  public int findNewSize(int currentSize){
	  if (currentSize>1073741793){
		  return Constants.MAX_CAPACITY;
	  }
	  int j = ((2*currentSize)-3)/4;
	  while((!Util.isPrime(4*j+3)) || ((4*j+3)<2*currentSize)){
		  j++;
	  }
	  return (4*j+3);
  }

  /**
   * TODO
   * 
   * Returns an Iterator that marches through each color in the key color space and
   * returns the sequence of frequency counts.
   */
  
  public Iterator iterator() {
	  return new Iterator() {
		  int maxLength = (int)Math.pow(2,3*bitsPerChannel);
		  int pos = 0;
		  public boolean hasNext(){
			  if (pos >= maxLength) {
				  return false;
			  }else{
				  return true;
			  }
		  }
		 public long next() {
			 int capacity = getCapacity();
			 long retValue = 0;
			 if (pos<capacity){
				 if (hashArray[pos][0] == -1) {
					 retValue = 0;
				 }else {
					 retValue = hashArray[pos][1];
				 }
				 
			 }else if ((pos >= capacity) & (pos < maxLength)) {
				 retValue = 0;
			 }else{
				 retValue = 0;
			 }
			 pos++;
			 return retValue;
			 
		 } 
	  };
  }

  /**
   * TODO
   * 
   * Returns a String representation of this table.
   */
  public String toString() {
	  
	  for (int i=0; i<hashArray.length; i++){
		  System.out.println(hashArray[i][0] + ":" + hashArray[i][1]);
	  }
    return "";
  }

  /**
   * TODO
   * 
   * Returns the count in the table at index i in the array representing the table.
   * The sole purpose of this function is to aid in writing the unit tests.
   */
  public long getCountAt(int i) { 
    return hashArray[i][1];
  }

  /**
   * Simple testing.
   */
  public static void main(String[] args) {
    //ColorTable table = new ColorTable(3, 6, Constants.QUADRATIC, .49);
    //int[] data = new int[] { 32960, 4293315, 99011, 296390 };
    //for (int i = 0; i < data.length; i++) 
      //table.increment(new Color(data[i]));
    //System.out.println("capacity: " + table.getCapacity()); // Expected: 7
    //System.out.println("size: " + table.getSize());         // Expected: 3

	
	  ColorTable table = new ColorTable(3, 4, Constants.LINEAR, 0.1);
	  //assertEquals(0, table.getSize());

	  System.out.println(table.getCapacity());
	  System.out.println(table.getLoadFactor());
	  table.put(Color.BLACK, 5);
	  System.out.println(table.getLoadFactor());
	  System.out.println(table.getCapacity());
	  
	    //assertEquals(3, table.getCapacity());
	    //assertEquals(true, table.getLoadFactor() < 0.1);
	
	//System.out.println(table.get(Color.ORANGE));

	//System.out.println(table.get(Color.BLUE));
	
    /* The following automatically calls table.toString().
       Notice that we only include non-zero counts in the String representation.
       
       Expected: [3:2096,2, 5:67632,1, 6:6257,1]
       
       This shows that there are 3 keys in the table. They are at positions 3, 5, and 6.
       Their color codes are 2096, 67632, and 6257. The code 2096 was incremented twice.
       You do not have to mimic this format exactly, but strive for something compact
       and readable.
       */
    //System.out.println(table);  
  }
}
