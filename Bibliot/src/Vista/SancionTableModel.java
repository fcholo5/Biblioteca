/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;


/**
 *
 * @author USUARIO
 */

import modelo.Sancion;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SancionTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID Usuario", "Razón", "Fecha Inicio", "Fecha Fin"};
    private List<Sancion> sanciones;

    public SancionTableModel(List<Sancion> sanciones) {
        this.sanciones = sanciones;
    }

    @Override
    public int getRowCount() {
        return sanciones.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Sancion sancion = sanciones.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return sancion.getIdUsuario();
            case 1:
                return sancion.getRazon();
            case 2:
                return sancion.getFechaInicio();
            case 3:
                return sancion.getFechaFin();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}

