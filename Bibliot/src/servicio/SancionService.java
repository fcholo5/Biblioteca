/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author USUARIO
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class SancionService {
    private Connection connection;

    public SancionService(Connection connection) {
        this.connection = connection;
    }

    public boolean sancionarUsuario(int idUsuario, String razon, Date fechaInicio, Date fechaFin) throws SQLException {
        String query = "INSERT INTO sanciones (id_usuario, razon, fecha_inicio, fecha_fin) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idUsuario);
            statement.setString(2, razon);
            statement.setDate(3, (java.sql.Date) fechaInicio);
            statement.setDate(4, (java.sql.Date) fechaFin);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
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
