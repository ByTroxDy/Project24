package controllers;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

import database.IngresoDB;
import model.Ingreso;
import model.Sesion;

public class IngresoController {
	@FXML
	private TableView<Ingreso> ingresoTableView;
	@FXML
	private TableColumn<Ingreso, Integer> idIngresoColumn;
	@FXML
	private TableColumn<Ingreso, String> tipoFacturaColumn;
	@FXML
	private TableColumn<Ingreso, String> fechaEntradaColumn;
	@FXML
	private TableColumn<Ingreso, String> fechaSalidaColumn;
	@FXML
	private TableColumn<Ingreso, Integer> numeroNochesColumn;
	@FXML
	private TableColumn<Ingreso, Integer> numeroPersonasColumn;
	@FXML
	private TableColumn<Ingreso, Integer> idTarifaColumn;
	@FXML
	private TableColumn<Ingreso, Double> descuentoColumn;
	@FXML
	private TableColumn<Ingreso, Double> totalIVAColumn;
	@FXML
	private TableColumn<Ingreso, Double> totalFacturaColumn;
	@FXML
	private TableColumn<Ingreso, String> observacionesColumn;
	@FXML
	private TableColumn<Ingreso, String> nifClienteColumn;
	@FXML
	private TableColumn<Ingreso, Integer> idIntermediario;

	private IngresoDB ingresoDB = new IngresoDB();

	@FXML
	private void initialize() {
		// Inicializar las columnas de la tabla
		initTableColumns();
		// Cargar los datos de los ingresos en la tabla
		loadIngresos();
	}

