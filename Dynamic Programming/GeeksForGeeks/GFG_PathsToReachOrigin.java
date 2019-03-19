import java.util.Scanner;

/**
 * https://practice.geeksforgeeks.org/problems/paths-to-reach-origin/0
 * 
 * You are standing on a point (n, m) and you want to go to origin (0, 0) by taking steps either left or down i.e. from each point you are allowed to move either in (n-1, m) or (n, m-1). Find the number of paths from point to origin.
 *
 * Input:
 * The first line of input contains an integer T denoting the number of test cases. Then T test cases follow. Each test case contains two integers n and m representing the point.
 * 
 * Output:
 * For each testcase, in a new line, print the total number of paths from point to origin.
 * 
 * Constraints:
 * 1 <= T<= 100
 * 1 <= n, m <= 25
 * 
 * Example:
 * Input:
 * 3
 * 3 2
 * 3 6
 * 3 0
 * 
 * Output:
 * 10
 * 84
 * 1
 * 
 * ----------------------------------
 * This solution takes O(1) time with pre-calculed NCr(n,r)
 */
class GFG_PathsToReachOrigin {
    
    static int[][] comb = new int[33][33];
    static {
        for(int i=0; i<33; i++)
            comb[i][0] = 1;
        comb[1][1] = 1;
        for(int i=2; i<33; i++) {
            for(int j=1; j <= i; j++)
                comb[i][j] = comb[i-1][j] + comb[i-1][j-1];
        }
        // 
    }
    
    // aaabbb
    
	public static void main (String[] args) {
		Scanner s = new Scanner(System.in);
		int testCases = s.nextInt();
		for(int i=0; i<testCases; i++) {
			int l = s.nextInt();
			int h = s.nextInt();
			System.out.println(comb[l+h][l]);
		}
	}
}