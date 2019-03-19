import java.util.Scanner;

/**
 * https://practice.geeksforgeeks.org/problems/minimum-sum-partition/0
 * 
 * Partition a set into two subsets such that the difference of subset sums is minimum
 * Given a set of integers, the task is to divide it into two sets S1 and S2 such that the absolute difference between their sums is minimum.
 * If there is a set S with n elements, then if we assume Subset1 has m elements, Subset2 must have n-m elements and the value of abs(sum(Subset1) – sum(Subset2)) should be minimum.
 *
 * Example:
 * 
 * Input:  arr[] = {1, 6, 11, 5} 
 * Output: 1
 * Explanation:
 * Subset1 = {1, 5, 6}, sum of Subset1 = 12 
 * Subset2 = {11}, sum of Subset2 = 11
 *
 * -------------------------------------------------
 * This solution takes O(N * M) time instead of O(N²),
 * where M is sum of all elements of the input.
 */
class GFG_MinimumSumPartition {
	
	public static int findMinDiff(int[] values) {
	    int total = 0;  
	    for(int i=0; i<values.length; i++) 
	    	total += values[i]; 
	    boolean dp[][] = new boolean[values.length + 1][total + 1]; 
	    for (int i=0; i<=values.length; i++) 
	    	dp[i][0] = true; 
	    for (int i = 1; i <= total; i++) 
	        dp[0][i] = false;
	    for (int i=1; i<=values.length; i++) { 
	    	int value = values[i-1];
	    	for (int j=1; j<=total; j++) { 
	    		dp[i][j] = dp[i-1][j]; 
	    		if (value <= j) 
	    			dp[i][j] |= dp[i-1][j-value]; 
	    	} 
	    }
	    for (int i=total/2; i>=0; i--) { 
	    	if (dp[values.length][i] == true) 
	    		return total - 2 * i;
	    }
	    return -1;
	}
	
    public static void main (String[] args) {
		Scanner s = new Scanner(System.in);
		int testCases = s.nextInt();
		for(int i=0; i<testCases; i++) {
			int n = s.nextInt();
			int[] v = new int[n];
			int total = 0;
			for(int j=0; j<n; j++) {
				v[j] = s.nextInt();
				total += v[j];
			}
			System.out.println(findMinDiff(v));
		}
	}
}