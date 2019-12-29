package framework;
import java.io.FileInputStream;
import java.util.Properties;


/**
 * 
 * @author ashwin
 *
 */


public class PropertiesConfig {
	//Properties file
	public static Properties properties = new Properties();
	public static String workingos = null;
	
	public PropertiesConfig(){
		try{
			workingos = System.getProperty("os.name");
				properties.load(new FileInputStream(""));
		
		}catch(Exception e){}
	}
	
}