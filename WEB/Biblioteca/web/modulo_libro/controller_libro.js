let libros = [];
let fotoB64 = null;
let inputFileArmazon = null;

export function inicializar() {
    inputFileArmazon = document.getElementById("inputFileImagenArmazon");
    inputFileArmazon.onchange = function (evt) {
        cargarFotografia(inputFileArmazon);
    };
    refrescarTabla();
}

export function refrescarTabla() {
    let filtro = document.getElementById("cmbFiltro").value;
    let url = "api/libro/getAll?filtro=" + filtro;
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

export function loadTable(data) {
    let cuerpo = "";
    libros = data;

    libros.forEach(function (Libro) {
        let registro =
                '<tr>' +
                '<td>' + Libro.titulo + '</td>' +
                '<td>' + Libro.autor + '</td>' +
                '<td>' + Libro.universidad.nombre_universidad + '</td>' +
                
                '<td><a href="#" onclick="moduloLibro.mostrarDetalle(' + Libro.id_libro + ')"><img src="resources/ojo.jpg" style="height: 25px; width: 30px;"></a></td>' + '</tr>';
        cuerpo += registro;
    });
    document.getElementById("tblLibro").innerHTML = cuerpo;
}

export function buscar() {
    let filtro = document.getElementById("txtBusquedaLibro").value;
    let url;
    if(filtro==="")
    {
    url = "api/libro/buscar?filtro=" + filtro;
    }
    else
    {
     filtro = document.getElementById("cmbFiltro").value;   
     url = "api/libro/getAll?filtro=" + filtro;   
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

export function save() {
    let datos = null;
    let params = null;

    let libro = new Object();
    libro.universidad = new Object();

    if (document.getElementById("txtIdLibro").value.trim().length < 1) {
        libro.id_libro = 0;
    } else {
        libro.id_libro = parseInt(document.getElementById("txtIdLibro").value);
    }

    //libro.universidad = (1);
    libro.universidad.id_universidad = (1);
    libro.titulo = document.getElementById("titulo").value;
    libro.autor = document.getElementById("autor").value;
    libro.editorial = document.getElementById("editorial").value;
    libro.idioma = document.getElementById("idioma").value;
    libro.genero = document.getElementById("genero").value;
    libro.no_paginas = document.getElementById("num").value;
    //libro.libro = fotoB64;
    libro.libro = document.getElementById("txtCodigoImagen").value;
    libro.derecho_autor = document.getElementById("cmbDerecho").value;
    
    //libro.estatus = 1;

    datos = {
        datosLibro: JSON.stringify(libro)
    };

    params = new URLSearchParams(datos);

    fetch("api/libro/save",
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
                Swal.fire('', "Registro de libro exitoso", 'success');
                refrescarTabla();
                clean();
            });

}

export function mostrarDetalle(id) {
    libros.forEach(function (libro){
        if (id === libro.id_libro){
            document.getElementById("titulo").value = libro.titulo;
            document.getElementById("autor").value = libro.autor;
            document.getElementById("editorial").value = libro.editorial;
            document.getElementById("idioma").value = libro.idioma;
            document.getElementById("genero").value = libro.genero;
            document.getElementById("num").value = libro.no_paginas;
            document.getElementById("cmbDerecho").value = libro.derecho_autor.toString();
            document.getElementById("txtCodigoImagen").value = libro.libro;
            document.getElementById("txtIdLibro").value = libro.id_libro;
            
            document.getElementById("titulo").disabled = true;
            document.getElementById("autor").disabled = true;
            document.getElementById("editorial").disabled = true;
            document.getElementById("idioma").disabled = true;
            document.getElementById("genero").disabled = true;
            document.getElementById("num").disabled = true;
            document.getElementById("cmbDerecho").disabled = true;
            //document.getElementById("imgFoto").src = "data:pdf/" +
            //     getImageFormat(fotoB64) + ";base64," + libro.id_libro;
              mostrarPDFDesdeBase64();
              refrescarTabla();
              
             
         
        }
    });

    //document.getElementById("btnDelete").classList.remove("disabled");
}


export function clean() {
    document.getElementById("titulo").value = " ";
    document.getElementById("autor").value = "";
    document.getElementById("editorial").value = "";
    document.getElementById("idioma").value = "";
    document.getElementById("genero").value = "";
    document.getElementById("num").value = "";
    document.getElementById("txtIdLibro").value = "";
    document.getElementById("cmbDerecho").value = "";
    document.getElementById("txtCodigoImagen").value = "";
    document.getElementById("imgFoto").setAttribute("src", "");
    
    document.getElementById("titulo").disabled = false;
    document.getElementById("autor").disabled = false;
    document.getElementById("editorial").disabled = false;
    document.getElementById("idioma").disabled = false;
    document.getElementById("genero").disabled = false;
    document.getElementById("num").disabled = false;
    document.getElementById("cmbDerecho").disabled = false;            
}

//Eliminar
export function deleteLibro() {
    let id2 = document.getElementById("txtIdLibro").value;
    let datos = {
        datosLibro: id2
    };
    let params = new URLSearchParams(datos);
    fetch("api/libro/delete",
            {
                method: "POST",
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response
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

                Swal.fire('', "Libro eliminada correctamente", 'success');
                refrescarTabla();
                clean();
            });
}



export function askDelete() {
    Swal.fire({
        title: '¿Deseas eliminar este libro?',
        text: "Se marcará como inactiva",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            Swal.fire(
                    deleteLibro(),
                    'Libro eliminada',
                    'Marcado como inactivo',
                    'success'
                    );
        }
    });
}

export function imprimir(el) {

    var restorepage = document.body.innerHTML;
    var printcontent = document.getElementById(el).innerHTML;
    document.body.innerHTML = printcontent;
    window.print();
    document.body.innerHTML = restorepage;

}

export function soloNumeros(evt) {
    if (window.event) {
        keynum = evt.keyCode;
    } else {
        keynum = evt.which;
    }

    if ((keynum > 47 && keynum < 58) || keynum === 8 || keynum === 13 || keynum === 9) {
        return true;
    } else {
        Swal.fire('', "Solo debes ingresar números.", 'warning');
        return false;
    }
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
    
    
    document.getElementById("imgFoto").src = 'data:application/pdf;base64,' + b64;
    
}
