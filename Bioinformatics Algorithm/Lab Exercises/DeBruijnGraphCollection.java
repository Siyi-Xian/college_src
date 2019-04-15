import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Scanner;

public class DeBruijnGraphCollection {
	int k, l;
	ArrayList<String> dnaStr;
	ArrayList<ArrayList<String>> maps;
	
	public DeBruijnGraphCollection() throws FileNotFoundException {
		dnaStr = new ArrayList<String>();
		File f = new File("rosalind_ba3e.txt");
		Scanner in = new Scanner(f);
		
		while (in.hasNextLine())
			dnaStr.add(in.nextLine());
		
		k = dnaStr.get(0).length();
		
		in.close();
		maps = new ArrayList<ArrayList<String>>();
	}
	
	private void addWay(String origin, String dest) {
		for (int i = 0; i < maps.size(); i++)
			if (origin.equals(maps.get(i).get(0))) {
				maps.get(i).add(dest);
				return;
			}
		ArrayList<String> tempList = new ArrayList<String>();
		tempList.add(origin);
		tempList.add(dest);
		
		maps.add(tempList);
	}
	
	public void run() {
		for (int i = 0; i < dnaStr.size(); i++)
			this.addWay(dnaStr.get(i).substring(0, k - 1), dnaStr.get(i).substring(1));
		
		Collections.sort(maps, new Comparator<ArrayList<String>>() {
		    @Override
		    public int compare(ArrayList<String> one, ArrayList<String> two) {
		        return one.get(0).compareTo(two.get(0));
		    }
		});
		
		for (int i = 0; i < maps.size(); i++) {
			System.out.print(maps.get(i).get(0) + " -> ");
			for (int j = 1; j < maps.get(i).size() - 1; j++)
				System.out.print(maps.get(i).get(j) + ", ");
			System.out.println(maps.get(i).get(maps.get(i).size() - 1));
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		DeBruijnGraphCollection d = new DeBruijnGraphCollection();
		d.run();
	}

}
