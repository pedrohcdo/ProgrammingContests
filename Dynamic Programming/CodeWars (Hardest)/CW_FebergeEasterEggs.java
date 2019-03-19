import java.math.BigInteger;

/**
 * https://www.codewars.com/kata/54cb771c9b30e8b5250011d4
 * 
 * One man (lets call him Eulampy) has a collection of some almost identical Fabergè eggs. One day his friend Tempter said to him:
 * 
 *         Do you see that skyscraper? And can you tell me a maximal floor that if you drop your egg from will not crack it?
 *         No, - said Eulampy.
 *         But if you give me N eggs, - says Tempter - I'l tell you an answer.
 *         Deal - said Eulampy. But I have one requirement before we start this: if I will see more than M falls of egg, my heart will be crushed instead of egg. So you have only M trys to throw eggs. Would you tell me an exact floor with this limitation?
 * 
 * Task
 * 
 * Your task is to help Tempter - write a function
 * 
 * height :: Integer -> Integer -> Integer
 * height n m = -- see text
 * 
 * that takes 2 arguments - the number of eggs n and the number of trys m - you should calculate maximum scyscrapper height (in floors), in which it is guaranteed to find an exactly maximal floor from which that an egg won't crack it.
 * 
 * Which means,
 * 
 *     You can throw an egg from a specific floor every try
 *     Every egg has the same, certain durability - if they're thrown from a certain floor or below, they won't crack. Otherwise they crack.
 *     You have n eggs and m tries
 *     What is the maxmimum height, such that you can always determine which floor the target floor is when the target floor can be any floor between 1 to this maximum height?
 * 
 * Examples
 * 
 * height 0 14 = 0
 * height 2 0  = 0
 * height 2 14 = 105
 * height 7 20 = 137979
 * 
 * Data range
 * 
 * n <= 20000
 * m <= 20000
 * -----------------------------------
 * This solution is:
 *     
 *     eggs = max(eggs, attempts)
 *     total = 2 ^ attempts
 *     for i in range(attempts - eggs - 1):
 *        total -= NCr(attempts, i)
 *     print(total)
 *  
 * This answer takes O(N)
 */
public class CW_FebergeEasterEggs {
	/**

	 *     
	 * @param eggs
	 * @param attempts
	 * @return
	 */
	public static BigInteger floors(BigInteger eggs, BigInteger attempts) {
		eggs = eggs.min(attempts);
		BigInteger floors = BigInteger.ONE.shiftLeft(attempts.intValue()).subtract(BigInteger.ONE);
    
		int diff = attempts.intValue() - eggs.intValue();
    
		if(diff==0) return floors;
    
		BigInteger ans = BigInteger.ONE;
		floors = floors.subtract(ans);
  
		for(int i = 0; i < diff - 1; i++) {
			BigInteger bigI = BigInteger.valueOf(i);
			
			ans = ans.multiply(attempts.subtract(bigI));
			ans = ans.divide(bigI.add(BigInteger.ONE));
          
			floors = floors.subtract(ans);
		}
      
		return floors;
	}
	
	/**
	 * Test function for CodeWars.
	 * 
	 * @param n
	 * @param m
	 * @return
	 */
	public static BigInteger height(BigInteger n, BigInteger m) {
		return floors(n, m);
	}
	
	/**
	 * Main
	 */
	public static void main(String[] args) {
		System.out.println(height(BigInteger.valueOf(7), BigInteger.valueOf(20)));
	}
}