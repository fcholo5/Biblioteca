/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicio;

/**
 *
 * @author USUARIO
 */
import vista.AdminPanel;
import Vista.BibliotecarioPanel;
import modelo.Usuario;
import servicio.UsuarioService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginPanel extends JPanel {
    private final UsuarioService usuarioService;
    private final JFrame parentFrame;
    private Usuario usuario;

    public LoginPanel(JFrame parentFrame, UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
        this.parentFrame = parentFrame;

        setLayout(new GridLayout(4, 2));

        JLabel usuarioLabel = new JLabel( "email:");
        JTextField usuarioField = new JTextField();
        JLabel contrasenaLabel = new JLabel( "Contraseña:");
        JPasswordField contrasenaField = new JPasswordField();
        JButton loginButton = new JButton("Iniciar Sesión");
        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setEnabled(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = usuarioField.getText();
                String password = new String(contrasenaField.getPassword());
                try {
                    usuario = usuarioService.iniciarSesion(user, password);
                    if (usuario != null) {
                        JOptionPane.showMessageDialog(LoginPanel.this, "Inicio de sesión exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        switch (usuario.getRol().toLowerCase()) {
                            case "administrador":
                                showAdminPanel(usuario);
                                break;
                            case "bibliotecario":
                            case "monitor":
                                showBibliotecarioPanel(usuario);
                                break;
                            default:
                                JOptionPane.showMessageDialog(LoginPanel.this, "Rol no reconocido", "Error", JOptionPane.ERROR_MESSAGE);
                                break;
                        }
                        logoutButton.setEnabled(true);
                        loginButton.setEnabled(false);
                        usuarioField.setEnabled(false);
                        contrasenaField.setEnabled(false);
                    } else {
                        JOptionPane.showMessageDialog(LoginPanel.this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(LoginPanel.this, "Error al iniciar sesión: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usuario = null;
                logoutButton.setEnabled(false);
                loginButton.setEnabled(true);
                usuarioField.setEnabled(true);
                contrasenaField.setEnabled(true);
                parentFrame.getContentPane().removeAll();
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        add(usuarioLabel);
        add(usuarioField);
        add(contrasenaLabel);
        add(contrasenaField);
        add(new JLabel());  // Espacio vacío
        add(loginButton);
        add(new JLabel());  // Espacio vacío
        add(logoutButton);
    }

    private void showAdminPanel(Usuario usuario) {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new AdminPanel(usuario, usuarioService));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void showBibliotecarioPanel(Usuario usuario) {
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new BibliotecarioPanel(usuario, usuarioService));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
