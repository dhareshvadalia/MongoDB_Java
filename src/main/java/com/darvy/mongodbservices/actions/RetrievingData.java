package com.darvy.mongodbservices.actions;
/**
 * @author dharesh
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.bson.Document;

import com.darvy.mongodbservices.config.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class RetrievingData {

	public static void findRecord() {
		MongoClient mongoCon = null;
		try {
		
		//get ticket id from user
		System.out.println("Enter Ticket Id :");
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		long ticketid = Long.parseLong(bfr.readLine());
		
		//Get connection
		mongoCon = MongoConnection.getMongoConnection();
		// Accessing the database 
	    MongoDatabase database = mongoCon.getDatabase("kharcha");  
	    if(database == null) System.out.println("Connection failed");
	     
	    //Fetching request 
		MongoCollection<Document> collection = database.getCollection("ticketbooking");
		Document response = collection.find(Filters.eq("ticket_id",ticketid)).first();
		if(response == null) System.out.println("No ticket found");
		else System.out.println("Requested Ticket: \n"+ response.toJson());
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally{
			mongoCon.close();
		}
	}
}
