let moduloAccesorio;
let moduloSoluciones;

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


