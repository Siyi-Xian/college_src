import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DeBruijnGraph {
	int k, l;
	String dnaStr;
	int[][] maps;
	
	public DeBruijnGraph() throws FileNotFoundException {
		File f = new File("rosalind_ba3d.txt");
		Scanner in = new Scanner(f);
		
		k = in.nextInt();
		in.nextLine();
		dnaStr = in.nextLine();
		
		l = dnaStr.length();
		
		in.close();
		maps = new int[(int) Math.pow(4, k)][5];
	}
	
	private int stringToNumber (char c) {
		switch (c) {
			case 'A' :
				return 0;
			case 'C' :
				return 1;
			case 'G' :
				return 2;
			case 'T' :
				return 3;
			default :
				return 0;
		}
	}
	
	private int toNumber(String subWord) {
		int l = subWord.length();
		
		if (l == 0) return 0;
		
		return 4 * (toNumber(subWord.substring(0, l - 1))) + stringToNumber(subWord.charAt(l - 1));
	}
	
	private String toWord(int num) {
		int kk = k - 1;
		int[] ans = new int[kk];
		String out = "";
		
		for (int i = kk - 1; i >= 0; i--) {
			ans[i] = num % 4;
			num /= 4;
		}
		
		for (int i = 0; i < kk; i++) {
			switch (ans[i]) {
				case 0 :
					out += "A";
					break;
				case 1 :
					out += "C";
					break;
				case 2 :
					out += "G";
					break;
				case 3 :
					out += "T";
					break;
			}
		}
		
		return out;
	}
	
	public void run() {
		for (int i = 0; i < l - k + 1; i++) {
			String tempStr = dnaStr.substring(i, i + k);
			int tempOrigin = this.toNumber(tempStr.substring(0, k - 1));
			int tempDes = this.toNumber(tempStr.substring(1));
			maps[tempOrigin][maps[tempOrigin][4]++] = tempDes;
		}
		
		for (int i = 0; i < (int) Math.pow(4, k); i++) {
			if (maps[i][4] > 0) {
				System.out.print(this.toWord(i) + " -> ");
				for (int j = 0; j < maps[i][4] - 1; j++)
					System.out.print(this.toWord(maps[i][j]) + ", ");
				System.out.println(this.toWord(maps[i][maps[i][4] - 1]));
			}
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		DeBruijnGraph d = new DeBruijnGraph();
		d.run();
	}

}
