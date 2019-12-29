package testscripts;

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

public class TC008_GET_SingleUser extends BaseFramework{
	
	@Test(priority=1)
	public void getSingleUser() {
        String email;
		String url = URL.baseURL + EndPointURL.GET_SINGLE_USER.getResourcePath();
		res = WebServices.Get(url);
		if (res.getStatusCode() == 200) {
			res.prettyPrint();
			email=res.then().extract().path("data.email");
			Assert.assertTrue(email.equals("janet.weaver"));	
		}
	}
	

}
