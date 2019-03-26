import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Pedro H. Chaves <pedrohcd@hotmail.com>
 * 
 * https://www.urionlinejudge.com.br/judge/en/problems/view/2239
 * 
 * Given a sequence of positive integers in hexadecimal, 
 * for example, S = [9af47c0b, 2,545,557, ff6447979], we define the sum (S) as 
 * the sum of all elements S. Now consider some permutation of the 16 hexadecimal 
 * digits, e.g. p = [4, 9; 5, 0, c, f, 3 d, 7, 8, b, 1, 2, 6, and]. 
 * From the S base sequence, we can define a transformed S[4] sequence, which is 
 * obtained by removing all ocorrêcias hexadecimal digit 4 of all 
 * integers of S, S[4] = [9af7c0b, 255557, ff67979]. 
 * Then we can remove the digit 9 and get S[4,9] = [af7c0b, 255557, ff677]. 
 * Following the order of the digits in the permutation p, we can define this way 16 sequences: 
 * S[4] , S[4,9] , S[4,9,5] ,...,S[4,9,5,a,0,c,f,3,d,7,8,b,1,2,6,e]. 
 * 
 * We are interested in adding all the elements of these 16 sequences:
 * 
 * total (S, p) = sum (S [4]) + sum (S [4,9]) + sum (S [4,9,5]) + ··· + sum (S [4,9,5 , a, 0, c, f, 3, d, 7.8, b, 1,2,6, e])
 * 
 * Clearly, all this depends on the permutation p used in subsequent removal. Given a positive integer N sequence in hex, your program must compute, considering all the possible permutations of the 16 hexadecimal digits: the minimum total, the maximum total and the sum of the total of all permutations. For the sum total of all the permutations, print the result 3b9aca07 module (109 + 7 in base 10).
 * 
 * Input
 * 
 * The first line of input contains an integer N, 1 ≤ N ≤ 3f, representing the size of the sequence. The following N lines contain each a positive integer P, 0 ≤ P ≤ fffffffff setting the initial sequence S integers. All numbers in the input are in hexadecimal, with lowercase letters.
 * 
 * Output
 * 
 * Your program should produce a single line in the output containing three positive integers in hexadecimal with lowercase letters, representing the total minimum, the maximum total and the total sum of considering all the possible permutations of the 16 hexadecimal digits.
 */
public class URI_2239_HexStatistics {

	static BigInteger[] fat = new BigInteger[17];
	static BigInteger[] fatC = new BigInteger[17];
	static BigInteger mod = BigInteger.valueOf(0x3b9aca07);

	static {
		BigInteger r = BigInteger.ONE;
		fat[0] = BigInteger.ONE;
		for (int i = 1; i <= 16; i++) {
			r = r.multiply(BigInteger.valueOf(i));
			fat[i] = r;
		}
		for (int i = 1; i <= 16; i++) {
			fatC[i] = fat[16 - i].multiply(fat[i]).mod(mod);
		}
	}

	static class Node {
		int mask = 0;
		long cost = 0;
		long cost2 = 0;
	}

	static class Result {
		BigInteger total;
		long[] results = new long[65536]; // 2^16
	}

	public static byte stoi(char h) {
		if (h >= '0' && h <= '9')
			return (byte) (h - '0');
		else
			return (byte) ((h - 'a') + 10);
	}

	public static long number(String n, int mask) {
		long valor = 0;
		long pow = 1;
		for (int i = n.length() - 1; i >= 0; i--) {
			byte v = stoi(n.charAt(i));
			if (((mask >> v) & 1) != 1) {
				valor += v * pow;
				pow *= 16;
			}
		}
		return valor;
	}

	public static long calcAll(String[] numbers, int mask) {
		long all = 0;
		for (String n : numbers) {
			all += number(n, mask);
		}
		return all;
	}

	public static void combHexa(Result r, int i, int v, int mask, String[] numbers) {
		if (i >= 16)
			return;
		for (int x = v; x <= 15; x++) {
			int newMask = mask | (1 << x);
			long sumAll = calcAll(numbers, newMask);
			BigInteger sum = BigInteger.valueOf(sumAll);
			r.total = r.total.add(sum.multiply(fatC[i + 1]));
			r.results[newMask] = sumAll;
			combHexa(r, i + 1, x + 1, newMask, numbers);
		}
	}


	static long calcMin(Result r) {
		long[] dist = new long[r.results.length];
		Arrays.fill(dist, Long.MAX_VALUE);

		dist[0] = 0;
		for (int i = 0; i < r.results.length; i++) {
			for (int x = 0; x < 16; x++) {
				if (((i >> x) & 1) != 1) {
					int toMask = i | (1 << x);
					if (dist[toMask] > dist[i] + r.results[toMask]) {
						dist[toMask] = dist[i] + r.results[toMask];
					}
				}
			}

		}

		return dist[r.results.length - 1];
	}

	
	static long calcMax(Result r) {
		long[] dist = new long[r.results.length];
		Arrays.fill(dist, Long.MIN_VALUE);

		dist[0] = 0;
		for (int i = 0; i < r.results.length; i++) {
			for (int x = 0; x < 16; x++) {
				if (((i >> x) & 1) != 1) {
					int toMask = i | (1 << x);
					if (dist[toMask] < dist[i] + r.results[toMask]) {
						dist[toMask] = dist[i] + r.results[toMask];
					}
				}
			}

		}

		return dist[r.results.length - 1];
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		boolean[] vec = new boolean[16];
		Result r = new Result();
		r.total = BigInteger.valueOf(0);

		Scanner s = new Scanner(System.in);
		int n = Integer.parseInt(s.next(), 16);
		String[] numbers = new String[n];
		for (int i = 0; i < n; i++)
			numbers[i] = s.next();

		long sumAll = calcAll(numbers, 0);
		for (int i = 0; i < 65536; i++)
			r.results[i] = sumAll;
		combHexa(r, 0, 0, 0, numbers);

		// calcMax(numbers, r);

		System.out.println(Long.toHexString(calcMin(r)) + " " + Long.toHexString(calcMax(r)) + " " + Long.toHexString(r.total.mod(BigInteger.valueOf(0x3b9aca07)).longValue()));
		s.close();
	}
}
