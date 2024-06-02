/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author USUARIO
 */
import modelo.Prestamo;
import servicio.PrestamoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrestamoPanel extends JPanel {
    private final PrestamoService prestamoService;

    public PrestamoPanel(Connection connection) {
        this.prestamoService = new PrestamoService(connection);

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        formPanel.add(new JLabel("ID Usuario:"));
        JTextField idUsuarioField = new JTextField();
        formPanel.add(idUsuarioField);

        formPanel.add(new JLabel("ID Libro:"));
        JTextField idLibroField = new JTextField();
        formPanel.add(idLibroField);

        formPanel.add(new JLabel("Fecha de Préstamo (yyyy-MM-dd):"));
        JTextField fechaPrestamoField = new JTextField();
        formPanel.add(fechaPrestamoField);

        formPanel.add(new JLabel("Fecha Acordada (yyyy-MM-dd):"));
        JTextField fechaAcordadaField = new JTextField();
        formPanel.add(fechaAcordadaField);

        JButton realizarPrestamoButton = new JButton("Realizar Préstamo");
        realizarPrestamoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Prestamo prestamo = new Prestamo();
                    prestamo.setIdUsuario(Integer.parseInt(idUsuarioField.getText()));
                    prestamo.setIdLibro(Integer.parseInt(idLibroField.getText()));
                    prestamo.setFechaPrestamo(parseDate(fechaPrestamoField.getText()));
                    prestamo.setFechaAcordada(parseDate(fechaAcordadaField.getText()));
                    prestamoService.realizarPrestamo(prestamo);
                    JOptionPane.showMessageDialog(PrestamoPanel.this, "Préstamo realizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(PrestamoPanel.this, "Error al realizar el préstamo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(PrestamoPanel.this, "Formato de fecha incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(PrestamoPanel.this, "ID de Usuario o Libro inválido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(formPanel, BorderLayout.CENTER);
        add(realizarPrestamoButton, BorderLayout.SOUTH);
    }

    private Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateStr);
    }
}


