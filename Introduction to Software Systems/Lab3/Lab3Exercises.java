import java.util.Scanner;

/**
 * Name : Siyi Xian
 * User Name : siyixian
 */
public class Lab3Exercises {
	// Problem a
	// return the nth number in the Fibonacci sequence
	public static int fib(){
		// start value in the fibonacci sequence
		int ThisNumber = 1;
		int PreNumber = 0;
		
		System.out.print("Please enter a integer: ");
		Scanner in = new Scanner(System.in);
		// Get input
		int num = in.nextInt();
		
		while (num != 1){
			int k = ThisNumber;
			
			ThisNumber += PreNumber;
			PreNumber = k;
			
			num--;
		}
		
		return ThisNumber;
	}
	
	//Problem b
	// find the sum, max value, and min value of a list of number enter by user.
	public static String numbers(){
		int MaxValue = 0;
		int MinValue = 0xfffffff;
		int sum = 0;
		int num;
		
		System.out.print("Please enter a list of integer, end with something other than number: ");
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()){
			num = in.nextInt();
			
			// check if this number is max
			if (num > MaxValue)
				MaxValue = num;
			// check if this number is min
			if (num < MinValue)
				MinValue = num;
			// add this number to sum
			sum += num;
		}
		
		String StrMax = "" + MaxValue;
		String StrMin = "" + MinValue;
		String StrSum = "" + sum;
		
		return "Sum is " + StrSum + ". Max Value is " + StrMax + ". Min value is " + StrMin + ".";
	}
	
	//Problem c
	// get the grade by the score enter by user.
	public static String grade(){
		System.out.print("Please enter your score: ");
		Scanner in = new Scanner(System.in);
		// Get input
		int score = in.nextInt();
		String grade;
		
		if (score > 89.4) grade = "A";
		else if (score > 79.4) grade = "B";
			 else if (score > 69.4) grade = "C"; 
			 	  else if (score > 59.4) grade = "D"; 
			 	  	   else grade = "F";
		
		return "Your grade is a " + grade + ".";
	}
	
	//Problem d
	// change a decimal number to a binary number
	public static String intToBinary(int n){
		String BinaryNumber = "";
		
		while (n != 0){
			if (n % 2 == 0) BinaryNumber = "0" + BinaryNumber;
			else BinaryNumber = "1" + BinaryNumber;
			
			n /= 2;
		}
		
		return BinaryNumber;
	}
	
	// test program
	public static void main(String[] args){
		// test case for problem a
		System.out.println(fib());
		
		// test case for problem b
		System.out.println(numbers());
		
		//test case for problem c
		System.out.println(grade());
		
		//test case for problem d
		System.out.print("Please enter the number you want to transfer to binary: ");
		Scanner in = new Scanner(System.in);
		// Get input
		int num = in.nextInt(); 
		System.out.println(intToBinary(num));
	}
}

/*
 * Answer the following questions as comments below your class file:
 * 	i. Give the type and value for each of the following expressions:
 * 		1. (1 + 2.236)/2 
 * 			double 1.618
 * 		2. 1+2+3+4.0 
 * 			double 10.0
 * 		3. 4.1 >= 40
 * 			Boolean False
 * 		4. 1+2+“3”
 * 			it cannot be compiled
 * ii. Give the value printed by each of the following code fragments:
 * 	1. The following code in 1 is using Newton’s Method to calculate the square root of a number\
 * 
 * 		double t = 9.0;
 * 		while (Math.abs(t – 9.0/t) > .001) {
 * 			t = (9.0/t + t) / 2.0; 
 * 		}
 * 		System.out.println(t);
 * 
 * 		t = 3.00009155413138
 * 
 * 	2. 
 * 		int sum=0;
 * 		for (int i = 1; i < 1000; i++) {
 * 			for (int j = 0; j < 1000; j++) { sum++;
 * 			}
 * 		}
 * 
 * 		sum = 999000
 * 
 * 3. 
 * 		int sum=0;
 * 		for (int i = 1; i < 1000; i *= 2) {
 * 			for (int j = 0; j < 1000; j++) { sum++;
 * 			} 
 * 		}
 * 		System.out.println(sum);
 * 
 * 		sum = 10000
 */