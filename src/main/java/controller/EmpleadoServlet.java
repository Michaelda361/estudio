package controller;

import DAO.UsuarioDAO;
import modelo.Usuario;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
// HttpSession import removed as it's not used in the provided new logic

@WebServlet("/EmpleadoServlet")
public class EmpleadoServlet extends HttpServlet {
    private final UsuarioDAO dao = new UsuarioDAO();
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String format = request.getParameter("format");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> responseMap = new HashMap<>();

        try {
            if ("listar".equalsIgnoreCase(accion) && "json".equalsIgnoreCase(format)) {
                List<Usuario> lista = dao.listarTodos();
                response.getWriter().write(gson.toJson(lista));
                return; // JSON response sent, exit method
            } else if ("eliminar".equalsIgnoreCase(accion)) {
                String idUsuarioStr = request.getParameter("idUsuario");
                if (idUsuarioStr != null && !idUsuarioStr.isEmpty()) {
                    int idUsuario = Integer.parseInt(idUsuarioStr);
                    boolean success = dao.eliminarUsuario(idUsuario);
                    if (success) {
                        responseMap.put("status", "success");
                        responseMap.put("message", "Usuario eliminado correctamente.");
                    } else {
                        responseMap.put("status", "error");
                        responseMap.put("message", "Error al eliminar el usuario.");
                    }
                } else {
                    responseMap.put("status", "error");
                    responseMap.put("message", "ID de usuario no proporcionado para eliminar.");
                }
                response.getWriter().write(gson.toJson(responseMap));
                return; // JSON response sent, exit method
            } else if ("listar".equalsIgnoreCase(accion)) {
                // Existing JSP forwarding logic for non-JSON list requests
                List<Usuario> lista = dao.listarTodos();
                request.setAttribute("listaEmpleados", lista);
                request.getRequestDispatcher("/vistas/admin/empleado.jsp").forward(request, response);
                return; // Forwarded, exit method
            } else {
                // Default redirect for other actions or if accion is null
                response.sendRedirect(request.getContextPath() + "/vistas/admin/inicioAdmin.jsp");
                return; // Redirected, exit method
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseMap.put("status", "error");
            responseMap.put("message", "ID de usuario inválido: " + e.getMessage());
            response.getWriter().write(gson.toJson(responseMap));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseMap.put("status", "error");
            responseMap.put("message", "Error en el servidor: " + e.getMessage());
            response.getWriter().write(gson.toJson(responseMap));
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idUsuarioStr = request.getParameter("idUsuario");
        String nombreUsuario = request.getParameter("nombreUsuario");
        String rol = request.getParameter("rol");
        String contraseña = request.getParameter("contraseña");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> responseMap = new HashMap<>();
        Usuario usuario = new Usuario();

        try {
            usuario.setNombreUsuario(nombreUsuario);
            usuario.setRol(rol);
            // Contraseña can be empty for updates, DAO handles logic
            if (contraseña != null && !contraseña.isEmpty()) {
                usuario.setContraseña(contraseña);
            }

            boolean success;
            if (idUsuarioStr == null || idUsuarioStr.isEmpty()) { // Add new user
                success = dao.insertarUsuario(usuario);
                if (success) {
                    responseMap.put("status", "success");
                    responseMap.put("message", "Usuario agregado correctamente.");
                    // Assuming insertarUsuario doesn't return the full user object with ID
                    // We can return the input data, or fetch if needed and if DAO supports returning ID
                    responseMap.put("usuario", usuario);
                } else {
                    responseMap.put("status", "error");
                    responseMap.put("message", "Error al agregar el usuario.");
                }
            } else { // Edit existing user
                int idUsuario = Integer.parseInt(idUsuarioStr);
                usuario.setIdUsuario(idUsuario);
                success = dao.actualizarUsuario(usuario);
                if (success) {
                    responseMap.put("status", "success");
                    responseMap.put("message", "Usuario actualizado correctamente.");
                    Usuario usuarioActualizado = dao.obtenerUsuarioPorId(idUsuario); // Fetch updated user
                    responseMap.put("usuario", usuarioActualizado);
                } else {
                    responseMap.put("status", "error");
                    responseMap.put("message", "Error al actualizar el usuario.");
                }
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseMap.put("status", "error");
            responseMap.put("message", "ID de usuario inválido: " + e.getMessage());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            responseMap.put("status", "error");
            responseMap.put("message", "Error en el servidor: " + e.getMessage());
        }
        response.getWriter().write(gson.toJson(responseMap));
    }
}