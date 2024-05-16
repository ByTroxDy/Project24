package controllers;

import java.io.IOException;
import java.sql.SQLException;
import database.OperacionesDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MenuPanelController {
	
    @FXML
    private Label lblTotalIngresos;
    @FXML
    private Label lblTotalGastos;
    @FXML
    private Label lblResultado;
    @FXML
    private Label lblLiquidacionIVA;
    
    @FXML
    private void initialize() {
    	loadDataFromDatabase();
    }
	
    @FXML
    private void loadDataFromDatabase() {
    	OperacionesDB operacionesDB = new OperacionesDB();
    	
        try {
            double totalIngresos = operacionesDB.obtenerTotalIngresos();
            lblTotalIngresos.setText(String.format("%.2f", totalIngresos) + "€");

            double totalGastos = operacionesDB.obtenerTotalGastos();
            lblTotalGastos.setText(String.format("%.2f", totalGastos) + "€");

            double resultado = totalIngresos - totalGastos;
            lblResultado.setText(String.format("%.2f", resultado) + "€");

            double liquidacionIVA = operacionesDB.calcularLiquidacionIVA();
            lblLiquidacionIVA.setText(String.format("%.2f", liquidacionIVA) + "€");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void verIngresos(ActionEvent e) throws IOException {
        cargarInterfaz("/interfaz/Ingresos.fxml", "Tabla ingresos");
        cerrarVentana(e);
    }

    @FXML
    private void verGastos(ActionEvent e) throws IOException {
        cargarInterfaz("/interfaz/Gastos.fxml", "Tabla gastos");
        cerrarVentana(e);
    }

    @FXML
    private void verClientes(ActionEvent e) throws IOException {
        cargarInterfaz("/interfaz/Clientes.fxml", "Tabla clientes");
        cerrarVentana(e);
    }

    @FXML
    private void verApartamentos(ActionEvent e) throws IOException {
        cargarInterfaz("/interfaz/Apartamentos.fxml", "Tabla apartamentos");
        cerrarVentana(e);
    }
    
	@FXML
	private void cerrarSesion(ActionEvent e) throws IOException {
		cargarInterfaz("/interfaz/Login.fxml", "GEAT - Iniciar Sesión");
		cerrarVentana(e);
	}
	
    @FXML
    private void salir() {
        System.exit(0);
    }

    public void cargarInterfaz(String ruta, String titulo) throws IOException {
        // Cargar la interfaz del panel desde el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        Parent root = loader.load();

        // Crear una nueva escena
        Scene scene = new Scene(root);

        // Obtener el escenario principal y establecer la nueva escena
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(titulo);

        // Mostrar el panel
        stage.setResizable(false);
        stage.show();
    }
    
	private void cerrarVentana(ActionEvent e) {
		Node source = (Node) e.getSource(); // Me devuelve el elemento al que hice click
		Stage stage = (Stage) source.getScene().getWindow(); // Me devuelve la ventana donde se encuentra el elemento
		stage.close(); // Me cierra la ventana
	}
    
}
