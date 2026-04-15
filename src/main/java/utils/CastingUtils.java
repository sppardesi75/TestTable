package utils;

import java.util.ArrayList;
import java.util.List;

import managers.ExtentTestManager;

public class CastingUtils {
	
	public static List<Integer> stringListToIntList(List<String> list){
		ExtentTestManager.log.info("Converting String list to Integer list, item count: " + list.size());
		List<Integer> intList = new ArrayList<>();

		for (String s : list) {
		    intList.add(Integer.parseInt(s));
		}
		ExtentTestManager.log.info("Conversion complete: " + intList.size() + " integers converted");
		return intList;
	}

}
