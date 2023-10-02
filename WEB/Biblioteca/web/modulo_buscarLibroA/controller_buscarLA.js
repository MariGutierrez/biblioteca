let libros = [];
let fotoB64 = null;
let inputFileArmazon = null;

export function inicializar() {
    inputFileArmazon = document.getElementById("inputFileImagenArmazon");
    inputFileArmazon.onchange = function (evt) {
        cargarFotografia(inputFileArmazon);
    };
}


export function loadTable(data) {
    let cuerpo = "";
    libros = data;

    libros.forEach(function (Libro) {
        let registro =
                '<tr>' +
                '<td>' + Libro.titulo + '</td>' +
                '<td>' + Libro.autor + '</td>' +
                '<td>' + Libro.universidad.nombre_universidad + '</td>' +
                '<td><a href="#" onclick="moduloBuscarL.mostrarDetalle(' + Libro.id_libro + ')"><img src="resources/ojo.jpg" style="height: 25px; width: 30px;"></a></td>' + '</tr>';
                
        cuerpo += registro;
    });
    document.getElementById("tblLibro").innerHTML = cuerpo;
}

export function buscar() {
    
    let filtro = document.getElementById("txtBusquedaLibro").value;
    let url;
    if(filtro==="")
    {
    url = "api/alumnoLibro/buscar?filtro=" + filtro;
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

export function mostrarDetalle(id) {
    libros.forEach(function (libro){
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
    });
}
