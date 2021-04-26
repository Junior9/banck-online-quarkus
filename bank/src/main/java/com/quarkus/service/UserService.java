package com.quarkus.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@Path("usuario")
public interface UserService {
	
	 @GET
	 @Produces(MediaType.APPLICATION_JSON)
	 @Path("/{id}")
	 //@Timeout(value =100)
	 //@Retry(maxRetries = 4)
	 @CircuitBreaker(requestVolumeThreshold = 3,failureRatio = 0.5,delay =1000, successThreshold = 1)
	 Response getById(@PathParam(value="id") String id);
	 
}