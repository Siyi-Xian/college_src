import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class Child implements Map
{
   public static void main (String[] args) {
	   LinkedList<String> words = new LinkedList<>();
	   words.addFirst("abc");
	   words.addLast("def");
	   words.addFirst("ghi");
	   System.out.print(words.removeLast());
	   System.out.print(words.removeFirst());
	   System.out.print(words.removeLast());
   }
   public static int count(LinkedList<String> theList, String name)
   {
      int number = 0;
      Iterator<String> iter = theList.iterator();

      while (iter.hasNext())
      {
         if (iter.next().equals(name))
         {       
            number++;
         }
      }
      return number;
   }
@Override
public int size() {
	// TODO Auto-generated method stub
	return 0;
}
@Override
public boolean isEmpty() {
	// TODO Auto-generated method stub
	return false;
}
@Override
public boolean containsKey(Object key) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public boolean containsValue(Object value) {
	// TODO Auto-generated method stub
	return false;
}
@Override
public Object get(Object key) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public Object put(Object key, Object value) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public Object remove(Object key) {
	// TODO Auto-generated method stub
	return null;
}
@Override
public void putAll(Map m) {
	// TODO Auto-generated method stub
	
}
@Override
public void clear() {
	// TODO Auto-generated method stub
	
}
@Override
public Set keySet() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public Collection values() {
	// TODO Auto-generated method stub
	return null;
}
@Override
public Set entrySet() {
	// TODO Auto-generated method stub
	return null;
}

}