let productos;
let ventas = [];
let ventaP = [];
let indexV = 0;
let cuerpo = "";
let indices = [];
let cantidades = [];
let descuentos = [];
let precios = [];
let currentUser = localStorage.getItem('currentUser');
let user = JSON.parse(currentUser);




export function inicializar() {
    const inputBuscar = document.querySelector('#txtBusquedaProducto');
    inputBuscar.addEventListener('input', (event) => {
        buscarProducto(event.target.value);
        console.log(event.target.value);
    });
}

export function buscarProducto(filtro) {
    let params = new URLSearchParams({filtro: filtro});
    fetch("api/ventapro/buscar", {
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


export function cargarTabla(data) {
    let cuerpo = "";
    ventas = data;
    ventas.forEach(function (producto) {
        let registro =
                '<tr>' +
                '<td>' + producto.nombre + '</td>' +
                '<td>' + producto.precioVenta + '</td>' +
                '<td>' + producto.codigoBarras + '</td>' +
                '<td><button onclick="moduloVenta.anadir(' + ventas.indexOf(producto) + ')">A</button></td>\n\
                </tr>';
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblBusProducto").innerHTML = cuerpo;
}

export function anadir(indice) {
    let renglon =
            '<tr>' +
            '<td>' + productos[indice].codigoBarras + '</td>' +
            '<td>' + productos[indice].nombre + '</td>' +
            '<td>' + productos[indice].precioVenta + '</td>' +
            '<td><input onchange = "moduloVenta.calcularTotal();" type="number" name="text" value="1" class = "input" id="txtExistencias' + indexV + '"></td>' +
            '<td><input onchange = "moduloVenta.calcularTotal();" type="number" name="text" value="0" class = "input" id="txtDescuento' + indexV + '"></td> </tr>';
    document.getElementById("tblVen").innerHTML += renglon;
    ventaP[indexV] = {producto: productos[indice], cantidad: 1,
        precioUnitario: productos[indice].precioVenta, descuento: 0};
    indexV++;
    calcularTotal();
}

export function calcularTotal() {
    let total = 0;
    let precio = 0;
    let descuento = 0;
    let cantidadT = 0;

    ventaP.forEach(function (venta) {
        venta.cantidad = document.getElementById("txtExistencias" + ventaP.indexOf(venta)).value;
        venta.descuento = document.getElementById("txtDescuento" + ventaP.indexOf(venta)).value;
        
        descuento = venta.descuento / 100;
        precio = venta.precioUnitario * venta.cantidad;
        total = precio - (precio * descuento);
        cantidadT += total;

    });
    document.getElementById("txtTotal").innerHTML = cantidadT;
}

export function save() {
    
    console.log(ventaP);
    
    ventaP.forEach(function (venta) {
        venta.cantidad = document.getElementById("txtExistencias" + ventaP.indexOf(venta)).value;
        venta.descuento = document.getElementById("txtDescuento" + ventaP.indexOf(venta)).value;
    });

    let venta = {clave: Math.random() * 1000, empleado: user};
    let dvp = {venta: venta, listaProductos: ventaP};
    let datosVP = {datosVP: JSON.stringify(dvp)};
    let params = new URLSearchParams(datosVP);
    console.log(params);

    fetch("api/ventapro/TransaccionVenta",
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
                if (data.result != null) {
                    Swal.fire('', "Venta realizada con éxito", 'success');
                    return;

                }

            });

}