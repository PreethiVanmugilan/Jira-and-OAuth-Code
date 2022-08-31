package org.petStore;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.Data.PetDetails;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RestServices1 {
	static int petid;
		@DataProvider(name = "pet")
		public Object[][] getPet(){
			return new Object[][] {{256,"chinna"},{257,"randow"},{258,"jacky"}};
		}
		
		@Test(priority = 1, dataProvider = "pet")
		public void addPet(int id, String name){
		RestAssured.baseURI = "https://petstore.swagger.io/v2";
		// add a new pet
		String postResponse = given().log().all().header("Content-Type","application/json").body(PetDetails.petData(id, name, "available"))
		.when().post("/pet")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("response : "+postResponse);
		JsonPath j = new JsonPath(postResponse);
		petid = j.get("id");
		System.out.println("pet ID : "+ id);
		}
		
		@Test(priority = 2)
		public void retreivePet() {
		//pet by id
		given().log().all().header("Content-Type","application/json").pathParam("id", petid)
		.when().get("/pet/{id}")
		.then().log().all().assertThat().statusCode(200);
		}
		
		@Test(priority = 3)
		public void updatePet(){
		given().log().all().header("Content-Type","application/json").body(PetDetails.petData(124, "jimmy", "sold"))
		.when().put("/pet")
		.then().log().all().assertThat().statusCode(200);
		}
		
		@Test(priority = 4)
		public void findByStatus(){
		given().log().all().header("Content-Type","application/json").queryParam("status","sold")
		.when().get("/pet/findByStatus")
		.then().log().all().assertThat().statusCode(200);
		}		
		
}