import java.util.Arrays;
import java.util.Scanner;

/**
 * https://practice.geeksforgeeks.org/problems/word-break-part-2/0
 * 
 * Given a string s and a dictionary of words dict, add spaces in s to construct a sentence where each word is a valid dictionary word.
 * 
 * Return all such possible sentences.
 * 
 * For example, given
 * s = "snakesandladder",
 * dict = ["snake", "snakes", "and", "sand", "ladder"].
 * 
 * A solution is ["snakes and ladder",
 *            "snake sand ladder"].
 * 
 * Input:
 * The first line contains an integer T, denoting the number of test cases.
 * Every test case has 3 lines.
 * The first line contains an integer N, number of words in the dictionary.
 * The second line contains N strings denoting the words of the dictionary.
 * The third line contains a string s.
 * 
 * Output:
 * For each test case, print all possible strings in one line. Each string is enclosed in (). See Example.
 * If no string possible print "Empty" (without quotes).
 * 
 * Constraints:
 * 1<=T<=100
 * 1<=N<=20
 * 1<=Length of each word in dictionary <=15
 * 1<=Length of s<=1000
 * 
 * 
 * Note: Make sure the strings are sorted in your result.
 */
public class GFG_WordBreakP2 {
	
	/**
	 * Modified Trie with Iterator
	 * 
	 */
	static class TrieNode {
		
		/**
		 * Phrases Iterator
		 * 
		 */
		private class Iterator {
			
			private TrieNode node;
			private String word;
			private int from;
			private int to;
			
			private Iterator(TrieNode node, String word, int from, int to) {
				this.node = node;
				this.word = word;
				this.from = from;
				this.to = to;
			}
			
			/**
			 * Get current match
			 * 
			 * @return
			 */
			public String get() {
				return this.word.substring(this.from, this.to);
			}
			
			/**
			 * Next match
			 * 
			 * @return
			 */
			public Iterator next() {
				return this.node.skip().nextWord(this.word, this.from, this.to);
			}
			
			/**
			 * Index of end of this match
			 * 
			 * @return
			 */
			public int to() {
				return this.to;
			}
		}
		
		//
		public TrieNode[] next = new TrieNode[26];
		public boolean isWord = false;
		public int depth = 0;
		public char utils;
		public boolean skip = false;
		
		/**
		 * Constructor
		 * 
		 * @param depth
		 */
		public TrieNode(final int depth) {
			this.depth = depth;
		}
		
		/**
		 * Internal use
		 * @return
		 */
		public TrieNode skip() {
			this.skip = true;
			return this;
		}
		
		/**
		 * Find the word in this trie and return an Iterator to matches others words.
		 * 
		 * @param word
		 * @param from
		 * @param to
		 * @return
		 */
		public Iterator nextWord(String word, int from, int to) {
			if(this.isWord && !this.skip) return new Iterator(this, word, from, to);
			this.skip = false;
			if(to >= word.length()) return null;
			int c = word.charAt(to);
			if(c >= 'a' && c <= 'z') c -= 'a';
			else if(c >= 'A' && c <= 'Z') c -= 'A';
			else throw new RuntimeException("Invalid char!");
			if(next[c] != null) return next[c].nextWord(word, from, to+1);
			return null;
		}
		
		/**
		 * Insert new word
		 * 
		 * @param word
		 */
		void addWord(String word) {
			if(this.depth >= word.length()) {
				this.isWord = true;
				return;
			}
			int c = word.charAt(this.depth);
			if(c >= 'a' && c <= 'z') c -= 'a';
			else if(c >= 'A' && c <= 'Z') c -= 'A';
			else throw new RuntimeException("Invalid char!");
			if(next[c] == null) next[c] = new TrieNode(this.depth + 1);
			next[c].utils = (char) (c + 'a');
			next[c].addWord(word);
		}
		
	}
	
	/**
	 * Print all matchs combinations.
	 * 
	 * @param from
	 * @param str
	 * @param dict
	 * @param mount
	 */
	public static void printValidPhrases(int from, String str, TrieNode dict, String mount) {
		TrieNode.Iterator itr = dict.nextWord(str, from, from);
		if(from == str.length())
			System.out.print("(" + mount + ")");
		while(itr != null) {
			String new_mount = mount;
			if(!new_mount.isEmpty())
				new_mount += " ";
			new_mount += itr.get();
			printValidPhrases(itr.to(), str, dict, new_mount);
			itr = itr.next();
		}
	}
	
	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		int t = s.nextInt();
		for(int i=0; i<t; i++) {
			int w = s.nextInt();
			TrieNode dict = new TrieNode(0);
			for(int j=0; j<w; j++)
				dict.addWord(s.next());
			printValidPhrases(0, s.next(), dict, "");
		}
		s.close();
	}
}
