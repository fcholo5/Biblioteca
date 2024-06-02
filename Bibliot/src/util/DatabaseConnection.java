/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author USUARIO
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import modelo.Usuario;
import servicio.UsuarioService;

public class DatabaseConnection {

    public static Connection getDataSource() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public DatabaseConnection(Usuario user, UsuarioService usuarioService) {
    }
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
