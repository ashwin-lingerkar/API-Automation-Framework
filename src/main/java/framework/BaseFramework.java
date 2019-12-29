package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.testng.ITestResult;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;

import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import framework.DataBase;


/**
 * 
 * @author ashwin
 *
 */



public class BaseFramework {
	
	
	Map<String, String> headers;
	public Response res;
	public ExtentReports reporter;
	public ExtentTest logger;
	public  SoftAssert  softAssert = new SoftAssert();
	public String reportPath;
	public HashMap<String, Object> panels = new HashMap<String, Object>();
	public int projectid = -1;
	public String className;
	public Package packageName;
	public ExtentReports report;
	public String errorMessage;
	
	private static String propFilePath = "./resource/messages.properties";
	
	
		public Properties loadPropertiesFile(String configFile) throws IOException{
		Properties prop = new Properties();
		FileInputStream config = new FileInputStream(configFile);
		prop.load(config);
		return prop;
	   }
		/**
		 * Function to load framework configuration file
		 * @return
		 * @throws IOException 
		 */
		public static Properties loadProperties() throws IOException
		{
			File propFile = new File(propFilePath);
			FileInputStream fileInput = null;
			
			try{
				fileInput = new FileInputStream(propFile);
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
			Properties paramPropFile = new Properties();
			//load properties file
			try
			{
				paramPropFile.load(fileInput);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			fileInput.close();
			return paramPropFile;
		}
		
		
		/**
		 * Function to GET value for a key from a configuration properties file
		 * @param strKey
		 * @return
		 * @throws IOException
		 */
		public static String getProperty(String strKey)
		{
			String value = null;
			
			try {
				value = loadProperties().getProperty(strKey);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return value;
		}
	
	   @BeforeMethod(alwaysRun = true)
	    public void intailize(Method method) {
	    	  report = ExtendFactory.getInstance().getReports();
	          logger = report.startTest((this.getClass().getSimpleName() + " --> " + method.getName()), method.getName());
	          logger.assignAuthor("Owner : Ashwin Lingerkar");
	          logger.assignCategory(this.getClass().getSimpleName());

	    }


	    @AfterMethod(alwaysRun = true)
	    public void clear(ITestResult result) throws IOException {
	         // headers.clear();
	          boolean error = false;
	          try {
	                softAssert.assertAll();
	                
	          } catch (java.lang.AssertionError e) {
	                error = true;
	               logger.log(LogStatus.FAIL,"FAIL");
	               logger.log(LogStatus.INFO, "Status Code: \n" + res.getStatusCode());
	   			   logger.log(LogStatus.INFO, "Response: \n" + res.prettyPrint());
	   			   logger.log(LogStatus.FAIL, e.getMessage());
	             
	                   }
	          if (!error) {
	                if (result.getStatus() == ITestResult.FAILURE) {
	                      logger.log(LogStatus.FAIL, result.getThrowable());
	                } else {
	                    logger.log(LogStatus.PASS, "PASS");
	                   logger.log(LogStatus.INFO, "Status Code: \n" + res.getStatusCode());
	                  logger.log(LogStatus.INFO, "Response: \n" + res.prettyPrint());
	                }
	          }
	          
	          report.endTest(logger);
	        report.flush();
	          softAssert = new SoftAssert();

	    }
	    
	    /* Sets the environment using payload specific config file per environment
	     * @param String - value
	     * @return Properties
	     * @throws Exception
	     */
	    public Properties setEnvironment(String value) throws Exception {
	    	Properties environment = new Properties();
	    	if (value.equals("IAT")) {
	    	environment = loadPropertiesFile("./resource/Environments/IAT.properties");	
	        } 
	    	else if (value.equals("UAT")) {
	        environment = loadPropertiesFile("./resource/Environments/UAT.properties");	
	        }
	    	else if(value.equals("PROD")) {
	    	environment = loadPropertiesFile("./resource/Environments/PROD.properties");	
	    	}
	    	else if(value.equals("DIT1")) {
	        environment = loadPropertiesFile("./resource/Environments/DIT1.properties");	
	        }
	    	else if(value.equals("DIT2")) {
		        environment = loadPropertiesFile("./resource/Environments/DIT2.properties");	
		        }
	    	else if(value.equals("UAT")) {
	            environment = loadPropertiesFile("./resource/Environments/UAT.properties");	
	            }
	    	return environment;
	    }
	    
	   
	    
	   
		

}
