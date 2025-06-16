package controller;
import DAO.UsuarioDAO;
import modelo.Usuario;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/EmpleadoServlet")
public class EmpleadoServlet extends HttpServlet{
private final UsuarioDAO dao = new UsuarioDAO();
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    String accion = request.getParameter("accion");

    if ("listar".equalsIgnoreCase(accion)) {
        List<Usuario> lista = dao.listarTodos();
        request.setAttribute("listaEmpleados", lista);
        request.getRequestDispatcher("/vistas/admin/empleado.jsp").forward(request, response);
    } else {
        response.sendRedirect(request.getContextPath() + "/vistas/admin/inicioAdmin.jsp");
    }
}
}