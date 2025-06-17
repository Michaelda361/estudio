<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Usuario" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
HttpSession sesion = request.getSession(false);
Usuario usuario = (Usuario) sesion.getAttribute("usuario");
if (usuario == null || !"Administrador".equalsIgnoreCase(usuario.getRol())) {
response.sendRedirect(request.getContextPath() + "/vistas/login/login.jsp");
return;
}
%>

<!DOCTYPE html> 
<html lang="es"> 
    <head> 
        <meta charset="UTF-8"> <title>Administrar Empleados</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/vistas/admin/empleado.css"> 
        <script>
            const CONTEXT_PATH = "${pageContext.request.contextPath}";
        </script>
    </head>
    <body>
        <div class="contenedor"> 
            <header> <h1>Administrar Empleados</h1> 
                <button id="btnAgregar">+ Agregar Empleado</button> </header>
    <table id="tablaEmpleados">
        <thead>
            <tr>
                <th>ID</th>
                <th>Usuario</th>
                <th>Rol</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="emp" items="${listaEmpleados}">
                <tr id="empleado-${emp.idUsuario}">
                    <td>${emp.idUsuario}</td>
                    <td>${emp.nombreUsuario}</td>
                    <td>${emp.rol}</td>
                    <td>
                        <button onclick="editar(${emp.idUsuario}, '${emp.nombreUsuario}', '${emp.rol}')">✏️</button>
                        <button onclick="eliminar(${emp.idUsuario})">🗑️</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div id="formularioEmpleado" class="modal oculto">
        <form id="formEmpleado" method="POST" action="${pageContext.request.contextPath}/EmpleadoServlet">
            <input type="hidden" name="idUsuario" id="idUsuario">
            <label for="nombreUsuario">Usuario:</label>
            <input type="text" name="nombreUsuario" id="nombreUsuario" required>
            <label for="rol">Rol:</label>
            <select name="rol" id="rol" required>
                <option value="Administrador">Administrador</option>
                <option value="Empleado">Empleado</option>
            </select>
            <label for="contraseña">Contraseña:</label>
            <input type="password" name="contraseña" id="contraseña">
            <div class="acciones">
                <button type="submit">Guardar</button>
                <button type="button" onclick="cerrarFormulario()">Cancelar</button>
            </div>
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/vistas/admin/empleado.js"></script>

</body> 
</html>