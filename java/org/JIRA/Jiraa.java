package org.JIRA;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class Jiraa {
	
	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8080";
		
		//session creation
		
		SessionFilter session = new SessionFilter();
		given().log().all().header("Content-Type","application/json")
		.body("{ \"username\": \"Preethi\", \"password\": \"Vanmugilan@1011\" }").filter(session)
		.when().post("/rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		//System.out.println(SessionResponse);
		//JsonPath j = new JsonPath(SessionResponse);
		//String sessionID = j.get("se;ssion.name");
		//String sessionValue = j.get("session.value")
		
		//create an issue
		String IssueResponse = given().log().all().header("Content-Type","application/json").filter(session)
		.body("{\r\n" + 
				"    \"fields\": {\r\n" + 
				"       \"project\":\r\n" + 
				"       {\r\n" + 
				"          \"key\": \"JIRA\"\r\n" + 
				"       },\r\n" + 
				"       \"summary\": \"Logout button is not working\",\r\n" + 
				"       \"description\": \"Creating of an issue using ids for projects and issue types using the REST API\",\r\n" + 
				"       \"issuetype\": {\r\n" + 
				"          \"name\": \"Bug\"\r\n" + 
				"       }\r\n" + 
				"   }\r\n" + 
				"}")
		.when().post("/rest/api/2/issue")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath j = new JsonPath(IssueResponse);
		String jira_id = j.get("id");
				
		//add comment
		given().log().all().pathParam("jira_id", jira_id).header("Content-Type","application/json").filter(session)
		.body("{\r\n" + 
				"    \"body\": \"This is my first comment from eclipse\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}")
		.when().post("/rest/api/2/issue/{jira_id}/comment")
		.then().log().all().assertThat().statusCode(201);
		
		//add attachments
		given().log().all().pathParam("jira_id", jira_id).header("Content-Type","multipart/form-data")
		.header("X-Atlassian-Token","no-check").filter(session)
		.multiPart(new File(".//jira.txt"))
		.when().post("/rest/api/2/issue/{jira_id}/attachments")
		.then().log().all().assertThat().statusCode(200);
		
		//get issue
		given().log().all().pathParam("jira_id", jira_id).header("Content-Type","application/json").filter(session)
		.when().get("/rest/api/2/issue/{jira_id}")
		.then().log().all().assertThat().statusCode(200);
		
		//get Comments
		given().log().all().pathParam("jira_id", jira_id).header("Content-Type","application/json").filter(session)
		.when().get("/rest/api/2/issue/{jira_id}/comment")
		.then().log().all().assertThat().statusCode(200);
	}

}
