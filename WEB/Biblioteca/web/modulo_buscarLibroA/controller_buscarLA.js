let libros = [];
let libros2 = [];
let libros3 = [];
let libros4 = [];
let fotoB64 = null;
let inputFileArmazon = null;

export function inicializar() {
    inputFileArmazon = document.getElementById("inputFileImagenArmazon");
    inputFileArmazon.onchange = function (evt) {
        cargarFotografia(inputFileArmazon);
    };
}


//export function loadTable(data, data2, data3, data4) {
//    let cuerpo = "";
//    libros = data;
//    libros2 = data2;
//    libros3 = data3;
//    libros4 = data4;
//
//    libros.forEach(function (Libro) {
//        let registro =
//                '<tr>' +
//                '<td>' + Libro.titulo + '</td>' +
//                '<td>' + Libro.autor + '</td>' +
//                '<td>' + Libro.universidad.nombre_universidad + '</td>' +
//                '<td><a href="#" onclick="moduloBuscarL.mostrarDetalle(' + Libro.id_libro + ')">Seleccionar</a></td>' + '</tr>';
//        cuerpo += registro;
//    });
//    
//    libros2.forEach(function (Libro) {
//        let registro =
//                '<tr>' +
//                '<td>' + Libro.titulo + '</td>' +
//                '<td>' + Libro.autor + '</td>' +
//                '<td>' + Libro.universidad.nombre_universidad + '</td>' +
//                '<td><a href="#" onclick="moduloBuscarL.mostrarDetalle2(' + Libro.id_libro + ')">Seleccionar</a></td>' + '</tr>';
//        cuerpo += registro;
//    });
//    libros3.forEach(function (Libro) {
//        let registro =
//                '<tr>' +
//                '<td>' + Libro.titulo + '</td>' +
//                '<td>' + Libro.autor + '</td>' +
//                '<td>' + Libro.universidad.nombre_universidad + '</td>' +
//                '<td><a href="#" onclick="moduloBuscarL.mostrarDetalle3(' + Libro.id_libro + ')">Seleccionar</a></td>' + '</tr>';
//        cuerpo += registro;
//    });
//    libros4.forEach(function (Libro) {
//        let registro =
//                '<tr>' +
//                '<td>' + Libro.titulo + '</td>' +
//                '<td>' + Libro.autor + '</td>' +
//                '<td>' + Libro.universidad.nombreUniversidad + '</td>' +
//                '<td><a href="#" onclick="moduloBuscarL.mostrarDetalle4(' + Libro.id_libro + ')">Seleccionar</a></td>' + '</tr>';
//        cuerpo += registro;
//    });
//    
//    document.getElementById("tblLibro").innerHTML = cuerpo;
//}
export function loadTable2(data) {
    let cuerpo = "";
    libros = data;

    libros.forEach(function (Libro) {
        let registro =
                '<tr>' +
                '<td>' + Libro.libro_nombre + '</td>' +
                '<td>' + Libro.tema + '</td>' +
                '<td>'+Libro.nombre_universidad+'</td>'+
                '<td><a href="#" onclick="moduloBuscarL.mostrarDetalle(' + Libro.universidad_libro_id + ')">Seleccionar</a></td>' + '</tr>';
        cuerpo += registro;
    });
       
    document.getElementById("tblLibro").innerHTML = cuerpo;
}

export function buscar() {
    
    let filtro = document.getElementById("txtBusquedaLibro").value;
    let url;
    let data1;
    url = "http://192.168.43.222:8080/api/buscar-libro";
    const headers = {'accept': '*/*',
    'Authorization': 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1bml2ZXJzaWRhZF9pZCI6IjIxMDAwMzg0Iiwibm9tYnJlIjoiQWxlYyIsImdydXBvIjoiSURHUzcwMyIsImlhdCI6MTY5NzA3MTMwMSwiZXhwIjoxNjk3MDc0OTAxfQ.1cMSslG6sDqxqHYMvHXmaNlyb-CvtbJUw0ijhD6BxVc',
    'Content-Type':'application/json'};
    const data = {'filtro': filtro};
    fetch(url, {method:'POST',headers:headers,body:JSON.stringify(data)})
            .then(response => {
                return response.json();
            })
            .then(function (data)
            {
                if (data.exception != null)
                {
                    swal.fire('', 'Error interno del servidor. Intente nuevamente más tarde.', 'error');
                    return;
                }
                if (data.error != null)
                {
                    Swal.fire('', data.error, 'warning');
                    return;
                }
                loadTable2(data);
//                buscar2(data);
            });
            
}


