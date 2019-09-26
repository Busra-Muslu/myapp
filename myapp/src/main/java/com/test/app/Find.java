/* Büşra MUSLU */
package com.test.app;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.SSLConfig;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.specification.RequestSpecification;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class Find {
    private Values values = new Values();
    private String movieId;
    
    public RequestSpecification req() {
    	RequestSpecification request = null;
    	try {
    		RestAssured.baseURI = "http://www.omdbapi.com/"; 
    	 	request = RestAssured
            		.given()
                    .config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames()));
    	 }catch (Exception e) {
	 		   e.printStackTrace();
	 	 }
    	return request;
    }
    
    public Response findMovie(RequestSpecification request) {
		Response response = null;
	 	try {
	 	  response = request
 			   .when()
 			   		.get("?t=harry+potter&apikey=PlzBanMe") 
                .then()
                	.extract().response();
	   }catch (Exception e) {
 		   e.printStackTrace();
 	   }
	 	return response;
    }
	 
    @Test
	    public void testFindMovie() {
		 	Response response = findMovie(req());
		    ResponseBody responseBody = response.getBody();
		    System.out.println("----- Find Harry Potter's Movie --------");
	        System.out.println("Response : " + responseBody.asString());
		    System.out.println("Status Code : " + response.getStatusCode());
		    movieId = response.jsonPath().getString("imdbID");
		 	values.setId(movieId);
			System.out.println("ID : "+ movieId);
		    System.out.println("*---------------------* \n");
		    assert responseBody.asString() != null;
	 }
	 
	 @Test
	    public void testStatus() {
		 Response response =findMovie(req());
		 assertEquals(200, (response).getStatusCode());
	 }
	 
	 @Test
	    public void testTitle() {
		 Response response =findMovie(req());
		 assertNotNull(response.jsonPath().getString("Title"));
	 }
	 
	 @Test
	    public void testReleased() {
		 Response response =findMovie(req());
		 assertNotNull(response.jsonPath().getString("Released"));
	 }
	
	 @Test
	    public void testYear() {
		 Response response =findMovie(req());
		 assertNotNull(response.jsonPath().getString("Year"));
	 }
	 
	 public Response findId(RequestSpecification request) {
		 Response response = null;
		 //String id = values.getId(); //id return null?
		 String id = "tt1201607"; 
		 try {
			 response = request
					.when()
		                .get("?i=" + id + "&apikey=PlzBanMe") 
		            .then()
		                .statusCode(200)
		                .extract().response();
		  }catch (Exception e) {
 		   e.printStackTrace();
 	      }
	 	return response;
	 }
	
	 @Test
	    public void testFindId() {
	        Response response=findId(req()); // Response response=findId(req(),movieId); movieId null?
	        ResponseBody responseBody = response.getBody();
	        System.out.println("-------- Find Id --------");
	        System.out.println("Response : " + responseBody.asString());
	        System.out.println("*---------------------*");
	        assert responseBody.asString() != null;
	 }
}
