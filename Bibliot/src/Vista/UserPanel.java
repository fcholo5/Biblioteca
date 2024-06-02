/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author USUARIO
 */


import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class UserPanel extends JPanel {
    private Connection connection;

    public UserPanel(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Example tabs, add your functionalities as needed
        tabbedPane.addTab("Libros", new LibroPanel(connection));
        tabbedPane.addTab("Préstamos", new PrestamoPanel(connection));
        tabbedPane.addTab("Reservas", new ReservaPanel(connection));
        
        add(tabbedPane, BorderLayout.CENTER);
    }
}
