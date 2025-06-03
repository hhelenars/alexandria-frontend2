package com.example.alexandriafrontend.controllers;

import com.example.alexandriafrontend.model.Anotacion;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;
import org.fxmisc.richtext.StyleClassedTextArea;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LectorController {

    @FXML
    private AnchorPane lectorPane;

    @FXML
    private StyleClassedTextArea textArea;

    private final List<Anotacion> anotaciones = new ArrayList<>();

    @FXML
    private void subrayarAmarillo() {
        aplicarEstiloSeleccionado("color-amarillo");
    }

    @FXML
    private void subrayarAzul() {
        aplicarEstiloSeleccionado("color-azul");
    }

    @FXML
    private void subrayarVerde() {
        aplicarEstiloSeleccionado("color-verde");
    }

    @FXML
    private void quitarSubrayado() {
        aplicarEstiloSeleccionado(""); // Sin clase = estilo por defecto
    }

    @FXML
    private void initialize() {
        textArea.getStylesheets().add(getClass().getResource("/styles/lector.css").toExternalForm());
        configurarTooltipComentarios();
    }

    private String leerYProcesarLibro(String urlFirmada) throws Exception {
        InputStream inputStream = new URL(urlFirmada).openStream(); // Descargar el epud
        Book libro = new EpubReader().readEpub(inputStream); // Convertirlo en objeto libro

        StringBuilder textoPlano = new StringBuilder();
        for (Resource recurso : libro.getContents()) {
            byte[] data = recurso.getData();
            if (data != null && data.length > 0) {
                textoPlano.append(new String(data, StandardCharsets.UTF_8)).append("\n\n"); // Convertirlo en texto
            }
        }

        return textoPlano.toString().replaceAll("<[^>]*>", ""); // quitar etiquetas HTML
    }

    private void mostrarTextoEnArea(String texto) {
        textArea.clear();
        textArea.replaceText(texto);
    }

    private void mostrarErrorCarga() {
        textArea.clear();
        textArea.replaceText("Error al cargar el libro.");
    }

    public void cargarLibroDesdeURL(String urlFirmada) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String textoPlano = leerYProcesarLibro(urlFirmada);
                Platform.runLater(() -> mostrarTextoEnArea(textoPlano));
                return null;
            }
            @Override
            protected void failed() {
                Platform.runLater(() -> mostrarErrorCarga());
                getException().printStackTrace();
            }
        };
        new Thread(task).start();
    }

    private void aplicarEstiloSeleccionado(String colorClaseCss) {
        int start = textArea.getSelection().getStart();
        int end = textArea.getSelection().getEnd();

        for (int i = start; i < end; i++) {
            var estilosActuales = textArea.getStyleOfChar(i);
            List<String> nuevosEstilos = new ArrayList<>(estilosActuales);

            // Quitar cualquier subrayado previo
            nuevosEstilos.removeIf(estilo -> estilo.startsWith("color-"));

            // Añadir nuevo color
            if (!colorClaseCss.isEmpty()) {
                nuevosEstilos.add(colorClaseCss);
            }

            textArea.setStyle(i, i + 1, nuevosEstilos);
        }
    }


    @FXML
    private void agregarComentario() {
        int start = textArea.getSelection().getStart();
        int end = textArea.getSelection().getEnd();

        if (start == end) {
            System.out.println("No hay texto seleccionado.");
            return;
        }

        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Nuevo comentario");
        dialogo.setHeaderText("Introduce el comentario asociado al subrayado");
        dialogo.setContentText("Comentario:");

        Optional<String> resultado = dialogo.showAndWait();

        resultado.ifPresent(comentario -> {
            for (int i = start; i < end; i++) {
                List<String> estilos = new ArrayList<>(textArea.getStyleOfChar(i));

                if (!estilos.contains("comentado")) {
                    estilos.add("comentado");  // línea decorativa
                }

                textArea.setStyle(i, i + 1, estilos);
            }

            // Guarda la anotación para la base de datos
            Anotacion nueva = new Anotacion(start, end, "comentado", comentario);
            anotaciones.add(nueva);
        });

    }


    private void configurarTooltipComentarios() {
        Tooltip tooltip = new Tooltip();
        tooltip.setWrapText(true);
        tooltip.setMaxWidth(300);

        textArea.setOnMouseMoved(event -> {
            int pos = textArea.hit(event.getX(), event.getY()).getInsertionIndex();

            for (Anotacion a : anotaciones) {
                if (a.getComentario() != null && pos >= a.getStart() && pos <= a.getEnd()) {
                    if (!tooltip.isShowing()) {
                        tooltip.setText(a.getComentario());
                        tooltip.show(textArea, event.getScreenX() + 10, event.getScreenY() + 10);
                    }
                    return;
                }
            }

            if (tooltip.isShowing()) {
                tooltip.hide();
            }
        });

        // Oculta tooltip si el ratón sale del área
        textArea.setOnMouseExited(event -> {
            if (tooltip.isShowing()) {
                tooltip.hide();
            }
        });
    }
}