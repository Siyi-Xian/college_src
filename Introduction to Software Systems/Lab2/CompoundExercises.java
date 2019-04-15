////////////////////////////////////////////////////////////////////////////////////
//
//  C212
//   
//  Created:  1/18/16
//  Lab 2
//  @Author  Siyi Xian  siyixian
//  Last Edited:  
//
//
//  Directions: See Below 
//               
//////////////////////////////////////////////////////////////////////////////////

// Change the following program to use compound assignments: Then repeat the process to use post increments only.
class CompoundExercises {

     public static void compoundOperators(){
          
          int result = 1 + 2; // result is now 3
          System.out.println(result);

          //result = result - 1; // result is now 2
          result -= 1; // result--; is also fine
          System.out.println(result);

          //result = result * 2; // result is now 4
          result *= 2;
          System.out.println(result);

          //result = result / 2; // result is now 2
          result /= 2;
          System.out.println(result);

          //result = result + 8; // result is now 10
          result += 8;
          //result = result % 7; // result is now 3
          result %= 7;
          System.out.println(result);
     }
     
     // In the following program, explain why the value "6" is printed twice in a row:
     // After printing "5", i = 5. When is comes to next step, because it is ++i, i need to add 1 first. So the first "6" comes out.
     // Then, because it is i++, i will printed first then add 1. So the secound "6" comes out.
     public static void question1() {
        int i = 3;
        i++;
        System.out.println(i);    // "4"
        ++i;                     
        System.out.println(i);    // "5"
        System.out.println(++i);  // "6"
        System.out.println(i++);  // "6"
        System.out.println(i);    // "7"
     }
}