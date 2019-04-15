import java.util.Scanner;

/**
 * Name: Siyi Xian
 * User Name: siyixian
 */

public class PointAccumulator {
	Point[] points; // An array of type point
	
	// constructor
	public PointAccumulator() {}
	
	// populates points array from user input
	public String run(){
		final int N; // final means variable can’t be changed once initialized
		Scanner scan = new Scanner(System.in); //set up a new scanner
		System.out.print("Please enter the number of points you wish: "); // prompt users to enter the number of points
		
		if (scan.hasNextInt())
			N = scan.nextInt(); // process input (assign a value to N from the scanner)
		else return "invalid input";
		
		points = new Point[N]; // initialize array points
		
		System.out.print("Please enter the x and y coordinate one by one: "); //prompt users to enter each points
		for (int i = 0; i < N; i++) {
			Scanner newscan = new Scanner(System.in); //set up a new scanner
			int x = 0, y = 0, j = 0;
			String temp_coordinate = newscan.nextLine(); //get the point as String
			
			// transfer String to int
			while (temp_coordinate.charAt(j) != ' ')
				j++;
			x = Integer.parseInt(temp_coordinate.substring(0, (j)));
			while (0 <= (temp_coordinate.charAt(j) - '0') && (temp_coordinate.charAt(j) - '0') <= 9) {
				y = y * 10 + (temp_coordinate.charAt(j) - '0');
				j++;
			}
			y = Integer.parseInt(temp_coordinate.substring(j + 1));
			
			points[i] = new Point(x, y); 
		}
		
		// get both  two answers using two helper function
		String max_origin = FindMaxToOrigin(points);
		String max_each = FindMaxToEach(points);
		
		return max_origin + "\n" + max_each;
	}
	
	// Use a helper method to find the max distance to origin
	private static String FindMaxToOrigin(Point[] ps) {
		double temp_max = 0; //store the furthest distance till now
		Point origin = new Point(0, 0); //set up the origin point
		int max_point = 0; //store the number of furthest point till now
		
		//find the max distance one by one
		for (int i = 0; i < ps.length; i++)
			if (temp_max < origin.distanceTo(ps[i])) {
				temp_max = origin.distanceTo(ps[i]);
				max_point = i;
			}
		
		// return the furthest point from the origin point.
		return "Point furthest from origin is: " + ps[max_point].toString();
	}
	
	// Use a helper method to find the max distance to each other
	private static String FindMaxToEach(Point[] ps) {
		double temp_max = 0; //store the max distance between each other till now
		// store the number of two points which have the max distance.
		int max_point_1 = 0; 
		int max_point_2 = 0;
		
		//find the max distance one by one
		for (int i = 0; i < ps.length; i++)
			for (int j = i; j < ps.length; j++)
				if (temp_max < ps[j].distanceTo(ps[i])) {
					temp_max = ps[j].distanceTo(ps[i]);
					max_point_1 = i;
					max_point_2 = j;
				}
		
		//return the two points which have max distance
		return "Points with furthest distance are: " + ps[max_point_1].toString() + " " + ps[max_point_2].toString();
	}
	
	public static void main(String[] args) {
		PointAccumulator p = new PointAccumulator(); 
		System.out.println(p.run()); // display all results
	} 
}