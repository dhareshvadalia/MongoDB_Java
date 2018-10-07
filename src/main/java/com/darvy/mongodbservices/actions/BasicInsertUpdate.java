package com.darvy.mongodbservices.actions;
/**
 * @author dharesh
 *
 */
import java.text.ParseException;
import java.util.Date;

import org.bson.Document;

import com.darvy.mongodbservices.config.MongoConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoServerException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class BasicInsertUpdate {
	
	static MongoClient mongoCon = null;
	static double tkt_id = Math.floor(Math.random() * 10000000);
	
	public static boolean addBookingRequest(){
	
		try{
		Document doc_reqBody = null;
        
        	JsonObject journey_detail = new JsonObject();
        	journey_detail.addProperty("stn_from", "mumbai-cstm");
        	journey_detail.addProperty("stn_to", "nagpur");
        	journey_detail.addProperty("cost", 2540);
        	journey_detail.addProperty("journey_date", new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 5)).toString());
       
        	doc_reqBody = Document.parse(new Gson().toJson(journey_detail));
        
		Document doc  = new Document("ticket_id", tkt_id)
		.append("name", "Darvy Code")
		.append("gender", "Male")
		.append("ticket_details", new Document("request", doc_reqBody))
		.append("status", "pending");
		
		mongoCon = MongoConnection.getMongoConnection();
		
		// Accessing the database 
	     MongoDatabase database = mongoCon.getDatabase("kharcha");  
	     if(database == null) System.out.println("Connection failed");
	     
	     //Get collection
	     MongoCollection<Document> collection = database.getCollection("ticketbooking");
		
		//insert document
		collection.insertOne(doc);
		System.out.println("Mongo Request Inserted");
		
		}
		catch (MongoServerException e){
			return false;
		}
		finally{
			mongoCon.close();
		}
		return true;
	}
	
	public static void updateBookingRequest() throws ParseException{
		
		try{
			
			mongoCon = MongoConnection.getMongoConnection();
			// Accessing the database 
		    MongoDatabase database = mongoCon.getDatabase("kharcha");  
		    if(database == null) System.out.println("Connection failed");
		     
		    //Updating request 
			MongoCollection<Document> collection = database.getCollection("ticketbooking");
			collection.updateOne(
					Filters.and(
					Filters.eq("ticket_id",tkt_id),
					Filters.eq("status","pending")),
					Updates.combine(
							Updates.set("status","Confirmed"),
							Updates.set("booking_date", new Date())));
			
			System.out.println("Ticket Confirmed : Ticket id ["+ Math.floor(tkt_id) +"] ");
		}
		catch (MongoServerException e){
		}
		finally{
			mongoCon.close();
		}
	}
}
