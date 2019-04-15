public class Year {
	private int year;
	
	/**
	 * Constructor
	 */
	public Year () {
		this.year = 0;
	}
	
	/**
	 * Constructor
	 * @param y : year in Integer
	 */
	public Year (int y) {
		this.year = y;
	}
	
	/**
	 * get the value of this year
	 * @return year in Integer
	 */
	public int get () {
		return year;
	}
	
	/**
	 * Display the value of this year
	 */
	public void print () {
		System.out.println(get());
	}
	
	/**
	 * get next year
	 * @return next year in Year
	 */
	public Year next () {
		return new Year(year + 1);
	}
}
