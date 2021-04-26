package com.banck.pagos.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.bson.Document;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.banck.pagos.dto.BalanceDTO;
import com.banck.pagos.dto.TransacionBankDTO;
import com.banck.pagos.model.Pago;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;

import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class PagoService {

	@Inject
	MongoClient mongoClient;

	@Inject
	@RestClient
	TransacionService transacionService;

	public List<JsonObject> history() {
		List<JsonObject> responseList = new ArrayList<JsonObject>();
		MongoCollection<Document> docs = mongoClient.getDatabase("banckdb").getCollection("pagos");
		docs.find().forEach(doc -> {
			if (!doc.isEmpty()) {
				JsonObject json = new JsonObject();
				json.put("idUser", doc.get("idUser").toString());
				json.put("idProducto", doc.get("idProducto").toString());
				json.put("idTransacion", doc.get("idTransacion").toString());
				json.put("total", doc.get("total").toString());
				responseList.add(json);
			}
		});
		return responseList;
	}

	public JsonObject add(Pago pago) {
		MongoCollection<Document> db = mongoClient.getDatabase("banckdb").getCollection("pagos");
		Document document = new Document();
		document.append("idUser", pago.getIdUser());
		document.append("idProducto", pago.getIdProducto());
		document.append("idTransacion", pago.getIdTransacion());
		document.append("total", pago.getTotal());
		try {
			Response balanceAccoutResponse = this.transacionService.banckBalanceByUserId(pago.getIdUser());
			if (balanceAccoutResponse.hasEntity()) {
				BalanceDTO balance = balanceAccoutResponse.readEntity(BalanceDTO.class);
				if (balance.getBalance() >= pago.getTotal()) {
					InsertOneResult mongoResult = db.insertOne(document);
					if (!mongoResult.getInsertedId().isNull()) {
						Response responsePayment = this.transacionService.payment(new TransacionBankDTO(pago.getIdUser(),pago.getTotal().toString(),"payment"));
						if(responsePayment.hasEntity()) {
							return new JsonObject().put("result", mongoResult.getInsertedId().toString()).put("error", false);
						}
					}
				} else {
					return new JsonObject().put("error", true).put("mensage", "Saldo insuficiente para la compra");
				}
			} else {
				return new JsonObject().put("error", true).put("mensage", "Error al validar saldo");
			}
			
		}catch(Exception e) {
			return new JsonObject().put("error", true).put("mensage", "Servicio Banck fuera de servicio: " +e.getMessage());
		}
		
		return new JsonObject().put("error", true).put("mensage", "Error al adicionar pago");
	}
}
