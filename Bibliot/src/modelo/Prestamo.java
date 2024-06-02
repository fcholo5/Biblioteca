/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USUARIO
 */




import java.util.Date;

public class Prestamo {
    private int idUsuario;
    private int idLibro;
    private Date fechaPrestamo;
    private Date fechaAcordada;

    public Prestamo(int idUsuario, int idLibro, Date fechaPrestamo, Date fechaAcordada) {
        this.idUsuario = idUsuario;
        this.idLibro = idLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaAcordada = fechaAcordada;
    }

    public Prestamo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaAcordada() {
        return fechaAcordada;
    }

    public void setFechaAcordada(Date fechaAcordada) {
        this.fechaAcordada = fechaAcordada;
    }
}
