package com.quarkus;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.quarkus.model.TransacionBank;
import com.quarkus.service.TransacionService;

import io.vertx.core.json.JsonObject;

@Path("/banck")
@Tag(name="Banck Acount Operations")
public class MainBanck {
	
	@Inject
	TransacionService transacionService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("history")
    @Operation(description="Get all transacions in the databaase",summary = "Get all transacion")
    public Response historyMongo() {
        return Response.ok(this.transacionService.history()).build();
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("history/{id}")
    @Operation(description="Get all transacions by user in the databaase",summary = "Get all transacion by user")
    public Response historyByUserId(@PathParam("id") String id) {
        return Response.ok(this.transacionService.historyById(id)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description="Make deposit",summary = "Make a deposit in user account")
    @Path("deposit")
    public Response deposit(TransacionBank transacion) {
        try {
			return Response.ok(this.transacionService.deposit(transacion)).build();
		} catch (Exception e) {
			JsonObject json = new JsonObject().put("error", true).put("mensage", e.getMessage());
			return Response.ok(json).build();
		}
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description="Make withdrawal",summary = "Make a withdrawal in user account")
    @Path("withdrawal")
    public Response withdrawal(TransacionBank transacion) {
    	try {
			return Response.ok(this.transacionService.withdrawal(transacion)).build();
		} catch (Exception e) {
			JsonObject json = new JsonObject().put("error", true).put("mensage", e.getMessage());
			return Response.ok(json).build();
		}
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description="Make withdrawal",summary = "Make a withdrawal in user account")
    @Path("payment")
    public Response payment(TransacionBank transacion) {
    	try {
			return Response.ok(this.transacionService.payment(transacion)).build();
		} catch (Exception e) {
			JsonObject json = new JsonObject().put("error", true).put("mensage", e.getMessage());
			return Response.ok(json).build();
		}
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(description="Make withdrawal",summary = "Make a balance account of user")
    @Path("balance/{id}")
    public Response banckBalanceByUserId(@PathParam("id") String id) {
    	return Response.ok(this.transacionService.balance(id)).build();
    }
}