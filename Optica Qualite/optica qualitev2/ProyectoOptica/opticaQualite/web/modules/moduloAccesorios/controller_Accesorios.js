let indexAccesorioSeleccionado;
let accesorios = []; 

fetch("modules/moduloAccesorios/data_Accesorios.json")
        .then(function(response){
            return response.json();
        })
        .then(function(jsondata){
                accesorios = jsondata;
                console.log(accesorios);
                loadTabla1();
            }            
        );

// FUNCION PARA AGREGAR ACCESORIO
export function addAccesorio(){
    let nombre,
        marca,
        precioC,
        precioV,
        cantidad;

    nombre = document.getElementById("txtNombre").value;
    marca = document.getElementById("txtMarca").value;
    precioC = document.getElementById("txtPrecioC").value;
    precioV = document.getElementById("txtPrecioV").value;
    cantidad = document.getElementById("txtCantidad").value;

    if (nombre=='' || marca=='' || precioC=='' || precioV =='' || cantidad==''){
        //ALERTA QUE INDICA SI UN CAMPO ESTA VACIO
        alertaVacio();
    } else {
    
    let accesorio = {};
    
    accesorio.nombre = nombre;
    accesorio.marca = marca;
    accesorio.precioC = precioC;
    accesorio.precioV = precioV;
    accesorio.cantidad = cantidad;
    accesorio.estatus = "Activo";
    accesorios.push(accesorio);
    clean();
    loadTabla1();
    alertaAdd();
    }
}

//FUNCION PARA CARGAR TABLA
export function loadTabla1(){
    var combo = document.getElementById("estado");
    var estatus = combo.options[combo.selectedIndex].text;
    if(estatus == "Activo"){
        activos();
    }else{
        inactivos();
    }
    
}


