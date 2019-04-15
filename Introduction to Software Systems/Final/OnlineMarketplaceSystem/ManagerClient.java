package OnlineMarketplaceSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ManagerClient {
	private Marketplace m;
	
	public ManagerClient()  throws FileNotFoundException, IOException {
		this.m = new Marketplace();
	}
	
	private String getTypingVaule() {
		Scanner in = new Scanner(System.in);
		String t = in.nextLine();
		return t;
	}
	
	private void managerOperate() throws IOException {
		while(true)
		{
			System.out.println("What do you want to do:\n1. view the transactions\n2. view the buyers\n3. view the sellers\n4. view the categorys\n5. view the items\n6. EXIT");
			int op = Integer.parseInt(this.getTypingVaule());
			switch (op){
				case 1:
					System.out.println("What trnasactions do you want to view:\n1. view a specific one\n2. view all");
					int t = Integer.parseInt(this.getTypingVaule());
					if(t == 1)
					{
							System.out.println("What's the ID of the specific transaction?");
							int tID = Integer.parseInt(this.getTypingVaule());
							System.out.println(m.getTransaction(tID));
							break;
					}
					else if(t == 2)
					{
							System.out.println(m.getTransactions());
							break;
					}

				case 2:
					System.out.println("What buyer do you want to view:\n1. view a specific one\n2. view all");
					int b = Integer.parseInt(this.getTypingVaule());
					if(b == 1)
					{
							System.out.println("What's the ID of the specific buyer?");
							int bID = Integer.parseInt(this.getTypingVaule());
							System.out.println(m.getBuyer(bID));
							break;
					}
					else if(b == 2)
					{
							System.out.println(m.getBuyers());
							break;
					}
					

				case 3:
					System.out.println("What seller do you want to view:\n1. view a specific one\n2. view all");
					int s = Integer.parseInt(this.getTypingVaule());
					if(s == 1)
					{
							System.out.println("What's the ID of the specific seller?");
							int sID = Integer.parseInt(this.getTypingVaule());
							System.out.println(m.getSeller(sID));
							break;
					}
					else if(s == 2)
					{
							System.out.println(m.getSellers());
							break;
					}
				case 4:
					System.out.println("What category do you want to view:\n1. view a specific one\n2. view all");
					int c = Integer.parseInt(this.getTypingVaule());
					if(c == 1)
					{
							System.out.println("What's the ID of the specific category?");
							int cID = Integer.parseInt(this.getTypingVaule());
							System.out.println(m.getCategory(cID));
							break;
					}
					else if(c == 2)
					{
							System.out.println(m.getCategorys());
							break;
					}
				case 5:
					System.out.println("What item do you want to view:\n1. view a specific one\n2. view all");
					int i = Integer.parseInt(this.getTypingVaule());
					if(i == 1)
					{
							System.out.println("What's the ID of the specific item?");
							int iID = Integer.parseInt(this.getTypingVaule());
							System.out.println(m.getItem(iID));
							break;
					}
					else if (i == 2)
					{
							m.getItems();
							break;
					}
				case 6:
					return;
				default:
					break;
			}
		}
	}
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ManagerClient newProgram = new ManagerClient();
		newProgram.managerOperate();
	}
}
