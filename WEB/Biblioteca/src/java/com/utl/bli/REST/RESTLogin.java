package com.utl.bli.REST;

import com.google.gson.Gson;
import com.utl.bli.controller.ControllerLogin;
import com.utl.bli.model.Usuario;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/* @author maria*/
@Path("log")
public class RESTLogin {
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("in")
    public Response logIn(@QueryParam("usuario") @DefaultValue("") String usuario,
            @QueryParam("contrasenia") @DefaultValue("") String contrasenia) {
        Gson gson = new Gson();
        String out = null;
        ControllerLogin cl = new ControllerLogin();
        Usuario usu = null;
        try {
            usu = cl.login(usuario, contrasenia);
            if (usu != null) {
                out = new Gson().toJson(usu);
            } else {
                out = """
                  {"error" : Operación denegada, inicia sesión.""}
                  """;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                  {"exception" : "%s"}
                  """;
            out = String.format(out, ex.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
