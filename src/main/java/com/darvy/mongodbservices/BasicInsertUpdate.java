package com.darvy.mongodbservices;
/**
 * @author dharesh
 *
 */
import java.text.ParseException;
import java.util.Date;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoServerException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class BasicInsertUpdate {
	
	MongoClient mongoCon = null;
	double tkt_id = Math.floor(Math.random() * 10000000);
	
	public boolean addBookingRequest(){
	
		try{
		Document doc_reqBody = null;
        
        	JsonObject journey_detail = new JsonObject();
        	journey_detail.addProperty("stn_from", "mumbai-cstm");
        	journey_detail.addProperty("stn_to", "nagpur");
        	journey_detail.addProperty("cost", 2540);
       
        	doc_reqBody = Document.parse(new Gson().toJson(journey_detail));
        
		Document doc  = new Document("ticket_id", tkt_id)
		.append("name", "Darvy Code")
		.append("gender", "Male")
		.append("ticket_details", new Document("request", doc_reqBody))
		.append("status", "pending");
		
		mongoCon = MongoConnection.getMongoConnection();
		
		// Accessing the database 
	     MongoDatabase database = mongoCon.getDatabase("testdb");  
	     if(database == null){
	    	 System.out.println("Connection failed");
	     }else{
	    	 System.out.println("Connected to the database successfully");
	     }
	     
	     //Get collection
	     MongoCollection<Document> collection = database.getCollection("practice");
		
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
	
	public void updateBookingRequest() throws ParseException{
		
		try{
			
			mongoCon = MongoConnection.getMongoConnection();
			// Accessing the database 
		    MongoDatabase database = mongoCon.getDatabase("practicedb");  
		    if(database == null){
		    System.out.println("Connection failed");
		    }else{
		    	System.out.println("Connected to the database successfully");
		    }
		     
		    //Updating request 
			MongoCollection<Document> collection = database.getCollection("hgapiuatlogs");
			collection.updateOne(
					Filters.and(
					Filters.eq("ticket_id",tkt_id),
					Filters.eq("status","pending")),
					Updates.combine(
							Updates.set("status","Confirmed"),
							Updates.set("booking_date", new Date())));
		}
		catch (MongoServerException e){
		}
		finally{
			mongoCon.close();
		}
	}
	
	
	public static void main() throws ParseException {
		BasicInsertUpdate conn = new BasicInsertUpdate();
		conn.addBookingRequest();
		conn.updateBookingRequest();
	}

}
