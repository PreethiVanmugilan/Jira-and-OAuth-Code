package org.petStore;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.Data.PetDetails;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RestServices {
	
	public static void main(String[] args) {
		
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		// add a new pet
		String postResponse = given().log().all().header("Content-Type","application/json").body(PetDetails.petData(250, "name", "status"))
		.when().post("/pet")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("response : "+postResponse);
		JsonPath j = new JsonPath(postResponse);
		int petid = j.get("id");
		String petname = j.getString("name");
		String status = j.getString("status");
		System.out.println("pet ID : "+ petid);
		System.out.println("pet name :"+ petname);
		System.out.println("pet status :"+ status);
		
		//Category
		int dogId = j.get("category.id");
		System.out.println(dogId);
		
		//tag
		//int tagId = j.get("tags[0].id");
		//System.out.println(tagId);
		
		int size = j.get("tags.size()");
		
		for (int i = 0; i < size; i++) {
			
			System.out.println(j.get("tags["+i+"].id"));
			System.out.println(j.get("tags["+i+"].name"));
		}
		
	}
}