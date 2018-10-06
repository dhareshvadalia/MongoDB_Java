package com.darvy.mongodbservices;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 * @author dharesh
 *
 */

public class MongoConnection 
{
	public static MongoClient getMongoConnection(){
		
		 MongoClientURI uri = new MongoClientURI("mongodb://admin:admin123@127.0.0.1:27017/?authSource=testdb");
		 MongoClient mongoCon = new MongoClient(uri); 
		
	return mongoCon;
	}
}
