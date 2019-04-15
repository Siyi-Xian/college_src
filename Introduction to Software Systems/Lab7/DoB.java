public class DoB extends Day{
	private Month month;
	private int day;
	private Year year;
	
	/**
	 * Constructor
	 */
	public DoB() {
		this.month = new Month();
		this.year = new Year();
		day = 0;
	}
	
	/**
	 * Constructor
	 * @param m : Month
	 * @param d : Day
	 * @param y : Year
	 */
	public DoB (Month m, int d, Year y) {
		this.month = m;
		this.year = y;
		this.day = d;
	}
	
	/**
	 * Get the day
	 * It is abstracted from Day class
	 */
	public int get () {
		return day;
	}
	
	/**
	 * Display the birthday
	 * It is abstracted from Day class 
	 */
	public void print () {
		System.out.println(month.getMonthS() + " " + (day + "") + ", " + (year.get() + ""));
	}
	
	/**
	 * Get next month
	 * @return the DoB of next month
	 */
	public DoB nextMonth () {
		return new DoB(month.next(), day, year);
	}
	
	/**
	 * Get next day
	 * It is abstracted from Day class
	 * @return the DoB of next day
	 */
	public DoB nextDay () {
		return new DoB(month, day + 1, year);
	}
	
	/**
	 * Get next year
	 * @return the DoB of next year
	 */
	public DoB nextYear () {
		return new DoB(month, day, year.next());
	}
	
	/**
	 * test case
	 * @param args
	 */
	public static void main (String[] args) {
		Month birthMonth = new Month("Sep");
		Month birthMonthNum = new Month(9);
		
		birthMonth.print();
		birthMonth.printStr();
		birthMonthNum.print();
		birthMonthNum.printStr();
		
		Year birthYear = new Year(1998);
		birthYear.print();
		
		DoB birth = new DoB(birthMonth, 17, birthYear);
		birth.print();
	}
}
