let indexClienteSeleccionado;
let clientes = [];
let num;
const d = new Date();
let day = d.getDate() + "" + (d.getMonth() + 1) + "" + d.getFullYear();
//        d.getDate() + "" + (d.getMonth()+1) + "" + d.getFullYear() + "" + "" + d.getMinutes() + "" + d.getSeconds;
//para la el bloqueo de servicios 
let currentUser = localStorage.getItem('currentUser');
let user = JSON.parse(currentUser);
//console.log(user);
let token = user.usuario.lastToken;

export function inicializar() {
    refrescarTabla();
}

export function buscarProducto(filtro) {
    let params = new URLSearchParams({filtro: filtro});
    fetch("api/cliente/buscar", {
        method: "POST",
        headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params
    })
            .then(response => {
                return response.json();
            })
            .then(function (data)
            {
                console.log(data);
                if (data.exception != null)
                {
                    swal.fire('', 'Error DE SERVIDOR.', 'Error');
                    window.location.replace("index.html");
                    return;
                }
                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorsec != null)
                {
                    Swal.fire('', data.errorsec, 'error');
                    window.location.replace('index.html');
                    return;
                } else {
                    productos = data;
                    cargarTabla(data);
                }
            });
}

export function save() {

    let datos = null;
    let params = null;

    let cliente = new Object();
    cliente.persona = new Object();

    if (document.getElementById("txtNumUnico").value.trim().length < 1) {

        cliente.idCliente = 0;
        cliente.idPersona = 0;


    } else {

        cliente.idCliente = parseInt(document.getElementById("txtIdCliente").value);
        cliente.persona.idPersona = parseInt(document.getElementById("txtIdPersona").value);
    }

    cliente.persona.nombre = normalizar(document.getElementById("txtNombre").value);
    cliente.persona.apellidoPaterno = normalizar(document.getElementById("txtApePaterno").value);
    cliente.persona.apellidoMaterno = normalizar(document.getElementById("txtApeMaterno").value);
    cliente.persona.fechaNacimiento = document.getElementById("txtFecha").value;
    cliente.persona.calle = normalizar(document.getElementById("txtCalle").value);
    cliente.persona.numero = document.getElementById("txtNumero").value;
    cliente.persona.colonia = normalizar(document.getElementById("txtColonia").value);
    cliente.persona.cp = document.getElementById("txtCodigo").value;
    cliente.persona.ciudad = normalizar(document.getElementById("txtCiudad").value);
    cliente.persona.estado = document.getElementById("txtEstado").value;
    cliente.persona.telCasa = document.getElementById("txtTelefono").value;
    cliente.persona.telMovil = document.getElementById("txtMovil").value;
    cliente.persona.email = document.getElementById("txtCorreo").value;
    cliente.persona.genero = document.getElementById("txtGenero").value;





    datos = {
        datosCliente: JSON.stringify(cliente)
    };

    params = new URLSearchParams({datosCliente: JSON.stringify(cliente), token: token});

    fetch("api/cliente/save",
            {method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response.json();
            })
            .then(function (data) {
                if (data.exception != null) {
                    Swal.fire('', 'No tiene permiso para realizar esta acción.', 'error');
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorperm != null) {
                    Swal.fire('', "No tiene permiso para realizar esta operacion.", 'warning');
                    return;
                }
                document.getElementById("txtIdPersona").value = data.idPersona;
                document.getElementById("txtIdCliente").value = data.idCliente;
                document.getElementById("txtNumUnico").value = data.numeroUnico;
                Swal.fire('', 'Datos del cliente guardados/actualizados correctamente.', 'success');
                refrescarTabla();
                clean();
            });
}

export function delet() {

    let datos = null;
    let params = null;

    let cliente = new Object();
    cliente.persona = new Object();


    cliente.numeroUnico = document.getElementById("txtNumUnico").value;

    cliente.idCliente = parseInt(document.getElementById("txtIdCliente").value);
    cliente.persona.idPersona = parseInt(document.getElementById("txtIdPersona").value);

    cliente.persona.nombre = normalizar(document.getElementById("txtNombre").value);
    cliente.persona.apellidoPaterno = normalizar(document.getElementById("txtApePaterno").value);
    cliente.persona.apellidoMaterno = normalizar(document.getElementById("txtApeMaterno").value);
    cliente.persona.fechaNacimiento = document.getElementById("txtFecha").value;
    cliente.persona.calle = normalizar(document.getElementById("txtCalle").value);
    cliente.persona.numero = document.getElementById("txtNumero").value;
    cliente.persona.colonia = normalizar(document.getElementById("txtColonia").value);
    cliente.persona.cp = document.getElementById("txtCodigo").value;
    cliente.persona.ciudad = normalizar(document.getElementById("txtCiudad").value);
    cliente.persona.estado = document.getElementById("txtEstado").value;
    cliente.persona.telCasa = document.getElementById("txtTelefono").value;
    cliente.persona.telMovil = document.getElementById("txtMovil").value;
    cliente.persona.email = document.getElementById("txtCorreo").value;
    cliente.persona.genero = document.getElementById("txtGenero").value;


    datos = {
        datosCliente: JSON.stringify(cliente)
    };

    params = new URLSearchParams({datosCliente: JSON.stringify(cliente), token: token});

    fetch("api/cliente/delete",
            {method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response.json();
            })
            .then(function (data)
            {
                if (data.exception != null)
                {
                    Swal.fire('', 'Error interno del servidor. Intente nuevamente mas tarde.', 'error');
                    return;
                }

                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning');
                    return;
                }

                if (data.errorperm != null)
                {
                    Swal.fire('', "No tiene permiso para realizar esta operacion.", 'warning');
                    return;
                }
//        si no hay errores se guardan en las cajas de textos los id's 

                Swal.fire('', 'Datos del cliente eliminados correctamente.', 'success');
                refrescarTabla();
                clean();
            });

}

