package com.utl.bli.REST;

import com.google.gson.Gson;
import com.utl.bli.controller.ControllerLogin;
import com.utl.bli.model.Usuario;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
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
    public Response logIn(@FormParam("nombre_usuario") @DefaultValue("") String nombre_usuario,
            @FormParam("contrasenia") @DefaultValue("") String contrasenia) {
       
        String out = null;
        Gson gson = new Gson();
        Usuario usu = null;
        ControllerLogin cl = new ControllerLogin();
        try {
            
            usu = cl.login(nombre_usuario, contrasenia);
            if (usu != null) {
                out = new Gson().toJson(usu);
            }
            else{
                out = "{\"error\": 'Usuario y/o contraseña incorrectos'}";
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
}
