package testscripts;

import org.testng.annotations.Test;

import framework.BaseFramework;
import framework.WebServices;
import io.restassured.RestAssured;
import utils.EndPointURL;
import utils.URL;

/**
 * 
 * @author ashwin
 *
 */

public class TC009_OAuth2_Demo extends BaseFramework{
	public String oAuth2Token;
	@Test(priority=2)
	public void testPost() throws Exception{
		
	  oAuth2Token =WebServices.getOauth2Token();
	  res = RestAssured
				.given()
				.auth()
				.oauth2(oAuth2Token)
				.post(URL.baseURL_coops+EndPointURL.GET_CHIKEN_INFO.getResourcePath());
				softAssert.assertEquals(res.getStatusCode(), 401);
				if(res.statusCode() == 401){
					errorMessage= res.jsonPath().getString("error_message");
					  String err = getProperty("error_message");
					
					softAssert.assertTrue(errorMessage.equals(getProperty("error_message")));
				}
	}

}
