let clientes = [];
let examenesVista;
let ventaPLC;
let indexVLC = 0;
let ventasLC;
let lentesContacto;
let productos;
let presupuestoLC = [];
let ventaPresupuesto = [];
let currentUser = localStorage.getItem('currentUser');
let e = JSON.parse(currentUser);

export function inicializar() {
    configureTableFilter(document.getElementById('txtBuscarCliente'),
            document.getElementById('tablaVentaLC'));

    buscarCliente();
}

export function buscarCliente() {
    let params = new URLSearchParams({filtro: " "});
    fetch("api/cliente/buscar",
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
                '<td>' + cliente.persona.nombre + ' ' + cliente.persona.apellidoPaterno + ' ' + cliente.persona.apellidoMaterno + '</td>' +
                '<td>' + cliente.persona.telCasa + '</td>' +
                '<td><button onclick="moduloVentaLC.datosSelect(' + cliente.idCliente + ')">Agregar</button></td>';
        cuerpo += registro;
    });
    document.getElementById("tblClientes").innerHTML = cuerpo;
}

// Parte examen vista-----------------------------------------------------------
export function datosSelect(idCliente) {
    let params = new URLSearchParams({idCliente: idCliente});
    fetch("api/examenV/buscar",
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

                    cargarTabla(data);
                    lentesDContacto();
                }
            });
}

export function cargarTabla(data) {
    let renglon = "";
    examenesVista = data;

    examenesVista.forEach(function (examenVista) {
        let renglon =
                '<tr>' +
                '<td>' + examenVista.clave + '</td>' +
                '<td>' + examenVista.fecha + '</td>' +
                '</td></tr>';
        document.getElementById("tablaExamenV").innerHTML += renglon;
    });
}

//----- Parte Lente de contacto -------
export function lentesDContacto() {
    let params = new URLSearchParams({'filtro': ""});
    fetch("api/lentesContacto/getAll",
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
                    lentesContacto = data;
                    cargarTabla1(data);
                }

            });
}

export function cargarTabla1(data) {
    let cuerpo = "";
    ventasLC = data;
    ventasLC.forEach(function (lenteContacto) {
        let registro =
                '<tr>' +
                '<td>' + lenteContacto.producto.nombre + '</td>' +
                '<td>' + lenteContacto.producto.marca + '</td>' +
                '<td>' + lenteContacto.keratometria + '</td>' +
                '<td>' + lenteContacto.producto.precioVenta + '</td>' +
                '<td><button onclick="moduloVentaLC.anadir(' + ventasLC.indexOf(lenteContacto) + ')">A</button></td>\n\
                </tr>';
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblPresupuestoLC").innerHTML = cuerpo;
}

export function anadir(indice) {
    let renglon =
            '<tr>' +
            '<td>' + lentesContacto[indice].producto.nombre + '</td>' +
            '<td>' + lentesContacto[indice].producto.precioVenta + '</td>' +
            '<td><input onchange = "moduloVentaLC.calcularTotal();" type="number" name="text" value="1" class = "input" id="txtExistencias' + indexVLC + '"></td>' +
            '<td><input onchange = "moduloVentaLC.calcularTotal();" type="number" name="text" value="0" class = "input" id="txtDescuento' + indexVLC + '"></td>'+
            '</tr>';
    document.getElementById("tblLC").innerHTML += renglon;
   
   let presupuesto = {clave:Math.random()*100000, examenVista:examenesVista[indexVLC]};
   presupuestoLC [indexVLC] = {lenteContacto:lentesContacto[indexVLC],clave:Math.random()*10000,presupuesto:presupuesto,examenVista:examenesVista[indexVLC]};
   ventaPresupuesto [indexVLC] = {presupuestoLC:presupuestoLC[indexVLC],cantidad:1,precioUnitario:0.0,descuento:0};
    indexVLC++;
    
}

export function calcularTotal() {
    let total = 0;
    let precio = 0;
    let descuento = 0;
    let cantidadTLC = 0;

    ventaPresupuesto.forEach(function (venta) {
        venta.cantidad = document.getElementById("txtExistencias" + ventaPresupuesto.indexOf(venta)).value;
        venta.descuento = document.getElementById("txtDescuento" + ventaPresupuesto.indexOf(venta)).value;
        
        descuento = venta.descuento / 100;
        precio = venta.precioUnitario * venta.cantidad;
        total = precio - (precio * descuento);
        cantidadTLC += total;
    });
    document.getElementById("txtTotal1").innerHTML = cantidadTLC;
}


export function save(){

    let venta = {clave: Math.random() * 10000, empleado: e};
    let dvplc = {venta: venta, ventaPresupuesto: ventaPresupuesto};
    let datosLC = {datosVP: JSON.stringify(dvplc)};
    let params = new URLSearchParams(datosLC);

    fetch("api/ventaLC/TransaccionVenta",
            {method: "POST",
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
                if (data.result != null) {
                    Swal.fire('', "Venta realizada con éxito", 'success');

                }

            });
}



