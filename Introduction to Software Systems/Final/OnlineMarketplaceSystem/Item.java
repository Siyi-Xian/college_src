package OnlineMarketplaceSystem;

public class Item {
	private int sellerID;
	private double price;
	private int amount;
	private String name;
	
	public Item(String n, double p, int amo, int sID) {
		this.setName(n);
		this.setPrice(p);
		this.setAmount(amo);
		this.setSellerID(sID);
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sellerID
	 */
	public int getSellerID() {
		return sellerID;
	}

	/**
	 * @param sellerID the sellerID to set
	 */
	public void setSellerID(int sellerID) {
		this.sellerID = sellerID;
	}
	
	public String toString() {
		String info = "";
		
		info += this.getName() + "\n";
		info += "Price: " + (this.getPrice() + "") + "\n";
		info += "Seller: " + (this.getSellerID() + "") + "\n\n";
		
		return info;
	}
}
