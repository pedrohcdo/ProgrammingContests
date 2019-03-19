import java.util.Scanner;

/**
 * https://practice.geeksforgeeks.org/problems/count-of-subarrays/0
 * 
 * Given an array of N positive integers  a1, a2 ............ an.
 * The value of each contiguous subarray in given array is the maximum element present in that subarray. Return the number of subarrays having value strictly greater than K.
 * 
 * Input:
 * 
 * The first line of the input contains 'T' denoting the total number of testcases.Then follows the description of testcases.
 * The first line of each testcase contains two space separated positive integers N and K denoting the size of array and the value of K. The second line contains N space separated positive integers denoting the elements of array.
 * 
 * Output:
 * 
 * Output the number of subarrays having value strictly greater than K.
 * 
 * Constraints:
 * 
 * 1<=T<=50
 * 1<=N<=100
 * 1<=a[i]<=10^5
 * 
 * Example:
 * 
 * Input:
 * 2
 * 3 2
 * 3 2 1
 * 4 1
 * 1 2 3 4
 * 
 * Output:
 * 3
 * 9
 * -------------------------------
 * This solution takes O(N) time
 */
class GFG_CountOfSubarrays {
	
    public static int count(int x) {
        return x * (x + 1) / 2;
    }
    
	public static void main (String[] args) {
		Scanner s = new Scanner(System.in);
        int tests = s.nextInt();
       
        for(int i=0; i<tests; i++) {
            int k = s.nextInt();
            int g = s.nextInt();
            int c = count(k);
            
            // Find biggest invalid sub-array and calculate all smaller sub-arrays
            int min_len = 0;
            for(int j=0; j<k+1; j++) {
                if(j == k || s.nextInt() > g) {
                    c -= count(j - min_len);
                    min_len = j+1;
                }
            }
            
            //
            System.out.println(c);
        }
	}
}