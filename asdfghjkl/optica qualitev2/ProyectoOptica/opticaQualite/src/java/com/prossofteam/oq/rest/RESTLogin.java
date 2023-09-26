package com.prossofteam.oq.rest;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.prossofteam.oq.controller.ControllerLogin;
import com.prossofteam.oq.model.Empleado;
import com.prossofteam.oq.model.Usuario;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("login")
public class RESTLogin extends Application {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("login")
    public Response logIn(@FormParam("datos") @DefaultValue("") String datos) {

        Gson gson = new Gson();
        String out = null;
        ControllerLogin cl = new ControllerLogin();
        Empleado emp = null;

        Usuario usuario = gson.fromJson(datos, Usuario.class);
        try {
            emp = cl.login(usuario.getNombre(), usuario.getContrasenia());
            if (emp != null) {
                emp.getUsuario().setLastToken();
                out = new Gson().toJson(emp);
                cl.guardarToken(emp.getUsuario().getIdUsuario(), emp.getUsuario().getLastToken());

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("out")
    public Response logout(@FormParam("empleado") @DefaultValue("") String e) throws Exception {

        String out = null;
        Empleado empleado = null;
        ControllerLogin cl = null;
        Gson gson = new Gson();

        try {
            empleado = gson.fromJson(e, Empleado.class);
            cl = new ControllerLogin();

            if (cl.eliminarToken(empleado)) {
                out = """
                         {"ok": "Eliminacion de token completa"}      
                """;
            } else {
                out = """
                         {"error": "Eliminacion de token no completa"}      
                """;
            }
        } catch (JsonParseException jpe) {
            out = """
                  {"excepcion":"Formatos JSON de datos inorrectos"}
                  """;
            jpe.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
            out = """
                  {"excepcion":"%s"}
                  """;
            out = String.format(out, ex.toString());
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
}
