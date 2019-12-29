package utils;

/**
 * 
 * @author ashwin
 *
 */

public enum EndPointURL {

	GET_ALL_USERS("/api/users?page=2"),
	GET_SINGLE_USER("/api/users/2"),
	GET_CHIKEN_INFO("/api/462/chickens-feed"),
	GET_WEATHER_INFO("/Hyderabad"),
	POST_REGISTER_CUSTOMER("/register"),
	GET_GOOGLEMAPS_INFO("/maps/api/place/nearbysearch/xml?location=-33.8670522,151.1957362&radius=1500&type=supermarket&key=AIzaSyBjGCE3VpLU4lgTqSTDmHmJ2HoELb4Jy1s"),
	GET_AUTHENTICATION("/");
	
	public String resourcePath;
	
	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	EndPointURL(String resourcePath){
		this.resourcePath=resourcePath;
	}
	
}