//export function buscar() {
//    
//    let filtro = document.getElementById("txtBusquedaLibro").value;
//    let url;
//    let data1;
//    if(filtro==="")
//    {
//    url = "api/alumnoLibro/buscar?filtro="+filtro;
//    }
//    else
//    {
//     url = "api/alumnoLibro/buscar?filtro=" + filtro;
//    }
//    fetch(url)
//            .then(response => {
//                return response.json();
//            })
//            .then(function (data)
//            {
//                if (data.exception != null)
//                {
//                    swal.fire('', 'Error interno del servidor. Intente nuevamente más tarde.', 'error');
//                    return;
//                }
//                if (data.error != null)
//                {
//                    Swal.fire('', data.error, 'warning');
//                    return;
//                }
////                loadTable2(data);
//                buscar2(data);
//            });
//            
//}
//export function buscar2(dataA) {
//    
//    let filtro = document.getElementById("txtBusquedaLibro").value;
//    let url;
//    url = "http://192.168.137.143:8080/Biblioteca/api/alumnoLibro/buscar?filtro="+filtro;
////    url = "api/alumnoLibro/buscar?filtro=" + filtro;
//    fetch(url)
//            .then(response => {
//                return response.json();
//            })
//            .then(function (data)
//            {
//                if (data.exception != null)
//                {
//                    swal.fire('', 'Error interno del servidor. Intente nuevamente más tarde.', 'error');
//                    return;
//                }
//                if (data.error != null)
//                {
//                    Swal.fire('', data.error, 'warning');
//                    return;
//                }
////                loadTable(data);
//                buscar3(dataA, data);
//            });
//            
//}
//export function buscar3(dataA, dataB) {
//    
//    let filtro = document.getElementById("txtBusquedaLibro").value;
//    let url;
//    url = "http://192.168.137.122:8081/Biblioteca/api/alumnoLibro/buscar?filtro="+filtro;
//    fetch(url)
//            .then(response => {
//                return response.json();
//            })
//            .then(function (data)
//            {
//                if (data.exception != null)
//                {
//                    swal.fire('', 'Error interno del servidor. Intente nuevamente más tarde.', 'error');
//                    return;
//                }
//                if (data.error != null)
//                {
//                    Swal.fire('', data.error, 'warning');
//                    return;
//                }
////                loadTable(dataA, dataB, data, data);
//                buscar4(dataA,dataB,data);
//            });
//            
//}
//
//export function buscar4(dataA, dataB, dataC) {
//    
//    let filtro = document.getElementById("txtBusquedaLibro").value;
//    let url;
//    url = "http://192.168.137.248:8082/biblioteca_virtual/api/libro/obtenerFiltro?filtro="+filtro;
//    fetch(url)
//            .then(response => {
//                return response.json();
//            })
//            .then(function (data)
//            {
//                if (data.exception != null)
//                {
//                    swal.fire('', 'Error interno del servidor. Intente nuevamente más tarde.', 'Error');
//                    return;
//                }
//                if (data.error != null)
//                {
//                    Swal.fire('', data.error, 'warning');
//                    return;
//                }
//                loadTable(dataA, dataB, dataC, data);
//            });
//            
//}
export function mostrarDetalle(id) {
    libros.forEach(function (libro){
        if (id === libro.id_libro){
            document.getElementById("txtCodigoImagen").value = libro.libro_base64;
            document.getElementById("txtIdLibro").value = libro.id_libro;
           
           
            //document.getElementById("imgFoto").src = "data:pdf/" +
            //     getImageFormat(fotoB64) + ";base64," + libro.id_libro;
              mostrarPDFDesdeBase64();
              refrescarTabla();
        }
    });

    //document.getElementById("btnDelete").classList.remove("disabled");
}
export function mostrarDetalle2(id) {
    libros2.forEach(function (libro){
        if (id === libro.id_libro){
            document.getElementById("txtCodigoImagen").value = libro.libro;
            document.getElementById("txtIdLibro").value = libro.id_libro;
           
           
            //document.getElementById("imgFoto").src = "data:pdf/" +
            //     getImageFormat(fotoB64) + ";base64," + libro.id_libro;
              mostrarPDFDesdeBase64();
              refrescarTabla();
              
             
         
        }
    });

    //document.getElementById("btnDelete").classList.remove("disabled");
}
export function mostrarDetalle3(id) {
    libros3.forEach(function (libro){
        if (id === libro.id_libro){
            document.getElementById("txtCodigoImagen").value = libro.libro;
            document.getElementById("txtIdLibro").value = libro.id_libro;
           
           
            //document.getElementById("imgFoto").src = "data:pdf/" +
            //     getImageFormat(fotoB64) + ";base64," + libro.id_libro;
              mostrarPDFDesdeBase64();
              refrescarTabla();
              
             
         
        }
    });

    //document.getElementById("btnDelete").classList.remove("disabled");
}
export function mostrarDetalle4(id) {
    libros4.forEach(function (libro){
        if (id === libro.id_libro){
            document.getElementById("txtCodigoImagen").value = libro.libro;
            document.getElementById("txtIdLibro").value = libro.id_libro;
           
           
            //document.getElementById("imgFoto").src = "data:pdf/" +
            //     getImageFormat(fotoB64) + ";base64," + libro.id_libro;
              mostrarPDFDesdeBase64();
              refrescarTabla();
              
             
         
        }
    });

    //document.getElementById("btnDelete").classList.remove("disabled");
}
export function clean() {

    document.getElementById("txtIdLibro").value = "";
    document.getElementById("txtCodigoImagen").value = "";
    document.getElementById("imgFoto").setAttribute("src", "");

}


