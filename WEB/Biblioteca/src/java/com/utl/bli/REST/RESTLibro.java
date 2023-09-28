package com.utl.bli.REST;

import com.google.gson.Gson;
import com.utl.bli.controller.ControllerLibro;
import com.utl.bli.model.Libro;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;
import org.apache.tomcat.jakartaee.commons.io.IOUtils;

/*@author maria*/
@Path("libro")
public class RESTLibro {
    
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {        
        String out = null;
        ControllerLibro cl = null;
        List<Libro> libros = null;

        try {
            cl = new ControllerLibro();
            libros = cl.getAll(filtro);
            out = new Gson().toJson(libros);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    } 
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("save")
    public Response save(@FormParam("datosLibro") @DefaultValue("") String datosLibro) {        
        String out = null;
        Gson gson = new Gson();
        Libro l = null;
        ControllerLibro cl = new ControllerLibro();
        try {
                l = gson.fromJson(datosLibro, Libro.class);
                   
                    cl.insert(l);
                out = gson.toJson(l);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"result":"error"}
                  """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
       

    
    

}
    
