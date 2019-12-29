package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.BaseFramework;
import framework.WebServices;
import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import utils.EndPointURL;
import utils.URL;

/**
 * 
 * @author ashwin
 *
 */

public class TC006_PreemtiveAuthorization extends BaseFramework{
	
	@Test
	public void testAuthorization(){
		
		//Basic Authentication
		PreemptiveBasicAuthScheme authScheme= new PreemptiveBasicAuthScheme();
		authScheme.setUserName("ToolsQA");
		authScheme.setPassword("TestPassword");
		
		RestAssured.authentication= authScheme;
		
		res = WebServices.Get(URL.baseURL_AUTHENTICATION + EndPointURL.GET_AUTHENTICATION.getResourcePath());
        
		// Status code validation
		int statusCode = res.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		String successMessage = res.then().extract().path("Fault");
		String message="Operation completed successfully";
		
		Assert.assertEquals(message, successMessage);

	}

}
