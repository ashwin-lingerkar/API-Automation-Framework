package framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;


/**
 * 
 * @author ashwin
 *
 */

public class ConfiguratorSupport {
	
	private static String propFilePath = "./resource/config.properties";
	
	/**
	 * Function to load framework configuration file
	 * @return
	 * @throws IOException 
	 */
	public static Properties loadPropertiesFile() throws IOException
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
			value = loadPropertiesFile().getProperty(strKey);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * Function to Retrieve Environment name from framework configuration file
	 * 
	 * @return
	 * @throws IOException
	 */
	public static String gGetEnvironment() {
		return getProperty("testEnv").toString();
	}
	

	// return date
		public static String gGetTodaysDateWithDashSeparator() {
			Date todaysDate = new Date(); 
			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
			String formattedDate = formatter.format(todaysDate);
			return formattedDate;
		}
		
		public static String gGetCurrentTimeWithoutSeparator() {
			Date todaysDate = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
			String formattedDate = formatter.format(todaysDate);
			return formattedDate.replaceAll(":", "");
		}
}
