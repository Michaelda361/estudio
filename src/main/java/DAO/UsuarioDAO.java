package DAO;

import modelo.Usuario;
import Conexion.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
public Usuario validarLogin(String nombreUsuario, String contraseña) {
    Usuario usuario = null;
    String sql = "{ CALL sp_validar_login(?, ?) }";

    try (Connection conn = DBConnection.obtenerConexion();
         CallableStatement cs = conn.prepareCall(sql)) {

        cs.setString(1, nombreUsuario);
        cs.setString(2, contraseña);
        ResultSet rs = cs.executeQuery();

        if (rs.next()) {
            usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNombreUsuario(rs.getString("NombreUsuario"));
            usuario.setRol(rs.getString("Rol"));
        }

    } catch (SQLException e) {
        System.err.println("Error en validarLogin: " + e.getMessage());
    }

    return usuario;
} 

public List<Usuario> listarTodos() {
    List<Usuario> lista = new ArrayList<>();
    String sql = "SELECT idUsuario, NombreUsuario, Rol FROM usuario";

    try (Connection conn = DBConnection.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getInt("idUsuario"));
            u.setNombreUsuario(rs.getString("NombreUsuario"));
            u.setRol(rs.getString("Rol"));
            lista.add(u);
        }

    } catch (SQLException e) {
        System.err.println("Error al listar usuarios: " + e.getMessage());
    }

    return lista;
}
    
}