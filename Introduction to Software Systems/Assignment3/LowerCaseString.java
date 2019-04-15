////////////////////////////////////////////////////////////////////////////////////
//
//  H212 Fall 16
//  Homework 3 
//  
//  Due: 9/24/16 11:59 PM
//
//  Author  Siyi Xian    siyixian
//  Last Edited:  22 February 2017
//               
//////////////////////////////////////////////////////////////////////////////////
/*
 * Converts a string to lowercase,
 * and displays the string's length
 * as well as a count of letters
 */

/*The program should produce the following outupt once all the bugs are fixed.
 * Output:
 * In all lowercase, the sentence is: "debugging is fun!!!" 
 * The number of CHARACTERS in the string is: 19 
 * The number of LETTERS is: 14
 */
public class LowerCaseString
{
  public static void main(String[] args)
  {
    
 String str = "Debugging is FUN!!!";//input string that needs to be converted to lowercase
    
 int numLetters = 0;
 
 //converts string to lower case and counts number of letters
    int stringLength = str.length();                                   // String.length() is a method. We need to add () after length.
    System.out.print("In all lowercase, the sentence is: \"");
    
 for(int i = 0; i < stringLength; i++)                                 // stringLength is the value which we store the length of  given string.
    {
      char ch = Character.toLowerCase(str.charAt(i));                  // i is the current index in the loop, instead of length
      System.out.print(ch);
      if(Character.isLetter(ch))                                       // Whenever we use Character.inLetter(), 
        numLetters++;                                                  // we must put a parameter which is the character we need to check.
    }
 
 //printing number of letters and characters
    System.out.println("\"");
    System.out.println("The number of CHARACTERS in the string is: " + stringLength);
    System.out.println("The number of LETTERS is: " + numLetters);     // numLetters is the variety which we count the number of letters
  }
}