function ingresar() {
    let datos = null;
    let params = null;
    let usuario = document.getElementById("usuario").value;
    let contrasenia = document.getElementById("contrasena").value;
    datos = {
        nombre_usuario: usuario,
        contrasenia: contrasenia
    };
    params = new URLSearchParams(datos);
    console.log(datos);

    fetch("api/log/in", {
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
<<<<<<< HEAD
=======
            
            let tipoUser = data.rol;
            console.log(tipoUser);
            localStorage.setItem("user", tipoUser);
>>>>>>> 01aa28975e78a4e417658a2b10f894bb3da970fe
            window.location = "inicio.html";
        } else {
            Swal.fire('', 'Usuario y/o contraseña incorrectos.', 'warning');
        }
<<<<<<< HEAD
=======
        
>>>>>>> 01aa28975e78a4e417658a2b10f894bb3da970fe
    })
    .catch(error => {
        console.error(error);
        Swal.fire('', 'Usuario y/o contraseña incorrectos.', 'error');
    });
}
