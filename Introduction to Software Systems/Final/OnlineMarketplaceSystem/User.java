package OnlineMarketplaceSystem;

abstract class User {
	private int ID;
	private String passwords;
	private String email;
	private String name;
	private String address;
	private boolean status; // true for buyer
	
	public User(String e, String n, String p, String add, int id) {
		this.setAddress(add);
		this.setEmail(e);
		this.setName(n);
		this.setPasswords(p);
		this.setID(id);
	}
	
	/**
	 * @return the passwords
	 */
	public String getPasswords() {
		return passwords;
	}

	/**
	 * @param passwords the passwords to set
	 */
	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}
	
	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	/**
	 * @return the status
	 */
	public boolean getStatus() {
		return status;
	}
	
	public String toString() {
		return this.getName() + "(" + (this.getID() + "") + ")";
	}
}
