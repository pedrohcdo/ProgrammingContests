import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

/**
 * @author Pedro H. Chaves <pedrohcd@hotmail.com>
 * 
 * https://www.urionlinejudge.com.br/judge/en/problems/view/1226
 * 
 * 
 * China is building a space elevator, which will allow the launching probes and satellites to a much lower cost, enabling not only scientific research projects but also space tourism.
 * 
 * However, the Chinese are very superstitious, and therefore have a very especial care with the numbering of floors in the elevator: they do not use any number containing the digit “4” or the sequence of digits “13”. Thus, they do not use the fourth floor or the floor 13 or the floor 134 nor the floor 113, but use the floor 103. Thus, the first floors are numbered 1, 2, 3, 5, 6, 7, 8, 9, 10, 11, 12, 15, 16, . . .
 * 
 * As the space elevator has many levels, and levels must be numbered, the Chinese asked you to write a program that, given the level, indicates which number should be assigned to it.
 * 
 * Input
 * 
 * The input contains several test cases. Each test case consists of a single line containing an integer N (1 ≤ N ≤ 1018) which indicates the floor whose number should be determined.
 * 
 * Output
 * 
 * For each test case, print a line containing a single integer indicating the number assigned to the N-th floor.
 */
public class URI_1226_SpaceElevator {
	
	// Constant Cache
	static long[] cacheFor13_4 = new long[17 * 17 * 2];
	static long[] cacheForMask = new long[18 * 18 * 2];
	static long[] model = new long[17 * 17 * 2];

	// Static Cache
	static String cache9 = "99999999999999999999";
	static String[] mask = new String[20];
	static String endLine = "\n";

	// Caches
	static {
		mask[19] = "9";
		for (int i = 1; i < 20; i++)
			mask[20 - i - 1] = mask[20 - i] + "9";
		//
	}

	/**
	 * Get Cursor
	 */
	public static int getCursor(int start, int end, boolean remove3) {
		int x = start;
		int y = end;
		int z = remove3 ? 1 : 0;
		return x + 20 * y + 2 * y * z;
	}

	/**
	 * Jump Wrong Floors
	 * 
	 * @return
	 */
	public static long jumpWrongNumbers(String real, String mask, int start, int end, boolean remove3, int bitmask) {
		if (bitmask == 1) {
			if (cacheForMask[getCursor(start, end, remove3)] != 0)
				return cacheForMask[getCursor(start, end, remove3)] - 1;
		} else {
			if (cacheFor13_4[getCursor(start, end, remove3)] != 0)
				return cacheFor13_4[getCursor(start, end, remove3)] - 1;
		}

		int size = end - start;
		if (size == 0)
			return 1;

		// 10000
		int digit = real.charAt(start) - '0';

		//
		long result;

		if (digit == 0)
			result = jumpWrongNumbers(real, mask, start + 1, end, false, bitmask);
		else if (digit == 1) {
			result = jumpWrongNumbers(mask, mask, start + 1, end, false, 1)
					+ jumpWrongNumbers(real, mask, start + 1, end, true, bitmask);
		} else if (digit == 2) {
			result = jumpWrongNumbers(mask, mask, start + 1, end, false, 1)
					+ jumpWrongNumbers(mask, mask, start + 1, end, true, 1)
					+ jumpWrongNumbers(real, mask, start + 1, end, false, bitmask);
			;
		} else if (digit == 3) {
			if (remove3) {
				result = 2 * jumpWrongNumbers(mask, mask, start + 1, end, false, 1)
						+ jumpWrongNumbers(mask, mask, start + 1, end, true, 1);
			} else {
				result = 2 * jumpWrongNumbers(mask, mask, start + 1, end, false, 1)
						+ jumpWrongNumbers(mask, mask, start + 1, end, true, 1)
						+ jumpWrongNumbers(real, mask, start + 1, end, false, bitmask);
			}
		} else if (digit == 4) {
			int mult = 3;
			if (remove3)
				mult--;
			result = mult * jumpWrongNumbers(mask, mask, start + 1, end, false, 1)
					+ jumpWrongNumbers(mask, mask, start + 1, end, true, 1);
		} else {
			int mult = digit - 2;
			if (remove3)
				mult--;
			result = mult * jumpWrongNumbers(mask, mask, start + 1, end, false, 1)
					+ jumpWrongNumbers(mask, mask, start + 1, end, true, 1)
					+ jumpWrongNumbers(real, mask, start + 1, end, false, bitmask);

		}
		if (bitmask == 1)
			cacheForMask[getCursor(start, end, remove3)] = result + 1;
		else
			cacheFor13_4[getCursor(start, end, remove3)] = result + 1;
		return result;
	}

	public static String makeNumber(String n) {
		return mask[n.length()] + n;
	}

	/**
	 * Count P13_4
	 */
	public static long countP13_4(String n) {
		System.arraycopy(model, 0, cacheFor13_4, 0, cacheFor13_4.length);
		return jumpWrongNumbers(makeNumber(n), cache9, 20 - n.length(), 20, false, 0) - 1;
	}

	/**
	 * Found Next Floor
	 */
	public static BigInteger foundNextFloor(BigInteger floor) {
		long last = 0;
		while (true) {
			String sfloor = floor.toString();
			long wrongFloors = floor.subtract(BigInteger.valueOf(countP13_4(sfloor))).longValue();
			long jump = wrongFloors - last;
			if (jump == 0)
				break;
			last = wrongFloors;
			floor = floor.add(BigInteger.valueOf(jump));
		}
		return floor;
	}

	/**
	 * Main
	 */
	public static void main(String[] a) throws IOException {
		BufferedReader bi = new BufferedReader(new InputStreamReader(System.in));
		String line;
		StringBuilder builder = new StringBuilder();
		while ((line = bi.readLine()) != null) {
			builder.append(foundNextFloor(BigInteger.valueOf(Long.parseLong(line))));
			builder.append(endLine);
		}
		System.out.print(builder.toString());
	}
}
