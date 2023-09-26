package com.prossofteam.oq.rest;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.prossofteam.oq.model.Empleado;
import com.prossofteam.oq.controller.ControllerEmpleado;
import com.prossofteam.oq.controller.ControllerLogin;
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

@Path("empleado")
public class RESTEmpleado {

    
    @GET
    @Path("getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll(@QueryParam("filtro") @DefaultValue("") String filtro) {
       
        String out = null;
        ControllerEmpleado ce = null;
        List<Empleado> empleados = null;
        
        try {
            ce = new ControllerEmpleado();
            empleados = ce.getAll(filtro);
            out = new Gson().toJson(empleados);
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }

    @Path("save")
    @POST  // por que solo se ingresan los datos 
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(@FormParam("datosEmpleado") @DefaultValue("") String datosEmpleado,
                         @FormParam("token") @DefaultValue("") String token) {

        String out = null;
        Gson gson = new Gson();
        Empleado emp = null;  
        ControllerEmpleado ce = new ControllerEmpleado();
        ControllerLogin cl = new ControllerLogin();

        try {
            if (cl.validarToken(token)) { 
                if(emp.getIdEmpleado() == 0){
                emp = gson.fromJson(datosEmpleado, Empleado.class);
                ce.insert(emp); // se inserta el empleado
                out = gson.toJson(emp);
                 
            }else {
            out = """
                  {"exception":"Formato JSON de Datosn incorrecto."}
                  """;
            }
                
            }else{
                out = """
                  {"error":"Credenciales incorrectas."}
                  """;
         
          }
        } catch (Exception e) { // excepciones de manera general 

            e.printStackTrace();
            out =  """
                  {"result":"error"}
                  """;
        }
        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    
    @POST
    @Path("delete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@FormParam("datosEmpleado") @DefaultValue("") String datosEmpleado){
        
        String out = null;
        Gson gson = new Gson(); // trabajar 
        Empleado emp = null; // tipo empleado 
        ControllerEmpleado ce = new ControllerEmpleado();
        
        try {

            emp = gson.fromJson(datosEmpleado, Empleado.class); // se pasa la cadena de datos  y se le dice de que tipo de clase se quiere 
                
            ce.delete(emp.getIdEmpleado());
            
            out = gson.toJson(emp); // el json se combierte una cadena String

        } catch (JsonParseException jpe) { // cuando ocurra una excepcion del json 

            jpe.printStackTrace(); // imprimir algo
            out = """
                  {"exception":"Formato JSON de Datos incorrecto."}
                  """;
        } catch (Exception e) { // excepciones de manera general 

            e.printStackTrace();
            out = """
                  {"exception":"%s"}
                  """;
            out = String.format(out, e.toString());
        }
        return Response.status(Response.Status.OK).entity(out).build();

    }
    

}
