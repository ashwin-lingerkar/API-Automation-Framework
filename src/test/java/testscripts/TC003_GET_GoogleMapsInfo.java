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

public class TC003_GET_GoogleMapsInfo extends BaseFramework{
	
	@Test
	public void googleMapInformatoin(){
        res= WebServices.Get(URL.BASEURL_GOOGLEMAPSAPI+EndPointURL.GET_GOOGLEMAPS_INFO.getResourcePath());
		
		if(res.getStatusCode() == 200){
			System.out.println("Response Body is "+res.getBody().asString());
		}
		//Status code validation
		int statusCode = res.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		
		//Status Line validation
		String statusLine= res.getStatusLine();
		System.out.println("Status Line is "+statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");
		
		//Headers validation
		String contentType= res.getHeader("Content-Type"); //capture details of Content-Type header
		System.out.println("Content Type is "+contentType);
		Assert.assertEquals(contentType, "application/xml; charset=UTF-8");
		
		String contentEncoding= res.getHeader("Content-Encoding"); //capture details of Content-Encoding header
		System.out.println("Content Encoding is "+contentEncoding);
		Assert.assertEquals(contentEncoding, "gzip");
		
	}

}
