/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;



/**
 *
 * @author USUARIO
 */

import modelo.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.Libro;

public class UsuarioService {
    private Connection connection;

    public UsuarioService(Connection connection) {
        this.connection = connection;
    }

    public Usuario iniciarSesion(String usuario, String contrasena) throws SQLException {
        String query = "SELECT * FROM usuarios WHERE email = ? AND contrasena = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, usuario);
            stmt.setString(2, contrasena);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("contrasena"),
                        rs.getString("rol")
                );
            }
        }
        return null;
    }

     public boolean registrarUsuario(String nombre, String email, String estado, int numSanciones, String contrasena, String rol) throws SQLException {
        String registrarUsuarioQuery = "INSERT INTO usuarios (nombre, email, estado, num_sanciones, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement registrarUsuarioStmt = connection.prepareStatement(registrarUsuarioQuery)) {
            registrarUsuarioStmt.setString(1, nombre);
            registrarUsuarioStmt.setString(2, email);
            registrarUsuarioStmt.setString(3, estado);
            registrarUsuarioStmt.setInt(4, numSanciones);
            registrarUsuarioStmt.setString(5, contrasena);
            registrarUsuarioStmt.setString(6, rol);

            int rowsInserted = registrarUsuarioStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            throw new SQLException("Error al registrar el usuario", e);
        }
    }
    public boolean sancionarUsuario(int usuarioId, String razon, Date fechaInicio) throws SQLException {
        String insertSancionQuery = "INSERT INTO sanciones (id_usuario, razon, fecha_inicio) VALUES (?, ?, ?)";

        try (PreparedStatement insertSancionStmt = connection.prepareStatement(insertSancionQuery)) {
            insertSancionStmt.setInt(1, usuarioId);
            insertSancionStmt.setString(2, razon);
            insertSancionStmt.setDate(3, new java.sql.Date(fechaInicio.getTime()));

            int rowsInserted = insertSancionStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
          
            throw new SQLException("Error al sancionar el usuario", e);
        }
    }

     public boolean quitarSancion(int userId, Date fecha_fin) throws SQLException {
    String updateQuery = "UPDATE usuarios SET num_sanciones = 0, fecha_fin = ? WHERE id = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
        preparedStatement.setDate(1, new java.sql.Date(fecha_fin.getTime()));
        preparedStatement.setInt(2, userId);
        int rowsAffected = preparedStatement.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        throw new SQLException("Error al quitar la sanción", e);
    }
}


    
}