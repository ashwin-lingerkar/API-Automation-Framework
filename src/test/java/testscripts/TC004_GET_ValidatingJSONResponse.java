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

public class TC004_GET_ValidatingJSONResponse extends BaseFramework{
	
	@Test
	public void validateCityWeatherInformation() {

		res = WebServices.Get(URL.baseURL_weatherAPI + EndPointURL.GET_WEATHER_INFO.getResourcePath());
        String responseBody=res.getBody().asString();
		if (res.getStatusCode() == 200) {
			System.out.println("Response Body is " + res.getBody().asString());
		}
		Assert.assertEquals(responseBody.contains("Hyderabad"),true);
	}


}
