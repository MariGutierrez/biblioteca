

function ingresar() {

    let usuario = document.getElementById("usuario").value;
    let contrasenia = document.getElementById("contrasena").value;

    let parametros = new URLSearchParams({usuario: usuario, contrasenia: contrasenia});
    console.log(parametros);
    

        fetch("api/log/in",
                {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                    body: parametros

                }).then(response => response.json())
                .then(data => {
//                console.log(data);
                   window.location.href = 'inicio.html';
                });
}
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


