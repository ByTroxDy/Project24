package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Gasto;
import model.Sesion;
import database.GastoDB;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class GastoController {
    @FXML
    private TableView<Gasto> gastoTableView;
    @FXML
    private TableColumn<Gasto, Integer> idGastoColumn;
    @FXML
    private TableColumn<Gasto, String> tipoGastoColumn;
    @FXML
    private TableColumn<Gasto, String> conceptoColumn;
    @FXML
    private TableColumn<Gasto, String> fechaColumn;
    @FXML
    private TableColumn<Gasto, String> nifProveedorColumn;
    @FXML
    private TableColumn<Gasto, Double> ivaColumn;
    @FXML
    private TableColumn<Gasto, Double> totalIVAColumn;
    @FXML
    private TableColumn<Gasto, Double> totalGastoColumn;
    @FXML
    private TableColumn<Gasto, String> pagadoColumn;
    @FXML
    private TableColumn<Gasto, String> nifClienteColumn;
    
    private GastoDB gastoDB = new GastoDB();

    @FXML
    private void initialize() {
        // Inicializar las columnas de la tabla
        initTableColumns();
        // Cargar los datos de los gastos en la tabla
        loadGastos();
    }

    private void initTableColumns() {
        // Configurar las columnas de la tabla
        tipoGastoColumn.setCellValueFactory(cellData -> cellData.getValue().tipoGastoProperty());
        conceptoColumn.setCellValueFactory(cellData -> cellData.getValue().conceptoProperty());
        fechaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        nifProveedorColumn.setCellValueFactory(cellData -> cellData.getValue().nifProveedorProperty());
        ivaColumn.setCellValueFactory(cellData -> cellData.getValue().ivaProperty().asObject());
        totalIVAColumn.setCellValueFactory(cellData -> cellData.getValue().totalIVAProperty().asObject());
        totalGastoColumn.setCellValueFactory(cellData -> cellData.getValue().totalGastoProperty().asObject());
        pagadoColumn.setCellValueFactory(cellData -> cellData.getValue().pagadoProperty());
        nifClienteColumn.setCellValueFactory(cellData -> cellData.getValue().nifClienteProperty());
        
        // Configuración de las celdas para mostrar "null"
        tipoGastoColumn.setCellFactory(TableCellFactory.createCellFactory());
        conceptoColumn.setCellFactory(TableCellFactory.createCellFactory());
        fechaColumn.setCellFactory(TableCellFactory.createCellFactory());
        nifProveedorColumn.setCellFactory(TableCellFactory.createCellFactory());
        ivaColumn.setCellFactory(TableCellFactory.createCellFactory());
        totalIVAColumn.setCellFactory(TableCellFactory.createCellFactory());
        totalGastoColumn.setCellFactory(TableCellFactory.createCellFactory());
        pagadoColumn.setCellFactory(TableCellFactory.createCellFactory());
        nifClienteColumn.setCellFactory(TableCellFactory.createCellFactory());
    }

    private void loadGastos() {
    	GastoDB gastoDB = new GastoDB();
    	gastoTableView.getItems().addAll(gastoDB.getGastosToApartameto(Sesion.getIdApartamento()));
    }

    @FXML
    private void agregarGasto() throws SQLException {
        Dialog<Gasto> dialog = new Dialog<>();
        dialog.setTitle("Agregar Gasto");
        dialog.setHeaderText("Ingrese los detalles del nuevo gasto");

        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
		ComboBox<String> tipoGastoComboBox = new ComboBox<>();
		tipoGastoComboBox.getItems().add("Factura");
		tipoGastoComboBox.getItems().add("Interno");
		tipoGastoComboBox.setPromptText("Tipo de Gasto");
        
        TextField conceptoField = new TextField(null);
        conceptoField.setPromptText("Concepto");
        
        DatePicker fechaPicker = new DatePicker(null);
        fechaPicker.setPromptText("Fecha");
        
        TextField nifProveedorField = new TextField(null);
        nifProveedorField.setPromptText("NIF Proveedor");
        
        TextField ivaField = new TextField(null);
        ivaField.setPromptText("IVA");
        ivaField.setTextFormatter(
				new TextFormatter<>(change -> (change.getControlNewText().matches("\\d*")) ? change : null));
        
        TextField totalIVAField = new TextField(null);
        totalIVAField.setPromptText("Total IVA");
        totalIVAField.setTextFormatter(
				new TextFormatter<>(change -> (change.getControlNewText().matches("\\d*")) ? change : null));
        
        TextField totalGastoField = new TextField(null);
        totalGastoField.setPromptText("Total Gasto");
        totalGastoField.setTextFormatter(
				new TextFormatter<>(change -> (change.getControlNewText().matches("\\d*")) ? change : null));
        
        ComboBox<String> pagadoComboBox = new ComboBox<>();
		tipoGastoComboBox.getItems().add("Si");
		tipoGastoComboBox.getItems().add("No");
		tipoGastoComboBox.setPromptText("Pagado");
        
        ComboBox<String> nifClienteComboBox = new ComboBox<>();
		// Llenar el ComboBox con los NIF de clientes disponibles
		List<String> nifClienteList = gastoDB.obtenerNifClientesDisponibles();
		nifClienteComboBox.getItems().addAll(nifClienteList);
		nifClienteComboBox.setPromptText("NIF Cliente");

        grid.add(new Label("Tipo de Gasto:"), 0, 0);
        grid.add(tipoGastoComboBox, 1, 0);
        grid.add(new Label("Concepto:"), 0, 1);
        grid.add(conceptoField, 1, 1);
        grid.add(new Label("Fecha:"), 0, 2);
        grid.add(fechaPicker, 1, 2);
        grid.add(new Label("NIF Proveedor:"), 0, 3);
        grid.add(nifProveedorField, 1, 3);
        grid.add(new Label("IVA:"), 0, 4);
        grid.add(ivaField, 1, 4);
        grid.add(new Label("Total IVA:"), 0, 5);
        grid.add(totalIVAField, 1, 5);
        grid.add(new Label("Total Gasto:"), 0, 6);
        grid.add(totalGastoField, 1, 6);
        grid.add(new Label("Pagado:"), 0, 7);
        grid.add(pagadoComboBox, 1, 7);
        grid.add(new Label("NIF Cliente:"), 0, 8);
        grid.add(nifClienteComboBox, 1, 8);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
        	String tipoGasto = tipoGastoComboBox.getValue() != null ? tipoGastoComboBox.getValue() : null;
        	String concepto = conceptoField.getText() != null ? conceptoField.getText() : null;
        	String fecha = fechaPicker.getValue() != null ? fechaPicker.getValue().toString() : null;
        	String nifProveedor = nifProveedorField.getText() != null ? nifProveedorField.getText() : null;
        	double iva = ivaField.getText() != null ? Double.parseDouble(ivaField.getText()) : 0;
        	double totalIVA = totalIVAField.getText() != null ? Double.parseDouble(totalIVAField.getText()) : 0;
        	double totalGasto = totalGastoField.getText() != null ? Double.parseDouble(totalGastoField.getText()) : 0;
        	String pagado = pagadoComboBox.getValue() != null ? pagadoComboBox.getValue() : null;
        	String nifCliente = nifClienteComboBox.getValue() != null ? nifClienteComboBox.getValue() : null;
            if (dialogButton == buttonTypeOk) {
                return new Gasto(tipoGasto, concepto, fecha, nifProveedor, iva, totalIVA, totalGasto, pagado, Sesion.getIdApartamento(), nifCliente);
            }
            return null;
        });

        Optional<Gasto> result = dialog.showAndWait();
        result.ifPresent(gasto -> {
        	GastoDB gastoDB = new GastoDB();
			gastoDB.saveGasto(gasto);
        });
        
    	refreshTable();
    }
    
    @FXML
    private void modificarGasto() throws SQLException {
        Gasto selectedGasto = gastoTableView.getSelectionModel().getSelectedItem();
        if (selectedGasto == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modificar Gasto");
            alert.setHeaderText("Ningún gasto seleccionado");
            alert.setContentText("Por favor, selecciona un gasto de la tabla para modificar.");
            alert.showAndWait();
            return;
        }
        
        Dialog<Gasto> dialog = new Dialog<>();
        dialog.setTitle("Modificar Gasto");
        dialog.setHeaderText("Modifique los detalles del gasto seleccionado");

        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField idGastoField = new TextField(String.valueOf(selectedGasto.getIdGasto()));
        idGastoField.setEditable(false);
        
		ComboBox<String> tipoGastoComboBox = new ComboBox<>();
		tipoGastoComboBox.getItems().add("Factura");
		tipoGastoComboBox.getItems().add("Interno");
		tipoGastoComboBox.setPromptText("Tipo de Gasto");
		tipoGastoComboBox.setValue(selectedGasto.getTipoGasto());
		
        TextField conceptoField = new TextField(selectedGasto.getConcepto());
        
        DatePicker fechaPicker = new DatePicker();
	    fechaPicker.setValue(selectedGasto.getFecha() != null ? LocalDate.parse(selectedGasto.getFecha()) : null);
        
        TextField nifProveedorField = new TextField(selectedGasto.getNifProveedor());
        TextField ivaField = new TextField(String.valueOf(selectedGasto.getIva()));
        TextField totalIVAField = new TextField(String.valueOf(selectedGasto.getTotalIVA()));
        TextField totalGastoField = new TextField(String.valueOf(selectedGasto.getTotalGasto()));
        
        ComboBox<String> pagadoComboBox = new ComboBox<>();
        pagadoComboBox.getItems().add("factura");
        pagadoComboBox.getItems().add("interno");
		tipoGastoComboBox.setPromptText("Pagado");
        pagadoComboBox.setValue(selectedGasto.getPagado());
        
        ComboBox<String> nifClienteComboBox = new ComboBox<>();
		// Llenar el ComboBox con los NIF de clientes disponibles
		List<String> nifClienteList = gastoDB.obtenerNifClientesDisponibles();
		nifClienteComboBox.getItems().addAll(nifClienteList);
		nifClienteComboBox.setPromptText("NIF Cliente");
		nifClienteComboBox.setValue(selectedGasto.getNifCliente());

        grid.add(new Label("Tipo de Gasto:"), 0, 0);
        grid.add(tipoGastoComboBox, 1, 0);
        grid.add(new Label("Concepto:"), 0, 1);
        grid.add(conceptoField, 1, 1);
        grid.add(new Label("Fecha:"), 0, 2);
        grid.add(fechaPicker, 1, 2);
        grid.add(new Label("NIF Proveedor:"), 0, 3);
        grid.add(nifProveedorField, 1, 3);
        grid.add(new Label("IVA:"), 0, 4);
        grid.add(ivaField, 1, 4);
        grid.add(new Label("Total IVA:"), 0, 5);
        grid.add(totalIVAField, 1, 5);
        grid.add(new Label("Total Gasto:"), 0, 6);
        grid.add(totalGastoField, 1, 6);
        grid.add(new Label("Pagado:"), 0, 7);
        grid.add(pagadoComboBox, 1, 7);
        grid.add(new Label("NIF Cliente:"), 0, 8);
        grid.add(nifClienteComboBox, 1, 8);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
        	String tipoGasto = tipoGastoComboBox.getValue() != null ? tipoGastoComboBox.getValue() : null;
        	String concepto = conceptoField.getText() != null ? conceptoField.getText() : null;
        	String fecha = fechaPicker.getValue() != null ? fechaPicker.getValue().toString() : null;
        	String nifProveedor = nifProveedorField.getText() != null ? nifProveedorField.getText() : null;
        	double iva = ivaField.getText() != null ? Double.parseDouble(ivaField.getText()) : 0;
        	double totalIVA = totalIVAField.getText() != null ? Double.parseDouble(totalIVAField.getText()) : 0;
        	double totalGasto = totalGastoField.getText() != null ? Double.parseDouble(totalGastoField.getText()) : 0;
        	String pagado = pagadoComboBox.getValue() != null ? pagadoComboBox.getValue() : null;
        	String nifCliente = nifClienteComboBox.getValue() != null ? nifClienteComboBox.getValue() : null;
            if (dialogButton == buttonTypeOk) {
                return new Gasto(tipoGasto, concepto, fecha, nifProveedor, iva, totalIVA, totalGasto, pagado, selectedGasto.getIdApartamento(), nifCliente);
            }
            return null;
        });

        Optional<Gasto> result = dialog.showAndWait();
        result.ifPresent(gasto -> {
        	GastoDB gastoDB = new GastoDB();
			gastoDB.updateGasto(gasto);
        });
            
        refreshTable();
    }

    @FXML
    private void eliminarGasto() {
        int selectedIndex = gastoTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Gasto selectedGasto = gastoTableView.getItems().get(selectedIndex);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Gasto");
            alert.setHeaderText("Eliminar gasto seleccionado");
            alert.setContentText("¿Estás seguro de eliminar el gasto seleccionado?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
            	GastoDB gastoDB = new GastoDB();
				gastoDB.deleteGasto(selectedGasto);
            }
            
        	refreshTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Eliminar Gasto");
            alert.setHeaderText("Ningún gasto seleccionado");
            alert.setContentText("Por favor, selecciona un gasto de la tabla para eliminar.");
            alert.showAndWait();
        }
    }
    
    private void refreshTable() {
    	gastoTableView.getItems().clear();
        loadGastos();
    }

    @FXML
    private void cerrar() {
    	Stage stage = (Stage) gastoTableView.getScene().getWindow();
        stage.close();
    }
}
