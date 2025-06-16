package controller;
import DAO.UsuarioDAO;
import modelo.Usuario;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

@Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String usuario = request.getParameter("usuario");
    String contraseña = request.getParameter("contraseña");

    try {
        Usuario usuarioValidado = usuarioDAO.validarLogin(usuario, contraseña);

        if (usuarioValidado != null) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuarioValidado);

            String rol = usuarioValidado.getRol();
            if ("Administrador".equalsIgnoreCase(rol)) {
                response.sendRedirect(request.getContextPath() + "/vistas/admin/inicioAdmin.jsp");
            } else if ("Empleado".equalsIgnoreCase(rol)) {
                response.sendRedirect(request.getContextPath() + "/vistas/empleado/inicioEmpleado.jsp");
            } else {
                sesion.invalidate();
                request.setAttribute("error", "Rol no reconocido");
                request.getRequestDispatcher("/vistas/login/login.jsp").forward(request, response);
            }

        } else {
            request.setAttribute("error", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("/vistas/login/login.jsp").forward(request, response);
        }

    } catch (Exception ex) {
        request.setAttribute("error", "Error interno del servidor: " + ex.getMessage());
        request.getRequestDispatcher("/vistas/login/login.jsp").forward(request, response);
        ex.printStackTrace();
    }
}
}
