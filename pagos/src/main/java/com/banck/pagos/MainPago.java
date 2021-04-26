package com.banck.pagos;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.banck.pagos.model.Pago;
import com.banck.pagos.service.PagoService;


//import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/pay")
@Tag(name="Payment Operations")
public class MainPago {

	@Inject
	PagoService pagoService;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("history")
    @Operation(description="Get all payment in the databaase",summary = "Get all payment")
	// all transacion")
	public Response history() {
		return Response.ok(this.pagoService.history()).build();
	}
	
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description="Make payment",summary = "Make a payment in user account")
    public Response deposit(Pago pago) {
        return Response.ok(this.pagoService.add(pago)).build();
    }
	
}