export function refrescarTabla() {

    let url = "api/cliente/getAll";
    fetch(url)
            .then(response => {
                return response.json();
            })
            .then(function (data) {

                if (data.exception != null)
                {
                    Swal.fire('',
                            'Error interno del servicio. Intente nuevamente mas tarde.',
                            'error');

                    return;
                }

                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning');
                    return;
                }

                if (data.errosec != null) {
                    Swal.fire('', data, errorsec, 'error');
                    window.location.replace('index.html');
                    return;
                }
                fillTable(data);
            });
}

function fillTable(data) {

    let cuerpo = "";
    clientes = data;
    clientes.forEach(function (cliente) {
        let registro =
                '<tr onclick="moduloClientes.selectClientes(' + clientes.indexOf(cliente) + ');">' +
                '<td>' + cliente.numeroUnico + '</td>' +
                '<td>' + cliente.persona.nombre + '</td>' +
                '<td>' + cliente.persona.apellidoPaterno + ' ' + cliente.persona.apellidoMaterno + '</td>' +
                '<td>' + cliente.persona.genero + '</td>' +
                '<td>' + cliente.persona.telMovil + '</td></tr>';
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblClientes").innerHTML = cuerpo;
}

export function loadTable1(data) {
    var combo = document.getElementById("estado");
    var estatus = combo.options[combo.selectedIndex].text;
    let a = '<tr>' +
            '<th>NUC</th>' +
            '<th>Nombre(s)</th>' +
            '<th>Apellido(s)</th>' +
            '<th>Genero</th>' +
            '<th>Movil</th></tr>';
    document.getElementById("tb2").innerHTML = a;
    if (estatus == "Activo") {
        activos();
    } else {
        inactivos();
    }
}

export function loadTabla1(data) {
    var combo = document.getElementById("estado");
    var estatus = combo.options[combo.selectedIndex].text;

    if (estatus == "Activo") {
        activos();
    } else {
        inactivos();
    }

}

export function selectClientes(index) {

    document.getElementById("txtNumUnico").value = clientes[index].numeroUnico;
    document.getElementById("txtIdCliente").value = clientes[index].idCliente;
    document.getElementById("txtIdPersona").value = clientes[index].persona.idPersona;
    document.getElementById("txtNombre").value = clientes[index].persona.nombre;
    document.getElementById("txtApePaterno").value = clientes[index].persona.apellidoPaterno;
    document.getElementById("txtApeMaterno").value = clientes[index].persona.apellidoMaterno;
    document.getElementById("txtFecha").value = clientes[index].persona.fechaNacimiento;
    document.getElementById("txtCalle").value = clientes[index].persona.calle;
    document.getElementById("txtNumero").value = clientes[index].persona.numero;
    document.getElementById("txtColonia").value = clientes[index].persona.colonia;
    document.getElementById("txtCodigo").value = clientes[index].persona.cp;
    document.getElementById("txtCiudad").value = clientes[index].persona.ciudad;
    document.getElementById("txtEstado").value = clientes[index].persona.estado;
    document.getElementById("txtTelefono").value = clientes[index].persona.telCasa;
    document.getElementById("txtMovil").value = clientes[index].persona.telMovil;
    document.getElementById("txtCorreo").value = clientes[index].persona.email;
    document.getElementById("txtGenero").value = clientes[index].persona.genero;


    indexClienteSeleccionado = index;
}

export function clean() {

    document.getElementById("txtNumUnico").value = "";
    document.getElementById("txtIdPersona").value = "";
    document.getElementById("txtIdCliente").value = "";
    document.getElementById("txtNombre").value = "";
    document.getElementById("txtApePaterno").value = "";
    document.getElementById("txtApeMaterno").value = "";
    document.getElementById("txtFecha").value = "";
    document.getElementById("txtCalle").value = "";
    document.getElementById("txtNumero").value = "";
    document.getElementById("txtColonia").value = "";
    document.getElementById("txtCodigo").value = "";
    document.getElementById("txtCiudad").value = "";
    document.getElementById("txtEstado").value = "";
    document.getElementById("txtTelefono").value = "";
    document.getElementById("txtMovil").value = "";
    document.getElementById("txtCorreo").value = "";

    document.getElementById("txtNombre").focus();
    indexClienteSeleccionado = 0;
}



function normalizar(texto){
    texto = texto.toUpperCase();
    for (var i = 0; i < texto.length; i++) {
        texto=texto.replace("Á","A");
        texto=texto.replace("É","E");
        texto=texto.replace("Í","I");
        texto=texto.replace("Ó","O");
        texto=texto.replace("Ú","U");
        
    }
    return texto;
    }




function alertaNohay() {
    Swal.fire({
        title: '¡Campo vacío!',
        text: 'Ingresa el dato a buscar',
        icon: 'error'
    })
}

var input = document.getElementById('txtTelefono');
input.addEventListener('input', function () {
    if (this.value.length > 12)
        this.value = this.value.slice(0, 12);
})

var input = document.getElementById('txtMovil');
input.addEventListener('input', function () {
    if (this.value.length > 12)
        this.value = this.value.slice(0, 12);
})