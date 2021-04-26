package com.banck.pagos.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.banck.pagos.dto.TransacionBankDTO;

@RegisterRestClient
@Path("banck")
public interface TransacionService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(description = "Make withdrawal", summary = "Make a balance account of user")
	@Retry(maxRetries = 2)
	//@Timeout(value=50)
	@CircuitBreaker(requestVolumeThreshold = 4,failureRatio = 0.5, successThreshold = 1)
	@Path("balance/{id}")
	public Response banckBalanceByUserId(@PathParam("id") String id);

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(description = "Make withdrawal", summary = "Make a withdrawal in user account")
	@Retry(maxRetries = 2)
	//@Timeout(value=50)
	@CircuitBreaker(requestVolumeThreshold = 4,failureRatio = 0.5, successThreshold = 1)
	@Path("payment")
	public Response payment(TransacionBankDTO transacion);

}