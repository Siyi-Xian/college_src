import java.util.Scanner;

public class FuzzyMedianString {
	
	String[] dna;
	int k, t;
	
	public FuzzyMedianString() {
		System.out.println("a");
		Scanner s = new Scanner(System.in);
		
		k = s.nextInt();
		t = s.nextInt();
		s.nextLine();
		dna = new String[t];
		
		for (int i = 0; i < t; i++)
			dna[i] = s.nextLine();
		
		s.close();
	}
	
	private int hammingDistance(String str1, String str2) {
		int dis = 0;
		
		for (int i = 0; i < str1.length(); i++)
			if (str1.charAt(i) != str2.charAt(i))
				dis++;
		
		return dis;
	}
	
	public void run() {
		String median = "";
		
		for (int i = 0; i < t; i++) {
			String text = dna[i];
			int d_min = k * t;
			
			for (int j = 0; j < text.length() - k + 1; j++) {
				String p = text.substring(j, j + k);
				int d = 0;
				
				for (int l = 0; l < t; l++)
					if (l != i) {
						String text_0 = dna[l];
						int d_min_0 = k;
						
						for (int m = 0; m < text_0.length() - k + 1; m++) {
							String p_0 = text_0.substring(m, m + k);
							int d_0 = hammingDistance(p, p_0);
							
							if (d_0 < d_min_0)
								d_min_0 = d_0;
						}
						d += d_min_0;
					}
				
				if (d < d_min) {
					d_min = d;
					median = p;
				}
			}
		}
		System.out.println(median);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FuzzyMedianString f = new FuzzyMedianString();
		f.run();
	}

}
