package com.example.alexandriafrontend.controllers;

import com.example.alexandriafrontend.api.ApiClient;
import com.example.alexandriafrontend.api.ApiService;
import com.example.alexandriafrontend.model.Usuario;
import com.example.alexandriafrontend.response.LibroResponse;
import com.example.alexandriafrontend.response.LoginResponse;
import com.example.alexandriafrontend.session.SesionUsuario;
import com.example.alexandriafrontend.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class InicioController {

    @FXML
    private ListView<String> listalibros;

    @FXML
    private Button btnIniciarSesion;

    @FXML
    private Button btnCrearCuenta;

    @FXML
    private Label lblNombreUsuario;

    private ApiService apiService = ApiClient.getApiService();


    @FXML
    private void initialize() {

        Call<List<LibroResponse>> call = apiService.obtenerTodosLibros();

        call.enqueue(new Callback<List<LibroResponse>>() {
            @Override
            public void onResponse(Call<List<LibroResponse>> call, Response<List<LibroResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (LibroResponse libro : response.body()) {
                        String item = libro.getTitulo() + " - " + libro.getAutor();
                        javafx.application.Platform.runLater(() -> listalibros.getItems().add(item));
                    }
                } else {
                    System.out.println("Credenciales inválidas. Inténtalo de nuevo.");
                }
            }

            @Override
            public void onFailure(Call<List<LibroResponse>> call, Throwable t) {
                System.out.println("Error de conexión con el servidor");
                t.printStackTrace();
            }
        });
    }

    public void mostrarUsuarioLogueado(Usuario usuario) {
        lblNombreUsuario.setText(usuario.getNombre()+" "+usuario.getApellido() );
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
}

