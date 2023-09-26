let clientes;
let clienteSeleccionado;
let examenVista;
let tratamientos;
let tratamientoSeleccionado;
let tipoMica;
let armazon;
let material;
let PrecioM;


export function inicializar() {
    const inputBuscar = document.querySelector('#txtBuscarCliente');
    inputBuscar.addEventListener('input', (event) => {
        buscarCliente(event.target.value);
        console.log(event.target.value);
    });
    
    armazones("");
    materiales("");
    tipoMicas("");
}


//Parte cliente ---------------------------------------------------------------------------------------------------------
export function buscarCliente() {
    let params = new URLSearchParams({filtro: " "});
    fetch("api/cliente/buscar",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response.json()
            })
            .then(function (data)
            {
                console.log(data);
                if (data.exception != null) {
                    Swal.fire('',
                            'Error interno del servidor. Intente nuevamente más tarde',
                            'error'
                            );
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorsec != null) {
                    Swal.fire('', data.errorsec, 'error');
                    return;
                } else {
                    loadTablaC(data);
                }
            });
}

export function loadTablaC(data) {
    let cuerpo = "";
    clientes = data;
    clientes.forEach(function (cliente) {
        let registro =
                '<tr id=' + clientes.indexOf(cliente) + clientes.indexOf(cliente) + ');">' +
                '<td>' + cliente.persona.nombre + '' + cliente.persona.apellidoPaterno + '' + cliente.persona.apellidoMaterno + '</td>' +
                '<td>' + cliente.persona.telCasa + '</td>' +
                '<td>' + '<button onclick="moduloDetalleVentaPL.seleccionCliente(' + clientes.indexOf(cliente) + ')" id="btnAgregarCliente">Agregar</button>' + '</td>';
        cuerpo += registro;
    });
    document.getElementById("tblClientes").innerHTML = cuerpo;
}

export function seleccionCliente(index) {
    datosSelect(clientes[index].idCliente);
}

//Parte examen de vista ---------------------------------------------------------------------------------------------------------
export function datosSelect(idCliente) {
    let params = new URLSearchParams({idCliente: idCliente});
    fetch("api/examenV/buscar",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response.json()
            })
            .then(function (data)
            {
                console.log(data);
                if (data.exception != null) {
                    Swal.fire('',
                            'Error interno del servidor. Intente nuevamente más tarde',
                            'error'
                            );
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorsec != null) {
                    Swal.fire('', data.errorsec, 'error');
                    return;
                } else {
                    examenVista = data;
                    const examenSeleccionado = document.querySelector("#examen");
                    examenVista.forEach(function (examenVista) {
                        let vista = document.createElement("option");
                        vista.value = examenVista.idExamenVista;
                        vista.text = examenVista.clave + ' ' + examenVista.fecha;
                        examenSeleccionado.appendChild(vista);
                    });
                }
            });
}

// Parte Tratamiento -----------------------------------------------------------
export function tratamiento(filtro) {
    let params = new URLSearchParams({filtro: filtro});
    fetch("api/tratamiento/getAll",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response.json()
            })
            .then(function (data)
            {
                console.log(data);
                if (data.exception != null) {
                    Swal.fire('',
                            'Error interno del servidor. Intente nuevamente más tarde',
                            'error'
                            );
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorsec != null) {
                    Swal.fire('', data.errorsec, 'error');
                    return;
                } else {
                    tratamientos = data;
                    const tratamientoSeleccionado = document.querySelector("#tratamientos");
                    tratamientos.forEach(function (tratamiento) {
                        let vista = document.getElementById("option");
                        vista.value = tratamiento.idTratamiento;
                        vista.text = tratamiento.nombre;
                        tratamientoSeleccionado.appendChild(vista);
                    });
                }
            });
}

// Parte Armazones--------------------------------------------------------------
export function armazones(filtro) {
    let params = new URLSearchParams({filtro: filtro});
    fetch("api/armazon/getAll",
            {
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
                if (data.exception != null) {
                    Swal.fire('',
                            'Error interno del servidor. Intente nuevamente más tarde',
                            'error'
                            );
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorsec != null) {
                    Swal.fire('', data.errorsec, 'error');
                    return;
                } else {
                    armazon = data;
                    const armazonSeleccionado = document.querySelector("#armazon");
                    armazon.forEach(function (arma) {
                        let vista = document.createElement("option");
                        vista.value = arma.idArmazon;
                        vista.text = arma.modelo;
                        armazonSeleccionado.appendChild(vista);
                    });
                }

            });
}

// Tipo de mica ----------------------------------------------------------------
export function tipoMicas(filtro) {
    let params = new URLSearchParams({filtro: filtro});
    fetch("api/tipoMica/getAll",
            {
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
                if (data.exception != null) {
                    Swal.fire('',
                            'Error interno del servidor. Intente nuevamente más tarde',
                            'error'
                            );
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorsec != null) {
                    Swal.fire('', data.errorsec, 'error');
                    return;
                } else {
                    tipoMica = data;
                    const tipoMicaSeleccionado = document.querySelector("#tipoMica");
                    tipoMica.forEach(function (tipoM) {
                        let vista = document.createElement("option");
                        vista.value = tipoM.idTipoMica;
                        vista.text = tipoM.nombre;
                        tipoMicaSeleccionado.appendChild(vista);
                        console.log(tipoM.idTipoMica);
                    });
                }

            });
}


// MATERIAL---------------------
export function materiales(filtro) {
    let params = new URLSearchParams({filtro: filtro});
    fetch("api/material/getAll",
            {
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
                if (data.exception != null) {
                    Swal.fire('',
                            'Error interno del servidor. Intente nuevamente más tarde',
                            'error'
                            );
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                if (data.errorsec != null) {
                    Swal.fire('', data.errorsec, 'error');
                    return;
                } else {
                    material = data;
                    const materialSeleccionado = document.querySelector("#material");
                    material.forEach(function (mat) {
                        let vista = document.createElement("option");
                        vista.value = mat.idMaterial;
                        vista.text = mat.nombre;
                        materialSeleccionado.appendChild(vista);
                    });
                }

            });
}


export function CalcularTotal() {
    let total = 0;
    let precioT = 0;
    let descuento = 0;
    let cantidadT = 0;
    let trat
    
    ventaPL.forEach(function (ventaPL){
        
        ventaPL.cantidad = document.getElementById()
        ventaPL.descuento = document.getElementById()
    });
    
   precioA
    
}