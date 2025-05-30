package com.example.alexandriafrontend.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class BuscarController {

    @FXML
    private ListView<String> listalibros;

    @FXML
    private TextField txtBuscar;

    private ObservableList<String> libros = FXCollections.observableArrayList(
            "La Celestina",
            "Don Quijote",
            "Cien años de soledad",
            "1984",
            "Orgullo y prejuicio",
            "El Principito",
            "El Señor de los Anillos"
    );

    @FXML
    private void initialize() {
        listalibros.setItems(libros);

        // Añadir listener para buscar en tiempo real
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarLibros(newValue);
        });
    }

    private void filtrarLibros(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            listalibros.setItems(libros);
            return;
        }

        String filtroMinusculas = filtro.toLowerCase();

        ObservableList<String> filtrados = libros.filtered(libro ->
                libro.toLowerCase().contains(filtroMinusculas)
        );

        listalibros.setItems(filtrados);
    }
}
