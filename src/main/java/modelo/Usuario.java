/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author anime
 */
public class Usuario {
private int idUsuario;
private String nombreUsuario;
private String rol;

public Usuario() {}

public Usuario(int idUsuario, String nombreUsuario, String rol) {
    this.idUsuario = idUsuario;
    this.nombreUsuario = nombreUsuario;
    this.rol = rol;
}

public int getIdUsuario() {
    return idUsuario;
}

public void setIdUsuario(int idUsuario) {
    this.idUsuario = idUsuario;
}

public String getNombreUsuario() {
    return nombreUsuario;
}

public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
}

public String getRol() {
    return rol;
}

public void setRol(String rol) {
    this.rol = rol;
}
}

