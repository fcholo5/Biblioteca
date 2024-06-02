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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservaService {
    private Connection connection;

    public ReservaService(Connection connection) {
        this.connection = connection;
    }

    public boolean realizarReserva(int usuarioId, int libroId, Date fechaReserva) throws SQLException {
        String sql = "INSERT INTO reservas (id_usuario, id_libro, fecha_reserva) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, libroId);
            stmt.setDate(3, fechaReserva);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }
    public boolean cancelarReserva(int usuarioId, int libroId) throws SQLException {
        String cancelarReservaQuery = "UPDATE reservas SET estado = 'cancelada' WHERE id_usuario = ? AND id_libro = ? AND estado = 'pendiente'";

        try (PreparedStatement cancelarReservaStmt = connection.prepareStatement(cancelarReservaQuery)) {
            cancelarReservaStmt.setInt(1, usuarioId);
            cancelarReservaStmt.setInt(2, libroId);

            int rowsUpdated = cancelarReservaStmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new SQLException("Error al cancelar la reserva", e);
        }
    }

}


