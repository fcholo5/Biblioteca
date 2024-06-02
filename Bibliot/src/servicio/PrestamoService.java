
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author USUARIO
 */


import modelo.Prestamo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrestamoService {
    private Connection connection;

    public PrestamoService(Connection connection) {
        this.connection = connection;
    }

    public void realizarPrestamo(Prestamo prestamo) throws SQLException {
        String sql = "INSERT INTO prestamos (id_usuario, id_libro, fecha_prestamo, fecha_acordada) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, prestamo.getIdUsuario());
            stmt.setInt(2, prestamo.getIdLibro());
            stmt.setDate(3, new java.sql.Date(prestamo.getFechaPrestamo().getTime()));
            stmt.setDate(4, new java.sql.Date(prestamo.getFechaAcordada().getTime()));
            stmt.executeUpdate();
        }
    }

    public void devolverLibro(int idPrestamo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
