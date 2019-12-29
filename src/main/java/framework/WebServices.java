package framework;

import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


/**
 * 
 * @author ashwin
 *
 */

public class WebServices extends BaseFramework{
	
	public static String token;
	
	/**
	 * 
	 * 
	 * @param uri
	 * @param stringJSON
	 * @return this method returns a post response
	 */
	
	public static Response Post(String uri, String stringJSON){
		RequestSpecification requestSpecification = given().body(stringJSON);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.post(uri);
		return response;
	}
	
	/**
	 * 
	 * 
	 * @param uri
	 * @return this method returns a get response
	 */
	
	public static Response Get(String uri){
		RequestSpecification requestSpecification = given();
		requestSpecification.contentType(ContentType.JSON);
		Response response= requestSpecification.get(uri);
		return response;
	}
	
	/**
	 * 
	 * 
	 * @param uri
	 * @param stringJSON
	 * @return this method returns a put response
	 */
	
	public static Response Put(String uri, String stringJSON){
		RequestSpecification requestSpecification = given().body(stringJSON);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.put(uri);
		return response;
	}
	
	/**
	 * 
	 * 
	 * @param uri
	 * @param stringJSON
	 * @return this method returns a delete response
	 */
	
	public static Response Delete(String uri, String stringJSON){
		RequestSpecification requestSpecification = given().body(stringJSON);
		requestSpecification.contentType(ContentType.JSON);
		Response response = requestSpecification.delete(uri);
		return response;
	}
	
	 /**
     * This method returns oauth2 token
     * 
     */
    
    public static String getOauth2Token(){
    	Response resp=RestAssured.
    			given()
    			.formParam("client_id", "DemoAPITest")
    			.formParam("client_secret", "60840df8fad3ef3e679da1463b566a77")
    			.formParam("grant_type", "client_credentials")
    			.post("http://coop.apps.symfonycasts.com/token");
    	token = resp.jsonPath().get("access_token");
    	return token;
    }

}
