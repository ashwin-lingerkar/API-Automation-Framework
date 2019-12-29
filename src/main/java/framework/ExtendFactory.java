package framework;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;


/**
 * 
 * @author ashwin
 *
 */

public class ExtendFactory extends BaseFramework{
	private static final ExtendFactory fact = new ExtendFactory();
	private final ExtentReports report;
	private ExtendFactory() {
		
		String reportPath = "reports"+ "\\"
				+ConfiguratorSupport.gGetTodaysDateWithDashSeparator()+"_"
				+ConfiguratorSupport.gGetCurrentTimeWithoutSeparator()
				+ "\\" + "Report"+"_"
				+ ConfiguratorSupport.gGetCurrentTimeWithoutSeparator() + ".html";
			report = new ExtentReports(reportPath, false); 
			report.loadConfig(new File("./resource/extent-config.xml"));
	}
	public static ExtendFactory getInstance(){
		return fact;
	}
	public ExtentReports getReports() {
		return report;
	}
	
}
