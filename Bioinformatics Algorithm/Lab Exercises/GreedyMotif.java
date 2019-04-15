import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class GreedyMotif {
	
	ArrayList<String> dna;
	int k, t, l;
	
	/**
	 * Get all data from the given file
	 * @throws FileNotFoundException
	 */
	public GreedyMotif() throws FileNotFoundException {
		File f = new File("rosalind_ba2d.txt");
		Scanner s = new Scanner(f);
		
		k = s.nextInt();
		t = s.nextInt();
		
		s.nextLine();
		dna = new ArrayList<String>();
		for (int i = 0; i < t; i++)
			dna.add(s.nextLine());
		
		l = dna.get(0).length();
		
		s.close();
	}
	
	/**
	 * count the score of a motif
	 * @param motifs
	 * @return scoreOfMotif
	 */
	private int score(ArrayList<String> motifs) {
		int sco = 0;
		
		for (int i = 0; i < k; i++) {
			int a = 0;
			int c = 0;
			int g = 0;
			int tt = 0;
			for (int j = 0; j < t; j++) 
				switch (motifs.get(j).charAt(i)) {
					case 'A' :
						a++;
						break;
					case 'C' :
						c++;
						break;
					case 'G' :
						g++;
						break;
					case 'T' :
						tt++;
						break;
				}
			sco += (a + c + g + tt - Math.max(a, Math.max(c, Math.max(g, tt))));
		}
		
		return sco;
	}
	
	/**
	 * Profile-most probable k-mer in the i-th string
	 * @param s
	 * @param p
	 * @return
	 */
	private String profileMost(String s, int[][] p) {
		String mostKmer = s.substring(0, k);
		long max = 0;
		
		for (int i = 0; i < s.length() - k + 1; i++) {
			long tempMax = 1;
			String tempKmer = s.substring(i, i + k);
			for (int j = 0; j < k; j++) {
				switch (tempKmer.charAt(j)) {
					case 'A' :
						tempMax *= p[0][j];
						break;
					case 'C' :
						tempMax *= p[1][j];
						break;
					case 'G' :
						tempMax *= p[2][j];
						break;
					case 'T' :
						tempMax *= p[3][j];
						break;
				}
				if (tempMax == 0)
					break;
			}
			if (tempMax > max) {
				max = tempMax;
				mostKmer = tempKmer;
			}
		}
		
		return mostKmer;
	}
	
	/**
	 * renew profile
	 * @param s
	 * @param p
	 * @return
	 */
	private int[][] renewProfile(String s, int[][] p) {
		for (int i = 0; i < s.length(); i++)
			switch (s.charAt(i)) {
				case 'A' :
					p[0][i]++;
					break;
				case 'C' :
					p[1][i]++;
					break;
				case 'G' :
					p[2][i]++;
					break;
				case 'T' :
					p[3][i]++;
			}
		
		return p;
	}
	
	/**
	 * Greedy Algorithm
	 * @param dna
	 * @param k
	 * @param t
	 * @return
	 */
	// GREEDYMOTIFSEARCH(Dna, k, t)
	private ArrayList<String> greedyMotifSerch(ArrayList<String> dna, int k, int t) {
		// BestMotifs ← motif matrix formed by first k-mers in each string from Dna
		ArrayList<String> bestMotifs = new ArrayList<String>();
		for (int i = 0; i < t; i++) bestMotifs.add(dna.get(i).substring(0, k));
		
		// for each k-mer Motif in the first string from Dna
		for (int i = 0; i < l - k + 1; i++) {
			// Motif1 ← Motif
			ArrayList<String> motifs = new ArrayList<String>();
			motifs.add(dna.get(0).substring(i, i + k));
			
			// for i = 2 to t
			int[][] profile = new int[4][k];
			profile = renewProfile(motifs.get(0), profile);
			
			// form Profile from motifs Motif1, …, Motifi - 1
			for (int j = 1; j < t; j++) {
				// Motifi ← Profile-most probable k-mer in the i-th string in Dna
				// Motifs ← (Motif1, …, Motift)
				motifs.add(profileMost(dna.get(j), profile));
				profile = renewProfile(motifs.get(j), profile);
			}
			
			// if Score(Motifs) < Score(BestMotifs)
			//		BestMotifs ← Motifs
			if (score(motifs) < score(bestMotifs))
				bestMotifs = motifs;
		}
		
		// return BestMotifs
		return bestMotifs;
	}
	
	public void run() {
		ArrayList<String> ans = greedyMotifSerch(dna, k, t);
		for (int i = 0; i < t; i++)
			System.out.println(ans.get(i));
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		GreedyMotif g = new GreedyMotif();
		g.run();
	}

}