export function showInputDialog() {
    document.getElementById("inputFileImagenArmazon").click();
}

function cargarFotografia(objetoInputFile) {
    //Revisamos que el usuario haya seleccionado un archivo
    if (objetoInputFile.files && objetoInputFile.files[0]) {
        let reader = new FileReader();

        //Agregamos un oyente al lector del archivo para que,
        //en cuanto el usuario cargue una imagen, esta se lea
        //y se convierta de forma automatica en una cadena de Base64
        reader.onload = function (e) {
            fotoB64 = e.target.result;
            document.getElementById("imgFoto").src = fotoB64;

            //fotoB64 = fotoB64.substring(fotoB64.indexOf(",") + 1, fotoB64.length);
            document.getElementById("txtCodigoImagen").value =
                    fotoB64.substring(fotoB64.indexOf(",") + 1, fotoB64.length);
        };

        //Leemos el archivo que seleccionó el usuario y lo
        //convertimos en una cadena con la Base64
        reader.readAsDataURL(objetoInputFile.files[0]);
    }
}


export function getImageFormat(strb64) {
    let fc = strb64 != null && strb64.length > 0 ? strb64.subtr(0, 1) : "";

    switch (fc) {
        case "/" :
            return "jpeg";
        case "i" :
            return "png";
        case "Q" :
            return "bmp";
        case "S" :
            return "tiff";
        case "J" :
            return "pdf";
        case "U" :
            return "webp";
        case "R" :
            return "gif";
    }
}

function mostrarPDFDesdeBase64() {

    let b64 = document.getElementById("txtCodigoImagen").value;

    var bin = atob(b64);
    console.log('File Size:', Math.round(bin.length / 1024), 'KB');
    console.log('PDF Version:', bin.match(/^.PDF-([0-9.]+)/)[1]);

    //document.getElementById("imgFoto").src = 'data:application/pdf;base64,' + b64;

    var obj = document.createElement('object');
    obj.style.width = '86%';
    obj.style.height = '842pt';
    obj.style.float = 'right';
    obj.type = 'application/pdf';
    obj.data = 'data:application/pdf;base64,' + b64;
    document.body.appendChild(obj);

    document.getElementById("tabla").classList.add("d-none");

    document.getElementById("txtBusquedaLibro").addEventListener("click", function () {
        document.getElementById("tabla").classList.remove("d-none");
        document.body.removeChild(obj);
    });
}
