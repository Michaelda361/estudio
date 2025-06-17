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

public boolean insertarUsuario(Usuario usuario) {
    String sql = "INSERT INTO usuario (NombreUsuario, Contraseña, Rol) VALUES (?, ?, ?)";
    try (Connection conn = DBConnection.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, usuario.getNombreUsuario());
        ps.setString(2, usuario.getContraseña());
        ps.setString(3, usuario.getRol());
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al insertar usuario: " + e.getMessage());
        return false;
    }
}

public boolean actualizarUsuario(Usuario usuario) {
    String sql;
    boolean actualizaContraseña = usuario.getContraseña() != null && !usuario.getContraseña().isEmpty();
    if (actualizaContraseña) {
        sql = "UPDATE usuario SET NombreUsuario = ?, Contraseña = ?, Rol = ? WHERE idUsuario = ?";
    } else {
        sql = "UPDATE usuario SET NombreUsuario = ?, Rol = ? WHERE idUsuario = ?";
    }

    try (Connection conn = DBConnection.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, usuario.getNombreUsuario());
        if (actualizaContraseña) {
            ps.setString(2, usuario.getContraseña());
            ps.setString(3, usuario.getRol());
            ps.setInt(4, usuario.getIdUsuario());
        } else {
            ps.setString(2, usuario.getRol());
            ps.setInt(3, usuario.getIdUsuario());
        }
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al actualizar usuario: " + e.getMessage());
        return false;
    }
}

public boolean eliminarUsuario(int idUsuario) {
    String sql = "DELETE FROM usuario WHERE idUsuario = ?";
    try (Connection conn = DBConnection.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        System.err.println("Error al eliminar usuario: " + e.getMessage());
        return false;
    }
}

public Usuario obtenerUsuarioPorId(int idUsuario) {
    Usuario usuario = null;
    String sql = "SELECT idUsuario, NombreUsuario, Rol FROM usuario WHERE idUsuario = ?";
    try (Connection conn = DBConnection.obtenerConexion();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idUsuario);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("idUsuario"));
                usuario.setNombreUsuario(rs.getString("NombreUsuario"));
                usuario.setRol(rs.getString("Rol"));
                // Nota: No recuperamos la contraseña por seguridad y porque usualmente no se necesita.
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener usuario por ID: " + e.getMessage());
    }
    return usuario;
}
    
}