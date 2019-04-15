package SelectSort;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class SlectSort {
	private <T extends Comparable <T>> T findTarget(T o, T t, String status) {
		if (status.equals("ascending"))
			if (o.compareTo(t) < 0)
				return o;
			else
				return t;
		else
			if (o.compareTo(t) < 0)
				return t;
			else
				return o;
	}
	
	public <T extends Comparable <T>> List<T> sort(List<T> list, String status) {
		List<T> sorted = new ArrayList<T>();
		
		while (list.size() > 0) {
			T target = list.get(0);
			
			for (T find : list)
				target = this.findTarget(target, find, status);
			
			sorted.add(target);
			list.remove(target);
		}
		
		return sorted;
	}
	
	public static void main (String[] args) {
		Result result = JUnitCore.runClasses(SlectSortTest.class);
        for (Failure failure : result.getFailures()) {
           System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
	}
}
