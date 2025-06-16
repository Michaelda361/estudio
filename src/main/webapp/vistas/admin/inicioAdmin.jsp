<%-- 
    Document   : inicioAdmin
    Created on : 12/06/2025, 5:46:30 p. m.
    Author     : anime
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="modelo.Usuario" %>
<%
HttpSession sesion = request.getSession(false);
Usuario user = (Usuario) sesion.getAttribute("usuario");
if (user == null || !"Administrador".equalsIgnoreCase(user.getRol())) {
    response.sendRedirect(request.getContextPath() + "/vistas/login/login.jsp");
    return;
}%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8"> <title>Panel Administrador</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <link rel="stylesheet" href="inicioAdmin.css"> 
    </head> 
    <body> <div class="admin-container"> <header> <h1>Bienvenido, <%= user.getNombreUsuario() %></h1> <button id="cerrarSesion">Cerrar sesión</button> </header>
            <main>
     <div class="card" onclick="irModulo('productos')">
        <h3>📦 Productos</h3>
     <p>Gestionar inventario</p>
    </div>
  <div class="card" onclick="irModulo('usuarios')">
    <h3>👤 Usuarios</h3>
    <p>Administrar empleados</p>
  </div>
  <div class="card" onclick="irModulo('reportes')">
    <h3>📊 Reportes</h3>
    <p>Ver estadísticas</p>
  </div>
</main>
  </div>
     <script src="inicioAdmin.js"></script> 
    </body> 
</html>