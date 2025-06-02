package com.example.alexandriafrontend.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
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
            System.out.println("🔗 Cargando EPUB desde: " + urlFirmada);

            InputStream inputStream = new URL(urlFirmada).openStream();
            Book libro = new EpubReader().readEpub(inputStream);

            StringBuilder textoPlano = new StringBuilder();
            for (Resource recurso : libro.getContents()) {
                textoPlano.append(new String(recurso.getData(), StandardCharsets.UTF_8)).append("\n\n");
            }

            // Eliminar etiquetas HTML básicas
            String textoFinal = textoPlano.toString().replaceAll("<[^>]*>", "");

            // Preparar el TextFlow
            textFlow.getChildren().clear();
            textFlow.setId("textFlow"); // importante para aplicar el estilo por ID
            textFlow.getStylesheets().clear();
            textFlow.getStylesheets().add(getClass().getResource("/styles/lector.css").toExternalForm());

            // Crear párrafos interactivos
            String[] parrafos = textoFinal.split("\\n\\n");
            for (String p : parrafos) {
                Label parrafo = new Label(p.trim());
                parrafo.setWrapText(true);
                parrafo.getStyleClass().add("parrafo");

                // ✅ Subrayado al hacer clic
                parrafo.setOnMouseClicked(e -> {
                    if (parrafo.getStyleClass().contains("subrayado")) {
                        parrafo.getStyleClass().remove("subrayado");
                    } else {
                        parrafo.getStyleClass().add("subrayado");
                    }
                });

                textFlow.getChildren().add(parrafo);
            }

        } catch (Exception e) {
            e.printStackTrace();
            textFlow.getChildren().clear();
            textFlow.setId("textFlow");
            textFlow.setStyle("-fx-background-color: #fff0f0; -fx-padding: 30px;");
            textFlow.getChildren().add(new Text("Error al cargar el libro."));
        }
    }

}

