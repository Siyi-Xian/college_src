import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AminoAcid {
	
	String rna;
	static final char[][][] encode = {{{'K', 'N', 'K', 'N'}, {'T', 'T', 'T', 'T'}, {'R', 'S', 'R', 'S'}, {'I', 'I', 'M', 'I'}},
									  {{'Q', 'H', 'Q', 'H'}, {'P', 'P', 'P', 'P'}, {'R', 'R', 'R', 'R'}, {'L', 'L', 'L', 'L'}},
									  {{'E', 'D', 'E', 'D'}, {'A', 'A', 'A', 'A'}, {'G', 'G', 'G', 'G'}, {'V', 'V', 'V', 'V'}},
									  {{'*', 'Y', '*', 'Y'}, {'S', 'S', 'S', 'S'}, {'*', 'C', 'W', 'C'}, {'L', 'F', 'L', 'F'}}};
	
	public AminoAcid() throws FileNotFoundException {
		File f = new File ("rosalind_ba4a.txt");
		Scanner s = new Scanner (f);
		
		rna = s.nextLine();
		
		s.close();
	}
	
	private int toNum(char ch) {
		switch (ch) {
			case 'A' :
				return 0;
			case 'C' :
				return 1;
			case 'G' :
				return 2;
			case 'U' :
				return 3;
			default :
				return -1;
		}
	}
	
	public void run() {
		for (int i = 0; i < rna.length(); i += 3) {
			char codon = encode[this.toNum(rna.charAt(i))][this.toNum(rna.charAt(i + 1))][this.toNum(rna.charAt(i + 2))];
			if (codon == '*')
				return;
			System.out.print(codon);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		AminoAcid a = new AminoAcid();
		a.run();
	}

}
