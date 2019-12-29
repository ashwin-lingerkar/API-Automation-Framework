package testscripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import framework.BaseFramework;
import framework.WebServices;
import io.restassured.path.json.JsonPath;
import utils.EndPointURL;
import utils.URL;

/**
 * 
 * @author ashwin
 *
 */

public class TC005_GET_ExtractValuesOfEachNodeInJSON extends BaseFramework {
    
	@Test
	public void extractValuesOfEachNodeInJSON() {
		res = WebServices.Get(URL.baseURL_weatherAPI + EndPointURL.GET_WEATHER_INFO.getResourcePath());
		
		System.out.println(res.getBody().asString());

		JsonPath jsonPath = res.jsonPath();
		// Validating the each field in JSON response

		// Validating City
		Assert.assertEquals("Hyderabad", jsonPath.get("City"));

		// Validating Temperature
		//Assert.assertEquals("24.8 Degree celsius", jsonPath.get("Temperature"));

		// Validating Humidity
		Assert.assertEquals("69 Percent", jsonPath.get("Humidity"));

		// Validating Weather Description
		Assert.assertEquals("haze", jsonPath.get("WeatherDescription"));

		// Validating WindSpeed
		//Assert.assertEquals("2.6 Km per hour", jsonPath.get("WindSpeed"));

		// Validating Wind Direction Degree
		//Assert.assertEquals("80 Degree", jsonPath.get("WindDirectionDegree"));

	}

}
