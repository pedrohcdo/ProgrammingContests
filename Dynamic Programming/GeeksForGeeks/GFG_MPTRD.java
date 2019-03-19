import java.util.Scanner;

/**
 * https://www.geeksforgeeks.org/minimum-positive-points-to-reach-destination/
 * 
 * Given a grid with each cell consisting of positive, negative or no points i.e, zero points. We can move across a cell only if we have positive points ( > 0 ). Whenever we pass through a cell, points in that cell are added to our overall points. We need to find minimum initial points to reach cell (m-1, n-1) from (0, 0) by following these certain set of rules :
 *  
 * 1.From a cell (i, j) we can move to (i+1, j) or (i, j+1).
 * 2.We cannot move from (i, j) if your overall points at (i, j) is <= 0.
 * 3.We have to reach at (n-1, m-1) with minimum positive points i.e., > 0.
 * 
 * Example:
 * 
 * Input: points[m][n] = { {-2, -3,   3},  
 *                         {-5, -10,  1},  
 *                         {10,  30, -5}  
 *                       };
 * Output: 7
 * Explanation:  
 * 7 is the minimum value to reach destination with  
 * positive throughout the path. Below is the path.
 *  
 * (0,0) -> (0,1) -> (0,2) -> (1, 2) -> (2, 2)
 *  
 * We start from (0, 0) with 7, we reach(0, 1)  
 * with 5, (0, 2) with 2, (1, 2) with 5, (2, 2)
 * with and finally we have 1 point (we needed  
 * greater than 0 points at the end).
 * 
 * Input:
 * 
 * The first line contains an integer 'T' denoting the total number of test cases.
 * In each test cases, the first line contains two integer 'R' and 'C' denoting the number of rows and column of array.  
 * The second line contains the value of the array i.e the grid, in a single line separated by spaces in row major order.
 * 
 * 
 * Output:
 * 
 * Print the minimum initial points to reach the bottom right most cell in a separate line.
 * 
 * 
 * Constraints:
 * 
 * 1 ≤ T ≤ 30
 * 1 ≤ R,C ≤ 10
 * -30 ≤ A[R][C] ≤ 30
 * 
 * 
 * Example:
 * 
 * Input:
 * 1
 * 3 3
 * -2 -3 3 -5 -10 1 10 30 -5
 * Output:
 * 7
 * -------------------------------------
 * This solution takes O(MN * log(P)) time, where P is max points.
 * 
 * Obs(It was possible to improve to O(MN) by performing dp from top to bottom,
 * 		but I used this solution for the laziness of redoing everything XD.)
 */
public class GFG_MPTRD {
	
	/**
	 * Class to simulate flux.
	 */
	public static class Flux {
		
		int acc;
		
		/**
		 * Constructor
		 * 
		 * @param acc
		 */
		public Flux(int acc) {
			this.acc = acc;
		}
		
		/**
		 * Update flux
		 * 
		 * @param fluxA
		 * @param fluxB
		 */
		public void update(Flux fluxA, Flux fluxB) {
			if(fluxA != null && fluxA.acc <= 0) fluxA = null;
			if(fluxB != null && fluxB.acc <= 0) fluxB = null;
			if(fluxA == null && fluxB == null) {
				this.acc = 0;
				return;
			}
			if(fluxA == null) fluxA = fluxB;
			if(fluxB == null) fluxB = fluxA;
			if(fluxA.acc > fluxB.acc)
				this.acc = fluxA.acc + this.acc;
			else
				this.acc = fluxB.acc + this.acc;
		}
		
		/**
		 * To test
		 */
		@Override
		public String toString() {
			return "(ACC: " + this.acc + ")";
		}
	}
	
	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = s.nextInt();
		
		for(int i=0; i<t; i++) {
			int l = s.nextInt();
			int c = s.nextInt();
			int[][] mat = new int[l][c];
			for(int j=0; j<l; j++) {
				for(int k=0; k<c; k++)
					mat[j][k] = s.nextInt();
			}
			
	        int start = 1;
	        int end = 3001;
	        int ans = Integer.MAX_VALUE;
	        
			while(start < end) {
				int middle  = (start + end) / 2;
				Flux[][] min_path = new Flux[l+1][c+1];
				min_path[0][1] = min_path[1][0] = new Flux(middle);
		        for(int j=1; j<=l; j++) {
		        	for(int k=1; k<=c; k++) {
		        		Flux flux = new Flux(mat[j-1][k-1]);
		        		flux.update(min_path[j-1][k], min_path[j][k-1]);
		        		min_path[j][k] = flux;
		        	}
		        }
		        if(min_path[l][c].acc > 0) {
		        	end = middle;
		        	ans = Math.min(ans, middle);
		        } else {
		        	start = middle + 1;
		        }
			}
			System.out.println(ans);
	        
	    
		}
        s.close();
	}
}
