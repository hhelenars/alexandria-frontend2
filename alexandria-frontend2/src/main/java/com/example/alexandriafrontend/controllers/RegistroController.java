package com.example.alexandriafrontend.controllers;

import com.example.alexandriafrontend.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistroController {

    @FXML
    private TextField tfPrimer_Nombre;

    @FXML
    private TextField tfApellidos;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfContraseña;

    @FXML
    private ComboBox<String> cbRol;

    @FXML
    private Button btnRegistrarse;

    @FXML
    private Button btnVolver;

    @FXML
    private void initialize() {
        // Añadimos roles al ComboBox
        cbRol.getItems().addAll("Alumno", "Profesor", "Administrador");
    }

    @FXML
    private void handleRegistrarse() {
        String nombre = tfPrimer_Nombre.getText();
        String apellidos = tfApellidos.getText();
        String email = tfEmail.getText();
        String contrasena = tfContraseña.getText();
        String rol = cbRol.getValue();

        // Validación básica
        if (nombre.isEmpty() || apellidos.isEmpty() || email.isEmpty() || contrasena.isEmpty() || rol == null) {
            mostrarAlerta("Por favor, completa todos los campos.");
            return;
        }

        // Aquí luego enviarás los datos al backend
        String resumen = "Registrado con éxito:\n\n" +
                "Nombre: " + nombre + "\n" +
                "Apellidos: " + apellidos + "\n" +
                "Email: " + email + "\n" +
                "Rol: " + rol;

        mostrarAlerta(resumen);
        Stage stage = (Stage) btnRegistrarse.getScene().getWindow();
        Utils.cambiarPantalla(stage, "/com/example/alexandriafrontend/Login.fxml", c -> {});
        System.out.println("Cargando Login.fxml");
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Registro");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void volverAlInicio() {
        Stage stage = (Stage) btnVolver.getScene().getWindow();
        Utils.cambiarPantalla(stage, "/com/example/alexandriafrontend/Menu.fxml", c -> {});
        System.out.println("Cargando Inicio.fxml");
    }
}
