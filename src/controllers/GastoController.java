package controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Gasto;
import model.Sesion;
import database.GastoDB;

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

    @FXML
    private void initialize() {
        // Inicializar las columnas de la tabla
        initTableColumns();
        // Cargar los datos de los gastos en la tabla
        loadGastos();
    }

    private void initTableColumns() {
        // Configurar las columnas de la tabla
//        idGastoColumn.setCellValueFactory(cellData -> cellData.getValue().idGastoProperty().asObject());
        tipoGastoColumn.setCellValueFactory(cellData -> cellData.getValue().tipoGastoProperty());
        conceptoColumn.setCellValueFactory(cellData -> cellData.getValue().conceptoProperty());
        fechaColumn.setCellValueFactory(cellData -> cellData.getValue().fechaProperty());
        nifProveedorColumn.setCellValueFactory(cellData -> cellData.getValue().nifProveedorProperty());
        ivaColumn.setCellValueFactory(cellData -> cellData.getValue().ivaProperty().asObject());
        totalIVAColumn.setCellValueFactory(cellData -> cellData.getValue().totalIVAProperty().asObject());
        totalGastoColumn.setCellValueFactory(cellData -> cellData.getValue().totalGastoProperty().asObject());
        pagadoColumn.setCellValueFactory(cellData -> cellData.getValue().pagadoProperty());
        nifClienteColumn.setCellValueFactory(cellData -> cellData.getValue().nifClienteProperty());
    }

    private void loadGastos() {
    	GastoDB gastoDB = new GastoDB();
    	gastoTableView.getItems().addAll(gastoDB.getGastosToApartameto(Sesion.getIdApartamento()));
    }

    @FXML
    private void agregarGasto() {
        Dialog<Gasto> dialog = new Dialog<>();
        dialog.setTitle("Agregar Gasto");
        dialog.setHeaderText("Ingrese los detalles del nuevo gasto");

        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField tipoGastoField = new TextField();
        tipoGastoField.setPromptText("Tipo de Gasto");
        TextField conceptoField = new TextField();
        conceptoField.setPromptText("Concepto");
        TextField fechaField = new TextField();
        fechaField.setPromptText("Fecha");
        TextField nifProveedorField = new TextField();
        nifProveedorField.setPromptText("NIF Proveedor");
        TextField ivaField = new TextField();
        ivaField.setPromptText("IVA");
        TextField totalIVAField = new TextField();
        totalIVAField.setPromptText("Total IVA");
        TextField totalGastoField = new TextField();
        totalGastoField.setPromptText("Total Gasto");
        TextField pagadoField = new TextField();
        pagadoField.setPromptText("Pagado");
        TextField nifClienteField = new TextField();
        nifClienteField.setPromptText("NIF Cliente");

        grid.add(new Label("Tipo de Gasto:"), 0, 0);
        grid.add(tipoGastoField, 1, 0);
        grid.add(new Label("Concepto:"), 0, 1);
        grid.add(conceptoField, 1, 1);
        grid.add(new Label("Fecha:"), 0, 2);
        grid.add(fechaField, 1, 2);
        grid.add(new Label("NIF Proveedor:"), 0, 3);
        grid.add(nifProveedorField, 1, 3);
        grid.add(new Label("IVA:"), 0, 4);
        grid.add(ivaField, 1, 4);
        grid.add(new Label("Total IVA:"), 0, 5);
        grid.add(totalIVAField, 1, 5);
        grid.add(new Label("Total Gasto:"), 0, 6);
        grid.add(totalGastoField, 1, 6);
        grid.add(new Label("Pagado:"), 0, 7);
        grid.add(pagadoField, 1, 7);
        grid.add(new Label("NIF Cliente:"), 0, 8);
        grid.add(nifClienteField, 1, 8);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                return new Gasto(
                        tipoGastoField.getText(),
                        conceptoField.getText(),
                        fechaField.getText(),
                        nifProveedorField.getText(),
                        Double.parseDouble(ivaField.getText()),
                        Double.parseDouble(totalIVAField.getText()),
                        Double.parseDouble(totalGastoField.getText()),
                        pagadoField.getText(),
                        nifClienteField.getText()
                );
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
    private void modificarGasto() {
        Gasto selectedGasto = gastoTableView.getSelectionModel().getSelectedItem();
        if (selectedGasto != null) {
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
            TextField tipoGastoField = new TextField(selectedGasto.getTipoGasto());
            TextField conceptoField = new TextField(selectedGasto.getConcepto());
            TextField fechaField = new TextField(selectedGasto.getFecha());
            TextField nifProveedorField = new TextField(selectedGasto.getNifProveedor());
            TextField ivaField = new TextField(String.valueOf(selectedGasto.getIva()));
            TextField totalIVAField = new TextField(String.valueOf(selectedGasto.getTotalIVA()));
            TextField totalGastoField = new TextField(String.valueOf(selectedGasto.getTotalGasto()));
            TextField pagadoField = new TextField(selectedGasto.getPagado());
            TextField idApartamentoField = new TextField(String.valueOf(selectedGasto.getIdApartamento()));
            TextField nifClienteField = new TextField(selectedGasto.getNifCliente());

            grid.add(new Label("Tipo de Gasto:"), 0, 0);
            grid.add(tipoGastoField, 1, 0);
            grid.add(new Label("Concepto:"), 0, 1);
            grid.add(conceptoField, 1, 1);
            grid.add(new Label("Fecha:"), 0, 2);
            grid.add(fechaField, 1, 2);
            grid.add(new Label("NIF Proveedor:"), 0, 3);
            grid.add(nifProveedorField, 1, 3);
            grid.add(new Label("IVA:"), 0, 4);
            grid.add(ivaField, 1, 4);
            grid.add(new Label("Total IVA:"), 0, 5);
            grid.add(totalIVAField, 1, 5);
            grid.add(new Label("Total Gasto:"), 0, 6);
            grid.add(totalGastoField, 1, 6);
            grid.add(new Label("Pagado:"), 0, 7);
            grid.add(pagadoField, 1, 7);
            grid.add(new Label("NIF Cliente:"), 0, 8);
            grid.add(nifClienteField, 1, 8);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == buttonTypeOk) {
                	return new Gasto(
                		Integer.parseInt(idGastoField.getText()),
	                    tipoGastoField.getText(),
	                    conceptoField.getText(),
	                    fechaField.getText(),
	                    nifProveedorField.getText(),
	                    Double.parseDouble(ivaField.getText()),
	                    Double.parseDouble(totalIVAField.getText()),
	                    Double.parseDouble(totalGastoField.getText()),
	                    pagadoField.getText(),
	                    Integer.parseInt(idApartamentoField.getText()),
	                    nifClienteField.getText()
                    );
                }
                return null;
            });

            Optional<Gasto> result = dialog.showAndWait();
            result.ifPresent(gasto -> {
            	GastoDB gastoDB = new GastoDB();
				gastoDB.updateGasto(gasto);
            });
            
        	refreshTable();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Modificar Gasto");
            alert.setHeaderText("Ningún gasto seleccionado");
            alert.setContentText("Por favor, selecciona un gasto de la tabla para modificar.");
            alert.showAndWait();
        }
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
