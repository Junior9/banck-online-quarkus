package com.quarkus.endpoint;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import com.quarkus.model.TransacionBank;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class MainTests {
	
	   @Test    
	    public void testHistoryEndpoint() {
	        given()
	          .when().get("/banck/history")
	          .then()
	             .statusCode
	             (200);
	    }
	   
	   
	   /*
	   
	   @Test    
	    public void testDepositEndpoint() {
		   
		   TransacionBank transacion = new TransacionBank("785",Integer.parseInt("852"),"deposit");
	        given()
	          .body(transacion)
	          .when().post("banck/deposit")
	          .then()
	             .statusCode
	             (200);
	    }
	    
	    */

}
