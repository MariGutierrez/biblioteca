/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.utl.bli.controller.usuarioDao;

import com.utl.bli.CQRS.UsuarioCQRS;
import com.utl.bli.appService.UsuariosAppService;
import com.utl.bli.model.Usuario;

public class ControllerCliente {
    public String insertarCliente(Usuario u) throws Exception {
       UsuariosAppService ups = new UsuariosAppService();
       String res = ups.registroCliente(u);
       return res;
    }
}
