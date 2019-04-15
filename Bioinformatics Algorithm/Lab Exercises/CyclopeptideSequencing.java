import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CyclopeptideSequencing {
	
	ArrayList<Integer> spectrum;
	ArrayList<int[]> peptides;
	int n;
	static int[] s = {57,71,87,97,99,101,103,113,113,114,115,128,128,129,131,137,147,156,163,186};
	
	public CyclopeptideSequencing() throws FileNotFoundException {
		spectrum = new ArrayList<Integer>();
		
		File f = new File("rosalind_ba3d.txt");
		Scanner s = new Scanner(f);
		
		while (s.hasNextInt())
			spectrum.add(s.nextInt());
		
		s.close();
		
		n = (spectrum.size() - 2) / 2;
		
		peptides = new ArrayList<int[]>();	
		expand(n, new int[0]);
	}
	
	private void expand(int num, int[] pep) {
		if (num == 0) {
			peptides.add(pep);
			return;
		}
		
		int[][]
		
		for (int i = 0; i < s.length; i++) {
			int[] p = new int[pep.length + 1];
			for (int j = 0; j < pep.length; j++)
				p[j] = pep[j];
			p[pep.length] = s[i];
			expand(num - 1, p);
		}
	}
	
	private int mass(int[] pep) {
		int sum = 0;
		for (int i = 0; i < pep.length; i++)
			sum += pep[i];
		return sum;
	}
	
	private void output(int[] pep) {
		System.out.print(pep[0] + "");
		for (int i = 1; i < pep.length; i++)
			System.out.print("-" + pep[i] + "");
		System.out.print(" ");
	}
	
	public void run() {
		ArrayList<int[]> track = new ArrayList<int[]>();
        for (int i = 0; i < peptides.size(); i++) {
        	if (mass(peptides.get(i)) == spectrum.get(n * 2 + 1)) {
        		track.add(peptides.get(i));
        		output(peptides.get(i));
        		peptides.remove(i);
        	}
        	else
        		peptides.remove(i);
        }
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		CyclopeptideSequencing c = new CyclopeptideSequencing();
		c.run();
	}

}
