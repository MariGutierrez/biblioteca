let moduloUniversidad;

function cargarModuloUniversidad(){
    fetch("modulo_universidad/view_universidad.html")
        .then(
            function(response){
                return response.text();
            }
        )
        .then(
            function(html){
                document.getElementById("contPrincipal").innerHTML = html;
                import ("modulo_universidad/controller_universidad.js").then(
                    function(controller){
                        moduloUniversidad = controller;
                        moduloUniversidad.inicializar();
                    }
                );
            }
        );
}

let moduloLibro;
function cargarModuloLibro(){
    fetch("modulo_libro/view_libro.html")
        .then(
            function(response){
                return response.text();
            }
        )
        .then(
            function(html){
                document.getElementById("contPrincipal").innerHTML = html;
                import ("../modulo_libro/controller_libro.js").then(
                    function(controller){
                        moduloLibro = controller;
                        moduloLibro.inicializar();
                    }
                );
            }
        );
}
