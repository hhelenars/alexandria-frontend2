package com.example.alexandriafrontend.controllers;

import com.example.alexandriafrontend.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

	@FXML
	private TextField tfEmail;

	@FXML
	private TextField tfContrasena;

	@FXML
	private Button btnIniciarSesion;

	@FXML
	private Button btnVolver;

	@FXML
	private void iniciarSesion() {
		String usuario = tfEmail.getText();
		String contrasena = tfContrasena.getText();

		if (usuario.equals("helena") && contrasena.equals("123")) {
			Stage stage = (Stage) btnIniciarSesion.getScene().getWindow();
			Utils.cambiarPantalla(stage, "/com/example/alexandriafrontend/Menu.fxml", (MenuController m) -> {
				m.mostrarOpcionesPrivadas();
				m.cargarInicioConUsuario("Helena");
			});

		} else {
			mostrarAlerta("Credenciales incorrectas.");
		}
	}


	private void mostrarAlerta(String mensaje) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Login");
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
