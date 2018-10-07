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



public class ComplaintQuery {

	public static void registerComplaint() {
		
		MongoClient mongoCon = null;
		
		try {
			
			mongoCon = MongoConnection.getMongoConnection();
			// Accessing the database 
		    MongoDatabase database = mongoCon.getDatabase("kharcha");  
		    if(database == null) System.out.println("Connection failed");
		    //Updating request 
			MongoCollection<Document> collection = database.getCollection("ticketbooking");
			
			//get ticket id from user
			System.out.println("Enter Ticket Id to complaint against :");
			BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
			long ticketid = Long.parseLong(bfr.readLine());
			
			Document check = collection.find(Filters.and(
					Filters.eq("ticket_id",ticketid),
					Filters.eq("status","Confirmed"))).first();
			
			if(check == null) {
				System.out.println("Ticket not found for ticket id:" + ticketid);
				throw new Error("Ticket not found for ticket id:" + ticketid);
			}
			
			System.out.println("Reason of Complaint: ");
			String comp_reason = bfr.readLine();
			System.out.println("Describe Issue: ");
			String comp_description = bfr.readLine();
			long temp = (long) Math.floor(Math.random() * 1000000 + 1);
			String complaintid = "COMP" + temp;
			
			Document comp_doc = new Document("ticket_id", ticketid)
				.append("complaint_id",complaintid)
				.append("complaint_reason", comp_reason)
				.append("complain_description", comp_description)
				.append("complaint_date", new Date())
				.append("complain_status", "Regitered");
			
			collection.updateOne(Filters.and(
					Filters.eq("ticket_id",ticketid),
					Filters.eq("status","Confirmed")),
					Updates.set("complaints",comp_doc));
					//Updates.set("complaints",new Document("complaint",comp_doc)));
			
			System.out.println("Complaint Registered against ticket id: ["+ ticketid +"] complaint id: ["+ complaintid +"]");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
