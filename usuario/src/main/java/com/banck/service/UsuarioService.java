package com.banck.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.bson.Document;

import com.banck.model.User;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;

import io.vertx.core.json.JsonObject;
import org.bson.types.ObjectId;



@ApplicationScoped
public class UsuarioService {
	
	 @Inject 
	 MongoClient mongoClient;
	 
	 public JsonObject add(User user) {		 
		 return this.addUser(user);
	 }
	 
	  private JsonObject addUser(User user) {
			 Document document = new Document();
			 document.put("name",user.getName() );
			 document.put("saldo", user.getSaldo());
			 InsertOneResult mongoResult = mongoClient.getDatabase("banckdb").getCollection("users").insertOne(document);
			 return new JsonObject().put("result",mongoResult.getInsertedId().toString() );
	  }
	  
	  public List<JsonObject> list(){
		  List<JsonObject>responseList = new ArrayList<JsonObject>();
		  MongoCollection<Document> docs = mongoClient.getDatabase("banckdb").getCollection("users");
		  docs.find().forEach(doc->{
			  if(!doc.isEmpty()) {
				  JsonObject json = new JsonObject();
				  json.put("id", doc.get("_id").toString());
				  json.put("name", doc.get("name").toString());
				  json.put("saldo", doc.get("saldo"));
				  responseList.add(json);
			  }
		  });
		  return responseList;
	  }

	public User getById(String id) {
		MongoCollection<Document> docs = mongoClient.getDatabase("banckdb").getCollection("users");
		User user = null;
		BasicDBObject searchQuery = new BasicDBObject();
		ObjectId query = new ObjectId(id);
		searchQuery.put("_id", query);
		
		FindIterable<Document> re = docs.find(searchQuery);
		user = new User(re.first().get("name").toString());
		return user;
	}
}