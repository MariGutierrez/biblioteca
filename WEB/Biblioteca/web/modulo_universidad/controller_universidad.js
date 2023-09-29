let universidades = [];
let keynum;

export function inicializar() {
    refrescarTabla();
}

export function refrescarTabla() {
    let filtro = document.getElementById("cmbFiltro").value;
    let url = "api/Uni/getAll?filtro=" + filtro;
    fetch(url)
            .then(response => {
                return response.json();
            })
            .then(function (data)
            {
                if (data.exception != null)
                {
                    swal.fire('', 'Error interno del servidor. Intente nuevamente más tarde.', 'Error');
                    return;
                }
                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                loadTable(data);
            });
}

export function buscar() {
    let filtro = document.getElementById("txtBusquedaUni").value;
    let url;
    if(filtro==="")
    {
    url = "api/Uni/buscar?filtro=" + filtro;
    }
    else
    {
     filtro = document.getElementById("cmbFiltro").value;   
     url = "api/Uni/getAll?filtro=" + filtro;   
    }
    fetch(url)
            .then(response => {
                return response.json();
            })
            .then(function (data)
            {
                if (data.exception != null)
                {
                    swal.fire('', 'Error interno del servidor. Intente nuevamente más tarde.', 'Error');
                    return;
                }
                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                loadTable(data);
            });
}


export function loadTable(data) {
    let cuerpo = "";
    universidades = data;

    universidades.forEach(function (Universidad) {
        let registro =
                '<tr>' +
                '<td>' + Universidad.nombre_universidad + '</td>' +
                '<td>' + Universidad.pais + '</td>' +
                '<td><a href="#" onclick="moduloUniversidad.mostrarDetalle(' + Universidad.id_universidad + ')">Seleccionar</a></td>' + '</tr>';
        cuerpo += registro;
    });
    document.getElementById("tblUniversidad").innerHTML = cuerpo;
}


//Guardar cliente
export function save() {
    let datos = null;
    let params = null;

    let universidad = new Object();

    if (document.getElementById("txtIdUni").value.trim().length < 1) {
        universidad.id_universidad = 0;
    } else {
        universidad.id_universidad = parseInt(document.getElementById("txtIdUni").value);
    }

    universidad.nombre_universidad = document.getElementById("txtNombreUni").value;
    universidad.pais = document.getElementById("cmbPais").value;
    universidad.estatus = 1;

    datos = {
        datosUni: JSON.stringify(universidad)
    };

    params = new URLSearchParams(datos);

    fetch("api/Uni/save",
            {method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response.json();
            })
            .then(function (data)
            {
                if (data.exception != null) {
                    Swal.fire('', "Error interno del servidor. Intente nuevamente mas tarde.", 'error');
                    return;
                }

                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }

                if (data.errorperm != null) {
                    Swal.fire('', "No tiene permiso para realizar esta operacion", 'warning');
                    return;
                }



                Swal.fire('', "Datos de la universidad actualizados correctamente", 'success');
                refrescarTabla();
                document.getElementById("divTbl").classList.remove("d-none");
                clean();
            });

}


//Seleccionar datos
export function mostrarDetalle(id) {
    universidades.forEach(function (universidad)
    {
        if (id === universidad.id_universidad)
        {
            document.getElementById("txtNombreUni").value = universidad.nombre_universidad;
            document.getElementById("cmbPais").value = universidad.pais;
            document.getElementById("txtIdUni").value = universidad.id_universidad;

        }
    });

    document.getElementById("btnDelete").classList.remove("disabled");
    document.getElementById("divTbl").classList.add("d-none");
}

export function clean() {
    document.getElementById("txtNombreUni").value = "";
    document.getElementById("txtIdUni").value = "";
    document.getElementById("btnDelete").classList.add("disabled");
}


//Eliminar
export function deleteUni() {
    let id2 = document.getElementById("txtIdUni").value;
    let datos = {
        id: id2
    };
    let params = new URLSearchParams(datos);
    fetch("api/Uni/delete",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response
            })
            .then(function (data)
            {
                if (data.exception != null) {
                    Swal.fire('', "Error interno del servidor. Intente nuevamente mas tarde.", 'error');
                    return;
                }

                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }

                if (data.errorperm != null) {
                    Swal.fire('', "No tiene permiso para realizar esta operacion", 'warning');
                    return;
                }

                Swal.fire('', "Universidad eliminada correctamente", 'success');
                refrescarTabla();
                document.getElementById("divTbl").classList.remove("d-none");
                clean();
            });
}



function ocultarTabla() {

    if (document.getElementById("divTbl").classList.contains("d-none")) {
        ocultar();
    } else {
        mostrar();
    }
}

function mostrar() {
    document.getElementById("divTbl").classList.add("d-none");
}
function ocultar() {
    document.getElementById("divTbl").classList.remove("d-none");
}

export function askDelete() {
    Swal.fire({
        title: '¿Deseas eliminar esta universidad?',
        text: "Se marcará como inactiva",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire(
                    deleteUni(),
                    'Universidad eliminada',
                    'Se ha marcado como inactiva',
                    'success'
                    );
        }
    });
}



export function imprimir(el) {

    var restorepage = document.body.innerHTML;
    var printcontent = document.getElementById(el).innerHTML;
    document.body.innerHTML = printcontent;
    window.print();
    document.body.innerHTML = restorepage;

}

export function soloNumeros(evt) {
    if (window.event) {
        keynum = evt.keyCode;
    } else {
        keynum = evt.which;
    }

    if ((keynum > 47 && keynum < 58) || keynum === 8 || keynum === 13 || keynum === 9) {
        return true;
    } else {
        Swal.fire('', "Solo debes ingresar números.", 'warning');
        return false;
    }
}

