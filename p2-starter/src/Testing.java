import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit tests for all TODO methods.
 */

public class Testing {

	/**
	 * testing onBoard method
	 */
	@Test
	public void testOnBoard() {
		assertFalse(new Coord(3, 4).onBoard(4));
		assertTrue(new Coord(3, 4).onBoard(5));
		assertFalse(new Coord(-1, 4).onBoard(5));
		assertFalse(new Coord(3, -1).onBoard(5));
	}

	/**
	 * testing getNeighbors method
	 */
	@Test
	public void testGetNeighbors() {
		Coord testCoord = new Coord(3, 4);
		assertTrue(testCoord.neighbors(5).contains(testCoord.right()));
		assertTrue(testCoord.neighbors(5).contains(testCoord.left()));
		assertTrue(testCoord.neighbors(5).contains(testCoord.up()));
		assertFalse(testCoord.neighbors(5).contains(testCoord.down()));
	}

	/**
	 * testing hashCode method
	 */
	@Test
	public void testHashCode() {
		assertEquals(67, new Coord(2, 3).hashCode());
		assertEquals(0, new Coord(0, 0).hashCode());
		assertEquals(165, new Coord(5, 5).hashCode());
	}

	/**
	 * testing fullyFlooded, flood1 and Flood methods. Flooding is tested by
	 * checking if correct number of tiles are on the inside
	 */
	@Test
	public void testFullyFloodedAndFlood() {
		int size = 4;
		WaterColor[][] customColors = new WaterColor[size][size];
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				if (x > y)
					customColors[x][y] = WaterColor.BLUE;
				else
					customColors[x][y] = WaterColor.RED;

			}
		}
		Board customBoard = new Board(customColors, size);
		assertEquals(10, customBoard.getInsideCount());
		assertEquals(false, customBoard.fullyFlooded());

		customColors = new WaterColor[size][size];
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {

				customColors[x][y] = WaterColor.BLUE;

			}
		}
		Board customBoard2 = new Board(customColors, size);
		assertEquals(16, customBoard2.getInsideCount());
		assertEquals(true, customBoard2.fullyFlooded());

	}

	/**
	 * testing suggest method
	 */
	@Test
	public void testSuggest() {
		int size = 4;
		WaterColor[][] customColors = new WaterColor[size][size];
		customColors[0][0] = WaterColor.BLUE;
		customColors[0][1] = WaterColor.CYAN;
		customColors[0][2] = WaterColor.CYAN;
		customColors[0][3] = WaterColor.RED;
		customColors[1][0] = WaterColor.RED;
		customColors[1][1] = WaterColor.RED;
		customColors[1][2] = WaterColor.CYAN;
		customColors[1][3] = WaterColor.RED;
		customColors[2][0] = WaterColor.YELLOW;
		customColors[2][1] = WaterColor.YELLOW;
		customColors[2][2] = WaterColor.YELLOW;
		customColors[2][3] = WaterColor.YELLOW;
		Board customBoard2 = new Board(customColors, size);

		assertEquals(2, customBoard2.searchReward(WaterColor.RED));
		assertEquals(3, customBoard2.searchReward(WaterColor.CYAN));
		assertEquals(WaterColor.CYAN, customBoard2.suggest());

	}

}