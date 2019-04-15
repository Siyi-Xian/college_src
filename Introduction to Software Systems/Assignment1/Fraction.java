////////////////////////////////////////////////////////////////////////////////////
//
//  H212 Fall 16
//  Homework 1
//
//  Due: 9/11/16 11:59 PM  
//
//  Author  Siyi Xian  siyixian
//  Last Edited:  1/25/2017
//
//
//  Directions:  provide code for unimplemented methods
//
//               ** The fractions do not need to be in a simplified form **
//               ** without being in simplified form it makes adding and subtracting easier **
//////////////////////////////////////////////////////////////////////////////////

public class Fraction {
    // Instance Fields declerations
    private int num; 
    private int denom;
    
    // Constructror - method that initializes class 
    // Paramaters
    // num   - numerator of fraction 
    // denom - denomenator of fraction 
    public Fraction(int num, int denom) {
         // throw Exception if denominator is 0 
        if (denom == 0) { 
            throw new ArithmeticException("Cannot dvide by zero");
        }
        
        this.num = num;
        this.denom = denom; 
    }
    
    // @return value of numerator 
    public int num() {
        return num;
    }
    
    // @return value of denomenator 
    public int denom() {
        return denom; 
    }
    
    // add 2 fractions
    public Fraction add(Fraction other) {
        // TO-DO  
    	
    	int AddNum = num * other.denom + other.num * denom;
    	int AddDenom = denom * other.denom;
    	
        return new Fraction(AddNum, AddDenom); // return statments add initialy so file compiles
                                   				   // your will need to change the return statements 
    }
    
    // subtract two fractions 
    public Fraction minus(Fraction other) {
        // TO-DO
    	
    	int SubNum = num * other.denom - other.num * denom;
    	int SubDenom = denom * other.denom;
    	
        return new Fraction(SubNum, SubDenom);
    }
    
    // multiply two fractions 
    public Fraction multiply(Fraction other) {
        // TO-DO
    	
    	int MultNum = num * other.num;
    	int MultDenom = denom * other.denom;
    	
        return new Fraction(MultNum, MultDenom);
    }
    
    // divide two fractions 
    public Fraction divide(Fraction other) {
        // TO-DO
    	
    	int DiviNum = num * other.denom;
    	int DiviDenom = denom * other.num;
    	
        return new Fraction(DiviNum, DiviDenom);
    }
    
    // returns decimal value of this fraction
    public double decimalVal() {
        // TO-DO
        // cast integer num and denom values as doubles before operating on in this method
    	
    	double ans = (double) num / (double) denom;
    	
    	return (double) ans;   
    }
    
    // returns a string with numerator / denominator 
    public String toString() {
        // TO-DO
        return Integer.toString(num) + " / " + Integer.toString(denom);
    }
    
    // Test Client 
    public static void main(String[] args) {
        // creating a Fraction object from Class Fraction 
        // also known as in Instance 
        Fraction f1 = new Fraction(5, 10);
        Fraction f2 = new Fraction(1, 3);
        
        // example call of printing the value of two fractions multiplied 
        // f1.multiply(f2) returns a new Fraction object, so we can call its toString() method
        System.out.println( f1.multiply(f2).toString() );
        
        // test other method
        System.out.println( f1.add(f2).toString() );
        System.out.println( f1.divide(f2).toString() );
        System.out.println( f1.decimalVal() );
        
    }
}