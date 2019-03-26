import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Pedro H. Chaves <pedrohcd@hotmail.com>
 * 
 * https://www.urionlinejudge.com.br/judge/en/problems/view/1494
 * 
 * In this problem, we refer to the digits of a positive integer as the sequence of digits required to write it in base 10 without leading zeros. For instance, the digits of N = 2090 are of course 2, 0, 9 and 0. Let N be a positive integer. We call a positive integer M an eleven-multiple-anagram of N if and only if (1) the digits of M are a permutation of the digits of N, and (2) M is a multiple of 11. You are required to write a program that given N, calculates the number of its eleven-multiple-anagrams. As an example, consider again N = 2090. The values that meet the first condition above are 2009, 2090, 2900, 9002, 9020 and 9200. Among those, only 2090 and 9020 satisfy the second condition, so the answer for N = 2090 is 2.
 * 
 * Input
 * 
 * The input consists of many test cases and ends with EOF. Each test case is composed by a single line that contains an integer N (1 <= N <= 10^100).
 * 
 * Output
 * 
 * For each test case, print a line with an integer representing the number of eleven-multiple-anagrams of N. Because this number can be very large, you are required to output the remainder of dividing it by 109 + 7.
 */
public class URI_1494_Eleven {

	// Module
	static long mod = 1000000007L;
	
	// Binomial Coeficients
	static long[][] comb = new long[101][101];
	
	// Pascal Triangle of Binomial Coeficients
	static {
		comb[0][0] = 1;
		comb[1][0] = comb[1][1] = 1;
		for(int y=2; y<101; y++) {
			comb[y][0] = 1;
			for(int x=1; x<=y; x++) {
				comb[y][x] = (comb[y-1][x-1] + comb[y-1][x]) % mod;
			}
		}
	}
	
	static long[] zero = new long[11 * 110 * 11];
	
	// Segment Info
	static class Info {
		int[] digits;
		int[] front = new int[10];
		long[] map = new long[11 * 110 * 11];
	}
	
	/**
	 * Recursive Permutations
	 */
	static public long recursiveCountElevenPermutations(int digit, int c1, int c2, int t1, int t2, Info info) {
		int t1r = t1 % 11;
		if(digit == 10) return (t1r==t2%11) ? 1 : 0;
		int pos = digit + 11 * (c1 + 110 * t1r);
				
		if(info.map[pos] != 0)
			return info.map[pos] - 1;
		int limit = digit == 0 ? c1- 1 : c1;
		int min = Math.max(c1 - info.front[digit], 0);
		long result = 0;
		for(int i=Math.min(info.digits[digit], limit); i>=min; i--) {
			int j = (info.digits[digit] - i);
			long b1 = comb[digit == 0 ? c1 - 1 : c1][i];
			long b2 = comb[c2][j];
			long tmp = recursiveCountElevenPermutations(digit+1, c1-i, c2-j, t1 + i * digit,  t2 + j * digit, info);
			result = (result + ((((b1 * tmp) % mod) * b2) % mod)) % mod;
		}
		info.map[pos]= result + 1;
		return result;
	}
	
	/**
	 * Cont Eleven Permutations
	 */
	static public long countElevenPermutations(String number, Info info) {
		// Info Header
		info.digits = new int[10];
		int c2 = number.length()/2;
		int c1 = number.length() - c2;
		for(int i=0; i<number.length(); i++)  {
			int digit = number.charAt(i) - '0';
			info.digits[digit]++;
		}
		int front = 0;
		for(int i=9; i>=0; i--) {
			info.front[i] = front;
			front += info.digits[i];
		}
		//
		return recursiveCountElevenPermutations(0, c1, c2, 0, 0, info);
	}
	
	/**
	 * Main
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//
		BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
		String line;
		StringBuilder builder = new StringBuilder();
		Info info = new Info();
		//
		while((line=bi.readLine()) != null) {
			builder.append(countElevenPermutations(line, info));
			builder.append('\n');
			System.arraycopy(zero, 0, info.map, 0, zero.length);
		}
		System.out.print(builder.toString());
	}
}
