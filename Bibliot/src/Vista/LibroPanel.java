/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author USUARIO
 */



import modelo.Libro;
import servicio.LibroService;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibroPanel extends JPanel {
    private LibroService libroService;

    public LibroPanel(Connection connection) {
        this.libroService = new LibroService(connection);

        setLayout(new BorderLayout());

        JButton mostrarLibrosDisponiblesButton = new JButton("Mostrar Libros Disponibles");
        mostrarLibrosDisponiblesButton.addActionListener(e -> {
            try {
                mostrarLibrosDisponibles();
            } catch (SQLException ex) {
                Logger.getLogger(LibroPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        add(mostrarLibrosDisponiblesButton, BorderLayout.NORTH);
    }

    private void mostrarLibrosDisponibles() throws SQLException {
        List<Libro> libros = libroService.obtenerLibrosDisponibles();
        String[] columnNames = {"ID", "Título", "Autor", "Editorial", "Categoría", "Estado", "Número de Copias"};
        Object[][] data = new Object[libros.size()][7]; // Ajustado para 7 columnas
        for (int i = 0; i < libros.size(); i++) {
            Libro libro = libros.get(i); // Obtener el objeto Libro actual
            data[i][0] = libro.getId();
            data[i][1] = libro.getTitulo();
            data[i][2] = libro.getAutor();
            data[i][3] = libro.getEditorial();
            data[i][4] = libro.getCategoria();
            data[i][5] = libro.getEstado(); // Asegúrate de que el estado es una cadena
            data[i][6] = libro.getNumCopias(); // Cambiado a getNumCopias()
        }
        JTable table = new JTable(data, columnNames);
        JOptionPane.showMessageDialog(this, new JScrollPane(table), "Libros Disponibles", JOptionPane.INFORMATION_MESSAGE);
    
}

}
