import java.util.Scanner;
import java.util.Stack;

/**
 * @author Pedro H. Chaves <pedrohcd@hotmail.com>
 * 
 * https://www.urionlinejudge.com.br/judge/en/problems/view/1776
 * 
 * The class of Computer Science of CIn-UFPE 2025.1 is graduating! It is a very special graduation, not only because all major projects of students in this class have become multinational corporations, but also because the number 2025 is a perfect square! Therefore, the students decided to make all numbers of the ceremony a perfect square: dates, number of guests, hash of the name of the class, the amount of students graduating, etc.
 * 
 * The party organizers were able to meet this requirement until the time comes to buy the snacks. They came into boxes with N snacks at a time. If N is not a perfect square, they will have to buy more than one box. Help the party organizers by making a program to calculate the minimum number of snacks they should buy to meet the eccentric requirements of the students.
 * 
 * Input
 * 
 * The first line contains an integer T (1 ≤ T ≤ 1000), the number of test cases. Each of the next T lines contains an integer N (1 ≤ N ≤ 10⁹), the number of snacks that comes in a single box.
 * 
 * Output
 * 
 * For each test case print a line with "Caso #X: Y", where X is he number of the current case, starting with 1, and Y is the minimum number of snacks that the party organizers should buy.
 */
public class URI_1776_Prom {
	
	/**
	 * Get factors of N using prime sieve idea
	 * @param n
	 * @return
	 */
	public static Stack<Integer> factors(int n) {
		Stack<Integer> factors = new Stack<>();
		for(int i  = 2; i * i <= n; ++i) {
		    while(n%i == 0) {
		         factors.push(i);
		         n /= i;
		    }
		}
		if(n != 1)
		    factors.push(n);
		return factors; 
	}
	
	/**
	 * Calculate next sqrt of n
	 * @param n
	 * @return
	 */
	public static long nextSqrt(int n) {
		long result = 1;
		Stack<Integer> factors = factors(n);
		while(factors.size() > 0) {
			long current = factors.pop();
			
			int count = 1;
			while(factors.size() > 0 && factors.peek() == current) {
				count += 1;
				factors.pop();
				result *= current;
			}
			if(count % 2 != 0)
				result *= current;
			result *= current;
		}
		return result;
	}
	
	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		int t = s.nextInt();
		for(int i=0; i<t; i++)
			System.out.println(String.format("Caso #%d: %s", i + 1, nextSqrt(s.nextInt())));
		s.close();
	}
}
