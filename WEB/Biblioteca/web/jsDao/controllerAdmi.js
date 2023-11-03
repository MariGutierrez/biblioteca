let moduloBuscarL;
function cargarModuloBuscarLibroB(){
    fetch("modulo_buscarLibroB/view_buscarLB.html")
        .then(
            function(response){
                return response.text();
            }
        )
        .then(
            function(html){
                document.getElementById("contPrincipal").innerHTML = html;
                import ("../modulo_buscarLibroB/controller_buscarLB.js").then(
                    function(controller){
                        moduloBuscarL = controller;
                        moduloBuscarL.inicializar();
                    }
                );
            }
        );

};

function ingresar() {
    let datos = null;
    let params = null;
    let usuario = document.getElementById("usuario").value;
    datos = {
        nombre_usuario: usuario
    };
    params = new URLSearchParams(datos);
    console.log(datos);

    fetch("api/Busc/User", {
        method: "POST",
        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
        body: params
    })
        .then(response => {
            return response.json();
    })
    .then(function (data) {
        console.log('ata',data);

        if (data.exception != null) {
            Swal.fire('', 'Error interno del servidor. Intente nuevamente mas tarde.', 'error');
            return;
        }
        if (data.error != null) {
            Swal.fire('', data.error, 'warning');
            return;
        }
        if (data.errorperm != null) {
            Swal.fire('', 'No tiene permiso para realizar esta operacion.', 'warning');
            return;
        }
        if (data.nombre_usuario != null && data.contrasenia != null) {
            
            let tipoUser = data.rol;
            console.log(tipoUser);
            localStorage.setItem("user", tipoUser);
            window.location = "inicio.html";
        } else {
            Swal.fire('', 'Usuario y/o contraseña incorrectos.', 'warning');
        }

    })
    .catch(error => {
        console.error(error);
        Swal.fire('', 'Usuario y/o contraseña incorrectos.', 'error');
    });
}

