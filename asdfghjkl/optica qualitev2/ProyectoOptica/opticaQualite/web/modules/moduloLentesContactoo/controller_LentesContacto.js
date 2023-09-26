let indexLentesContactoSeleccionado;
let lentesContacto = [];

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
                  
                }
            });
}
export function refrescarTabla(){
    
    let url = "api/lentesContacto/getAll"; // definir la url del servicio 
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
    lentesContacto = data;
    //let resultados = empleados.filter(element => element.estatus === filtro);
    lentesContacto.forEach(function (lenteContacto){
        let registro =  
                '<tr onclick="moduloMaterial.selectMaterial('+ lentesContacto.indexOf(lenteContacto) +');">'+
                '<td>' + lenteContacto.nombre + '</td>' +
                '<td>' + material.precioV + '</td>' +
                '<td>' + material.precioC + '</td>' +
                '</tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblLentesContacto").innerHTML = cuerpo;
}




export function addLentesContacto(){
    let nombre,
        marca,
        color,
        queratometria,
        fotografia,foto;

    nombre = document.getElementById("txtNombre").value;
    marca = document.getElementById("txtMarca").value;
    color = document.getElementById("txtColor").value;
    queratometria = document.getElementById("txtQuera").value;
   
    if(nombre == "" || marca == "" || color == "" || queratometria == "" || fotografia == "" ){
        Swal.fire({
            title: '¡Un campo esta vacío!',
            text: 'Por favor, rellena todos los campos',
            icon: 'error'
        })
    }else{
   fotografia = document.getElementById("txtFoto").file[0].name;
//    foto="../../resources/Lente6.jpg";
    let lenteContacto={};
    lenteContacto.nombre = nombre;
    lenteContacto.marca = marca;
    lenteContacto.color = color;
    lenteContacto.queratometria = queratometria;
    lenteContacto.fotografia = foto;
    lenteContacto.estatus ="Activo";
    lentesContacto.push(lenteContacto);
    clean();
    loadTabla1();
    Swal.fire({
        title: '¡Lente de contacto agregado con exito!',
        text: 'Se actualizo el catalogo lentes de contacto',
        icon: 'success',
        confirmButtonText: 'Aceptar'
        });
    }
}


export function loadTabla1(){
    var combo = document.getElementById("esta");
    var estatus = combo.options[combo.selectedIndex].text;
    if(estatus == "Activo"){
        activos();
    }else{
        inactivos();
    }
    
}

function inactivos(){

    let cuerpo = "";
    let filtro = "Inactivo";
    let resultados = lentesContacto.filter(element => element.estatus === filtro);
    resultados.forEach(function (lenteContacto){
        let registro =  
                '<tr>'+
                '<td>' + lenteContacto.nombre + '</td>' +
                '<td>' + lenteContacto.marca +'</td>' +
                '<td>' + lenteContacto.color + '</td>' +
                '<td>' + lenteContacto.queratometria + '</td>' +
                '<td><img src="' + lenteContacto.fotografia + '" alt="" style="width: 50px"/></td>' +
                '<td>' + lenteContacto.estatus + '</td></tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblLentesContacto").innerHTML = cuerpo;
}

function activos(){

    let cuerpo = "";
    let filtro = "Activo";
    let resultados = lentesContacto.filter(element => element.estatus === filtro);
    resultados.forEach(function (lenteContacto){
        let registro =  
              '<tr onclick="moduloLentesContactoo.selectLentesContacto('+ lentesContacto.indexOf(lenteContacto) +');">'+
                '<td>' + lenteContacto.nombre + '</td>' +
                '<td>' + lenteContacto.marca +'</td>' +
                '<td>' + lenteContacto.color + '</td>' +
                '<td>' + lenteContacto.queratometria + '</td>' +
                '<td><img src="' + lenteContacto.fotografia + '" alt="" style="width: 50px"/></td>' +
                '<td>' + lenteContacto.estatus + '</td></tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("tblLentesContacto").innerHTML = cuerpo;
}

export function selectLentesContacto(index){
    document.getElementById("txtNombre").value = lentesContacto[index].nombre;
    document.getElementById("txtMarca").value = lentesContacto[index].marca;
    document.getElementById("txtColor").value = lentesContacto[index].color;
    document.getElementById("txtQuera").value = lentesContacto[index].queratometria;
    document.getElementById("txtFoto").icon = lentesContacto[index].fotografia;
    document.getElementById("btnUpdate").classList.remove("disabled");
    document.getElementById("btnDelete").classList.remove("disabled");
    document.getElementById("btnAdd").classList.add("disabled");
    indexLentesContactoSeleccionado = index;
}

export function updateLentesContacto(){
    let nombre,
        marca,
        color,
        queratometria,
        fotografia;

    Swal.fire({
        title: '¿Quieres actualizar el lente de contacto seleccionado?',
        showCancelButton: true,
        showConfirmButton: true,
        confirmButtonText: 'Si',
        cancelButtonText: 'No'
    }).then((result) => {
        if(result.isConfirmed){
    
    nombre = document.getElementById("txtNombre").value;
    marca = document.getElementById("txtMarca").value;
    color = document.getElementById("txtColor").value;
    queratometria = document.getElementById("txtQuera").value;
    fotografia = lentesContacto[indexLentesContactoSeleccionado];
    
    if(nombre == "" || marca == "" || color == "" || queratometria == "" || fotografia == "" ){
  Swal.fire("Campos vacios","Por favor verifique que todos los campos \n\
                                    esten llenados ","error");
        
        console.log("esta cosa si funciona");
    }else{    
     let lenteContacto={};
    
    lenteContacto.nombre = nombre;
    lenteContacto.marca = marca;
    lenteContacto.color = color;
    lenteContacto.queratometria = queratometria;
    lenteContacto.fotografia = fotografia;
    lenteContacto.estatus ="Activo";
    
    lentesContacto[indexLentesContactoSeleccionado] = lenteContacto;
    clean();
    loadTabla1();
    Swal.fire({
        title: '¡Lente de contacto modificado con exito!',
        text: 'Se actualizo el catalogo lentes de contacto',
        icon: 'success'

    })
    }
    }
    });
}

export function clean(){

    document.getElementById("txtNombre").value = "";
    document.getElementById("txtMarca").value = "";
    document.getElementById("txtColor").value = "";
    document.getElementById("txtQuera").value = "";
    document.getElementById("txtFoto").icon = "";
    document.getElementById("txtNombre").focus();
    document.getElementById("btnUpdate").classList.add("disabled");
    document.getElementById("btnDelete").classList.add("disabled");
    document.getElementById("btnAdd").classList.remove("disabled");
    indexLentesContactoSeleccionado = 0;
}

export function deleteLentesContacto(){
    Swal.fire({
        title: '¿Esta seguro de eliminar el lente de contacto seleccionado?',
        showCancelButton: true,
        showConfirmButton: true,
        confirmButtonText: 'Si',
        cancelButtonText: 'No'
    }).then((result) => {
        if(result.isConfirmed){
    lentesContacto[indexLentesContactoSeleccionado].estatus = "Inactivo";
    clean();
    loadTabla1();
    Swal.fire({
        title: '¡Lente de contacto eliminado con exito!',
        text: 'El estatus del lente de contacto se modifico a inactivo',
        icon: 'success'
    })
    }
    });
}


export function searchLentesContacto(){
    let filtro = document.getElementById("txtBusquedaLentesContacto").value;
    let filtroMin=filtro.toLowerCase();    
    let resultados = lentesContacto.filter(element => element.nombre.toLowerCase() === filtroMin || element.marca.toLowerCase() === filtroMin ||
    element.color.toLowerCase() === filtroMin || element.queratometria.toLowerCase() === filtroMin)
        
    let cuerpo = "";
    if(filtroMin===''){
        alertaNohay();
        loadTabla1();

    }else{
    resultados.forEach(function (lenteContacto){
        let registro = 
               '<tr onclick="moduloLentesContactoo.selectLentesContacto('+ lentesContacto.indexOf(lenteContacto) +');">'+
                '<td>' + lenteContacto.nombre + '</td>' +
                '<td>' + lenteContacto.marca +'</td>' +
                '<td>' + lenteContacto.color + '</td>' +
                '<td>' + lenteContacto.queratometria + '</td>' +
                '<td><img src="' + lenteContacto.fotografia + '" alt="" style="width: 50px"/></td>' +
                '<td>' + lenteContacto.estatus + '</td></tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
    document.getElementById("").innerHTML = cuerpo;
    }
}

export function imprimir(el) 
{
    var restorepage= document.body.innerHTML;
    var printcontent = document.getElementById(el).innerHTML;
    document.body.innerHTML=printcontent;
    window.print();
    document.body.innerHTML=restorepage;
}


function alertaNohay(){
    Swal.fire({
        title: '¡Campo vacio!',
        text: 'Ingresa el dato a buscar',
        icon: 'error'
    })
}