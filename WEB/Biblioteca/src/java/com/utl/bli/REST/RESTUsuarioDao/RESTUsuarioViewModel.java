package com.utl.bli.REST.RESTUsuarioDao;

import com.google.gson.Gson;
import com.utl.bli.appService.UsuariosAppService;
import com.utl.bli.controller.ControllerLogin;
import com.utl.bli.controller.usuarioDao.ControllerLoginAdministrador;
import com.utl.bli.controller.usuarioDao.ControllerLoginVendedor;
import com.utl.bli.model.Usuario;
import com.utl.bli.viewModel.UsuarioPublicViewModel;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("Busc")
public class RESTUsuarioViewModel {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("UVM")
    public Response logIn(@FormParam("nombre_usuario") @DefaultValue("") String nombre_usuario) {

        String out = null;
        Gson gson = new Gson();
        ControllerLoginAdministrador cla = new ControllerLoginAdministrador();
        Usuario usu;
        try {
            usu = cla.buscUs(nombre_usuario);
            if (usu != null) {
                out = new Gson().toJson(usu);
            } else {
                out = "{\"error\": 'Usuario y/o contraseña incorrectos'}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("UVMPublic")
    public Response logInPublic(@FormParam("nombre_usuario") @DefaultValue("") String nombre_usuario) {

        String out = null;
        Gson gson = new Gson();
        ControllerLoginAdministrador cla = new ControllerLoginAdministrador();
        UsuarioPublicViewModel usu;
        try {
            usu = cla.buscUs_vm(nombre_usuario);
            if (usu != null) {
                out = new Gson().toJson(usu);
            } else {
                out = "{\"error\": 'Usuario y/o contraseña incorrectos'}";
            }
        } catch (Exception e) {
            e.printStackTrace();
            out = "{\"exception\":\"Error interno del servidor.\"}";
        }

        return Response.status(Response.Status.OK).entity(out).build();
    }
}
