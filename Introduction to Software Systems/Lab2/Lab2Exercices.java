////////////////////////////////////////////////////////////////////////////////////
//
//  H212 Fall 16
//  Lab 2 
//  
//  Released:  8/26/16
//  Due:       9/4/16 11:59 PM
//
//  @Author  Siyi Xian  siyixian
//  Last Edited:  
//
//
//  Directions:  Implement the following methods 
//               
//               
//////////////////////////////////////////////////////////////////////////////////

// Note:  Do not need to explicitly import classes from java.lang but wanted to make it explicit 
// Goal - understand using Static classes - i.e. the Math class was not designed to be Instantiated (make objects) 
// It is a class that provides functionality and will be used to complete one of the methods
import java.lang.Math;

public class Lab2Exercices {
    
    // computes volume of a sphere when given a radius
    public static double volumeOfSphere(int radius) {
      //TODO
      return 4.0 / 3.0 * Math.PI * Math.pow(radius, 3.0);
    }
    
    // computes circumference of a circle
    public static double circumference(int radius) {
      //TODO
      return 2 * Math.PI * radius;
    }
    
    // returns a String, in the following format "SquareArea: *, Perimeter: *, Diagonal: *" where * refers to square of 
    // the rectanglar area, perimeter, and the diagonal of the square respectively 
    // (use Pythagorean Theorem)
    public static String square(int len) {
      //TODO
      String SquareArea = Integer.toString(len * len);
      String Perimeter = Integer.toString(len * 4);
      String Diagonal = Double.toString(len * Math.sqrt(2));
      
      return "SquareArea: " + SquareArea + ", Perimeter: " + Perimeter + ", Diagonal: " + Diagonal;
    }
    
    // reads a number between 1,000 and 999,999 and prints it with a \ separating the thousands
    // hint use modulus (%) to save part of the number, then concatenate back to gether as a String
    public static String addBackSlash(int num) {
      //TODO
      String LargerThanThousands = Integer.toString(num / 1000);
      String SmallerThanThousands = Integer.toString(num % 1000);
      
      while (SmallerThanThousands.length() < 3)
    	  SmallerThanThousands = "0" + SmallerThanThousands;
      
      System.out.print(LargerThanThousands);
      System.out.print("\\");
      System.out.println(SmallerThanThousands);
      
      return "value is " + LargerThanThousands + "\\" + SmallerThanThousands;
    }
    
    // given angle in Degrees, convert to radians (hint look at Math class Java doc API)
    // and return a string of the Sin, Cos, and Tangent of the angle  
    public static String trigFunctions(double angle) {
      double AngleInRadians = Math.toRadians(angle);
      
      String Sin = Double.toString(Math.sin(AngleInRadians));
      String Cos = Double.toString(Math.cos(AngleInRadians));
      String Tan = Double.toString(Math.tan(AngleInRadians));
      
      return "Sin is " + Sin + ", Cos is " + Cos + ", Tangent is " + Tan;
    }
    
 
    // test client 
    public static void main(String[] args) {
      //TODO: Modify to test all functions 
        System.out.println(Lab2Exercices.volumeOfSphere(1));
        System.out.println(Lab2Exercices.circumference(1));
        System.out.println(Lab2Exercices.square(1));
        System.out.println(Lab2Exercices.addBackSlash(123456));
        System.out.println(Lab2Exercices.trigFunctions(45));
        System.out.printf("The character %c has the value %d.\n", 'A', ( (int) 'A' ) );
    }
}

///////////////////////////////////
//                               //
// ANSER THE FOLLOWING QUESTIONS //
//                               //
///////////////////////////////////

/*
 * Questions 1-3 are on explicit and implicit casting of some numerical types  
 *
 * 1.  What happens if you multipy a double with a char? 
 *     The computer will regard the given char as the number of this char in ASCII Table.
 *     Then it will do the normal multipy with the given double.
 *     For example, 'a' * 2.0 = 194.0.
 * 
 * 2.  What happens if a method has a paramater of type int, but you pass it a char?
 *     The computer will regard the given char as the number of this char in ASCII Table.
 *     Then, it will run normally.
 * 
 * 3.  What happens if a mehtod has a paramater of type char, but you pass it an int? 
 *     There will be an error when compiling.
 *     The compiler will tell us that possible lossy converaion from int to char.
 * 
 * 4.  What are the 8 primitive data types in the Java language 
 *     Int, Byte, Short, Long, Double, Float, Char, Boolean.
 * 
 * 5.  Consider the following code snippet.
 *     int i = 10;
 * 
 *     n = ++(i++);
 *     System.out.println(++(i++)) % 5;
 * 
 *     What are the values of i and n after the code is executed?
 *     n = 12, i = 12;
 *     What are the final value printed?
 *     0
 */
