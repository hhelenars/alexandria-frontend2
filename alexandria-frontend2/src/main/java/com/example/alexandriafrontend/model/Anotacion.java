package com.example.alexandriafrontend.model;

public class Anotacion {

    private int start;
    private int end;
    private String color;       // por ejemplo: "subrayado-amarillo", "subrayado-comentario", etc.
    private String comentario;  // puede ser null si solo es subrayado

    // Constructor para subrayado sin comentario
    public Anotacion(int start, int end, String color) {
        this.start = start;
        this.end = end;
        this.color = color;
        this.comentario = null;
    }

    // Constructor para subrayado con comentario
    public Anotacion(int start, int end, String color, String comentario) {
        this.start = start;
        this.end = end;
        this.color = color;
        this.comentario = comentario;
    }

    // Getters y setters
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
