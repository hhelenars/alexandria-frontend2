package com.example.alexandriafrontend.controllers;

import com.example.alexandriafrontend.api.ApiClient;
import com.example.alexandriafrontend.api.ApiService;
import com.example.alexandriafrontend.response.LibroResponse;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class BuscarController {

    @FXML
    private ListView<String> listalibros;

    @FXML
    private TextField txtBuscar;

    private ApiService apiService = ApiClient.getApiService();

    @FXML
    private void initialize() {

        cargarLibros();

        // Añadir listener para buscar en tiempo real
        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarLibros(newValue);
        });
    }

    private void cargarLibros() {
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

    private void filtrarLibros(String filtro) {
        listalibros.getItems().clear();

        Call<List<LibroResponse>> call = apiService.buscarLibros(filtro);
        call.enqueue(new Callback<List<LibroResponse>>() {
            @Override
            public void onResponse(Call<List<LibroResponse>> call, Response<List<LibroResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<String> items = new ArrayList<>();
                    for (LibroResponse libro : response.body()) {
                        String item = libro.getTitulo() + " - " + libro.getAutor();
                        items.add(item);
                    }
                    Platform.runLater(() -> {
                        listalibros.getItems().clear();
                        listalibros.getItems().addAll(items);
                    });
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
}
