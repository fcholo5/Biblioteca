/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author USUARIO
 */



import servicio.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MainFrame extends JFrame {
    private UsuarioService usuarioService;

    public MainFrame() {
        setTitle("Sistema de Gestión de Biblioteca");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializar servicios
        initializeServices();

        // Mostrar el panel de inicio de sesión al iniciar
        setLayout(new BorderLayout());
        showLoginPanel();
    }

    private void initializeServices() {
        try {
            // Reemplaza los valores con la configuración correcta de tu base de datos
            String dbUrl = "jdbc:mysql://localhost:3306/biblioteca";
            String dbUser = "root";
            String dbPassword = "";

            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            usuarioService = new UsuarioService(connection);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        } catch (UnsupportedOperationException e) {
            JOptionPane.showMessageDialog(this, "Método no soportado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void showLoginPanel() {
        getContentPane().removeAll();
        add(new LoginPanel(this, usuarioService), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}
