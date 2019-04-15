import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MostFrequentWords {
	
	String word;
	int k;
	
	public MostFrequentWords() {
		word = "";
		k = 0;
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
		int[] ans = new int[k];
		String out = "";
		
		for (int i = k - 1; i >= 0; i--) {
			ans[i] = num % 4;
			num /= 4;
		}
		
		for (int i = 0; i < k; i++) {
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
	
	public void run() throws FileNotFoundException {
		File f = new File("rosalind_ba1b.txt");
		Scanner s = new Scanner(f);
		
		int hash[];
		
		word = s.nextLine();
		k = s.nextInt();
		
		int size = 1;
		for (int i = 0 ; i < k; i++)
			size *= 4;
		
		int maxNum[] = new int[size];
		hash = new int[size];
		int max = 1;
		int totalFrequent = 0;
		
		for (int i = 0; i < word.length() - k + 1; i++)
			hash[toNumber(word.substring(i, i + k))]++;
		
		for (int i = 0 ; i < hash.length; i++)
			if (hash[i] > max) {
				maxNum = new int[10000];
				maxNum[0] = i;
				totalFrequent = 1;
				max = hash[i];
			}
			else if (hash[i] == max)
				maxNum[totalFrequent++] = i;
		
		for (int i = 0; i < totalFrequent; i++)
			System.out.print(toWord(maxNum[i]) + " ");
		
		s.close();
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		MostFrequentWords frequent = new MostFrequentWords();
		frequent.run();
	}

}
