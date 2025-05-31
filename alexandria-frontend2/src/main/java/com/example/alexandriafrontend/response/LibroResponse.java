package com.example.alexandriafrontend.response;

public class LibroResponse {
    private String titulo;
    private String autor;

    public LibroResponse(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }
}
