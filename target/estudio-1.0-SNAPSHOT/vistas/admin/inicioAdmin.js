document.addEventListener("DOMContentLoaded", () => {
document.getElementById("cerrarSesion").addEventListener("click", () => {
if (confirm("¿Deseas cerrar sesión?")) {
window.location.href = "../../LogoutServlet";
}
});
});

function irModulo(modulo) {
  switch (modulo) {
    case 'productos':
      window.location.href = "../productos/";
      break;
    case 'usuarios':
      window.location.href = "../../EmpleadoServlet?accion=listar";
      break;
    case 'reportes':
      alert("Módulo de reportes en construcción");
      break;
    default:
      console.warn("Módulo desconocido");
  }
}