package controllers;

import java.io.IOException;
import java.util.Optional;

import database.ClienteDB;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Cliente;

public class ClienteController {
    @FXML
    private TableView<Cliente> clienteTableView;
    @FXML
    private TableColumn<Cliente, String> nifClienteColumn;
    @FXML
    private TableColumn<Cliente, String> nombreCompletoColumn;
    @FXML
    private TableColumn<Cliente, String> telefonoColumn;

    @FXML
    private TextField nombreCompletoField;
    @FXML
    private TextField telefonoField;

    @FXML
    private void initialize() {
        // Inicializar las columnas de la tabla
        initTableColumns();
        // Cargar los datos de los clientes en la tabla
        loadClientes();
    }

    private void initTableColumns() {
    	nifClienteColumn.setCellValueFactory(cellData -> cellData.getValue().nifClienteProperty());
    	nombreCompletoColumn.setCellValueFactory(cellData -> cellData.getValue().nombreCompletoProperty());
        telefonoColumn.setCellValueFactory(cellData -> cellData.getValue().telefonoProperty());
    }

    private void loadClientes() {
    	ClienteDB clienteDB = new ClienteDB();
    	clienteTableView.getItems().addAll(clienteDB.getAllClientes());
    }

    @FXML
    private void agregarCliente() {
        // Mostrar un diálogo para que el empleado ingrese los datos del nuevo cliente
        Dialog<Cliente> dialogCliente = new Dialog<>();
        dialogCliente.setTitle("Agregar Cliente");
        dialogCliente.setHeaderText("Ingresa los datos del nuevo cliente");

        // Setear los botones de OK y Cancelar en el diálogo
        ButtonType buttonTypeOkCliente = new ButtonType("Aceptar", ButtonData.OK_DONE);
        dialogCliente.getDialogPane().getButtonTypes().addAll(buttonTypeOkCliente, ButtonType.CANCEL);

        // Crear campos de texto para que el empleado ingrese los datos del nuevo cliente
        GridPane gridCliente = new GridPane();
        gridCliente.setHgap(10);
        gridCliente.setVgap(10);
        gridCliente.setPadding(new Insets(20, 150, 10, 10));

        TextField nifField = new TextField();
        nifField.setPromptText("NIF");
        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");
        TextField telefonoField = new TextField();
        telefonoField.setPromptText("Teléfono");

        gridCliente.add(new Label("NIF:"), 0, 0);
        gridCliente.add(nifField, 1, 0);
        gridCliente.add(new Label("Nombre:"), 0, 1);
        gridCliente.add(nombreField, 1, 1);
        gridCliente.add(new Label("Teléfono:"), 0, 2);
        gridCliente.add(telefonoField, 1, 2);

        dialogCliente.getDialogPane().setContent(gridCliente);

        // Convertir el resultado del diálogo a un objeto Cliente cuando se presiona OK
        dialogCliente.setResultConverter(dialogButtonCliente -> {
            if (dialogButtonCliente == buttonTypeOkCliente) {
                return new Cliente(nifField.getText(), nombreField.getText(), telefonoField.getText());
            }
            return null;
        });

        // Mostrar el diálogo y procesar el resultado cuando se presiona OK
        Optional<Cliente> resultCliente = dialogCliente.showAndWait();
        resultCliente.ifPresent(cliente -> {
        	ClienteDB clienteDB = new ClienteDB();
			clienteDB.saveCliente(cliente.getNifCliente(), cliente.getNombreCompleto(), cliente.getTelefono());
        });
        
        refreshTable();
    }

    @FXML
    private void modificarCliente() {
        // Mostrar un diálogo para que el empleado seleccione el cliente a modificar
        Cliente selectedCliente = clienteTableView.getSelectionModel().getSelectedItem();
        if (selectedCliente == null) {
            // Si no se selecciona ningún cliente, mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Selecciona un cliente antes de modificar.");
            alert.showAndWait();
            return;
        }

        // Mostrar un diálogo similar al de agregarCliente pero con los datos del cliente seleccionado prellenados
        Dialog<Cliente> dialog = new Dialog<>();
        dialog.setTitle("Modificar Cliente");
        dialog.setHeaderText("Modifica los datos del cliente seleccionado");

        // Setear los botones de OK y Cancelar en el diálogo
        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        // Crear campos de texto prellenados con los datos del cliente seleccionado
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nifField = new TextField(selectedCliente.getNifCliente());
        TextField nombreField = new TextField(selectedCliente.getNombreCompleto());
        TextField telefonoField = new TextField(selectedCliente.getTelefono());

        grid.add(new Label("NIF:"), 0, 0);
        grid.add(nifField, 1, 0);
        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(nombreField, 1, 1);
        grid.add(new Label("Teléfono:"), 0, 2);
        grid.add(telefonoField, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Convertir el resultado del diálogo a un objeto Cliente cuando se presiona OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                return new Cliente(nifField.getText(), nombreField.getText(), telefonoField.getText());
            }
            return null;
        });

        // Mostrar el diálogo y procesar el resultado cuando se presiona OK
        Optional<Cliente> result = dialog.showAndWait();
        result.ifPresent(cliente -> {
        	ClienteDB clienteDB = new ClienteDB();
			clienteDB.updateCliente(selectedCliente.getNifCliente(), cliente.getNombreCompleto(), cliente.getTelefono());
        });
        
        refreshTable();
    }

    @FXML
    private void eliminarCliente() {
        // Mostrar un diálogo para que el empleado seleccione el cliente a eliminar
        Cliente selectedCliente = clienteTableView.getSelectionModel().getSelectedItem();
        if (selectedCliente == null) {
            // Si no se selecciona ningún cliente, mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Selecciona un cliente antes de eliminar.");
            alert.showAndWait();
            return;
        }

        // Mostrar un diálogo de confirmación para confirmar la eliminación del cliente seleccionado
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar este cliente?");
        alert.setContentText("NIF: " + selectedCliente.getNifCliente() + "\nNombre: " + selectedCliente.getNombreCompleto());

        // Obtener el resultado del diálogo de confirmación y eliminar el cliente si se confirma
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        	ClienteDB clienteDB = new ClienteDB();
			clienteDB.deleteCliente(selectedCliente.getNifCliente());
        }
        
        refreshTable();
    }
    
    private void refreshTable() {
        clienteTableView.getItems().clear();
        loadClientes();
    }
    
    @FXML
    private void cerrar() throws IOException {
    	MenuPanelController menuPanel = new MenuPanelController();
    	menuPanel.cargarInterfaz("/interfaz/MenuPanel.fxml", "Panel de Gestor");
    	
    	// Cerrar la ventana actual
        Stage stage = (Stage) clienteTableView.getScene().getWindow();
        stage.close();
    }
}
