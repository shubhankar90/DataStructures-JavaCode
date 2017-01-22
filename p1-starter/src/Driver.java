
/**
 * 
 * @author Shubhankar Mitra
 */

import java.awt.Color;

public class Driver {

	private static int numCollisions;
	private static ColorTable table;

	/**
	 * 
	 * 
	 * Return the ColorTable associated with this image, assuming the color key
	 * space is restricted to bitsPerChannel. Increment numCollisions after each
	 * increment.
	 */
	public static ColorTable vectorize(Image image, int bitsPerChannel) {
		int imageHeight = image.getHeight();
		int imageWidth = image.getWidth();
		int initSize = (int) Math.pow(2, 3 * bitsPerChannel);
		table = new ColorTable(10, bitsPerChannel, Constants.QUADRATIC, 0.49);

		for (int i = 0; i < imageWidth; i++) {
			for (int j = 0; j < imageHeight; j++) {
				table.increment(image.getColor(i, j));
			}
		}
		numCollisions = table.getNumCollisions();
		return table;
	}

	/**
	 * 
	 * 
	 * Return the result of running Util.cosineSimilarity() on the vectorized
	 * images.
	 * 
	 * Note: If you compute the similarity of an image with itself, it should be
	 * close to 1.0.
	 */
	public static double similarity(Image image1, Image image2, int bitsPerChannel) {
		ColorTable image1Table = vectorize(image1, bitsPerChannel);
		ColorTable image2Table = vectorize(image2, bitsPerChannel);

		return Util.cosineSimilarity(image1Table, image2Table);
	}

	/**
	 * Uses the Painting images and all 8 bitsPerChannel values to compute and
	 * print out a table of collision counts.
	 */
	public static void allPairsTest() {
		Painting[] paintings = Painting.values();
		int n = paintings.length;
		for (int y = 0; y < n; y++) {
			for (int x = y + 1; x < n; x++) {
				System.out.println(paintings[y].get().getName() + " and " + paintings[x].get().getName() + ":");
				for (int bitsPerChannel = 1; bitsPerChannel <= 8; bitsPerChannel++) {
					numCollisions = 0;
					System.out.println(String.format("   %d: %.2f %d", bitsPerChannel,
							similarity(paintings[x].get(), paintings[y].get(), bitsPerChannel), numCollisions));
				}
				System.out.println();
			}
		}
	}

	/**
	 * Simple testing
	 */
	public static void main(String[] args) {
		System.out.println(Constants.TITLE);
		Image mona = Painting.MONA_LISA.get();
		Image starry = Painting.STARRY_NIGHT.get();
		Image christina = Painting.CHRISTINAS_WORLD.get();

		System.out.println("It looks like all three test images were successfully loaded.");
		System.out.println("mona's dimensions are " + mona.getWidth() + " x " + mona.getHeight());
		System.out.println("starry's dimenstions are " + starry.getWidth() + " x " + starry.getHeight());
		System.out.println("christina's dimensions are " + christina.getWidth() + " x " + christina.getHeight());
		// System.out.println(similarity(mona, mona, 6));
		// ColorTable monaLisaTable = vectorize(mona, 2);
		// System.out.println(monaLisaTable.get(Color.BLACK));
		// ColorTable starryTable = vectorize(starry, 2);
		// System.out.println(starryTable.get(Color.BLACK));
		// System.out.println(vectorize(mona, 8).getNumCollisions());
		// System.out.println(vectorize(mona, 5).getCapacity());
		allPairsTest();
	}
}
