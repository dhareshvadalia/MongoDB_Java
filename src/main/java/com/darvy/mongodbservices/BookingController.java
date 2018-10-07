package com.darvy.mongodbservices;
/**
 * @author dharesh
 *
 */

import java.text.ParseException;
import java.util.Scanner;

import com.darvy.mongodbservices.actions.BasicInsertUpdate;
import com.darvy.mongodbservices.actions.ComplaintQuery;
import com.darvy.mongodbservices.actions.RetrievingData;

public class BookingController {

	public void tcontroller(int choice) {
	try {
		switch (choice) {
		case 1:{
			boolean status = BasicInsertUpdate.addBookingRequest();
			if(status) BasicInsertUpdate.updateBookingRequest();
			else System.out.println("Booking failed");
			break;
		}
		case 2:{
			System.out.println(choice);
			break;
		}
		case 3:{
			RetrievingData.findRecord();
			break;
		}
		case 4:{
			ComplaintQuery.registerComplaint();
			break;
		}
		default:
			System.out.println("Select correct option, entered option: "+ choice);
			break;
		}
	}
	catch(Exception e){
		e.printStackTrace();
	}
	}
	
	public static void main(String... args) throws ParseException {
		BookingController bc = new BookingController();
		System.out.println("Enter Service to Avail\n 1. Ticket Booking\n 2. Booking Cancellation\n 3. Check Booking Status\n 4. Complaint and Query");
		int choice = new Scanner(System.in).nextInt();
		bc.tcontroller(choice);
	}
}
