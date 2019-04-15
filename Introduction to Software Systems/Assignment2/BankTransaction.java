import java.util.Scanner;

public class BankTransaction {
	private double saving;
	private double checking;
	
	public BankTransaction(){}
	
	// Constructor
	public BankTransaction(double s, double c) {
		this.saving = s;
		this.checking = c;
	}
	
	// Get initial value from user
	private double initialBalnace() {
		Scanner scanOne = new Scanner(System.in);
		double temp_ban = scanOne.nextDouble();
		
		if (temp_ban >= 0)
			return temp_ban;
		else{
			System.out.print("We do not accept nigtive balance. Please re-enter it: ");
			return initialBalnace();
		}
	}
	
	// Get a line of input as String
	private String getString() {
		Scanner scan_two = new Scanner(System.in);
		return scan_two.nextLine();
	}
	// Get next number as double
	private double getDouble() {
		Scanner scan_three = new Scanner(System.in);
		return scan_three.nextDouble();
	}
	
	// Withdraw money in given account.
	private double withdrawal(double ban) {
		System.out.print("Please enter the money you want to withdraw: ");
		
		double wd = getDouble();
		if (wd < 0) {
			System.out.println("Sorry, we do not accept nigtive withdraw. Please re-enter it."); // reject the negative withdraw.
			return withdrawal(ban);
		}
		else 
			if(wd > ban) {
				System.out.println("Sorry, we do not accept overdraw. Please re-enter it."); //reject the overdraw.
				return withdrawal(ban);
			}
			else
				return ban - wd; //withdraw
	}
	
	// Deposit money in given account.
	private double deposit(double ban) {
		System.out.print("Please enter the money you want to deposit: ");
		
		double wd = getDouble();
		if (wd < 0) {
			System.out.println("Sorry, we do not accept nigtive deposit. Please re-enter it."); //reject negative deposit.
			return deposit(ban);
		}
		else 
			return ban + wd; //deposit
	}
	
	// Display the balance.
	private void balance(String account, double ban) {
		System.out.println("The balance of your " + account + " account is $" + (ban + "") + ".");
	}
	
	// Ask user to choose which transaction to do, then run it.
	public BankTransaction transactionMenu() {
		System.out.print("Please choose one of the following transactions you want to do today:\n1. Withdrawal\n2. Deposit\n3. Balance\n4. Exit\n");
		
		// Check which type of transaction
		String type = getString();
		if (type.equals("4") || type.equals("Exit")) 
			return new BankTransaction(-1, -1); //Exit
		
		// Check which account is using
		System.out.print("Which account are you going to use:\nSaving\nChecking\n");
		String account = getString();
		
		// Check which transaction need to be done, then run it.
		if (account.equals("Saving"))
			switch (type) {
				case "1" : saving = withdrawal(saving); break;
				case "2" : saving = deposit(saving); break;
				case "3" : balance(account, saving); break;
				case "Withdrawal" : saving = withdrawal(saving); break;
				case "Deposit" : saving = deposit(saving); break;
				case "Balance" : balance(account, saving); break;
			}
		else
			switch (type) {
				case "1" : checking = withdrawal(checking); break;
				case "2" : checking = deposit(checking); break;
				case "3" : balance(account, checking); break;
				case "Withdrawal" : checking = withdrawal(checking); break;
				case "Deposit" : checking = deposit(checking); break;
				case "Balance" : balance(account, checking); break;
			}
		
		return new BankTransaction(saving, checking); // Return the new value of the given account after transactions.
	}
	
	public static void main(String[] args) {
		BankTransaction bc = new BankTransaction();
		double temp_s, temp_c;                                                         // Saving saving and checking account balance temporarily.
		System.out.print("Please enter the initial balance of your count:\nSaving: "); // promote user to enter their balance.
		temp_s = bc.initialBalnace();                                                  // get the value of initial saving account.
		System.out.print("Checking: ");                                                // promote user to enter their balance.
		temp_c = bc.initialBalnace();                                                  // get the value of initial checking account.
		
		bc = new BankTransaction(temp_s, temp_c);                                      // build a new bank account.
		
		// to do transaction
		while (bc.saving >= 0 && bc.checking >= 0)
			bc = bc.transactionMenu();
	}
}