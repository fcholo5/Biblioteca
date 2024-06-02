/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author USUARIO
 */

import modelo.Usuario;
import servicio.ReservaService;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

public class ReservaPanel extends JPanel {
    private Usuario usuario;
    private ReservaService reservaService;
    private Connection connection;

    public ReservaPanel(Usuario usuario, Connection connection) {
        this.usuario = usuario;
        this.connection = connection;
        this.reservaService = new ReservaService(connection);
        initialize();
    }

    ReservaPanel(Connection connection) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void initialize() {
        setLayout(new GridLayout(4, 2));

        JLabel libroIdLabel = new JLabel("ID del Libro:");
        JTextField libroIdField = new JTextField();
        
        JLabel fechaReservaLabel = new JLabel("Fecha de Reserva:");
        JTextField fechaReservaField = new JTextField();
        fechaReservaField.setText(new Date(System.currentTimeMillis()).toString()); // Set current date as default
        
        JButton realizarReservaButton = new JButton("Realizar Reserva");
        realizarReservaButton.addActionListener(e -> realizarReserva(libroIdField.getText(), fechaReservaField.getText()));
        
        add(libroIdLabel);
        add(libroIdField);
        add(fechaReservaLabel);
        add(fechaReservaField);
        add(new JLabel()); // Empty cell for layout alignment
        add(realizarReservaButton);
    }

    private void realizarReserva(String libroIdStr, String fechaReservaStr) {
        try {
            int libroId = Integer.parseInt(libroIdStr);
            Date fechaReserva = Date.valueOf(fechaReservaStr);

            boolean exito = reservaService.realizarReserva(usuario.getId(), libroId, fechaReserva);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Reserva realizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo realizar la reserva. Verifique los detalles.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al realizar la reserva", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para registrar el error en la consola.
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de libro inválido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Fecha de reserva inválida", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

