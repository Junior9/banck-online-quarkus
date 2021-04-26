package com.banck.usuario;

import javax.inject.Inject;
import javax.transaction.Transactional;
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

import com.banck.model.User;
import com.banck.service.UsuarioService;

@Path("/usuario")
@Transactional
@Tag(name="Users Operations")
public class MainUsuario {
	
	@Inject
	UsuarioService usuarioService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("list")
    @Operation(description="Get all users in the databaase",summary = "Get all users")
    public Response getAllUsers() {
    	return Response.ok(this.usuarioService.list() ).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @Operation(description="Get user in the databaase by Id",summary = "Get user by Id")
    public Response getById(@PathParam("id") String id) {
      return Response.ok(this.usuarioService.getById(id)).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("save")
    @Operation(description="Add user in the databaase",summary = "Add users database")
    public Response add(User user) {
        return Response.ok(this.usuarioService.add(user)).build();
    }   
}