package com.example.alexandriafrontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class MiBibliotecaController {
    @FXML
    private ListView<String> listaBiblioteca;

    @FXML
    public void initialize() {
        // Lista de libros de ejemplo para mostrar en la biblioteca
        ObservableList<String> libros = FXCollections.observableArrayList(
                "La sombra del viento",
                "El nombre de la rosa",
                "Crónica de una muerte anunciada",
                "Rayuela",
                "La casa de los espíritus"
        );


    }
}