	private void initTableColumns() {
		// Configurar las columnas de la tabla
		tipoFacturaColumn.setCellValueFactory(cellData -> cellData.getValue().tipoFacturaProperty());
		fechaEntradaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaEntradaProperty());
		fechaSalidaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaSalidaProperty());
		numeroNochesColumn.setCellValueFactory(cellData -> cellData.getValue().numeroNochesProperty().asObject());
		numeroPersonasColumn.setCellValueFactory(cellData -> cellData.getValue().numeroPersonasProperty().asObject());
		idTarifaColumn.setCellValueFactory(cellData -> cellData.getValue().idTarifaProperty().asObject());
		descuentoColumn.setCellValueFactory(cellData -> cellData.getValue().descuentoProperty().asObject());
		totalIVAColumn.setCellValueFactory(cellData -> cellData.getValue().totalIVAProperty().asObject());
		totalFacturaColumn.setCellValueFactory(cellData -> cellData.getValue().totalFacturaProperty().asObject());
		observacionesColumn.setCellValueFactory(cellData -> cellData.getValue().observacionesProperty());
		nifClienteColumn.setCellValueFactory(cellData -> cellData.getValue().nifClienteProperty());
		idIntermediario.setCellValueFactory(cellData -> cellData.getValue().idIntermediarioProperty().asObject());

		// Configuración de las celdas para mostrar "null"
		tipoFacturaColumn.setCellFactory(TableCellFactory.createCellFactory());
		fechaEntradaColumn.setCellFactory(TableCellFactory.createCellFactory());
		fechaSalidaColumn.setCellFactory(TableCellFactory.createCellFactory());
		numeroNochesColumn.setCellFactory(TableCellFactory.createCellFactory());
		numeroPersonasColumn.setCellFactory(TableCellFactory.createCellFactory());
		idTarifaColumn.setCellFactory(TableCellFactory.createCellFactory());
		descuentoColumn.setCellFactory(TableCellFactory.createCellFactory());
		totalIVAColumn.setCellFactory(TableCellFactory.createCellFactory());
		totalFacturaColumn.setCellFactory(TableCellFactory.createCellFactory());
		observacionesColumn.setCellFactory(TableCellFactory.createCellFactory());
		nifClienteColumn.setCellFactory(TableCellFactory.createCellFactory());
		idIntermediario.setCellFactory(TableCellFactory.createCellFactory());
	}

	private void loadIngresos() {
		IngresoDB ingresoDB = new IngresoDB();
		ingresoTableView.getItems().addAll(ingresoDB.getIngresosToApartamento(Sesion.getIdApartamento()));
	}

	@FXML
	private void agregarIngreso() throws SQLException {
		// Crear un diálogo para añadir un nuevo ingreso
		Dialog<Ingreso> dialog = new Dialog<>();
		dialog.setTitle("Agregar Ingreso");
		dialog.setHeaderText("Ingrese los detalles del nuevo ingreso");

		ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

		// Crear un formulario para ingresar detalles
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		ComboBox<String> tipoFacturaComboBox = new ComboBox<>();
		tipoFacturaComboBox.getItems().add("Propia");
		tipoFacturaComboBox.getItems().add("Intermediario");
		tipoFacturaComboBox.setPromptText("Tipo de Factura");

		DatePicker fechaEntradaPicker = new DatePicker(null);
		fechaEntradaPicker.setPromptText("Fecha de Entrada");

		DatePicker fechaSalidaPicker = new DatePicker(null);
		fechaSalidaPicker.setPromptText("Fecha de Salida");

		TextField numeroNochesField = new TextField(null);
		numeroNochesField.setPromptText("Número de Noches");
		numeroNochesField.setTextFormatter(
				new TextFormatter<>(change -> (change.getControlNewText().matches("\\d*")) ? change : null));

		TextField numeroPersonasField = new TextField(null);
		numeroPersonasField.setPromptText("Número de Personas");
		numeroPersonasField.setTextFormatter(
				new TextFormatter<>(change -> (change.getControlNewText().matches("\\d*")) ? change : null));

		ComboBox<Integer> idTarifaComboBox = new ComboBox<>();
		// Llenar el ComboBox con las ID de tarifa disponibles
		List<Integer> idTarifaList = ingresoDB.obtenerIdTarifasDisponibles();
		idTarifaComboBox.getItems().addAll(idTarifaList);
		idTarifaComboBox.setPromptText("ID Tarifa");

		TextField descuentoField = new TextField(null);
		descuentoField.setPromptText("Descuento");
		descuentoField.setTextFormatter(new TextFormatter<>(
				change -> (change.getControlNewText().matches("-?\\d*\\.?\\d{0,2}")) ? change : null));

		TextField totalIVAField = new TextField(null);
		totalIVAField.setPromptText("Total IVA");
		totalIVAField.setTextFormatter(new TextFormatter<>(
				change -> (change.getControlNewText().matches("-?\\d*\\.?\\d{0,2}")) ? change : null));

		TextField totalFacturaField = new TextField(null);
		totalFacturaField.setPromptText("Total Factura");
		totalFacturaField.setTextFormatter(new TextFormatter<>(
				change -> (change.getControlNewText().matches("-?\\d*\\.?\\d{0,2}")) ? change : null));

		TextField observacionesField = new TextField(null);
		observacionesField.setPromptText("Observaciones");

		ComboBox<String> nifClienteComboBox = new ComboBox<>();
		// Llenar el ComboBox con los NIF de clientes disponibles
		List<String> nifClienteList = ingresoDB.obtenerNifClientesDisponibles();
		nifClienteComboBox.getItems().addAll(nifClienteList);
		nifClienteComboBox.setPromptText("NIF Cliente");

		ComboBox<Integer> idIntermediarioComboBox = new ComboBox<>();
		// Llenar el ComboBox con las ID de intermediario disponibles
		List<Integer> idIntermediarioList = ingresoDB.obtenerIdIntermediariosDisponibles();
		idIntermediarioComboBox.getItems().addAll(idIntermediarioList);
		idIntermediarioComboBox.setPromptText("ID Intermediario");

		// Añadir campos al grid
		grid.add(new Label("Tipo de Factura:"), 0, 0);
		grid.add(tipoFacturaComboBox, 1, 0);
		grid.add(new Label("Fecha de Entrada:"), 0, 1);
		grid.add(fechaEntradaPicker, 1, 1);
		grid.add(new Label("Fecha de Salida:"), 0, 2);
		grid.add(fechaSalidaPicker, 1, 2);
		grid.add(new Label("Número de Noches:"), 0, 3);
		grid.add(numeroNochesField, 1, 3);
		grid.add(new Label("Número de Personas:"), 0, 4);
		grid.add(numeroPersonasField, 1, 4);
		grid.add(new Label("ID Tarifa:"), 0, 5);
		grid.add(idTarifaComboBox, 1, 5);
		grid.add(new Label("Descuento:"), 0, 6);
		grid.add(descuentoField, 1, 6);
		grid.add(new Label("Total IVA:"), 0, 7);
		grid.add(totalIVAField, 1, 7);
		grid.add(new Label("Total Factura:"), 0, 8);
		grid.add(totalFacturaField, 1, 8);
		grid.add(new Label("Observaciones:"), 0, 9);
		grid.add(observacionesField, 1, 9);
		grid.add(new Label("NIF Cliente:"), 0, 10);
		grid.add(nifClienteComboBox, 1, 10);
		grid.add(new Label("ID Intermediario:"), 0, 11);
		grid.add(idIntermediarioComboBox, 1, 11);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == buttonTypeOk) {
				String tipoFactura = tipoFacturaComboBox.getValue() != null ? tipoFacturaComboBox.getValue().toString() : null;
				String fechaEntrada = fechaEntradaPicker.getValue() != null ? fechaEntradaPicker.getValue().toString() : null;
				String fechaSalida = fechaSalidaPicker.getValue() != null ? fechaSalidaPicker.getValue().toString() : null;
	            int numeroNoches = numeroNochesField.getText() != null ? Integer.parseInt(numeroNochesField.getText()) : 0;
	            int numeroPersonas = numeroPersonasField.getText() != null ? Integer.parseInt(numeroPersonasField.getText()) : 0;
	            double descuento = descuentoField.getText() != null ? Double.parseDouble(descuentoField.getText()) : 0;
	            double totalIVA = totalIVAField.getText() != null ? Double.parseDouble(totalIVAField.getText()) : 0;
	            double totalFactura = totalFacturaField.getText() != null ? Double.parseDouble(totalFacturaField.getText()) : 0;
	            Integer idTarifa = idTarifaComboBox.getValue() != null ? idTarifaComboBox.getValue() : 0;
	            String nifCliente = nifClienteComboBox.getValue() != null ? nifClienteComboBox.getValue() : null;
	            Integer idIntermediario = idIntermediarioComboBox.getValue() != null ? idIntermediarioComboBox.getValue() : 0;
				return new Ingreso(tipoFactura, fechaEntrada, fechaSalida, numeroNoches, numeroPersonas, idTarifa,
						descuento, totalIVA, totalFactura, observacionesField.getText(), Sesion.getIdApartamento() ,nifCliente, idIntermediario
				);
			}
			return null;
		});

		Optional<Ingreso> result = dialog.showAndWait();
		result.ifPresent(ingreso -> {
			IngresoDB ingresoDB = new IngresoDB();
			ingresoDB.saveIngreso(ingreso);
		});

		// Recargar la tabla
		refreshTable();
	}

	@FXML
	private void modificarIngreso() throws SQLException {
		Ingreso selectedIngreso = ingresoTableView.getSelectionModel().getSelectedItem();
		if (selectedIngreso == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Selecciona un ingreso antes de modificar.");
			alert.showAndWait();
			return;
		}

		Dialog<Ingreso> dialog = new Dialog<>();
		dialog.setTitle("Modificar Ingreso");
		dialog.setHeaderText("Modifica los detalles del ingreso seleccionado");

		ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField idIngresoField = new TextField(String.valueOf(selectedIngreso.getIdIngreso()));
		idIngresoField.setEditable(false);
		
		ComboBox<String> tipoFacturaComboBox = new ComboBox<>();
		tipoFacturaComboBox.getItems().add("Propia");
		tipoFacturaComboBox.getItems().add("Intermediario");
		tipoFacturaComboBox.setValue(selectedIngreso.getTipoFactura());
		
	    DatePicker fechaEntradaPicker = new DatePicker();
	    fechaEntradaPicker.setValue(selectedIngreso.getFechaEntrada() != null ? LocalDate.parse(selectedIngreso.getFechaEntrada()) : null);
	    DatePicker fechaSalidaPicker = new DatePicker();
	    fechaSalidaPicker.setValue(selectedIngreso.getFechaSalida() != null ? LocalDate.parse(selectedIngreso.getFechaSalida()) : null);

		TextField numeroNochesField = new TextField(String.valueOf(selectedIngreso.getNumeroNoches()));
		TextField numeroPersonasField = new TextField(String.valueOf(selectedIngreso.getNumeroPersonas()));

		ComboBox<Integer> idTarifaComboBox = new ComboBox<>();
		// Llenar el ComboBox con las ID de tarifa disponibles
		List<Integer> idTarifaList = ingresoDB.obtenerIdTarifasDisponibles();
		idTarifaComboBox.getItems().addAll(idTarifaList);
		idTarifaComboBox.setValue(selectedIngreso.getIdTarifa());

		TextField descuentoField = new TextField(String.valueOf(selectedIngreso.getDescuento()));
		TextField totalIVAField = new TextField(String.valueOf(selectedIngreso.getTotalIVA()));
		TextField totalFacturaField = new TextField(String.valueOf(selectedIngreso.getTotalFactura()));
		TextField observacionesField = new TextField(selectedIngreso.getObservaciones());

		ComboBox<String> nifClienteComboBox = new ComboBox<>();
		// Llenar el ComboBox con los NIF de clientes disponibles
		List<String> nifClienteList = ingresoDB.obtenerNifClientesDisponibles();
		nifClienteComboBox.getItems().addAll(nifClienteList);
		nifClienteComboBox.setValue(selectedIngreso.getNifCliente());

		ComboBox<Integer> idIntermediarioComboBox = new ComboBox<>();
		// Llenar el ComboBox con las ID de intermediario disponibles
		List<Integer> idIntermediarioList = ingresoDB.obtenerIdIntermediariosDisponibles();
		idIntermediarioComboBox.getItems().addAll(idIntermediarioList);
		idIntermediarioComboBox.setValue(selectedIngreso.getIdIntermediario());

		grid.add(new Label("Tipo de Factura:"), 0, 0);
		grid.add(tipoFacturaComboBox, 1, 0);
		grid.add(new Label("Fecha de Entrada:"), 0, 1);
		grid.add(fechaEntradaPicker, 1, 1);
		grid.add(new Label("Fecha de Salida:"), 0, 2);
		grid.add(fechaSalidaPicker, 1, 2);
		grid.add(new Label("Número de Noches:"), 0, 3);
		grid.add(numeroNochesField, 1, 3);
		grid.add(new Label("Número de Personas:"), 0, 4);
		grid.add(numeroPersonasField, 1, 4);
		grid.add(new Label("ID Tarifa:"), 0, 5);
		grid.add(idTarifaComboBox, 1, 5);
		grid.add(new Label("Descuento:"), 0, 6);
		grid.add(descuentoField, 1, 6);
		grid.add(new Label("Total IVA:"), 0, 7);
		grid.add(totalIVAField, 1, 7);
		grid.add(new Label("Total Factura:"), 0, 8);
		grid.add(totalFacturaField, 1, 8);
		grid.add(new Label("Observaciones:"), 0, 9);
		grid.add(observacionesField, 1, 9);
		grid.add(new Label("NIF Cliente:"), 0, 10);
		grid.add(nifClienteComboBox, 1, 10);
		grid.add(new Label("ID Intermediario:"), 0, 11);
		grid.add(idIntermediarioComboBox, 1, 11);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			String fechaEntrada = fechaEntradaPicker.getValue() != null ? fechaEntradaPicker.getValue().toString()
					: null;
			String fechaSalida = fechaSalidaPicker.getValue() != null ? fechaSalidaPicker.getValue().toString()
					: null;
			if (dialogButton == buttonTypeOk) {
				return new Ingreso(selectedIngreso.getIdIngreso(), tipoFacturaComboBox.getValue(),
						fechaEntrada, fechaSalida,
						Integer.parseInt(numeroNochesField.getText()), Integer.parseInt(numeroPersonasField.getText()),
						idTarifaComboBox.getValue(), Double.parseDouble(descuentoField.getText()),
						Double.parseDouble(totalIVAField.getText()), Double.parseDouble(totalFacturaField.getText()),
						observacionesField.getText(), selectedIngreso.getIdApartamento(), nifClienteComboBox.getValue(),
						idIntermediarioComboBox.getValue());
			}
			return null;
		});

		Optional<Ingreso> result = dialog.showAndWait();
		result.ifPresent(ingreso -> {
			IngresoDB ingresoDB = new IngresoDB();
			ingresoDB.updateIngreso(ingreso);
		});

		refreshTable();
	}

	@FXML
	private void eliminarIngreso() {
		Ingreso selectedIngreso = ingresoTableView.getSelectionModel().getSelectedItem();
		if (selectedIngreso == null) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText(null);
			alert.setContentText("Selecciona un ingreso antes de eliminar.");
			alert.showAndWait();
			return;
		}

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Confirmar Eliminación");
		alert.setHeaderText("¿Estás seguro de que quieres eliminar este ingreso?");
		alert.setContentText(
				"ID: " + selectedIngreso.getIdIngreso() + "\nTipo de Factura: " + selectedIngreso.getTipoFactura());

		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			IngresoDB ingresoDB = new IngresoDB();
			ingresoDB.deleteIngreso(selectedIngreso.getIdIngreso());
		}

		refreshTable();
	}

	private void refreshTable() {
		ingresoTableView.getItems().clear();
		loadIngresos();
	}

	@FXML
	private void cerrar() {
		Stage stage = (Stage) ingresoTableView.getScene().getWindow();
		stage.close();
	}

	@FXML
	private void salir() {
		System.exit(0);
	}
}
