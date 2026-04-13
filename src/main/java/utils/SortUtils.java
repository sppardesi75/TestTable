package utils;

import java.util.List;



public class SortUtils {
	
	public static boolean verifyNumericalSort(List<Integer> list) {
		
		for (int i = 0; i < list.size() - 1; i++) {
	        // If current number is greater than the next, it's not sorted
	        if (list.get(i) > list.get(i + 1)) {
	            return false; 
	        }
	    }
	    return true;
		
	}
	
	
	public static boolean verifyAlphabeticalSort(List<String> list) {
	    for (int i = 0; i < list.size() - 1; i++) {
	        // compareTo > 0 means the first word comes AFTER the second word
	        if (list.get(i).compareTo(list.get(i + 1)) > 0) {
	            return false;
	        }
	    }
	    return true;
	}

	

}
