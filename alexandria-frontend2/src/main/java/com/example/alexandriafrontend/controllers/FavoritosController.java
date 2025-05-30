package com.example.alexandriafrontend.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class FavoritosController {

    @FXML
    private ListView<String> listaFavoritos;

    @FXML
    private void initialize() {
        // Lista simulada de libros favoritos para pruebas
        ObservableList<String> favoritos = FXCollections.observableArrayList(
                "El Principito",
                "Cien años de soledad",
                "1984",
                "Don Quijote"
        );

        listaFavoritos.setItems(favoritos);
    }
}
