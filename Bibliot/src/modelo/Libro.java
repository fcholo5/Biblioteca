/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author USUARIO
 */



public class Libro {
    private int id;
    private String titulo;
    private String autor;
    private String editorial;
    private String categoria;
    private Estado estado;
    private int numCopias;

    // Constructor vacío
    public Libro() {}

    // Constructor con parámetros
    public Libro(int id, String titulo, String autor, String editorial, String categoria, Estado estado, int numCopias) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.categoria = categoria;
        this.estado = estado;
        this.numCopias = numCopias;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public int getNumCopias() {
        return numCopias;
    }

    public void setNumCopias(int numCopias) {
        this.numCopias = numCopias;
    }

    // Método toString
    @Override
    public String toString() {
        return "ID: " + id + ", Título: " + titulo + ", Autor: " + autor + 
               ", Editorial: " + editorial + ", Categoría: " + categoria + 
               ", Estado: " + estado + ", Copias: " + numCopias;
    }

    // Enumeración para el estado del libro
    public enum Estado {
        DISPONIBLE,
        PRESTADO
    }
}
