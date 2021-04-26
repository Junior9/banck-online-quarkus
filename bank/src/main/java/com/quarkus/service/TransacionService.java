package com.quarkus.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.quarkus.model.TransacionBank;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class TransacionService {

	@Inject
	MongoClient mongoClient;

	@Inject
	@RestClient
	UserService userService;

	public JsonObject deposit(TransacionBank transacion) throws Exception {
		if (transacion.getType().equals("deposit")) {
			return this.addTransacion(transacion);
		}
		return new JsonObject().put("error", true).put("mensage", "Tipo de operacion invalida");
	}

	public JsonObject withdrawal(TransacionBank transacion) throws Exception {
		if (transacion.getType().equals("withdrawal")) {
			JsonObject balanceJson = this.balance(transacion.getIdUser());
			Integer balance = balanceJson.getInteger("balance");
			Integer withdrawal = Integer.parseInt(transacion.getAmount());
			if (balance >= withdrawal) {
				return this.addTransacion(transacion);
			} else {
				return new JsonObject().put("error", true).put("mensage", "Saldo insuficiente");
			}
		}
		return new JsonObject().put("error", true).put("mensage", "Tipo de operacion invalida");
	}

	public List<JsonObject> history() {
		List<JsonObject> responseList = new ArrayList<JsonObject>();
		MongoCollection<Document> docs = mongoClient.getDatabase("banckdb").getCollection("transaciones");
		docs.find().forEach(doc -> {
			if (!doc.isEmpty()) {
				JsonObject json = new JsonObject();
				json.put("idUser", doc.get("idUser").toString());
				json.put("amount", doc.get("amount").toString());
				json.put("type", doc.get("type").toString());
				responseList.add(json);
			}
		});
		return responseList;
	}

	public List<JsonObject> historyById(String id) {
		List<JsonObject> responseList = new ArrayList<JsonObject>();
		MongoCollection<Document> docs = mongoClient.getDatabase("banckdb").getCollection("transaciones");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("idUser", id);
		docs.find(searchQuery).forEach(doc -> {
			// System.out.println( "Here ");
			JsonObject json = new JsonObject();
			json.put("idUser", doc.get("idUser").toString());
			json.put("amount", doc.get("amount").toString());
			json.put("type", doc.get("type").toString());
			responseList.add(json);
		});

		return responseList;
	}

	public JsonObject balance(String id) {
		List<JsonObject> listOfTransacion = this.historyById(id);
		Integer balanceAccount = 0;
		for (int x = 0; x < listOfTransacion.size(); x++) {
			if (listOfTransacion.get(x).getString("type").equals("deposit")) {
				balanceAccount += Integer.parseInt(listOfTransacion.get(x).getString("amount"));
			} else if (listOfTransacion.get(x).getString("type").equals("withdrawal")   || listOfTransacion.get(x).getString("type").equals("payment") ) {
				balanceAccount -= Integer.parseInt(listOfTransacion.get(x).getString("amount"));
			}
		}
		return new JsonObject().put("balance", balanceAccount).put("error", false).put("userId", id);
	}
	
	public JsonObject payment(TransacionBank transacion) throws Exception {
		if (transacion.getType().equals("payment")) {
			JsonObject balanceJson = this.balance(transacion.getIdUser());
			Integer balance = balanceJson.getInteger("balance");
			Integer withdrawal = Integer.parseInt(transacion.getAmount());
			if (balance >= withdrawal) {
				return this.addTransacion(transacion);
			} else {
				return new JsonObject().put("error", true).put("mensage", "Saldo insuficiente");
			}
		}
		return new JsonObject().put("error", true).put("mensage", "Tipo de operacion invalida");
	}

	private JsonObject addTransacion(TransacionBank transacion) throws Exception {
		try {
			Response responseUser = this.userService.getById(transacion.getIdUser());
			if (responseUser.hasEntity()) {
				Document document = new Document();
				document.append("idUser", transacion.getIdUser());
				document.append("amount", transacion.getAmount());
				document.append("type", transacion.getType());
				InsertOneResult mongoResult = mongoClient.getDatabase("banckdb").getCollection("transaciones")
						.insertOne(document);

				if (!mongoResult.getInsertedId().isNull()) {
					return new JsonObject().put("result", mongoResult.getInsertedId().toString()).put("error", false);
				}
				return new JsonObject().put("error", true).put("mensage", "Erro al adicionar transacion");
			} else {
				return new JsonObject().put("error", true).put("mensage", "Usuario invalido");
			}
		}catch(Exception e) {
			throw new Exception("Servicio Usuario no responde :" + e.getMessage());
		}	
	}

}