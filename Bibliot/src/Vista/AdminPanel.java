
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

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

public class AdminPanel extends JPanel {
    private Usuario usuario;
    private UsuarioService usuarioService;
    private Connection connection;

    public AdminPanel(Usuario usuario, UsuarioService usuarioService) {
        this.usuario = usuario;
        this.usuarioService = usuarioService;
        this.connection = DatabaseConnection.getConnection();
        initialize();
    }

    private void initialize() {
        setLayout(new GridLayout(11, 1));

        JButton registrarUsuarioButton = new JButton("Registrar Usuario");
        registrarUsuarioButton.addActionListener((var e) -> registrarUsuario());
        
        JButton registrarLibroButton = new JButton("Registrar Libro Nuevo");
        registrarLibroButton.addActionListener(e -> {});

        JButton listarLibrosButton = new JButton("Listar Todos los Libros");
        listarLibrosButton.addActionListener(e -> listarLibros());

        JButton realizarPrestamoButton = new JButton("Realizar Préstamo de Libro");
        realizarPrestamoButton.addActionListener(e -> realizarPrestamo());

        JButton realizarDevolucionButton = new JButton("Realizar Devolución de Libro");
        realizarDevolucionButton.addActionListener(e -> realizarDevolucion());

        JButton sancionarUsuarioButton = new JButton("Sancionar Usuario");
        sancionarUsuarioButton.addActionListener(e -> {
            try {
                sancionarUsuario();
            } catch (SQLException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        JButton quitarSancionButton = new JButton("Quitar Sanción");
        quitarSancionButton.addActionListener(e -> quitarSancion());

        JButton realizarReservaButton = new JButton("Realizar Reserva de Libro");
        realizarReservaButton.addActionListener(e -> realizarReserva());


        JButton cancelarReservaButton = new JButton("Cancelar Reserva de Libro");
        cancelarReservaButton.addActionListener(e -> cancelarReserva());


        JButton listarLibrosPrestadosButton = new JButton("Listar Libros Prestados");
        listarLibrosPrestadosButton.addActionListener(e -> {
            // Implementa la lógica para listar libros prestados
        });

        JButton listarUsuariosSancionadosButton = new JButton("Listar Usuarios Sancionados");
        listarUsuariosSancionadosButton.addActionListener(e -> {
            // Implementa la lógica para listar usuarios sancionados
        });

        JButton cerrarSesionButton = new JButton("Cerrar Sesión");
        cerrarSesionButton.addActionListener(e -> cerrarSesion());

        add(registrarUsuarioButton);
        add(registrarLibroButton );
        add(listarLibrosButton);
        add(realizarPrestamoButton);
        add(realizarDevolucionButton);
        add(sancionarUsuarioButton);
        add(quitarSancionButton);
        add(realizarReservaButton);
        add(cancelarReservaButton);
        add(listarLibrosPrestadosButton);
        add(listarUsuariosSancionadosButton);
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

    private void sancionarUsuario() throws SQLException {
        try {
            String usuarioIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario a sancionar:");
            int usuarioId = Integer.parseInt(usuarioIdStr);

            String razon = JOptionPane.showInputDialog(this, "Ingrese la razón de la sanción:");

            String fechaInicioStr = JOptionPane.showInputDialog(this, "Ingrese la fecha de inicio de la sanción (yyyy-mm-dd):");
           

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaInicio = dateFormat.parse(fechaInicioStr);
           

            boolean exito = usuarioService.sancionarUsuario(usuarioId, razon, fechaInicio);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Sanción aplicada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo aplicar la sanción. Verifique los detalles.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Fecha inválida. Utilice el formato yyyy-mm-dd.", "Error", JOptionPane.ERROR_MESSAGE);
        }
         catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de usuario inválido", "Error", JOptionPane.ERROR_MESSAGE);
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

 private void registrarUsuario() {
        try {
            UsuarioService usuarioService = new UsuarioService(connection);

            String nombre = JOptionPane.showInputDialog(this, "Ingrese el nombre del usuario:");
            String email = JOptionPane.showInputDialog(this, "Ingrese el email del usuario:");
            String estado = JOptionPane.showInputDialog(this, "Ingrese el estado del usuario:");
            String numSancionesStr = JOptionPane.showInputDialog(this, "Ingrese el número de sanciones del usuario:");
            String contrasena = JOptionPane.showInputDialog(this, "Ingrese la contraseña del usuario:");
            String rol = JOptionPane.showInputDialog(this, "Ingrese el rol del usuario:");

    
            if (nombre == null || nombre.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                estado == null || estado.trim().isEmpty() ||
                numSancionesStr == null || numSancionesStr.trim().isEmpty() ||
                contrasena == null || contrasena.trim().isEmpty() ||
                rol == null || rol.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int numSanciones = Integer.parseInt(numSancionesStr);

            
            boolean exito = usuarioService.registrarUsuario(nombre, email, estado, numSanciones, contrasena, rol);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Usuario registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo registrar el usuario. Verifique los detalles.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
         
            JOptionPane.showMessageDialog(this, "Error al registrar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            
            JOptionPane.showMessageDialog(this, "Número de sanciones inválido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
private void quitarSancion() {
    try {
        String usuarioIdStr = JOptionPane.showInputDialog(this, "Ingrese el ID del usuario que desea quitar la sanción:");
        int usuarioId = Integer.parseInt(usuarioIdStr);
        
        // Solicitar la fecha de fin de la sanción
        String fechaStr = JOptionPane.showInputDialog(this, "Ingrese la fecha de fin de la sanción (YYYY-MM-DD):");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha_fin = sdf.parse(fechaStr);

        boolean exito = usuarioService.quitarSancion(usuarioId, fecha_fin);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Sanción eliminada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo quitar la sanción. Verifique los detalles.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error al quitar la sanción", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "ID de usuario inválido", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (ParseException ex) {
        JOptionPane.showMessageDialog(this, "Formato de fecha inválido", "Error", JOptionPane.ERROR_MESSAGE);
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
