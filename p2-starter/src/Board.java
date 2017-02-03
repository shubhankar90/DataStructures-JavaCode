import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.ArrayDeque;

/**
 * A Board represents the current state of the game. Boards know their dimension, 
 * the collection of tiles that are inside the current flooded region, and those tiles 
 * that are on the outside.
 * 
 * @author <put your name here>
 */

public class Board {
  private Map<Coord, Tile> inside, outside, perimeter ;   
  private int size;
  
  /**
   * Constructs a square game board of the given size, initializes the list of 
   * inside tiles to include just the tile in the upper left corner, and puts 
   * all the other tiles in the outside list.
   */
  public Board(int size) {
    // A tile is either inside or outside the current flooded region.
    inside = new HashMap<>();
    outside = new HashMap<>();
    perimeter = new HashMap<>();
    this.size = size;
    for (int y = 0; y < size; y++)
      for (int x = 0; x < size; x++) {
        Coord coord = new Coord(x, y);
        outside.put(coord, new Tile(coord));
        
      }
    // Move the corner tile into the flooded region and run flood on its color.
    Tile corner = outside.remove(Coord.ORIGIN);
    inside.put(Coord.ORIGIN, corner);
    perimeter.put(Coord.ORIGIN, corner);
    flood1(corner.getColor());
    
    
  }
  
  /**
   * Returns the tile at the specified coordinate.
   */ 
  public Tile get(Coord coord) {
    if (outside.containsKey(coord))
      return outside.get(coord);
    return inside.get(coord);
  }
  
  /**
   * Returns the size of this board.
   */
  public int getSize() {
    return size;
  }
  
  /**
   * TODO
   * 
   * Returns true iff all tiles on the board have the same color.
   */
  public boolean fullyFlooded() {
	  if (outside.size()==0)
		  return true;
    return false;
  }
  
  /**
   * TODO
   * 
   * Updates this board by changing the color of the current flood region 
   * and extending its reach.
   */
  public void flood(WaterColor color) {

	  for (Map.Entry<Coord, Tile> entry : inside.entrySet())
	  {
		  
		  entry.getValue().setColor(color);
		  
	  }
	  Queue<Coord> flooded = new ArrayDeque<Coord>();
	  	flooded.addAll(inside.keySet());
	  	while(!flooded.isEmpty()){
	  		Coord entry = flooded.remove();
	  		for (Coord neighbor : entry.neighbors(getSize())) {

				if (outside.containsKey(neighbor)) {

					if (get(neighbor).getColor() == color) {
	
						if (!flooded.contains(neighbor)) {
							
							flooded.add(neighbor);	
							
						}

						inside.put(neighbor, get(neighbor));
						outside.remove(neighbor);

					}
				}
			}
	  	}
	  
	  
   }
  
  public int searchReward(WaterColor color) {
	  	int countAdditions=0;
	  	Queue<Coord> flooded = new ArrayDeque<Coord>();
	  	flooded.addAll(inside.keySet());

	  	Map<Coord, Tile> fakeOutside = new HashMap<>();

	  	fakeOutside.putAll(outside);

	  	while(!flooded.isEmpty()){
	  		Coord entry = flooded.remove();
	  		for (Coord neighbor : entry.neighbors(getSize())) {

				if (fakeOutside.containsKey(neighbor)) {

					if (get(neighbor).getColor() == color) {
	
						if (!flooded.contains(neighbor)) {
							
							flooded.add(neighbor);
							countAdditions++;
							
						}

						fakeOutside.remove(neighbor);
					}
				}
			}
	  	}
	  
	  return countAdditions;
   }
  
  

  
  
  /**
   * TODO
   * 
   * Explore a variety of algorithms for handling a flood. Use the same interface 
   * as for flood above, but add an index so your methods are named flood1,
   * flood2, ..., and so on. Then, use the batchTest() tool in Driver to run game
   * simulations and then display a graph showing the run times of all your different 
   * flood functions. Do not delete your flood code attempts. For each variety of 
   * flood, including the one above that you eventually settle on, write a comment
   * that describes your algorithm in English. For those implementations that you
   * abandon, state your reasons.
  */
  public void flood1(WaterColor color) {
	  for (Map.Entry<Coord, Tile> entry : inside.entrySet())
	  {
		  
		  entry.getValue().setColor(color);
		  
	  }
	  Queue<Coord> flooded = new ArrayDeque<Coord>();
	  	flooded.addAll(perimeter.keySet());
	  	while(!flooded.isEmpty()){
	  		Coord entry = flooded.remove();
	  		for (Coord neighbor : entry.neighbors(getSize())) {

				if (outside.containsKey(neighbor)) {

					if (get(neighbor).getColor() == color) {
	
						if (!flooded.contains(neighbor)) {
							
							flooded.add(neighbor);
							for (Coord secNeighbor : neighbor.neighbors(getSize())){
								if (outside.containsKey(secNeighbor)){
									perimeter.put(neighbor, get(neighbor));
									break;
								}
									
							}
							
						}

						inside.put(neighbor, get(neighbor));
						outside.remove(neighbor);

					}
				}
			}
	  		perimeter.remove(entry);
	  		for (Coord secNeighbor : entry.neighbors(getSize())){
				if (outside.containsKey(secNeighbor)){
					perimeter.put(entry, get(entry));
					break;
				}
					
			}
	  	}

	  }
	  

  

   
  
  /**
   * TODO
   * 
   * Returns the "best" GameColor for the next move. 
   * 
   * Modify this comment to describe your algorithm. Possible strategies to pursue 
   * include maximizing the number of tiles in the current flooded region, or maximizing
   * the size of the perimeter of the current flooded region.
   */
  public WaterColor suggest() {
    WaterColor cornerColor = inside.get(Coord.ORIGIN).getColor();
    int highestReward = 0;
    int currReward = 0;
    WaterColor theChosenOne = WaterColor.pickOneExcept(cornerColor);
	  for (WaterColor color: WaterColor.values()){
		  currReward = searchReward(color);
		 
		  if (currReward>highestReward){
			  highestReward = currReward;
			  theChosenOne = color;
		  }
		  
	  }
    return theChosenOne;
  }
  
  /**
   * Returns a string representation of this board. Tiles are given as their
   * color names, with those inside the flooded region written in uppercase.
   */ 
  public String toString() {
    StringBuilder ans = new StringBuilder();
    for (int y = 0; y < size; y++) {
      for (int x = 0; x < size; x++) {
        Coord curr = new Coord(x, y);
        WaterColor color = get(curr).getColor();
        ans.append(inside.containsKey(curr) ? color.toString().toUpperCase() : color);
        ans.append("\t");
      }
      ans.append("\n");
    }
    return ans.toString();
  }
  
  /**
   * Simple testing.
   */
  public static void main(String... args) {
    // Print out boards of size 1, 2, ..., 5
    int n = 5;
    for (int size = 1; size <= n; size++) {
      Board someBoard = new Board(size);
      System.out.println("wd");
      System.out.println(someBoard);
    }
  }
}






