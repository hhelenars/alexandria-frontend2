package com.example.alexandriafrontend.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Popup;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class LectorController {

    @FXML
    private TextFlow textFlow;

    public void cargarLibroDesdeURL(String urlFirmada) {
        try {
            InputStream inputStream = new URL(urlFirmada).openStream();
            Book libro = new EpubReader().readEpub(inputStream);

            StringBuilder textoPlano = new StringBuilder();
            for (Resource recurso : libro.getContents()) {
                textoPlano.append(new String(recurso.getData(), StandardCharsets.UTF_8)).append("\n\n");
            }

            String textoFinal = textoPlano.toString().replaceAll("<[^>]*>", "");

            textFlow.getChildren().clear();
            textFlow.setId("textFlow");
            textFlow.getStylesheets().clear();
            textFlow.getStylesheets().add(getClass().getResource("/styles/lector.css").toExternalForm());

            // Crear popup de botones
            HBox barraFlotante = new HBox(10);
            barraFlotante.getStyleClass().add("barra-subrayado");

            Button btnAmarillo = new Button("🟡");
            Button btnAzul = new Button("🔵");
            Button btnVerde = new Button("🟢");
            Button btnQuitar = new Button("❌");

            barraFlotante.getChildren().addAll(btnAmarillo, btnAzul, btnVerde, btnQuitar);
            barraFlotante.setVisible(false);
            textFlow.getChildren().add(barraFlotante);

            final Label[] parrafoSeleccionado = {null};

            String[] parrafos = textoFinal.split("\\n\\n");
            for (String p : parrafos) {
                Label parrafo = new Label(p.trim());
                parrafo.setWrapText(true);
                parrafo.getStyleClass().add("parrafo");

                parrafo.setOnMouseClicked(e -> {
                    parrafoSeleccionado[0] = parrafo;
                    barraFlotante.setVisible(true);
                    barraFlotante.setLayoutX(e.getX());
                    barraFlotante.setLayoutY(parrafo.getLayoutY() + parrafo.getHeight());
                });

                textFlow.getChildren().add(parrafo);
            }

            btnAmarillo.setOnAction(a -> aplicarSubrayado(parrafoSeleccionado[0], "subrayado-amarillo"));
            btnAzul.setOnAction(a -> aplicarSubrayado(parrafoSeleccionado[0], "subrayado-azul"));
            btnVerde.setOnAction(a -> aplicarSubrayado(parrafoSeleccionado[0], "subrayado-verde"));
            btnQuitar.setOnAction(a -> quitarSubrayado(parrafoSeleccionado[0]));

        } catch (Exception e) {
            e.printStackTrace();
            textFlow.getChildren().clear();
            textFlow.setId("textFlow");
            textFlow.setStyle("-fx-background-color: #fff0f0; -fx-padding: 30px;");
            textFlow.getChildren().add(new Text("Error al cargar el libro."));
        }
    }




    private void aplicarSubrayado(Label parrafo, String claseColor) {
        quitarSubrayado(parrafo);
        parrafo.getStyleClass().add(claseColor);
    }

    private void quitarSubrayado(Label parrafo) {
        parrafo.getStyleClass().removeAll("subrayado-amarillo", "subrayado-azul", "subrayado-verde");
    }

}

