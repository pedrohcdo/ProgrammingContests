import java.util.Scanner;

/**
 * https://practice.geeksforgeeks.org/problems/dice-throw/0
 * 
 * Given n dice each with m faces, numbered from 1 to m, find the number of ways to get sum X. X is the summation of values on each face when all the dice are thrown.
 * 
 * Input: The first line of the input contains T denoting the number of test cases. First line of test case is faces 'm', number of throws 'n' and the sum to obtain 'x'.
 * Output: Number of ways to get sum 'x' are displayed.
 * Constraints:
 * 1 <=T<= 100
 * 1 <=m,n,x<= 50
 * 
 * 
 * Example:
 * 
 * Input: 
 * 2
 * 6 3 12
 * 10 8 25
 * 
 * Output:
 * 25
 * 318648
 * ---------------------------------
 * This solution takes O(NM) time.
 */
public class GFG_DiceThrow {
	
	/**
	 * Count ways
	 * 
	 * @param f
	 * @param d
	 * @param s
	 * @return
	 */
	public static long count(int f, int d, int s) {
		long resp[][] = new long[51][51];
		resp[0][0] = 1;
		for(int j=1; j<s; j++)
			resp[0][j] = 0;
		for(int i=1; i<=d; i++) {
			for(int j=i; j<=s; j++) {
				resp[i][j] = resp[i][j-1] + resp[i-1][j-1];
				if(j-f-1 >= 0)
					resp[i][j] -= resp[i-1][j-f-1];
			}
		}
		return resp[d][s];
	}
	
	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		for(int i=0; i<t; i++) {
			int f = sc.nextInt();
			int d = sc.nextInt();
			int s = sc.nextInt();
			System.out.println(count(f, d, s));
		}
		sc.close();
	}
}
