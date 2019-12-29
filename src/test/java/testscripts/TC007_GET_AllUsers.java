package testscripts;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.BaseFramework;
import framework.WebServices;
import pojo.UserDetails;
import utils.EndPointURL;
import utils.URL;

/**
 * 
 * @author ashwin
 *
 */

public class TC007_GET_AllUsers extends BaseFramework {
	ArrayList<String> firstName;
	@Test
	public void getAllUsers() {
		UserDetails[] userdetails;
		firstName = new ArrayList();
		firstName.add("Michael");
		firstName.add("Lindsay");
		firstName.add("Tobias");
		firstName.add("Byron");
		firstName.add("George");
		firstName.add("Rachel");

		String url = URL.baseURL + EndPointURL.GET_ALL_USERS.getResourcePath();
		res = WebServices.Get(url);
		List names = res.jsonPath().get("data.first_name");
		if (res.getStatusCode() == 200) {
			for (int i = 0; i < firstName.size(); i++) {
				Assert.assertEquals(firstName.get(i), names.get(i));
			}
		}
	}

}
