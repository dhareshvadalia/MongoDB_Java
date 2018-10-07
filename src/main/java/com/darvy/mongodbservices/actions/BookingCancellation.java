package com.darvy.mongodbservices.actions;
/**
 * @author dharesh
 *
 */

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import org.bson.Document;

import com.darvy.mongodbservices.config.MongoConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class BookingCancellation {

	public static void bookingcancellation() {
	
	MongoClient mongoCon = null;
	try {
		mongoCon = MongoConnection.getMongoConnection();
		// Accessing the database 
	    MongoDatabase database = mongoCon.getDatabase("kharcha");  
	    if(database == null) System.out.println("Connection failed");
	    //Updating request 
	  	MongoCollection<Document> collection = database.getCollection("ticketbooking");
	  		
	    //get ticket id from user
	  	System.out.println("Enter Ticket Id :");
	  	BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
	  	long ticketid = Long.parseLong(bfr.readLine());
	     
	  	Document check = collection.find(Filters.and(
				Filters.eq("ticket_id",ticketid),
				Filters.eq("status","Confirmed"))).first();
		
		if(check == null) {
			System.out.println("Ticket not found for ticket id:" + ticketid);
			throw new Error("Ticket not found for ticket id:" + ticketid);
		}
		
		collection.updateOne(
				Filters.and(
				Filters.eq("ticket_id",ticketid),
				Filters.eq("status","Confirmed")),
				Updates.combine(
						Updates.set("status","Cancelled"),
						Updates.set("cancellation_date", new Date())));
		
		System.out.println("Ticket Cancelled:" + ticketid);
	
	}
	catch (Exception e){
		e.printStackTrace();
	}
	finally{
		mongoCon.close();
	}
	}
}
