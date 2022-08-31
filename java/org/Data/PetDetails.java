package org.Data;

public class PetDetails {
	
	public static String petData(int id, String name, String status) {
		
		return"{\r\n" + 
				"    \"id\": "+id+",\r\n" + 
				"    \"category\": {\r\n" + 
				"        \"id\": 12,\r\n" + 
				"        \"name\": \"Dog\"\r\n" + 
				"    },\r\n" + 
				"    \"name\": \""+name+"\",\r\n" + 
				"    \"photoUrls\": [\r\n" + 
				"        \"string\"\r\n" + 
				"    ],\r\n" + 
				"    \"tags\": [\r\n" + 
				"        {\r\n" + 
				"            \"id\": 1,\r\n" + 
				"            \"name\": \"Labrador\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"id\": 2,\r\n" + 
				"            \"name\": \"Rotuweiler\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"id\": 3,\r\n" + 
				"            \"name\": \"Dalmation\"\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"status\": \""+status+"\"\r\n" + 
				"}";

	}
}