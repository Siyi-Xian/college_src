package OnlineMarketplaceSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class SellerClient {
	private Marketplace m;
	private User currentUser;
	Scanner in;
	
	public SellerClient() throws FileNotFoundException, IOException {
		this.m = new Marketplace();
		in = new Scanner(System.in);
	}
	
	public void logOut() {
		this.setCurrentUser(null);
	}
	
	/**
	 * @return the currentUser
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * @param currentUser the currentUser to set
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	/**
	 * operation of sellers after login
	 * @throws IOException 
	 */
	private void sellerOperate(Scanner i) throws IOException {
		while (this.getCurrentUser() != null) {
			System.out.println("What would you like to do?\n1. Add New Item\n2. Delete items\n3. View Items' List\n4. View transaction\n5. Logout");
			int op = Integer.parseInt(i.nextLine());
			Seller s = (Seller) this.getCurrentUser();
			switch (op) {
				case 1 :
					System.out.println("What's the name of item you want to add");
					String itemName = i.nextLine();
					System.out.println("what's the number of this item you want to add");
					int itemAmout =  Integer.parseInt(i.nextLine());
					System.out.println("what's the price of the item you want to add");
					double itemPrice = Double.parseDouble(i.nextLine());
					int itemSellerId = s.getID();
					Item item = new Item(itemName, itemPrice, itemAmout, itemSellerId);
					m.getItems().add(item);
					m.getSellers().get(currentUser.getID()).addItem(item);
					System.out.println("add item successful");
					break;
				case 2 :
					System.out.println("What's the item ID of itme you want to delete");
					int itemId = Integer.parseInt(i.nextLine());
					Item item1 = m.getItems().get(itemId);
					s.removeItem(item1);
					System.out.println("delete item successful");
					break;
				case 3 :
					s.getItem();
					break;
				case 4 :
					s.getHistory();
					break;
				case 5 :
					this.logOut();
					return;
				default :
					break;
			}
		}
		
	}
	
	/**
	 * General operation without login
	 * @throws IOException
	 */
	private void generalOperate() throws IOException {
		while (true) {
			System.out.println("Welcome to Our Online Marketplace!\nWhat would you like to do?\n1. Login\n2. Regist\n3. Exit");
			int value = Integer.parseInt(in.nextLine());
			while(value!= 1 && value!= 2 && value!= 3) {
				System.out.println("must tpye 1-3");
				value = Integer.parseInt(in.nextLine());
			}

			switch (value) {
				case 1 :
					this.setCurrentUser(m.login(2));
					this.sellerOperate(in);
					break;
				case 2 :
					m.regist(2, in);
					System.out.println("Please log in");
					this.setCurrentUser(m.login(2));
					this.sellerOperate(in);
					break;
				case 3 :
					System.out.println("Thanks for coming!");
					return;
				default :
					break;
			}
		}
	}
	
	// main program
	public static void main(String[] args) throws FileNotFoundException, IOException {
		SellerClient newProgram = new SellerClient();
		newProgram.generalOperate();
	}
}
