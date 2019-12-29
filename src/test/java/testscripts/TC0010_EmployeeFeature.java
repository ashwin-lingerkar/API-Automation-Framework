package testscripts;

import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import framework.BaseFramework;
import framework.WebServices;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojo.UserDetails;
import utils.EndPointURL;
import utils.URL;

/**
 * 
 * @author ashwin
 *
 */

public class TC0010_EmployeeFeature extends BaseFramework {

	ArrayList<String> firstName;
	public String oAuth2Token;

	// @Test(priority=1)
	public void getUser() {
		Gson gson = new GsonBuilder().create();
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
		if (res.getStatusCode() == 200) {
			userdetails = gson.fromJson(res.getBody().asString(), UserDetails[].class);
			String name = res.jsonPath().getString("data.first_name");
			for (int i = 0; i <= userdetails.length; i++) {
				Assert.assertEquals(firstName.get(i - 1), userdetails[i - 1].getFirstName());
			}
		}
	}

		
	
	

}
