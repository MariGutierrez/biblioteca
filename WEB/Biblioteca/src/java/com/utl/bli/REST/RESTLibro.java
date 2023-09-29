package com.utl.bli.REST;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
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
    @Path("buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@QueryParam("filtro") @DefaultValue("") String filtro) {

        String out = null;
        ControllerLibro cl = null;
        List<Libro> libros = null;

        try {
            cl = new ControllerLibro();
            libros = cl.buscar(filtro);
            out = new Gson().toJson(libros);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {        
        String out = null;
        ControllerLibro cl = null;
        List<Libro> libros = null;

        try {
            cl = new ControllerLibro();
            libros = cl.getAll();
            out = new Gson().toJson(libros);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    } 
    
    @Path("save")
    @Produces(MediaType.APPLICATION_JSON)
    @POST      
    public Response save(@FormParam("datosLibro") @DefaultValue("") String datosLibro) {
        String out = null;
        Gson gson = new Gson();
        Libro li = null;
        ControllerLibro cl = new ControllerLibro();
        try {
            
            li = gson.fromJson(datosLibro, Libro.class);
            int id = li.getId_libro();
            System.out.println(id);
            out = gson.toJson(li);
            if (id<=0) {
                cl.insert(li);
            } else {
                cl.update(li);
            }
        out = gson.toJson(li);
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"result":"error"}
                  """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    
    @POST
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("datosLibro") @DefaultValue("") String datosLibro) {

        String out = null;
        Gson gson = new Gson();
        Libro l = null;
        ControllerLibro cl = new ControllerLibro();

        try {
            l = gson.fromJson(datosLibro, Libro.class);
            cl.delete(l.getId_libro());
            out = gson.toJson(l);
        } catch (JsonParseException jpe) {
            jpe.printStackTrace();
            out = """
                  {"exception":"Formato JSON de Datos incorrecto."}
                  """;
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"exception":"%s"}
                  """;
            out = String.format(out, e.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();

    }
}


    
