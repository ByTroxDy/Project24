
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Cargar y mostrar la ventana de inicio de sesión
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaz/Login.fxml"));
			Parent root = loader.load();
			primaryStage.setScene(new Scene(root));

			// Crear el icono de la ventana
			Image icon = new Image(getClass().getResourceAsStream("/icon/geat_logo.jpg"));
			primaryStage.getIcons().add(icon);

			primaryStage.setTitle("GEAT - Iniciar Sesión");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
