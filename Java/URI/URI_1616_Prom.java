import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * https://www.urionlinejudge.com.br/judge/en/problems/view/1616
 * 
 * It's the end of the year, and finally Rafael is graduating in his Computing course. His classmates decided to celebrate the graduation organizing a ball, 
 * where there would be live music, food and free drinks. As all balls, the most expected moment is the one in which everyone starts to dance in pairs.
 * The pairs will be formed between a boy and a girl, and as Rafael's classmates are so shy, that they decided to plan ahead what the pairs would be. 
 * There is only one problem: there are more boys than girls in the class. This implies that, if everyone wants to dance at least once, one or more 
 * girls will have to dance with more than one boy.
 * Rafael asked your help: in how many ways the pairs can be formed, in such a way that all the boys dance exactly once, and all the girls dance at least once?
 *
 * Input:
 * 	There will be several test cases. Each test case consists of two integers, B and G (1 ≤ G < B ≤ 10³), indicating the number of boys and girls in the class, 
 * 	respectively.
 * 	The last test case is indicated when B = G = 0.
 * 
 * Output:
 *
 *	For each test case print one line, containing one integer, indicating in how many ways it's possible that the pairs are formed in such a way that
 *	all the boys dance exactly once, and all the girls dance at least once.	
 *	As the answer may be a little high, print the answer with rest of division in 1000000007 (10⁹+7).
 *------------------------------------------
 * This solution takes O(1) time with pre-calculated answers, for pre-calculation takes O(NM) time.
 */
public class URI_1616_Prom {

	static long mod = 1000000007;
	
	public static void main(String[] args) throws IOException {
		
		// Calculate
		int max = 1002;
		long ans[][] = new long[max+1][max+1];
		ans[0][0] = 1;
		for(int j=1; j<=max; j++)
			ans[j][1] = 1;
		for(int j=1; j<=max; j++) {
			for(int k=1; k<=j; k++)
				ans[j][k] = (k * ((ans[j-1][k] +  ans[j-1][k-1]) % mod)) % mod;
		}
		
		//
		BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
		String line;
		while ((line = bi.readLine()) != null) {
			String[] spl = line.split(" ");
			int i1 = Integer.parseInt(spl[0]);
			int i2 = Integer.parseInt(spl[1]);
			if (i1 == 0 && i2 == 0)
				break;
			System.out.println(ans[i1][i2]);
		}
	}
}