//FUNCION PARA TABLA DE INACTIVOS
function inactivos(){

    let cuerpo = "";
    let filtro = "Inactivo";
    let resultados = accesorios.filter(element => element.estatus === filtro);
    resultados.forEach(function (accesorio){
        let registro =  
                '<tr onclick="moduloAccesorio.selectAccesorio('+ accesorios.indexOf(accesorio) +');">'+
                '<td>' + accesorio.nombre + '</td>' +
                '<td>' + accesorio.marca +  '</td>'+ 
                '<td>' + accesorio.precioC +'</td>' +
                '<td>' + accesorio.precioV + '</td>' +
                '<td>' + accesorio.cantidad + '</td>' +
                '<td>' + accesorio.estatus + '</td></tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblAccesorios").innerHTML = cuerpo;
}

//FUNCION PARA TABLA DE ACTIVOS
function activos(){

    let cuerpo = "";
    let filtro = "Activo";
    let resultados = accesorios.filter(element => element.estatus === filtro);
    resultados.forEach(function (accesorio){
        let registro =  
                '<tr onclick="moduloAccesorio.selectAccesorio('+ accesorios.indexOf(accesorio) +');">'+
                '<td>' + accesorio.nombre + '</td>' +
                '<td>' + accesorio.marca +  '</td>'+ 
                '<td>' + accesorio.precioC +'</td>' +
                '<td>' + accesorio.precioV + '</td>' +
                '<td>' + accesorio.cantidad + '</td>' +
                '<td>' + accesorio.estatus + '</td></tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblAccesorios").innerHTML = cuerpo;
}

//FUNCION PARA SELECCIONAR UN ACCESORIO
export function selectAccesorio(index){
    document.getElementById("txtNombre").value = accesorios[index].nombre ;
    document.getElementById("txtMarca").value = accesorios[index].marca;
    document.getElementById("txtPrecioC").value = accesorios[index].precioC;
    document.getElementById("txtPrecioV").value = accesorios[index].precioV;
    document.getElementById("txtCantidad").value = accesorios[index].cantidad;
    
    document.getElementById("btnUpdate").classList.remove("disabled");
    document.getElementById("btnDelete").classList.remove("disabled");
    document.getElementById("btnAdd").classList.add("disabled");
    indexAccesorioSeleccionado = index;
}

//FUNCION PARA LIMPIAR EL FORMULARIO 
export function clean(){
    document.getElementById("txtNombre").value = "" ;
    document.getElementById("txtMarca").value = "";
    document.getElementById("txtPrecioC").value = "";
    document.getElementById("txtPrecioV").value = "";
    document.getElementById("txtCantidad").value = "";
    
    document.getElementById("txtNombre").focus();
    document.getElementById("btnUpdate").classList.add("disabled");
    document.getElementById("btnDelete").classList.add("disabled");
    document.getElementById("btnAdd").classList.remove("disabled");
    indexAccesorioSeleccionado = 0;
}

//FUNCION PARA ACTUALIZAR UN ACCESORIO
export function updateAccesorio(){
    let nombre,
        marca,
        precioC,
        precioV,
        cantidad;
        

    nombre = document.getElementById("txtNombre").value;
    marca = document.getElementById("txtMarca").value;
    precioC = document.getElementById("txtPrecioC").value;
    precioV = document.getElementById("txtPrecioV").value;
    cantidad = document.getElementById("txtCantidad").value;

    if (nombre=='' || marca=='' || precioC=='' || precioV =='' || cantidad==''){
        //ALERTA QUE INDICA SI UN CAMPO ESTA VACIO
        alertaVacio();
    } else {

    Swal.fire({
        title: '¿Esta seguro de actualizar el accesorio seleccionado?',
        showCancelButton: true,
        showConfirmButton: true,
        CancelButtonText: 'Cancelar',
        ConfirmButtonText: 'Actualizar'
    }).then((result) =>{
        if(result.isConfirmed){
    
            let accesorio= {};
            
            accesorio.nombre = nombre;
            accesorio.marca = marca;
            accesorio.precioC = precioC;
            accesorio.precioV = precioV;
            accesorio.cantidad = cantidad;
            accesorio.estatus = "Activo";
            accesorios[indexAccesorioSeleccionado] = accesorio;
            clean();
            loadTabla1();
            alertaActualizar();
        }
    });
}
}

//FUNCION PARA BORRAR UN ACCESORIO
export function deleteAccesorio(){
    Swal.fire({
        title: '¿Esta seguro de eliminar el accesorio seleccionado?',
        showCancelButton: true,
        showConfirmButton: true,
        CancelButtonText: 'Cancelar',
        ConfirmButtonText: 'Eliminar'
    }).then((result) =>{
        if(result.isConfirmed){
            accesorios[indexAccesorioSeleccionado].estatus = "Inactivo";        
            clean();
            loadTabla1();
            alertaBorrar();
        }
    });
}

//FUNCION PARA BUSCAR UN ACCESORIO
export function searchAccesorio(){
    let filtro = document.getElementById("txtBusquedaAccesorio").value;

    let filtroMin=filtro.toLowerCase();
    
    let resultados = accesorios.filter(element => element.nombre.toLowerCase() === filtroMin || element.marca.toLowerCase() === filtroMin || element.precioC.toLowerCase() === filtroMin
    || element.precioV.toLowerCase()===filtroMin || element.cantidad.toLowerCase()===filtroMin);
    let cuerpo = "";

    if(filtroMin===''){
        alertaNohay();
        loadTabla1();

    }else{
    resultados.forEach(function (accesorio){
        let registro =  
                '<tr onclick="moduloAccesorio.selectAccesorio('+ accesorios.indexOf(accesorio) +');">'+
                '<td>' + accesorio.nombre + '</td>' +
                '<td>' + accesorio.marca +  '</td>'+ 
                '<td>' + accesorio.precioC +'</td>' +
                '<td>' + accesorio.precioV + '</td>' +
                '<td>' + accesorio.cantidad + '</td>' +
                '<td>' + accesorio.estatus + '</td></tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblAccesorios").innerHTML = cuerpo;
    }
}

//FUNCION PARA IMPRIMIR TABLA
export function imprimir(el){
    var restorepage = document.body.innerHTML;
    var printcontent = document.getElementById(el).innerHTML;
    document.body.innerHTML = printcontent;
    window.print();
    document.body.innerHTML = restorepage;
    
}

// ALERTAS 
function alertaAdd(){
    Swal.fire({
    title: '¡Accesorio agregado con exito!',
    text: 'Se actualizo el catalogo accesorios',
    icon: 'success',
    confirmButtonText: 'Aceptar'
    });
  }

function alertaVacio(){
    Swal.fire({
        title: '¡Un campo esta vacío!',
        text: 'Por favor, rellena todos los campos',
        icon: 'error'
    })
}  

function alertaActualizar(){
    Swal.fire({
        title: '¡Accesorio modificado con exito!',
        text: 'Se actualizo el catalogo accesorios',
        icon: 'success'

    })
}

function alertaBorrar(){
    Swal.fire({
        title: '¡Accesorio eliminado con exito!',
        text: 'El estatus del accesorio se modifico a inactivo',
        icon: 'success'
    })
}

function alertaNohay(){
    Swal.fire({
        title: '¡Campo vacio!',
        text: 'Ingresa el dato a buscar',
        icon: 'error'
    })
}