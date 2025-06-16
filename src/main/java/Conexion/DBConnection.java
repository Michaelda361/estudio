
package Conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
private static final String URL = "jdbc:mysql://localhost:3306/tiendaamigo?useSSL=false&serverTimezone=UTC";
private static final String USUARIO = "root";          // Cámbialo si usas otro usuario
private static final String CONTRASEÑA = "";           // Pon tu contraseña aquí si tienes

public static Connection obtenerConexion() {
    Connection conn = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(URL, USUARIO, CONTRASEÑA);
    } catch (ClassNotFoundException | SQLException e) {
        System.err.println("❌ Error en DBConnection: " + e.getMessage());
    }
    return conn;
}

}
