public class Month {
	private int month;
	
	/**
	 * Constructor
	 * @param m : input as Integer
	 */
	public Month(int m) {
		this.month = m;
	}
	
	/**
	 * set month
	 * @param m : the month which in three letters
	 * @return the value of month in integer
	 */
	private int setMonth (String m) {
		switch (m) {
			case "Jan" : return 1;
			case "Feb" : return 2;
			case "Mar" : return 3;
			case "Apr" : return 4;
			case "May" : return 5;
			case "Jun" : return 6;
			case "Jul" : return 7;
			case "Aug" : return 8;
			case "Sep" : return 9;
			case "Oct" : return 10;
			case "Nov" : return 11;
			case "Dec" : return 12;
			default : return 0;
		}
	}
	
	/**
	 * Constructor
	 * @param m : input as three letters String
	 */
	public Month(String m) {
		this.month = setMonth(m);
	}
	
	/**
	 * Constructor
	 */
	public Month() {
		this.month = 0;
	}
	
	/**
	 * Get the value of this month
	 * @return month in Integer
	 */
	public int get () {
		return month;
	}
	
	/**
	 * Get the value of this month
	 * @return month in three letter String
	 */
	public String getMonthS () {
		switch (month) {
			case 1 : return "Jan";
			case 2 : return "Feb";
			case 3 : return "Mar";
			case 4 : return "Apr";
			case 5 : return "May";
			case 6 : return "Jun";
			case 7 : return "Jul";
			case 8 : return "Aug";
			case 9 : return "Sep";
			case 10 : return "Oct";
			case 11 : return "Nov";
			case 12 : return "Dec";
			default : return "";
		}
	}
	
	/**
	 * Display this month in Integer
	 */
	public void print () {
		System.out.println(get());
	}
	
	/**
	 * Display this month in three letter String
	 */
	public void printStr () {
		System.out.println(getMonthS());
	}
	
	/**
	 * get next month
	 * @return the value of next month in Month
	 */
	public Month next () {
		return new Month(month + 1);
	}
}
