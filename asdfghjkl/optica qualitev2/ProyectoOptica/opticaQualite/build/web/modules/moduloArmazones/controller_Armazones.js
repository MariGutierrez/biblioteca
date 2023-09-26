let indexArmazonSeleccionado;
let armazones = [];
let num=6; 
let inputImagen = null;


export function inicializar(){

    inputImagen = document.getElementById("txtFoto");
    inputImagen.onchange = function (e){cargarFotografia(inputImagen)};
    
    refrescarTabla();
}

function cargarFotografia(objetoInputFile){
    //
    if (objetoInputFile.files && objetoInputFile.files[0]) {
        let reader = new FileReader();
        reader.onload = function(e){
            let fotoB64 = e.target.result;
            document.getElementById("imgFoto").src = fotoB64;
            document.getElementById("txtaCodigoImagen").value = 
                    fotoB64.substring(fotoB64.indexOf(",") + 1, fotoB64.length);
        };
        reader.readAsDataURL(objetoInputFile.files[0]);
    }
}

export function guardar(){
    
    let datos = null;
    let params = null;
    
    let armazon = new Object();       // se inicializa como objeto 
                                       // forma de crear un objeto, 
    armazon.producto = new Object();   // se crea un objeto dentro del objeto empleado, si no existe en el objeto se crea y si existe se remplaza
    
    
    if(document.getElementById("txtIdEmpleado").value.trim() < 1){   
        //sirve para saber si no hay codigo en la caja de texto se le asigna el valor de 0 
        armazon.idArmazon = 0;
        armazon.producto.idProducto = 0;
        armazon.producto.codigoBarras = "";
    }
    else{
        // si existe se recojen los id's de las cajas de texto
        armazon.idArmazon = parseInt(document.getElementById("txtIdArmazon").value);
        armazon.producto.idProducto = parseInt(document.getElementById("txtIdProducto").value);
        armazon.producto.codigoBarras = parseInt(document.getElementById("txtCodigoBarras").value);
    }
    //se llenan los datos de los empleado organizados de forma en que estan en la vista 
    armazon.producto.nombre = document.getElementById("txtNombre").value;
    armazon.producto.marca = document.getElementById("txtMarca").value;
    armazon.producto.precioCompra = document.getElementById("txtPrecioCompra").value;
    armazon.producto.precioVenta = document.getElementById("txtPrecioVenta").value;
    armazon.producto.existencias = document.getElementById("txtExistencia").value;
    armazon.producto.modelo = document.getElementById("txtModelo").value;
    armazon.producto.color = document.getElementById("txtColor").value;
    armazon.dimensiones = document.getElementById("txtDimesiones").value;
    armazon.descripcion = document.getElementById("txtDescripcion").value;
    armazon.fotografia = document.getElementById("txtFoto").value;
    
    //JSON al que se le pasa el nombre del atributo que se espera en el servicio, se convierte
    // el objeto JavaScript a cadena con JSON.stringify
    
    datos = {
        datosEmpleado : JSON.stringify(armazon)
    };
    
    //se convierten , se guardan los datos en params para poder mandarlos al servidor 
    params = new URLSearchParams(datos);
    
    //se pone la ruta del servicio, se pone como metodo POST,
    //se establecen las cabeceras(headers) para saber como se van a mandar los datos , 
    //y los parametros van dentro de un paramaetro llamdo body
    //si hay errores se muestra la alerta necesaria
    fetch(  "api/armazon/save",
            {   method:"POST",
                headers:{'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body:params
            })
    .then(response => {return response.json();})
    .then(function(data)
    {
//        if(data.exception != null)
//        {
//            Swal.fire('', 'Error interno del servidor. Intente nuevamente mas tarde.', 'error');
//            return;
//        }
//        
//        if(data.error != null)
//        {
//            Swal.fire('', data.error, 'warning');
//            return;
//        }
//        
//        if(data.errorperm != null)
//        {
//            Swal.fire('',"No tiene permiso para realizar esta operacion.", 'warning');
//            return;
//        }
//        si no hay errores se guardan en las cajas de textos los id's 
        document.getElementById("txtIdProducto").value = data.producto.idProducto;
        document.getElementById("txtIdArmazon").value = data.idArmazon;
        document.getElementById("txtCodigoBarras").value = data.producto.codigoBarras;
        Swal.fire('', 'Datos del empleado actualizados correctamente.', 'success');
                refrescarTabla();
    });
}

export function refrescarTabla(){
    
    let url = "api/armazon/getAll"; // definir la url del servicio 
    fetch(url)
    .then(response => {return response.json();})
    .then(function(data){
        
        if(data.exception != null)
        {
            Swal.fire('',
                      'Error interno del servicio. Intente nuevamente mas tarde.',
                      'error');
                      
            return;
        }
        
        if(data.error != null)
        {
            Swal.fire('',data.error, 'warning');
            return;
        }
        
        if(data.errosec != null){
            Swal.fire('',data,errorsec,'error');
            window.location.replace('index.html');
            return;
        }
        fillTable(data);
    });
}

function fillTable(data){

    let cuerpo = "";
    armazones = data;
    //let resultados = empleados.filter(element => element.estatus === filtro);
    armazones.forEach(function (armazon){
        let registro =  
                '<tr onclick="moduloArmazon.selectArma('+ armazones.indexOf(armazon) +');">'+
                '<td>' + armazon.producto.codigoBarras + '</td>' +
                '<td>' + armazon.marca + '</td>' +
                '<td>' + armazon.producto.modelo + '</td>' +
                '<td>' + armazon.color + '</td>' +
                '<td>' + armazon.producto.precioVenta + '</td>' +
                '<td>' + armazon.producto.existencias + '</td>' +
                '<td>' + armazon.dimensiones + '</td>' +
                '<td>' + armazon.descripcion + '</td>' +
                '</tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblCompras").innerHTML = cuerpo;
}

export function addArmazon(){
    let cla, nom, mar, mod, col, des, foto, dimen, preC, preV, exis;    
    cla = document.getElementById("txtNumUnico").value;
    nom = document.getElementById("txtNom").value;
    col = document.getElementById("txtCol").value;
    mod = document.getElementById("txtMod").value;
    mar = document.getElementById("txtMar").value;
    preC = document.getElementById("txtPrecom").value;
    preV = document.getElementById("txtPreV").value;
    exis = document.getElementById("txtExis").value;
    dimen = document.getElementById("txtDim").value;
    des = document.getElementById("txtDes").value;
   
    if(cla==='' || nom==='' || col===''|| mod===''|| mar==='' || preC==='' || preV==='' || exis==='' || dimen==='' || des==='')
    {
        Swal.fire({
            title: '¡Un campo esta vacío!',
            text: 'Por favor, rellena todos los campos',
            icon: 'error',
            confirmButtonText: 'Aceptar'
       });
    }
    else
    {
    
    foto = document.getElementById("txtFoto").files[0].name;
    let Arma = {};
    Arma.codigoBarras= cla;
    Arma.nombre= nom;
    Arma.marca= mar;
    Arma.modelo= mod;
    Arma.color= col;
    Arma.descripcion= des;
    Arma.fotografia= foto;
    Arma.dimensiones= dimen;
    Arma.precioC= preC;
    Arma.precioV= preV;
    Arma.existencias= exis;
    Arma.estatus= "Activo";
    Armazon.push(Arma);
    clean();
    loadTabla();
    num=num+1;
    document.getElementById("txtNumUnico").value = "OQ"+num;
    alertaAdd();
    }
}

function alertaAdd()
{
  Swal.fire({
  title: 'Completado con exito!',
  text: 'Se ha registrado un nuevo armazon',
  icon: 'success',
  confirmButtonText: 'De acuerdo.'
  });
}

export function loadTabla(){
    var combo = document.getElementById("esta");
    var esta = combo.options[combo.selectedIndex].text;
    let a='<tr>'+
                    '<th>Clave</th>'+
                    '<th>Marca</th>'+
                    '<th>Modelo</th>'+
                    '<th>Color</th>'+
                    '<th>Precio</th>'+
                    '<th>Existencias</th>'+
                    '<th>Dimenciones</th>'+
                    '<th>Descripcion</th>'+
                    '<th>Fotografía</th>'+
                '</tr>';
        document.getElementById("tb2").innerHTML = a;
    if(esta=="Activo")
    {
        aclis();
    }
    else
    {
        Inaclis();
    }
}

function aclis()
{
    let cuerpo = "";
    let filtro = "Activo";
    let resultados = Armazon.filter(element => element.estatus === filtro);
    resultados.forEach(function (compra){
        let registro =  
                '<tr onclick="moduloArmazon.selectArma('+ Armazon.indexOf(compra) +');">'+
                '<td>' + compra.codigoBarras + '</td>' +
                '<td>' + compra.marca + '</td>' +
                '<td>' + compra.modelo + '</td>' +
                '<td>' + compra.color + '</td>' +
                '<td>' + compra.precioV + '</td>' +
                '<td>' + compra.existencias + '</td>' +
                '<td>' + compra.dimensiones + '</td>' +
                '<td>' + compra.descripcion + '</td>' +
                '<td><img src="../resources/' + compra.fotografia+ '" alt="" style="width: 50px"/></td></tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblCompras").innerHTML = cuerpo;
}

function Inaclis()
{
    let cuerpo = "";
    let filtro = "Inactivo";
    let resultados = Armazon.filter(element => element.estatus === filtro);
    resultados.forEach(function (compra){
        let registro =  
                '<tr>'+
                '<td>' + compra.codigoBarras + '</td>' +
                '<td>' + compra.marca + '</td>' +
                '<td>' + compra.modelo + '</td>' +
                '<td>' + compra.color + '</td>' +
                '<td>' + compra.precioV + '</td>' +
                '<td>' + compra.existencias + '</td>' +
                '<td>' + compra.dimensiones + '</td>' +
                '<td>' + compra.descripcion + '</td>' +
                '<td><img src="../resources/' + compra.fotografia+ '" style="width: 50px" alt=""/></td></tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblCompras").innerHTML = cuerpo;
}

export function selectArma(index){
    document.getElementById("txtCodigoBarras").value = armazones[index].codigoBarras;
    document.getElementById("txtNombre").value = armazones[index].nombre;
    document.getElementById("txtColor").value= armazones[index].color;
    document.getElementById("txtModelo").value= armazones[index].modelo;
    document.getElementById("txtMarca").value= armazones[index].marca;
    document.getElementById("txtPrecioCompra").value= armazones[index].precioC;
    document.getElementById("txtPrecioVenta").value= armazones[index].precioV;
    document.getElementById("txtExisistencias").value= armazones[index].existencias;
    document.getElementById("txtDimensiones").value= armazones[index].dimensiones;
    document.getElementById("txtFoto").icon= armazones[index].fotografia;
    document.getElementById("txtDescripcion").value= armazones[index].descripcion;
    indexArmazonSeleccionado = index;
    document.getElementById("btnUpdate").classList.remove("disabled");
    document.getElementById("btnDelete").classList.remove("disabled");
    document.getElementById("btnAdd").classList.add("disabled");
}

export function clean(){
    document.getElementById("txtNumUnico").value = "";
    document.getElementById("txtNom").value = "";
    document.getElementById("txtCol").value= "";
    document.getElementById("txtMod").value= "";
    document.getElementById("txtMar").value= "";
    document.getElementById("txtPrecom").value= "";
    document.getElementById("txtPreV").value= "";
    document.getElementById("txtExis").value= "";
    document.getElementById("txtDim").value= "";
    document.getElementById("txtFoto").value= "";
    document.getElementById("txtDes").value= "";
    
    document.getElementById("txtNom").focus();
    document.getElementById("btnUpdate").classList.add("disabled");
    document.getElementById("btnDelete").classList.add("disabled");
    document.getElementById("btnAdd").classList.remove("disabled");
    indexArmazonSeleccionado=0;
}

export function updateArma(){
    let cla, nom, mar, mod, col, des, foto, dimen, preC, preV, exis;    
            cla = document.getElementById("txtNumUnico").value;
            nom = document.getElementById("txtNom").value;
            col = document.getElementById("txtCol").value;
            mod = document.getElementById("txtMod").value;
            mar = document.getElementById("txtMar").value;
            preC = document.getElementById("txtPrecom").value;
            preV = document.getElementById("txtPreV").value;
            exis = document.getElementById("txtExis").value;
            dimen = document.getElementById("txtDim").value;
            foto = document.getElementById("txtFoto").files.name;
            des = document.getElementById("txtDes").value;

            if(cla==='' || nom==='' || col===''|| mod===''|| mar==='' || preC==='' || preV==='' || exis==='' || dimen==='' || des==='')
    {
        Swal.fire({
            title: '¡Un campo esta vacío!',
            text: 'Por favor, rellena todos los campos',
            icon: 'error',
            confirmButtonText: 'Aceptar'
       });
    }
    else
    {
            clean();
            loadTabla();
            document.getElementById("txtNumUnico").value = "OQ"+num;
    Swal.fire({
        title: '¿Quieres actualizar el armazon seleccionado?',
        showCancelButton: true,
        showConfirmButton: true,
        CancelButtonText: 'Cancelar',
        ConfirmButtonText: 'Actualizar'
    }).then((result) =>{
        if(result.isConfirmed)
        {
            let Arma = {};
            Arma.codigoBarras= cla;
            Arma.nombre= nom;
            Arma.marca= mar;
            Arma.modelo= mod;
            Arma.color= col;
            Arma.descripcion= des;
            Arma.fotografia= foto;
            Arma.dimensiones= dimen;
            Arma.precioC= preC;
            Arma.precioV= preV;
            Arma.existencias= exis;
            Arma.estatus= Armazon[indexArmazonSeleccionado].estatus;
            Armazon[indexArmazonSeleccionado] = Arma;
            loadTabla();
        }
    }); 
}  
}

export function deleteArma(){
    Swal.fire({
        title: '¿Quieres eliminar el armazon seleccionado?',
        showCancelButton: true,
        showConfirmButton: true,
        CancelButtonText: 'Cancelar',
        ConfirmButtonText: 'Eliminar'
    }).then((result) =>{
        if(result.isConfirmed)
        {
             Armazon[indexArmazonSeleccionado].estatus = "Inactivo";
    clean();
    loadTabla();
    document.getElementById("txtNumUnico").value = "OQ"+num;
    Swal.fire("¡Armazon eliminado con éxito!","El estatus del armazon se modifico a inactivo","success");
        }
    });  
}

export function searchArma(){
    let filtro = document.getElementById("txtBusquedaCompra").value;
    let filtroMin=filtro.toLowerCase();
    let resultados = Armazon.filter(element => element.marca.toLowerCase() === filtroMin);
    let cuerpo = "";
    if(filtroMin==='')
    {
        cargartabla();
        alertaNohay();
      
    }
    else
    {
        resultados.forEach(function (compra){
        let registro =  
                '<tr onclick="moduloArmazon.selectArma('+ Armazon.indexOf(compra) +');">'+
                '<td>' + compra.codigoBarras + '</td>' +
                '<td>' + compra.marca + '</td>' +
                '<td>' + compra.modelo + '</td>' +
                '<td>' + compra.color + '</td>' +
                '<td>' + compra.precioV + '</td>' +
                '<td>' + compra.existencias + '</td>' +
                '<td>' + compra.dimensiones + '</td>' +
                '<td>' + compra.descripcion + '</td>' +
                '<td><img src="../resources/' + compra.fotografia+ '" style="width: 50px" alt="" /></td></tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblCompras").innerHTML = cuerpo;
    }
}

export function loadTabla1(){
    var combo = document.getElementById("esta");
    var esta = combo.options[combo.selectedIndex].text;
    if(esta=="Activo")
    {
        aclis1();
    }
    else
    {
        Inaclis1();
    }
}
function aclis1()
{
    let cuerpo = "";
    let a="";
    let filtro = "Activo";
    let resultados = Armazon.filter(element => element.estatus === filtro);
    a='<tr><th>Codigo de barras</th>'+
                    '<th>Nombre</th>'+
                    '<th>Marca</th>'+
                    '<th>Modelo</th>'+
                    '<th>Color</th>'+
                    '<th>Descripción</th>'+
                    '<th>fotografia</th>'+
                    '<th>Dimensiones</th>'+
                    '<th>Precio compra</th>'+
                    '<th>Precio venta</th>'+
                    '<th>Existencias</th>'+
                    '<th>Estatus</th></tr>';
    resultados.forEach(function (compra){
        let registro =  
                '<tr>'+
                '<td>' + compra.codigoBarras + '</td>' +
                '<td>' + compra.nombre + '</td>' +
                '<td>' + compra.marca + '</td>' +
                '<td>' + compra.modelo + '</td>' +
                '<td>' + compra.color + '</td>' +
                '<td>' + compra.descripcion + '</td>' +
                '<td><img src="../resources/' + compra.fotografia+ '" style="width: 50px" alt=""/></td>' +
                '<td>' + compra.dimensiones + '</td>' +
                '<td>' + compra.precioC + '</td>' +
                '<td>' + compra.precioV + '</td>' +
                '<td>' + compra.existencias + '</td>' +
                '<td>' + compra.estatus+ '</td></tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tb2").innerHTML = a;
    document.getElementById("tblCompras").innerHTML = cuerpo;
}
function Inaclis1()
{
    let cuerpo = "";
    let filtro = "Inactivo";
    let resultados = Armazon.filter(element => element.estatus === filtro);
    let a='<tr><th>Codigo de barras</th>'+
                    '<th>Nombre</th>'+
                    '<th>Marca</th>'+
                    '<th>Modelo</th>'+
                    '<th>Color</th>'+
                    '<th>Descripción</th>'+
                    '<th>fotografia</th>'+
                    '<th>Dimensiones</th>'+
                    '<th>Precio compra</th>'+
                    '<th>Precio venta</th>'+
                    '<th>Existencias</th>'+
                    '<th>Estatus</th></tr>';
    resultados.forEach(function (compra){
        let registro =  
                '<tr>'+
                '<td>' + compra.codigoBarras + '</td>' +
                '<td>' + compra.nombre + '</td>' +
                '<td>' + compra.marca + '</td>' +
                '<td>' + compra.modelo + '</td>' +
                '<td>' + compra.color + '</td>' +
                '<td>' + compra.descripcion + '</td>' +
                '<td><img src="../resources/' + compra.fotografia+ '" style="width: 50px" alt=""/></td>' +
                '<td>' + compra.dimensiones + '</td>' +
                '<td>' + compra.precioC + '</td>' +
                '<td>' + compra.precioV + '</td>' +
                '<td>' + compra.existencias + '</td>' +
                '<td>' + compra.estatus+ '</td></tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tb2").innerHTML = a;
    document.getElementById("tblCompras").innerHTML = cuerpo;
}

function alertaNohay(){
    Swal.fire({
        title: '¡Campo vacío!',
        text: 'Ingresa el dato a buscar',
        icon: 'error'
    })
}