package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import database.ConexionDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Sesion;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Agregar un evento de clic al botón de inicio de sesión
        loginButton.setOnAction(event -> login());
        
        // Agregar un evento de presionar Enter al campo de contraseña
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                login();
            }
        });
    }

    private void login() {
//        String username = usernameField.getText();
//        String password = passwordField.getText();
        String username = "1";
        String password = "1";

        // Consulta SQL para buscar el usuario en la base de datos
        String query = "SELECT COUNT(*) FROM usuarios WHERE usuario = ? AND password = ?";
        
        try (PreparedStatement pstmt = ConexionDB.getConnection().prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
            	Sesion.setUsuario(username);
            	try {
            		MenuPanelController menuPanel = new MenuPanelController();
                	menuPanel.cargarInterfaz("/interfaz/Apartamentos.fxml", "GEAT - MENU PANEL");
	                
	                // Cerrar la ventana actual (ventana de inicio de sesión)
	                Stage stage = (Stage) loginButton.getScene().getWindow();
	                stage.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            } else {
                // Mostrar mensaje de error si las credenciales son inválidas
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error de inicio de sesión");
                alert.setHeaderText(null);
                alert.setContentText("Credenciales inválidas. Por favor, inténtalo de nuevo.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void salir() {
        System.exit(0);
    }
}
