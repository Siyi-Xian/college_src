import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GenomeMinimizingSkew {
	
	String genome;
	int[] skew;
	int len;
	
	public GenomeMinimizingSkew() {
		
	}
	
	public void run() throws FileNotFoundException {
		File f = new File("rosalind_ba2f.txt");
		Scanner s = new Scanner(f);
		
		genome = s.nextLine();
		len = genome.length();
		skew = new int[len + 1];
		
		int min = 0xffffff;
		int position = 0;
		int[] minPos = new int[len];
		
		for (int i = 0; i < len; i++)
			switch (genome.charAt(i)) {
				case 'A' :
					skew[i + 1] = skew[i];
					break;
				case 'C' :
					skew[i + 1] = skew[i] - 1;
					break;
				case 'G' :
					skew[i + 1] = skew[i] + 1;
					break;
				case 'T' :
					skew[i + 1] = skew[i];
					break;
			}
		
		for (int i = 0; i < len + 1; i++) {
			if (skew[i] < min) {
				min = skew[i];
				minPos = new int[len];
				minPos[0] = i;
				position = 1;
			}
			else if (skew[i] == min)
				minPos[position++] = i;
		}
		
		for (int i = 0; i < position; i++)
			System.out.print(minPos[i] + "" + " ");
		
		s.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		GenomeMinimizingSkew s = new GenomeMinimizingSkew();
		s.run();
	}

}
