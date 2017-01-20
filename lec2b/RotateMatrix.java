import java.util.Arrays;

/**
 * Algorithm Explanation: The algorithm starts with rotating the outermost layer in clockwise direction 
 * and then moves to the inner layer. 
 * This goes in a for loop until we reach the innermost layer.
 * For each layer, the algorithm first rotates the four vertices positions clockwise.
 * This is done by using one temp variable to put one of the elements and moving the other three.
 * It then moves the next four positions clockwise.
 * 
 * X 0 0 0 0 X
 * 0 0 0 0 0 0 
 * 0 0 0 0 0 0 
 * 0 0 0 0 0 0 
 * X 0 0 0 0 X 
 *  
 * 0 X 0 0 0 0
 * 0 0 0 0 0 X 
 * 0 0 0 0 0 0 
 * X 0 0 0 0 0 
 * 0 0 0 0 X 0  
 *
 * @author red6
 * @author Shubhankar Mitra
 */

public class RotateMatrix {

  /**
   * Takes an n x n array of integers and rotates it in place in a
   * clockwise direction. Uses at most O(n) extra space.
   */

  public static void rotateCW(int[][] a) {
	//calculating depth of matrix for finding number of layers to work on
    int depth = a[0].length/2; 
    //using a for loop to move through the matrix layers
    for (int j=0; j<depth; j++){
    	int colLength = (a[0].length)-1-2*j;
    	for (int i = j; i<colLength+j; i++){
    		int temp = a[j][i];
			a[j][i] = a[colLength+2*j-i][j];
			a[colLength+2*j-i][j] = a[colLength+2*j-j][colLength+2*j-i];
			a[colLength+2*j-j][colLength+2*j-i] = a[i][colLength+2*j-j];
			a[i][colLength+2*j-j] = temp;
  		}
    }
  }

  public static void printMatrix(int[][] a) {
    for (int[] row : a)
      System.out.println(Arrays.toString(row));
  }

  public static void main(String[] args) {
    int[][] a;
    a = new int[][] {
        { 11, 12, 13, 14, 15 },
        { 26, 27, 28, 29, 16 },
        { 25, 34, 35, 30, 17 },
        { 24, 33, 32, 31, 18 },
        { 23, 22, 21, 20, 19 },
    };
    System.out.println("5X5 test before: ");
    printMatrix(a);
    rotateCW(a);
    System.out.println("\nafter: ");
    printMatrix(a);
    
    a = new int[][] {
        { 5 },
    };
    System.out.println("1X1 test before: ");
    printMatrix(a);
    rotateCW(a);
    System.out.println("\nafter: ");
    printMatrix(a);
    
    a = new int[][] {
        { 1, 2 },
        { 4, 3 },
    };
    System.out.println("2X2 test before: ");
    printMatrix(a);
    rotateCW(a);
    System.out.println("\nafter: ");
    printMatrix(a);
    
    a = new int[][] {
        { 1, 2 ,3 },
        { 6 ,5, 4 },
        { 7, 8, 9},
    };
    System.out.println("3X3 test before: ");
    printMatrix(a);
    rotateCW(a);
    System.out.println("\nafter: ");
    printMatrix(a);
    
    a = new int[][] {
        { 1, 1, 1, 1, 1, 1 },
        { 1, 2, 222, 2, 2, 1 },
        { 1, 2, 3, 4, 2, 1 },
        { 1, 2, 6, 5, 2, 111 },
        { 1, 2, 2, 2, 2, 1 },
        { 1, 1, 1, 1, 1, 1 },
    };
    System.out.println("6X6 before: ");
    printMatrix(a);
    rotateCW(a);
    System.out.println("\nafter: ");
    printMatrix(a);

    /* Expected output:
    
       before: 
       [11, 12, 13, 14, 15]
       [26, 27, 28, 29, 16]
       [25, 34, 35, 30, 17]
       [24, 33, 32, 31, 18]
       [23, 22, 21, 20, 19]

       after: 
       [23, 24, 25, 26, 11]
       [22, 33, 34, 27, 12]
       [21, 32, 35, 28, 13]
       [20, 31, 30, 29, 14]
       [19, 18, 17, 16, 15]

     */
    
    /* Other matrices you should test:
     
       a = new int[][] {
           { 5 },
       };
       
       a = new int[][] {
           { 1, 2 },
           { 4, 3 },
       };

       a = new int[][] {
           { 1, 1, 1, 1, 1, 1 },
           { 1, 2, 2, 2, 2, 1 },
           { 1, 2, 3, 4, 2, 1 },
           { 1, 2, 6, 5, 2, 1 },
           { 1, 2, 2, 2, 2, 1 },
           { 1, 1, 1, 1, 1, 1 },
       };
    */ 
  }
}
