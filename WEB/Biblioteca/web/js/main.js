tipoUSer();
function tipoUSer()
{
    let rol = localStorage.getItem("user").toString();
    if(rol === "Administrador")
    {
        
    }
    else
    {
        document.getElementById("menOp2").classList.add("d-none");
        document.getElementById("menOp3").classList.add("d-none");
        document.getElementById("menOp4").classList.add("d-none");
    }
    console.log(rol);
}


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
                import ("../modulo_universidad/controller_universidad.js").then(
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

};

let moduloBuscarL;
function cargarModuloBuscarLibroA(){
    fetch("modulo_buscarLibroA/view_buscarLA.html")
        .then(
            function(response){
                return response.text();
            }
        )
        .then(
            function(html){
                document.getElementById("contPrincipal").innerHTML = html;
                import ("../modulo_buscarLibroA/controller_buscarLA.js").then(
                    function(controller){
                        moduloBuscarL = controller;
                        moduloBuscarL.inicializar();
                    }
                );
            }
        );

};

let moduloAlumno;
function cargarModuloAlumno(){
    fetch("modulo_alumnos/view_alumno.html")
        .then(
            function(response){
                return response.text();
            }
        )
        .then(
            function(html){
                document.getElementById("contPrincipal").innerHTML = html;
                import ("../modulo_alumnos/controller_alumno.js").then(
                    function(controller){
                        moduloAlumno = controller;
                        moduloAlumno.inicializar();
                    }
                );
            }
        );
};