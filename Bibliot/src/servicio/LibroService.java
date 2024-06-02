/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author USUARIO
 */


import modelo.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LibroService {
    private Connection connection;

    public LibroService(Connection connection) {
        this.connection = connection;
    }

    public List<Libro> obtenerLibrosDisponibles() throws SQLException {
        String query = "SELECT * FROM libros WHERE estado = 'disponible' AND num_copias > 0";
        List<Libro> libros = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Libro libro = new Libro();
                libro.setId(resultSet.getInt("id"));
                libro.setTitulo(resultSet.getString("titulo"));
                libro.setAutor(resultSet.getString("autor"));
                libro.setEditorial(resultSet.getString("editorial"));
                libro.setCategoria(resultSet.getString("categoria"));
                libro.setEstado(Libro.Estado.valueOf(resultSet.getString("estado").toUpperCase()));
                libro.setNumCopias(resultSet.getInt("num_copias"));
                libros.add(libro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener los libros disponibles", e);
        }

        return libros;
    }
    public boolean realizarDevolucion(int libroId, int usuarioId) throws SQLException {
        String updateLibroQuery = "UPDATE libros SET num_copias = num_copias + 1, estado = CASE WHEN num_copias + 1 > 0 THEN 'disponible' ELSE 'prestado' END WHERE id = ?";
        String updatePrestamoQuery = "UPDATE prestamos SET fecha_devolucion = CURRENT_DATE() WHERE id_libro = ? AND id_usuario = ? AND fecha_devolucion IS NULL";

        try (PreparedStatement updateLibroStmt = connection.prepareStatement(updateLibroQuery);
             PreparedStatement updatePrestamoStmt = connection.prepareStatement(updatePrestamoQuery)) {

            connection.setAutoCommit(false);

            updateLibroStmt.setInt(1, libroId);
            int rowsUpdated = updateLibroStmt.executeUpdate();

            if (rowsUpdated == 0) {
                connection.rollback();
                return false;
            }

            updatePrestamoStmt.setInt(1, libroId);
            updatePrestamoStmt.setInt(2, usuarioId);
            updatePrestamoStmt.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw new SQLException("Error al realizar la devolución", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public boolean realizarPrestamo(int libroId, int usuarioId) throws SQLException {
        String updateLibroQuery = "UPDATE libros SET num_copias = num_copias - 1, estado = CASE WHEN num_copias - 1 = 0 THEN 'prestado' ELSE 'disponible' END WHERE id = ? AND estado = 'disponible' AND num_copias > 0";
        String insertPrestamoQuery = "INSERT INTO prestamos (id_libro, id_usuario, fecha_prestamo) VALUES (?, ?, CURRENT_DATE())";

        try (PreparedStatement updateLibroStmt = connection.prepareStatement(updateLibroQuery);
             PreparedStatement insertPrestamoStmt = connection.prepareStatement(insertPrestamoQuery)) {

            connection.setAutoCommit(false);

            updateLibroStmt.setInt(1, libroId);
            int rowsUpdated = updateLibroStmt.executeUpdate();

            if (rowsUpdated == 0) {
                connection.rollback();
                return false;
            }

            insertPrestamoStmt.setInt(1, libroId);
            insertPrestamoStmt.setInt(2, usuarioId);
            insertPrestamoStmt.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace();
            throw new SQLException("Error al realizar el préstamo", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
