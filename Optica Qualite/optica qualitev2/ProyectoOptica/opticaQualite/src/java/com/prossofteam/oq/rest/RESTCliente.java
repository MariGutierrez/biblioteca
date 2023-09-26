package com.prossofteam.oq.rest;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.prossofteam.oq.controller.ControllerCliente;
import com.prossofteam.oq.controller.ControllerLogin;
import com.prossofteam.oq.model.Cliente;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author ximer
 */
@Path("cliente")
public class RESTCliente {

    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {

        String out = null;
        ControllerCliente cc = null;
        List<Cliente> clientes = null;

        try {
            cc = new ControllerCliente();
            clientes = cc.getAll(filtro);
            out = new Gson().toJson(clientes);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    } 

    
    
    @POST
    @Path("buscar")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response buscar(@FormParam("filtro") @DefaultValue("") String filtro) {
        
        String out = null;
        ControllerCliente cc = null;
        List<Cliente> clientes = null;

            try {
                cc = new ControllerCliente();
                clientes = cc.getAll(filtro);
                out = new Gson().toJson(clientes);
            } catch (Exception e) {
                e.printStackTrace();
                out = "{\"exception\":\"Error interno del servidor.\"}";
            }
        
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    
    
    @Path("save")
    @POST  // por que solo se ingresan los datos 
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosCliente") @DefaultValue("") String datosCliente,
            @FormParam("token") @DefaultValue("") String token) {

        String out = null;
        Gson gson = new Gson();
        Cliente cli = null;
        ControllerCliente cc = new ControllerCliente();
        ControllerLogin cl = new ControllerLogin();

        try {

            if (cl.validarToken(token)) {
              
                cli = gson.fromJson(datosCliente, Cliente.class);
                if (cli.getIdCliente() == 0) {
                    
                    cc.insert(cli);
                    
                } else {
                    cc.update(cli);
                }
                out = gson.toJson(cli);

            } else {
                out = """
                  {"error":"Credenciales incorrectas."}
                  """;
            }
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
    public Response delete(@FormParam("datosCliente") @DefaultValue("") String datosCliente, 
            @FormParam("token") @DefaultValue("") String token) {

        String out = null;
        Gson gson = new Gson();
        Cliente cli = null;
        ControllerCliente cliente = new ControllerCliente();
        ControllerLogin cl = new ControllerLogin();

        try {
            if(cl.validarToken(token)){
                
                cli = gson.fromJson(datosCliente, Cliente.class);

            cliente.delete(cli.getIdCliente());

            out = gson.toJson(cli);
                
            }else{
                  out = """
                  {"error":"Credenciales incorrectas."}
                  """;
            }
            
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