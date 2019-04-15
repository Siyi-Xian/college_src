package OnlineMarketplaceSystem;

public class Transaction {
	private Buyer buyer;
	private Seller seller;
	private Item item;
	private int quantity;
	private String date;
	private int trakingNumber;
	
	public Transaction(Buyer b, Seller s, Item i, int q, String d, int t) {
		this.setBuyer(b);
		this.setSeller(s);
		this.setItem(i);
		this.setQuantity(q);
		this.setDate(d);
		this.setTrakingNumber(t);
	}
	
	public Transaction getTransaction() {
		return this;
	}

	/**
	 * @return the buyer
	 */
	public Buyer getBuyer() {
		return buyer;
	}

	/**
	 * @param buyer the buyer to set
	 */
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	/**
	 * @return the seller
	 */
	public Seller getSeller() {
		return seller;
	}

	/**
	 * @param seller the seller to set
	 */
	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	/**
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item the item to set
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the trakingNumber
	 */
	public int getTrakingNumber() {
		return trakingNumber;
	}

	/**
	 * @param trakingNumber the trakingNumber to set
	 */
	public void setTrakingNumber(int trakingNumber) {
		this.trakingNumber = trakingNumber;
	}
	
	public String toString() {
		String info = "";
		
		info += "Buyer: " + this.getBuyer().toString() + "\n";
		info += "Transaction Date: " + this.getDate() + "\n";
		info += "Quantity: " + (this.getQuantity() + "") + "\n";
		info += "Total Amount: " + (this.getQuantity() * this.getItem().getPrice() + "") + "\n";
		info += "Traking Number: " + (this.getTrakingNumber() + "") + "\n";
		info += "Item Details: " + this.getItem().toString();
		
		return info;
	}
}