export function soloEspeciales(evt) {
    if (window.event) {
        keynum = evt.keyCode;
    } else {
        keynum = evt.which;
    }

    if ((keynum > 47 && keynum < 58) || keynum === 8 || keynum === 13 || keynum >= 97 && keynum <= 103
            || keynum === 105 || keynum === 111 || keynum === 117 || keynum === 35 || keynum === 45 || keynum === 13) {
        return true;
    } else {
        Swal.fire('', "Solo puedes ingresar numeros y los digitos siguientes: 'a''b''c''d''e'f'g''i''o''u''#''-'", 'warning');
        return false;
    }
}

export function soloNumerosYLetras(evt) {
    if (window.event) {
        keynum = evt.keyCode;
    } else {
        keynum = evt.which;
    }

    if ((keynum > 47 && keynum < 58) || keynum === 20 || keynum === 32 || keynum === 9 || keynum === 8 || keynum === 13 || keynum > 64 && keynum < 91 || keynum > 96 && keynum < 123) {
        return true;
    } else {
        Swal.fire('', "Solo debes ingresar números y letras.", 'warning');
        return false;
    }
}

export function soloLetras(e) {
    let key = e.keyCode || e.which;
    let tecla = String.fromCharCode(key).toString();
    let letras = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZÁÉÍÓÚabcdefghijklmnñopqrstuvwxyzáéíóú";

    let especiales = [8, 9, 13, 32, 20, 15, 132, 133, 126, 241, 209];
    let tecla_especial = false;
    for (var i in especiales) {
        if (key === especiales[i]) {
            tecla_especial = true;
            break;
        }
    }

    if (letras.indexOf(tecla) === -1 && !tecla_especial) {
        Swal.fire('', "Solo debes ingresar letras.", 'warning');
        return false;
    }
}

export function soloNumerosDiagonales(evt) {
    if (window.event) {
        keynum = evt.keyCode;
    } else {
        keynum = evt.which;
    }

    if ((keynum > 47 && keynum < 58) || keynum === 8 || keynum === 13 || keynum === 47 || keynum === 13) {
        return true;
    } else {
        Swal.fire('', "Solo debes ingresar números y diagonales.", 'warning');
        return false;
    }
}

export function validarFormulario() {
    let bandera = false;

    if (document.getElementById("txtNombre").value === "") {
        document.getElementById("txtNombre").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Nombre'", 'warning');
        return bandera;
    }
    if (document.getElementById("txtApePaterno").value === "") {
        document.getElementById("txtApePaterno").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Apellido paterno'", 'warning');
        return bandera;
    }
    if (document.getElementById("cmbGenero").value === "") {
        document.getElementById("cmbGenero").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Genero'", 'warning');
        return bandera;
    }
    if (document.getElementById("dpFechaNacimiento").value === "") {
        document.getElementById("dpFechaNacimiento").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Fecha de nacimiento'", 'warning');
        return bandera;
    }
    if (document.getElementById("cmbGenero").value === "") {
        document.getElementById("cmbGenero").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Género'", 'warning');
    }
    if (document.getElementById("dpFechaNacimiento").value.length < 10) {
        document.getElementById("dpFechaNacimiento").focus();
        Swal.fire('', "Ingresa el formato correcto (dd/mm/aaaa) en el campo 'Fecha de nacimiento'", 'warning');
        return bandera;
    }

    if (document.getElementById("txtRfc").value === "") {
        document.getElementById("txtRfc").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'RFC'", 'warning');
        return bandera;
    }

    if (document.getElementById("txtCorreo").value === "") {
        document.getElementById("txtCorreo").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Correo'", 'warning');
        return bandera;
    }
    if (document.getElementById("txtCalle").value === "") {
        document.getElementById("txtCalle").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Calle'", 'warning');
        return bandera;
    }
    if (document.getElementById("txtNumero").value === "") {
        document.getElementById("txtNumero").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Número'", 'warning');
        return bandera;
    }
    if (document.getElementById("txtColonia").value === "") {
        document.getElementById("txtColonia").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Colonia'", 'warning');
        return bandera;
    }
    if (document.getElementById("txtCodigoPostal").value === "") {
        document.getElementById("txtCodigoPostal").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Códugo postal'", 'warning');
        return bandera;
    }
    if (document.getElementById("txtCodigoPostal").value.length < 5) {
        document.getElementById("txtCodigoPostal").focus();
        Swal.fire('', "Ingresa 5 digitos en el campo 'Código postal'", 'warning');
        return bandera;
    }
    if (document.getElementById("txtCiudad").value === "") {
        document.getElementById("txtCiudad").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Ciudad'", 'warning');
        return bandera;
    }
    if (document.getElementById("txtEstado").value === "") {
        document.getElementById("txtEstado").focus();
        Swal.fire('', "Asegúrate de llenar el campo 'Estado'", 'warning');
        return bandera;
    }
    return;
}

export function validarTelefono() {
    let bandera = false;

    if (document.getElementById("txtTelefono").value.length < 10 && document.getElementById("txtTelefono").value.length >= 1) {
        document.getElementById("txtTelefono").focus();
        Swal.fire('', "El teléfono fijo debe tener 10 dígitos.", 'warning');
        return bandera;
    }
    if (document.getElementById("txtMovil").value === "" || document.getElementById("txtMovil").value.length < 10) {
        document.getElementById("txtMovil").focus();
        Swal.fire('', "El teléfono móvil debe tener 10 dígitos.", 'warning');
        return bandera;
    }
    return;
}

export function validarRfc() {
    let bandera = false;

    if (document.getElementById("txtRfc").value.length < 12) {
        document.getElementById("txtRfc").focus();
        Swal.fire('', "El RFC debe tener 12 o 13 digitos según corresponda", 'warning');
        return bandera;
    }
    return;
}
