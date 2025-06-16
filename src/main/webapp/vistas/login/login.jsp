<%-- 
    Document   : login
    Created on : 12/06/2025, 2:57:20 p. m.
    Author     : anime
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="es"> 
    <head> 
        <meta charset="UTF-8" /> <title>Login - El Vecino Amigo</title> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="stylesheet" href="login.css" /> 
        <script src="https://kit.fontawesome.com/a076d05399.js" crossorigin="anonymous"></script> 
    </head> 
    <body> 
        <canvas id="particles"></canvas>
        <div class="login-card"> 
            <h2>Bienvenido</h2> <p><a href="#">Accede a tu cuenta</a></p> 
            <form id="loginForm" action="${pageContext.request.contextPath}/LoginServlet" method="POST"> 
                <div class="input-group"> <input type="text" id="usuario" name="usuario" placeholder="Usuario" required /> </div> 
                <div class="input-group"> <input type="password" id="contraseña" name="contraseña" placeholder="Contraseña" required /> </div>
                <button type="submit">Iniciar Sesión</button>
                <p class="error-msg" id="mensajeError"> <%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %> </p> 
            </form> 
        </div>
        <script src="login.js"></script> 
    </body> 
</html>