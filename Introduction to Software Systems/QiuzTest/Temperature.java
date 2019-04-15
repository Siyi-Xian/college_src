public class Temperature implements Comparable
{
   private int value;
   
   // other methods go here
 
   public int compareTo(Object otherObject)
   { 
	  Temperature otherTemp = (Temperature) otherObject;
	  return (value - otherTemp.value);
	  
   }
}