/* global persona */

let index;
let empleados = [];


//para la el bloqueo de servicios 


export function inicializar(){ 
        

//    configureTableFilter(document.getElementById('txtBusquedaEmpleado'),
//                        document.getElementById('tblEmp'));
    refrescarTabla();
}


export function refrescarTabla() {
    let url = "api/empleado/getAll"; //definimos url del servicio (relativa)
    fetch(url).then(response => {
        return response.json();
    }) //.json cargamos documentos json
            .then(function (data) //proceso 
            {
                console.log(data.usuario);
                if (data.exception != null)
                {
                    Swal.fire('',
                            'Error interno del servidor. Intente nuevamente más tarde.',
                            'error');
                    return;
                }
                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorsec != null)  //si el usuario no se logea, se regresa al index 
                {
                    Swal.fire('', data.errorsec, 'error');
                    window.location.replace('index.html');
                    return;
                }
                fillTable(data);
            });
}

function fillTable(data) {

    let cuerpo = '';
    empleados = data;
    empleados.forEach(function (empleado) {
        let registro =
                '<tr onclick="moduloEmpleado.selectEmpleado(' + empleado.idEmpleado + ');">' +
                '<td>' + empleado.numeroUnico + '</td>' +
                '<td>' + empleado.persona.nombre + '</td>' +
                '<td>' + empleado.persona.apellidoPaterno + ' ' + empleado.persona.apellidoMaterno + '</td>' +
                '<td>' + empleado.persona.telMovil + '</td>' +
                '<td>' + empleado.usuario.nombre + '</td></tr>';
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblEmpleados").innerHTML = cuerpo;
}

//SAVE
export function save() {
    
    let currentUser = localStorage.getItem('currentUser');
    let user = JSON.parse(currentUser);
    //console.log(user);
    let token = user.usuario.lastToken;
    let datos = null;
    let params = null;

    let empleado = new Object();
    empleado.usuario = new Object();
    empleado.persona = new Object();

    if (document.getElementById("txtNumUnico").value.trim().length < 1) {
       
        empleado.idEmpleado = 0;
        empleado.persona.idPersona = 0;
        empleado.usuario.idUsuario = 0;
    
    } else {
    
        empleado.idEmpleado = parseInt(document.getElementById("txtIDEmpleado").value);
        empleado.persona.idPersona = parseInt(document.getElementById("txtIDPersona").value);
        empleado.usuario.idUsuario = parseInt(document.getElementById("txtIDUsuario").value);
    
    }
    
    empleado.persona.nombre = document.getElementById("txtNombre").value;
    empleado.persona.apellidoPaterno = document.getElementById("txtApePaterno").value;
    empleado.persona.apellidoMaterno = document.getElementById("txtApeMaterno").value;
    empleado.persona.genero = document.getElementById("cmbGenero").value;
    empleado.persona.fechaNacimiento = document.getElementById("dpFecha").value;
    empleado.persona.calle = document.getElementById("txtCalle").value;
    empleado.persona.numero = document.getElementById("txtNumero").value;
    empleado.persona.colonia = document.getElementById("txtColonia").value;
    empleado.persona.cp = document.getElementById("txtCP").value;
    empleado.persona.ciudad = document.getElementById("txtCiudad").value;
    empleado.persona.estado = document.getElementById("cmbEstado").value;
    empleado.persona.telCasa = document.getElementById("txtTelefono").value;
    empleado.persona.telMovil = document.getElementById("txtMovil").value;
    empleado.persona.email = document.getElementById("txtCorreo").value;

    empleado.usuario.nombre = document.getElementById("txtUsuario").value;
    empleado.usuario.contrasenia = document.getElementById("txtContraseña").value;
    empleado.usuario.rol = document.getElementById("cmbRol").value;

    datos = {
        datosEmpleado: JSON.stringify(empleado)
    };

    params = new URLSearchParams({datosCliente: JSON.stringify(empleado), token: token});

    fetch("api/empleado/save",
            {method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response.json();
            })
            .then(function (data) {
                console.log(data);

                if(data.exception != null){
                    Swal.fire('','Error interno del servidor. Intente nuevamente más tarde.','error');
                    return;
                }
                if(data.error != null){
                    Swal.fire('',data.error, 'warning');
                    return;
                }
                if(data.errorperm != null){
                    Swal.fire('',"No tiene permiso para realizar esta operacion.", 'warning');
                    return;
                }
                document.getElementById("txtIDEmpleado").value = data.idEmpleado;
                document.getElementById("txtIDUsuario").value = data.usuario.idUsuario;
                document.getElementById("txtIDPersona").value = data.persona.idPersona;
                document.getElementById("txtNumUnico").value = data.numeroUnico;
                Swal.fire('',"Empleado agregado.", 'success');
                refrescarTabla();
                clean();
            });

}


export function selectEmpleado(index) {
    empleados.forEach(function (empleado) {
        if (index === empleado.idEmpleado) {
            document.getElementById("txtNumUnico").value = empleado.numeroUnico;
            document.getElementById("txtNombre").value = empleado.persona.nombre;
            document.getElementById("txtApePaterno").value = empleado.persona.apellidoPaterno;
            document.getElementById("txtApeMaterno").value = empleado.persona.apellidoMaterno;
            document.getElementById("cmbGenero").value = empleado.persona.genero;
            document.getElementById("dpFecha").value = empleado.persona.fechaNacimiento;
            document.getElementById("txtCalle").value = empleado.persona.calle;
            document.getElementById("txtNumero").value = empleado.persona.numero;
            document.getElementById("txtColonia").value = empleado.persona.colonia;
            document.getElementById("txtCP").value = empleado.persona.cp;
            document.getElementById("txtCiudad").value = empleado.persona.ciudad;
            document.getElementById("cmbEstado").value = empleado.persona.estado;
            document.getElementById("txtTelefono").value = empleado.persona.telCasa;
            document.getElementById("txtMovil").value = empleado.persona.telMovil;
            document.getElementById("txtCorreo").value = empleado.persona.email;


            document.getElementById("txtUsuario").value = empleado.usuario.nombre;
            document.getElementById("txtContraseña").value = empleado.usuario.contrasena;

            document.getElementById("txtIDEmpleado").value = empleado.idEmpleado;
            document.getElementById("txtIDPersona").value = empleado.persona.idPersona;
            document.getElementById("txtIDUsuario").value = empleado.usuario.idUsuario;

           }
    });


}

export function clean() {

    document.getElementById("txtNumUnico").value = "";
    document.getElementById("txtNombre").value = "";
    document.getElementById("txtApePaterno").value = "";
    document.getElementById("txtApeMaterno").value = "";
    document.getElementById("txtCalle").value = "";
    document.getElementById("txtNumero").value = "";
    document.getElementById("cmbEstado").value = "";
    document.getElementById("txtTelefono").value = "";
    document.getElementById("txtMovil").value = "";
    document.getElementById("txtCorreo").value = "";
    document.getElementById("txtCP").value = "";
    document.getElementById("cmbGenero").value = "";
    document.getElementById("txtUsuario").value = "";
    document.getElementById("txtContraseña").value = "";
    document.getElementById("dpFecha").value = "";
    document.getElementById("txtColonia").value = "";
    document.getElementById("txtCiudad").value = "";
    document.getElementById("cmbEstado").value = "";
    document.getElementById("cmbRol").value = "";
}

export function delet() {
    let datos = null;
    let params = null;
    
    let empleado = new Object();
    empleado.usuario = new Object();
    empleado.persona = new Object();
    
    empleado.numeroUnico = document.getElementById("txtNumUnico").value;
    
    empleado.idEmpleado = parseInt(document.getElementById("txtIDEmpleado").value);
    empleado.persona.idPersona = parseInt(document.getElementById("txtIDPersona").value);
    empleado.usuario.idUsuario = parseInt(document.getElementById("txtIDUsuario").value);
    empleado.persona.nombre = document.getElementById("txtNombre").value;
    empleado.persona.apellidoPaterno = document.getElementById("txtApePaterno").value;
    empleado.persona.apellidoMaterno = document.getElementById("txtApeMaterno").value;
    empleado.persona.genero = document.getElementById("cmbGenero").value;
    empleado.persona.fechaNacimiento = document.getElementById("dpFecha").value;
    empleado.persona.calle = document.getElementById("txtCalle").value;
    empleado.persona.numero = document.getElementById("txtNumero").value;
    empleado.persona.colonia = document.getElementById("txtColonia").value;
    empleado.persona.cp = document.getElementById("txtCP").value;
    empleado.persona.ciudad = document.getElementById("txtCiudad").value;
    empleado.persona.estado = document.getElementById("cmbEstado").value;
    empleado.persona.telCasa = document.getElementById("txtTelefono").value;
    empleado.persona.telMovil = document.getElementById("txtMovil").value;
    empleado.persona.email = document.getElementById("txtCorreo").value;

    empleado.usuario.nombre = document.getElementById("txtUsuario").value;
    empleado.usuario.contrasenia = document.getElementById("txtContraseña").value;
    empleado.usuario.rol = document.getElementById("cmbRol").value;
    
    datos = {
        datosEmpleado: JSON.stringify(empleado)
    };

    params = new URLSearchParams({datosCliente: JSON.stringify(empleado), token: token});

    fetch("api/empleado/delete",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response.json();
            })
            .then(function (data) {
                
            Swal.fire('', 'Datos eliminados correctamente.', 'success');
                refrescarTabla();
                clean();
            });

}



//SECCIÓN PARA IMPRIMIR
export function loadTabla11() {

    var combo = document.getElementById("estado");
    var estatus = combo.options[combo.selectedIndex].text;
    if (estatus == "Activo") {
        aclis1();
    } else {
        Inaclis1();
    }
}

export function imprimir(el)
{
    loadTabla11();
    var restorepage = document.body.innerHTML;
    var printcontent = document.getElementById(el).innerHTML;
    document.body.innerHTML = printcontent;
    window.print();
    document.body.innerHTML = restorepage;
    loadTable1();
}

var input = document.getElementById('txtTelefono');
input.addEventListener('input', function () {
    if (this.value.length > 10)
        this.value = this.value.slice(0, 10);
});

var input = document.getElementById('txtMovil');
input.addEventListener('input', function () {
    if (this.value.length > 10)
        this.value = this.value.slice(0, 10);
});