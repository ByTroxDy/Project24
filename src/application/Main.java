package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			// Cargar y mostrar la ventana de inicio de sesión
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaz/Login.fxml"));
			Parent root = loader.load();
			primaryStage.setScene(new Scene(root));

//			Scene scene = new Scene(root);

			// Cargar el archivo CSS y aplicarlo a la escena
//			scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
//			primaryStage.setScene(scene);

			// Crear el icono de la ventana
//			Image icon = new Image(getClass().getResourceAsStream("/icon/mbd_logo.png"));
//			primaryStage.getIcons().add(icon);

			primaryStage.setTitle("GEAT - Iniciar Sesión");
			primaryStage.setResizable(false);
			primaryStage.show();
			
//			// Cargar la interfaz del panel de cliente desde el archivo FXML
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaz/MenuPanel.fxml"));
//            Parent root = loader.load();
//			
//            // Crear una nueva escena
//            Scene scene = new Scene(root);
//            
//            // Obtener el escenario principal y establecer la nueva escena
//            Stage clientStage = new Stage();
//            clientStage.setScene(scene);
//            clientStage.setTitle("Panel de Gestor");
//            
//            // Mostrar el panel de cliente
//            clientStage.setResizable(false);
//            clientStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
