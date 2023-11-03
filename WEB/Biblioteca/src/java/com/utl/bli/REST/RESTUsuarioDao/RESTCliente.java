/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utl.bli.REST.RESTUsuarioDao;

import com.google.gson.Gson;
import com.utl.bli.controller.ControllerAlumno;
import com.utl.bli.controller.usuarioDao.ControllerCliente;
import com.utl.bli.controller.usuarioDao.ControllerLoginAdministrador;
import com.utl.bli.model.Alumno;
import com.utl.bli.model.Usuario;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("cli")
public class RESTCliente {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("insert")
    public Response logIn(@FormParam("datosCli") @DefaultValue("") String datoCli) {
        String out = null;
        Gson gson = new Gson();
        Usuario usu = null;
        ControllerCliente ca = new ControllerCliente();

        try {
            usu = gson.fromJson(datoCli, Usuario.class);
            String res = ca.insertarCliente(usu);
            if (res.equals("0")) {
                out = "{\"error\": 'Ha faltado alguno de los campos'}";
            } else {
                out = gson.toJson(usu);
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = """
                  {"result":"error"}
                  """;
        }

        return Response.status(Response.Status.OK)
                .entity(out).build();
    }
//    @Path("insertar")
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response insertar(@FormParam("datosAlumno") @DefaultValue("") String datosAlumno) {
//
//        String out = null;
//        Gson gson = new Gson();
//        Alumno al = null;
//        usuarioCQRS us = new usuarioCQRS();
//
//        try {
//            al = gson.fromJson(datosAlumno, Alumno.class);
//               us.insertarCliente(al);
//
//            
//            out = gson.toJson(al);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            out = """
//                  {"result":"error"}
//                  """;
//        }
//
//        return Response.status(Response.Status.OK).entity(out).build();
//
//    }
}
