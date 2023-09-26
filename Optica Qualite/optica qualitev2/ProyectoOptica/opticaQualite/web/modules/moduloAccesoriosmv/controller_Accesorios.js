let index;
let accesorios = []; 

export function inicializar()
{
    refrescarTabla();
}


export function refrescarTabla() {
    let url = "api/acce/getAll"; //definimos url del servicio (relativa)
    fetch(url).then(response => {
        return response.json();
    }) //.json cargamos documentos json
            .then(function (data) //proceso 
            {
                
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
    accesorios = data;
    accesorios.forEach(function (accesorio) {
        let registro =
                '<tr onclick="moduloAccesorio.selectAccesorio(' + accesorio.idAccesorio + ');">' +
                '<td>' + accesorio.producto.nombre +'</td>' +
                '<td>' + accesorio.producto.marca+ '</td>' +
                '<td>' + accesorio.producto.precioCompra + '</td>' +
                '<td>' + accesorio.producto.precioVenta +  '</td>' +
                '<td>' + accesorio.producto.existencias + '</td>' +
                '<td>' + accesorio.producto.estatus + '</td></tr>';
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblAccesorios").innerHTML = cuerpo;
}

export function selectAccesorio(index){
    accesorios.forEach(function (accesorio) {
        if (index === accesorio.idAccesorio) {
    document.getElementById("txtCod").value = accesorio.producto.codigoBarras;
    document.getElementById("txtIDAccesorio").value = accesorio.idAccesorio;
    document.getElementById("txtIDProducto").value = accesorio.producto.idProducto;
    
    document.getElementById("txtNombre").value = accesorio.producto.nombre ;
    document.getElementById("txtMarca").value = accesorio.producto.marca;
    document.getElementById("txtPrecioC").value = accesorio.producto.precioCompra;
    document.getElementById("txtPrecioV").value = accesorio.producto.precioVenta;
    document.getElementById("txtExistencias").value = accesorio.producto.existencias;
}
    });    
}

export function save() {
    let datos = null;
    let params = null;
    
    let accesorio = new Object();
    accesorio.producto = new Object();

    if (document.getElementById("txtCod").value.trim().length < 1) {
        accesorio.idAccesorio = 0;
        accesorio.producto.idProducto = 0;
    } else {
        accesorio.idAccesorio = parseInt(document.getElementById("txtIDAccesorio").value);
        accesorio.producto.idProducto = parseInt(document.getElementById("txtIDProducto").value);
    }
    accesorio.producto.codigoBarras = document.getElementById("txtCod").value;
    accesorio.producto.nombre = document.getElementById("txtNombre").value;
    accesorio.producto.marca = document.getElementById("txtMarca").value;
    accesorio.producto.precioCompra = parseFloat(document.getElementById("txtPrecioC").value);
    accesorio.producto.precioVenta = parseFloat(document.getElementById("txtPrecioV").value);
    accesorio.producto.existencias = parseInt(document.getElementById("txtExistencias").value);

    datos = {
        datosAccesorio: JSON.stringify(accesorio)
    };

    params = new URLSearchParams(datos);

    fetch("api/acce/save",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response.json();
            })
            .then(function (data) {
                console.log(data);
//                console.log(data);
//                if (data.exception !== null) {
//                    Swal.fire('', 'Error interno del servidor. Intente nuevamente más tarde.', 'error');
//                    return;
//                }
//                if (data.error !== null) {
//                    Swal.fire('', data.error, 'warning');
//                    return;
//                }
//                if (data.errorperm !== null) {
//                    Swal.fire('', 'No tiene permiso para realizar esta operación', 'error');
//                    return;
//                }

                document.getElementById("txtIDAccesorio").value = data.idAccesorio;
                document.getElementById("txtIDProducto").value = data.producto.idProducto;
                
                Swal.fire('',"Accesorio agregado.", 'sucess');
                refrescarTabla();
                clean();
            });
}

//FUNCION PARA SELECCIONAR UN ACCESORIO


//FUNCION PARA LIMPIAR EL FORMULARIO 
export function clean(){
    document.getElementById("txtCod").value = "" ;
    document.getElementById("txtNombre").value = "" ;
    document.getElementById("txtMarca").value = "";
    document.getElementById("txtPrecioC").value = "";
    document.getElementById("txtPrecioV").value = "";
    document.getElementById("txtExistencias").value = "";
    
}

export function delet() {
    let datos = null;
    let params = null;
    let accesorio = new Object();

    accesorio.producto = new Object();

    accesorio.idAccesorio = parseInt(document.getElementById("txtIDAccesorio").value);
    accesorio.producto.idProducto = parseInt(document.getElementById("txtIDProducto").value);
    accesorio.producto.codigoBarras = document.getElementById("txtCod").value;
    accesorio.producto.nombre = document.getElementById("txtNombre").value;
    accesorio.producto.marca = document.getElementById("txtMarca").value;
    accesorio.producto.precioCompra = parseFloat(document.getElementById("txtPrecioC").value);
    accesorio.producto.precioVenta = parseFloat(document.getElementById("txtPrecioV").value);
    accesorio.producto.existencias = parseInt(document.getElementById("txtExistencias").value);

    datos = {
        datosAccesorio: JSON.stringify(accesorio.producto)
    };

    params = new URLSearchParams(datos);

    fetch("api/acce/delete",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response.json();
            })
            .then(function (data) {
                
                refrescarTabla();
                
                clean();
            });

}



//FUNCION PARA IMPRIMIR TABLA
export function imprimir(el){
    var restorepage = document.body.innerHTML;
    var printcontent = document.getElementById(el).innerHTML;
    document.body.innerHTML = printcontent;
    window.print();
    document.body.innerHTML = restorepage;
    
}
