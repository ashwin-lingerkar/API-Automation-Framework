package testscripts;

import io.restassured.*;
import io.restassured.path.json.JsonPath;

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

public class TC001_GET_WeatherInfo extends BaseFramework {

	@Test
	public void getCityWeatherInformation() {

		res = WebServices.Get(URL.baseURL_weatherAPI + EndPointURL.GET_WEATHER_INFO.getResourcePath());

		if (res.getStatusCode() == 200) {
			System.out.println("Response Body is " + res.getBody().asString());
		}
		// Status code validation
		int statusCode = res.getStatusCode();
		Assert.assertEquals(statusCode, 200);

		// Status Line validation
		String statusLine = res.getStatusLine();
		System.out.println("Status Line is " + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
	}
	
}
