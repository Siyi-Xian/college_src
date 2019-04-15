import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author xiansiyi
 *
 */
public class CountingNucleotides {
	
	private static final int MAX = 1000;
	private String s;
	private int a, c, g, t;
	
	public CountingNucleotides() {
		a = 0;
		c = 0;
		g = 0;
		t = 0;
	}
	
	public void run() throws FileNotFoundException {
		File f = new File("rosalind_dna.txt");
		Scanner scan = new Scanner(f);
		
		s = scan.next();
		int len = s.length();
		
		for (int i = 0; i < len; i++)
			switch (s.substring(i, i + 1)) {
				case "A" :
					a++;
					break;
				case "C" :
					c++;
					break;
				case "G" :
					g++;
					break;
				case "T" :
					t++;
					break;
				default : break;
			}
		
		System.out.print((a + "") + " ");
		System.out.print((c + "") + " ");
		System.out.print((g + "") + " ");
		System.out.print((t + "") + " ");
		
		scan.close();
	}
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		CountingNucleotides cn = new CountingNucleotides();
		cn.run();
	}

}
