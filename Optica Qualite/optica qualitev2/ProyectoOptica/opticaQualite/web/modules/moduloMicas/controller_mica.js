let indexMicaSeleccionado;
let tiposMica = [];
let loadTiposMica;



export function refrescarTabla(){
    
    let url = "api/tipoMica/getAll"; // definir la url del servicio 
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
    tiposMica = data;
    //let resultados = empleados.filter(element => element.estatus === filtro);
    tiposMica.forEach(function (tipoMica){
        let registro =  
                '<tr onclick="moduloMica.selectMica('+ tiposMica.indexOf(tipoMica) +');">'+
                '<td>' + tipoMica.nombre + '</td>' +
                '<td>' + tipoMica.precioV + '</td>' +
                '<td>' + tipoMica.precioC + '</td>' +
                '</tr>' ; 
        cuerpo += registro;
    });
    console.log(cuerpo);
}

