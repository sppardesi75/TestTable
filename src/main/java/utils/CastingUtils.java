package utils;

import java.util.ArrayList;
import java.util.List;

public class CastingUtils {
	
	public static List<Integer> stringListToIntList(List<String> list){
		
		List<Integer> intList = new ArrayList<>();

		for (String s : list) {
		    intList.add(Integer.parseInt(s));
		}

		return intList;
	}

}
