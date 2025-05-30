package com.example.alexandriafrontend.controllers;

import com.example.alexandriafrontend.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class InicioController {

    @FXML
    private ListView<String> listalibros;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnCrearCuenta;

    @FXML
    private Label lblNombreUsuario;

    @FXML
    private void initialize() {
        listalibros.getItems().addAll(
                "La Celestina - Un clásico español",
                "Don Quijote - Aventuras y locura",
                "Cien años de soledad - Gabriel García Márquez",
                "1984 - Distopía totalitaria",
                "Orgullo y prejuicio - Jane Austen"
        );
    }

    public void mostrarUsuarioLogueado(String nombreUsuario) {
        lblNombreUsuario.setText("Bienvenido, " + nombreUsuario);
        lblNombreUsuario.setVisible(true);
        btnIniciarSesion.setVisible(false);
        btnCrearCuenta.setVisible(false);
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnIniciarSesion) {
            Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
            Utils.cambiarPantalla(stage, "/com/example/alexandriafrontend/Login.fxml",controller -> {});
            System.out.println("Cargando Login.fxml");
        } else if (source == btnCrearCuenta) {
            Stage stage = (Stage) btnCrearCuenta.getScene().getWindow();
            Utils.cambiarPantalla(stage, "/com/example/alexandriafrontend/Registro.fxml", controller -> {});
            System.out.println("Cargando Registro.fxml");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

