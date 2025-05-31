package com.example.alexandriafrontend.controllers;

import com.example.alexandriafrontend.model.Usuario;
import com.example.alexandriafrontend.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;

import javax.swing.undo.UndoableEditSupport;
import java.io.IOException;

public class MenuController {

    @FXML private AnchorPane contentPane;

    @FXML private Button btnHome;
    @FXML private Button btnBuscar;
    @FXML private Button btnFavoritos;
    @FXML private Button btnBiblioteca;
    @FXML private Button btnLecturasCompartidas;
    @FXML private Button btnCerrarSesion;

    @FXML
    private void initialize() {
        cargarContenido("/com/example/alexandriafrontend/Inicio.fxml");
        ocultarOpcionesPrivadas();
    }

    public void mostrarOpcionesPrivadas() {
        btnFavoritos.setVisible(true);
        btnFavoritos.setManaged(true);
        btnBiblioteca.setVisible(true);
        btnBiblioteca.setManaged(true);
        btnLecturasCompartidas.setVisible(true);
        btnLecturasCompartidas.setManaged(true);
        btnCerrarSesion.setVisible(true);
        btnCerrarSesion.setManaged(true);
    }

    public void ocultarOpcionesPrivadas() {
        btnFavoritos.setVisible(false);
        btnFavoritos.setManaged(false);
        btnBiblioteca.setVisible(false);
        btnBiblioteca.setManaged(false);
        btnLecturasCompartidas.setVisible(false);
        btnLecturasCompartidas.setManaged(false);
        btnCerrarSesion.setVisible(false);
        btnCerrarSesion.setManaged(false);
    }

    @FXML
    private void handleMenuAction(ActionEvent event) {
        Object source = event.getSource();

        if (source == btnHome) {
            cargarContenido("/com/example/alexandriafrontend/Inicio.fxml");
        } else if (source == btnBuscar) {
            cargarContenido("/com/example/alexandriafrontend/Buscar.fxml");
        } else if (source == btnFavoritos) {
            cargarContenido("/com/example/alexandriafrontend/Favoritos.fxml");
        } else if (source == btnBiblioteca) {
            cargarContenido("/com/example/alexandriafrontend/MiBiblioteca.fxml");
        } else if (source == btnLecturasCompartidas) {
            cargarContenido("/com/example/alexandriafrontend/LecturasCompartidas.fxml");
        } else if (source == btnCerrarSesion) {
            // Aquí podrías gestionar cerrar sesión y volver al login
            System.out.println("Cerrar sesión pulsado");
        }
    }

    public void cargarInicioConUsuario(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/alexandriafrontend/Inicio.fxml"));
            AnchorPane inicioContent = loader.load();

            InicioController inicioController = loader.getController();
            inicioController.mostrarUsuarioLogueado(usuario);

            contentPane.getChildren().clear();
            contentPane.getChildren().add(inicioContent);

            AnchorPane.setTopAnchor(inicioContent, 0.0);
            AnchorPane.setBottomAnchor(inicioContent, 0.0);
            AnchorPane.setLeftAnchor(inicioContent, 0.0);
            AnchorPane.setRightAnchor(inicioContent, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarContenido(String rutaFXML) {
        Utils.cargarPantalla(contentPane, rutaFXML);
    }
}
