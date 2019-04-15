package OnlineMarketplaceSystem;

import java.util.ArrayList;

public class Category {
	private ArrayList<Item> items;
	private String name;
	
	public Category(String n, int itID) {}
	
	public void addItem(Item it) {}
	
	public void removeItem(Item it) {}
	
	public Item getIndexOf() {
		return null;
	}

	/**
	 * @return the items
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(ArrayList<Item> items) {
		this.items = items;
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
}
