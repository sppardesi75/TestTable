package utils;

import java.util.List;

import managers.ExtentTestManager;

public class SortUtils {

	/** Returns true if the list is sorted in ascending numerical order. */
	public static boolean isNumericallyAscending(List<Integer> list) {
		ExtentTestManager.log.info("Checking if list is numerically ascending, size: " + list.size());
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i) > list.get(i + 1)) {
				ExtentTestManager.log.info("Numerical sort check failed at index " + i
						+ ": " + list.get(i) + " > " + list.get(i + 1));
				return false;
			}
		}
		ExtentTestManager.log.info("List is numerically ascending");
		return true;
	}

	/** Returns true if the list is sorted in ascending alphabetical order. */
	public static boolean isAlphabeticallyAscending(List<String> list) {
		ExtentTestManager.log.info("Checking if list is alphabetically ascending, size: " + list.size());
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i).compareTo(list.get(i + 1)) > 0) {
				ExtentTestManager.log.info("Alphabetical sort check failed at index " + i
						+ ": \"" + list.get(i) + "\" > \"" + list.get(i + 1) + "\"");
				return false;
			}
		}
		ExtentTestManager.log.info("List is alphabetically ascending");
		return true;
	}

}
