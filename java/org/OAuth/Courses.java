package org.OAuth;
import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class Courses {
	
	public static void main(String[] args) {
		
		//code
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qidJZmX2T8qxaVhHchSk6xETfwdHWSJy0F2g9fbof-3crFqf-c8iUYppfTYyQLcIA&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String[] s = url.split("code=");
		String[] s1 = s[1].split("&scope");
		//access token
		String tokenResponse = given().log().all()
		.queryParam("code", s1[0])
		.queryParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code").urlEncodingEnabled(false)
		.when().post("https://www.googleapis.com/oauth2/v4/token")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath j = new JsonPath(tokenResponse);
		String token = j.get("access_token");
		
		//get courses
		String courseResponse = given().log().all().queryParam("access_token", token).header("Content-Type","application/json")
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println("Report :"+courseResponse);
		
		JsonPath j1 = new JsonPath(courseResponse);
		
		int web =j1.get("courses.webAutomation.size()");
        for (int i = 0; i < web; i++) {
        	String courseTitle = j1.get("courses.webAutomation["+i+"].courseTitle");
			System.out.println("webAutomation course title :"+courseTitle);
			String price= j1.get("courses.webAutomation["+i+"].price");
			System.out.println("webAutomation course title :"+price);
		}
        
		int api =j1.get("courses.api.size()");
        for (int k = 0; k < api; k++) {
        	String courseTitle = j1.get("courses.api["+k+"].courseTitle");
			System.out.println("api course title :"+courseTitle);
			String price= j1.get("courses.api["+k+"].price");
			System.out.println("api course title :"+price);
		
		}
        
		int mob =j1.get("courses.mobile.size()");
		for (int l = 0; l < mob; l++) {
		  String courseTitle = j1.get("courses.mobile["+l+"].courseTitle");
		  System.out.println("mobile course title :"+courseTitle);
		  String price= j1.get("courses.mobile["+l+"].price");
	      System.out.println("mobile course title :"+price);
		
		}
		
		
	}

}
