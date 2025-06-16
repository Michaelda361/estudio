document.addEventListener("DOMContentLoaded", () => {
document.getElementById("btnAgregar").addEventListener("click", () => {
document.getElementById("formEmpleado").reset();
document.getElementById("idUsuario").value = "";
document.getElementById("formularioEmpleado").classList.remove("oculto");
});
});

function editar(id, nombre, rol) {
document.getElementById("idUsuario").value = id;
document.getElementById("nombreUsuario").value = nombre;
document.getElementById("rol").value = rol;
document.getElementById("formularioEmpleado").classList.remove("oculto");
}

function eliminar(id) {
if (confirm("¿Estás seguro de eliminar al empleado " + id + "?")) {
window.location.href = "EmpleadoServlet?accion=eliminar&idUsuario=" + id;
}
}

function cerrarFormulario() {
document.getElementById("formularioEmpleado").classList.add("oculto");
}