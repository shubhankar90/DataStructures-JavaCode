
/**
 * TODO: The algorithm has O(n) time complexity
 * as it goes over the array of length n once. This is
 * further justified by the linear increase of time
 * on increasing time as seen by print statements 
 * on running the code.
 * 
 * @author Shubhankar Mitra
 */

public class LongestIncreasingFragment {

  /**
   * Takes an array of positive integers and returns 
   * the length of the longest
   * strictly increasing fragment (of consecutive values).
   */

  public static int lif(int[] a) {
	int maxCont=0;
	if (a.length>0){
		int currCont = 1;
		maxCont = 1;
	    for (int i=1;i<a.length;i++){
	    	if (a[i]>a[i-1]) {
	    		currCont++;
	    		if (currCont>maxCont)
	    			maxCont = currCont;
	    	}else {
	    		currCont = 1;	
	    	}
	    }
	    return maxCont;
    }
	else{
		return maxCont;
	}
    
  }

  public static void main(String[] args) {
    int[] a;
    a = new int[] {};
    System.out.println(lif(a));
    assert lif(a) == 0;
    a = new int[] { 5 };
    System.out.println(lif(a));
    assert lif(a) == 1;
    a = new int[] { 1, 2, 3, 4, 5, 6 };
    System.out.println(lif(a));
    assert lif(a) == 6;
    a = new int[] { 6, 5, 4, 3, 2, 1 };
    System.out.println(lif(a));
    assert lif(a) == 1;
    a = new int[] { 5, 1, 5, 2, 3, 4 };
    System.out.println(lif(a));
    assert lif(a) == 3;
    a = new int[] { 3, 2, 4, 6, 7, 2, 9, 1 };
    System.out.println(lif(a));
    assert lif(a) == 4;
    a = new int[] { 5,4,3,2,1,1,4,3,4,5,5,2,1 };
    System.out.println(lif(a));
    assert lif(a) == 3;
    
    long gameTime = 0;
    long startTime = System.currentTimeMillis();
    a = new int[1000000];
    System.out.println(lif(a));
    long endTime = System.currentTimeMillis();
    gameTime += (endTime - startTime);
    System.out.println("Time Taken:"+gameTime);
    
    gameTime = 0;
    startTime = System.currentTimeMillis();
    a = new int[10000000];
    System.out.println(lif(a));
    endTime = System.currentTimeMillis();
    gameTime += (endTime - startTime);
    System.out.println("Time Taken:"+gameTime);
    
    int i=10;
    for (int j=1;j<6;j++){
    	i=i*10;
    	gameTime = 0L;
        startTime = System.currentTimeMillis();
        a = new int[i];
        lif(a);
        endTime = System.currentTimeMillis();
        gameTime += (endTime - startTime);
        System.out.println("Time Taken for array length "+i+":"+gameTime);
    }
  }
}
