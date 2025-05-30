package com.example.alexandriafrontend.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.Consumer;

public class Utils {

    public static <T> void cambiarPantalla(Stage stage, String rutaFXML, Consumer<T> controladorAccion) {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource(rutaFXML));
            Parent root = loader.load();
            T controller = loader.getController(); // recupera el controller
            controladorAccion.accept(controller);  // aplica la lógica que tú decidas
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cargarPantalla(AnchorPane contenedor, String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource(rutaFXML));
            AnchorPane nuevoContenido = loader.load();

            contenedor.getChildren().clear();
            contenedor.getChildren().add(nuevoContenido);

            // Ajustar anclajes para que el contenido ocupe todo el espacio del contenedor
            AnchorPane.setTopAnchor(nuevoContenido, 0.0);
            AnchorPane.setBottomAnchor(nuevoContenido, 0.0);
            AnchorPane.setLeftAnchor(nuevoContenido, 0.0);
            AnchorPane.setRightAnchor(nuevoContenido, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
