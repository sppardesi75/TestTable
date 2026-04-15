package managers;

import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {
	
	public static ExtentTest log;
	
	public static void createTest(String methodName) {
		log = ExtentManager.extent.createTest(methodName);
	}

}
