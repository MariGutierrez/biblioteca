let moduloAccesorio;
let moduloSoluciones;


async function encriptar(texto) {
    const encoder = new TextEncoder();
    const data = encoder.encode(texto);
    const hash = await crypto.subtle.digest('SHA-256', data);
    const hashArray = Array.from(new Uint8Array(hash));
    const hashHex = hashArray.map((b) => b.toString(16).padStart(2, '0')).join('');

    return hashHex;
}

function accesar() {

    let usuario = document.getElementById("us").value;
    let contrasenia = document.getElementById("contr").value;

    encriptar(contrasenia).then((textoEncriptado) => {

//        alert(textoEncriptado.toString());

//    console.log(usuario + " " + contrasenia)

        let datos = JSON.stringify({nombre: usuario, contrasenia: textoEncriptado.toString()});

        let parametros = new URLSearchParams({datos: datos});
//    console.log(parametros);

        fetch("api/login/login",
                {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                    body: parametros

                }).then(response => response.json())
                .then(data => {
//                console.log(data);
//                alert("Bienvenido " + data.persona.nombre);
                    localStorage.setItem('currentUser', JSON.stringify(data));
                    window.location.href = 'inicio.html';
                });
    });
}


function cerrarSesion() {

    let em = localStorage.getItem('currentUser');
    let empleado = {"empleado": em};

    let params = new URLSearchParams(empleado);
//     console.log(parametros);
    fetch("api/login/out",
            {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'},
                body: params
            })
            .then(response => {
                return response.json();
            })
            .then(function (data) {


                if (data.exception != null) {
                    Swal.fire('', 'error interno del servidor');
                    return;
                }
                if (data.error != null) {
                    Swal.fire('', data.error, 'warning');
                    return;
                } else {
                    localStorage.removeItem('currentUser');
                    Swal.fire('', 'Sesion cerrada con exito');
                    //window.location.replace('index.html');
                }
            });
}


function cargarModuloAccesorios() {
    fetch("modules/moduloAccesorios/view_Accesorios.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloAccesorios/controller_Accesorios.js").then(
                                function (controller) {
                                    moduloAccesorio = controller;
                                    moduloAccesorio.loadTabla1();
                                }
                        );
                    }
            );
}
;

let moduloTratamiento;

function cargarModuloTratamientos() {
    fetch("modules/moduloTratamientos/view_Tratamientos.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloTratamientos/controller_Tratamientos.js").then(
                                function (controller) {
                                    moduloTratamiento = controller;
                                    moduloTratamiento.loadTabla1();
                                }
                        );
                    }
            );
}

let moduloLentesContactoo;
function cargarLentesContacto() {
    fetch("modules/moduloLentesContactoo/view_LentesContacto.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloLentesContactoo/controller_LentesContacto.js").then(
                                function (controller) {
                                    moduloLentesContactoo = controller;
                                    moduloLentesContactoo.loadTabla1();
                                }
                        );
                    }
            );
}




function cargarModuloSoluciones() {
    fetch("modules/moduloSoluciones/view_Soluciones.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloSoluciones/controller_Soluciones.js").then(
                                function (controller) {
                                    moduloSoluciones = controller;
                                    moduloSoluciones.loadTabla1();
                                }
                        );
                    }
            );
}
;

let moduloEmpleado;

function cargarModuloEmpleados() {
    fetch("modules/moduloEmpleados/view_Empleados.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloEmpleados/controller_Empleados.js").then(
                                function (controller) {
                                    moduloEmpleado = controller;
                                    moduloEmpleado.inicializar();
                                }
                        );
                    }
            );
}
;

let moduloCompra;

function cargarModulo() {
    fetch("modules/Bitacora_Compra/view_compras.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/Bitacora_Compra/controller_Compras.js").then(
                                function (controller) {
                                    moduloCompra = controller;
                                    moduloCompra.loadTabla();
                                }
                        );
                    }
            );
}
;

let moduloArmazon;

function cargarModuloArmazones() {
    fetch("modules/moduloArmazones/view_Armazones.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloArmazones/controller_Armazones.js").then(
                                function (controller) {
                                    moduloArmazon = controller;
                                    moduloArmazon.refrescarTabla();
                                    moduloArmazon.inicializar();
                                }
                        );
                    }
            );
}
;

let moduloMateriales;

function cargarModuloMateriales() {
    fetch("modules/moduloMateriales/view_Materiales.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloMateriales/controller_Materiales.js").then(
                                function (controller) {
                                    moduloMateriales = controller;
                                    moduloMateriales.loadTabla();
                                }
                        );
                    }
            );
}
;

let moduloExamenVista;

function cargarmoduloExamenVista() {
    fetch("modules/moduloExamenVista/view_ExamenVista.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloExamenVista/controller_ExamenVista.js").then(
                                function (controller) {
                                    moduloExamenVista = controller;
                                    moduloExamenVista.loadTabla();
                                }
                        );
                    }
            );
}
;

let moduloClientes;

function cargarmoduloClientes() {
    fetch("modules/moduloClientes/view_Clientes.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloClientes/controller_Clientes.js").then(
                                function (controller) {
                                    moduloClientes = controller;
                                    moduloClientes.inicializar();
                                }
                        );
                    }
            );
}
;

let moduloVenta;

function cargarmoduloVentas() {
    fetch("modules/moduloVentas/view_ventas.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloVentas/controller_venta.js").then(
                                function (controller) {
                                    moduloVenta = controller;
                                    moduloVenta.inicializar();
                                }
                        );
                    }
            );
}
;

let moduloVentaLC;

function cargarmoduloVentaLC() {
    fetch("modules/moduloVentaLC/view_ventaLC.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloVentaLC/controller_ventaLC.js").then(
                                function (controller) {
                                    moduloVentaLC = controller;
                                    moduloVentaLC.inicializar();
                                }
                        );
                    }
            );
}
;

let moduloDetalleVentaPL;

function cargarmoduloDetalleVentaPL() {
    fetch("modules/moduloDetalleVentaPL/view_detalleVentaPL.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloDetalleVentaPL/controller_detalleVentaPL.js").then(
                                function (controller) {
                                    moduloDetalleVentaPL = controller;
                                    moduloDetalleVentaPL.inicializar();
                                }
                        );
                    }
            );
}
;

let moduloMicas;

function cargarmoduloMicas() {
    fetch("modules/moduloMicas/view_micas.html")
            .then(
                    function (response) {
                        return response.text();
                    }
            )
            .then(
                    function (html) {
                        document.getElementById("contenedorPrincipal").innerHTML = html;
                        import("../modules/moduloMicas/controller_mica.js").then(
                                function (controller) {
                                    moduloMicas = controller;
                                    moduloMicas.inicializar();
                                }
                        );
                    }
            );
}
;
