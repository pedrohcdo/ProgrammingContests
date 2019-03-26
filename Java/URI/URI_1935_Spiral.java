import java.util.Scanner;

/**
 * @author Pedro H. Chaves <pedrohcd@hotmail.com>
 * 
 * https://www.urionlinejudge.com.br/judge/en/problems/view/1616
 * 
 * Given a board dimensions N × N, would like to place beans, a grain in each square, following a spiral as shown in the figure. Starting from the top left corner, with coordinates (1, 1), and then going right as possible, then down as possible, then to the left as possible and then up as possible. We repeat this pattern, right-down-left-up, until B beans are placed on the board. The problem is: given N and B data, in which coordinates will be placed the last grain of beans? In the figure, for N = 8 and B = 53, the last grain was placed on the squared coordinates (4, 6).
 * 
 * 
 * Input
 * 
 * The entry contains only one line with two integers, N and B, where 1 ≤ N ≤ 230 and 1 ≤ B ≤ N2.
 * 
 * Output
 * 
 * Your program should produce a single line with two integers L and C representing the coordinates of the last grain of beans.
 *------------------------------------------
 * This solution takes O(1) time.
 */
public class URI_1935_Spiral {
	
	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		long size = s.nextLong();
		long beans = s.nextLong();

		long removeLayers = (long) Math.floor((size - Math.sqrt(size * size - beans)) / 2);
		long removeBeans = removeLayers * 4 * (size - removeLayers);

		beans -= removeBeans;
		size -= 2 * removeLayers;

		long x = removeLayers, y = removeLayers;
		if (beans <= size) {
			x += beans;
			y += 1;
		} else if (beans > size && beans < size * 2) {
			x += size;
			y += beans - size + 1;
		} else if (beans >= size * 2 && beans <= size * 3 - 2) {
			x += size * 3 - 1 - beans;
			y += size;
		} else {
			x += 1;
			y += size * 4 - 2 - beans;
		}

		System.out.println(y + " " + x);
		s.close();
	}
}
