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
import modelo.Libro;
import servicio.UsuarioService;
import servicio.LibroService;
import servicio.LoginPanel;
import util.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.StringJoiner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import servicio.ReservaService;

public class BibliotecarioPanel extends JPanel {
    private Usuario usuario;
    private UsuarioService usuarioService;
    private Connection connection;

    public BibliotecarioPanel(Usuario usuario, UsuarioService usuarioService) {
        this.usuario = usuario;
        this.usuarioService = usuarioService;
        this.connection = DatabaseConnection.getConnection();
        initialize();
    }

    private void initialize() {
        setLayout(new GridLayout(7, 1));


        JButton listarLibrosButton = new JButton("Listar Todos los Libros Disponibles ");
        listarLibrosButton.addActionListener(e -> listarLibros());

        JButton realizarPrestamoButton = new JButton("Realizar Préstamo de Libro");
        realizarPrestamoButton.addActionListener(e -> realizarPrestamo());

        JButton realizarDevolucionButton = new JButton("Realizar Devolución de Libro");
        realizarDevolucionButton.addActionListener(e -> realizarDevolucion());

        JButton realizarReservaButton = new JButton("Realizar Reserva de Libro");
        realizarReservaButton.addActionListener(e -> realizarReserva());



        JButton cancelarReservaButton = new JButton("Cancelar Reserva de Libro");
        cancelarReservaButton.addActionListener(e ->cancelarReserva());


        JButton listarLibrosPrestadosButton = new JButton("Listar Libros Prestados");
        listarLibrosPrestadosButton.addActionListener(e -> {
            // Implementa la lógica para listar libros prestados
        });


        JButton cerrarSesionButton = new JButton("Cerrar Sesión");
        cerrarSesionButton.addActionListener(e -> cerrarSesion());
        
        add(listarLibrosButton);
        add(realizarPrestamoButton);
        add(realizarDevolucionButton);
        add(realizarReservaButton);
        add(cancelarReservaButton);
        add(listarLibrosPrestadosButton);
        add(cerrarSesionButton);
    }
    

    private void realizarPrestamo() {
        try {
            LibroService libroService = new LibroService(connection);

            String libroIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del libro a prestar:");
            int libroId = Integer.parseInt(libroIdStr);

            String usuarioIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario que realiza el préstamo:");
            int usuarioId = Integer.parseInt(usuarioIdStr);

            boolean exito = libroService.realizarPrestamo(libroId, usuarioId);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Préstamo realizado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo realizar el préstamo. Verifique que el libro esté disponible.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al realizar el préstamo", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para registrar el error en la consola.
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de libro o usuario inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    


    private void realizarDevolucion() {
        try {
            LibroService libroService = new LibroService(connection);

            String libroIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del libro a devolver:");
            int libroId = Integer.parseInt(libroIdStr);

            String usuarioIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario que realiza la devolución:");
            int usuarioId = Integer.parseInt(usuarioIdStr);

            boolean exito = libroService.realizarDevolucion(libroId, usuarioId);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Devolución realizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo realizar la devolución. Verifique los detalles.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al realizar la devolución", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para registrar el error en la consola.
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de libro o usuario inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void listarLibros() {
        try {
            LibroService libroService = new LibroService(connection);
            List<Libro> libros = libroService.obtenerLibrosDisponibles();
            StringJoiner librosList = new StringJoiner("\n");
            for (Libro libro : libros) {
                librosList.add(libro.toString());
            }
            JOptionPane.showMessageDialog(this, librosList.toString(), "Lista de Libros", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al listar los libros", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Para registrar el error en la consola.
        }
    }
    private void realizarReserva() {
    try {
        ReservaService reservaService = new ReservaService(connection);

        String libroIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del libro a reservar:");
        int libroId = Integer.parseInt(libroIdStr);

        String usuarioIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario que realiza la reserva:");
        int usuarioId = Integer.parseInt(usuarioIdStr);

        java.sql.Date fechaReserva = new java.sql.Date(System.currentTimeMillis());

        boolean exito = reservaService.realizarReserva(usuarioId, libroId, fechaReserva);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Reserva realizada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo realizar la reserva. Verifique los detalles.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al realizar la reserva", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "ID de libro o usuario inválido", "Error", JOptionPane.ERROR_MESSAGE);
    }
    }
private void cancelarReserva() {
    try {
        ReservaService reservaService = new ReservaService(connection);

        String libroIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del libro cuya reserva desea cancelar:");
        if (libroIdStr == null || libroIdStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID del libro no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int libroId = Integer.parseInt(libroIdStr);

        String usuarioIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario que desea cancelar la reserva:");
        if (usuarioIdStr == null || usuarioIdStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID del usuario no puede estar vacío", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int usuarioId = Integer.parseInt(usuarioIdStr);
      
        boolean exito = reservaService.cancelarReserva(usuarioId, libroId);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Reserva cancelada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo cancelar la reserva. Verifique los detalles.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
       
        JOptionPane.showMessageDialog(this, "Error al cancelar la reserva", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException ex) {
      
        JOptionPane.showMessageDialog(this, "ID de libro o usuario inválido", "Error", JOptionPane.ERROR_MESSAGE);
    }
}










    private void cerrarSesion() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.getContentPane().removeAll();
        parentFrame.getContentPane().add(new LoginPanel(parentFrame, usuarioService));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
