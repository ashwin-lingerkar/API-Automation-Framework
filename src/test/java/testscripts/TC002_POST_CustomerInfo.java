package testscripts;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import framework.BaseFramework;
import framework.WebServices;
import utils.EndPointURL;
import utils.URL;

/**
 * 
 * @author ashwin
 *
 */

public class TC002_POST_CustomerInfo extends BaseFramework {

	@Test
	public void registerCustomer() {

		// Request payload sending along with post request
		JSONObject requestParams = new JSONObject();
		requestParams.put("FirstName", "john");
		requestParams.put("LastName", "hays");
		requestParams.put("UserName", "johnhays");
		requestParams.put("Password", "Jolly123");
		requestParams.put("Email", "johnays@gmail.com");

		res=WebServices.Post(URL.BASEURL_REGISTER_CUSTOMER + EndPointURL.POST_REGISTER_CUSTOMER.getResourcePath(),
				requestParams.toJSONString());
		System.out.println("Response code is "+res.getStatusCode());
		
		//Status code validation
				int statusCode = res.getStatusCode();
				Assert.assertEquals(statusCode, 201);
				
		//Validating the response success code
		String successCode = res.jsonPath().getString("SuccessCode");
		Assert.assertEquals(successCode, "OPERATION_SUCCESS");
				
	}

}

