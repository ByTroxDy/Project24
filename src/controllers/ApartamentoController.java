package controllers;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;

import database.ApartamentoDB;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Apartamento;
import model.Sesion;

public class ApartamentoController {
    @FXML
    private TableView<Apartamento> apartamentoTableView;
    @FXML
    private TableColumn<Apartamento, Integer> idApartamentoColumn;
    @FXML
    private TableColumn<Apartamento, String> nombreApartamentoColumn;
    @FXML
    private TableColumn<Apartamento, String> direccionApartamentoColumn;
    @FXML
    private TableColumn<Apartamento, Integer> numHabitacionesColumn;
    @FXML
    private TableColumn<Apartamento, Integer> capacidadMaxColumn;
    @FXML
    private TextField searchField;

    private FilteredList<Apartamento> filteredApartamentos;

    @FXML
    private void initialize() {
        // Inicializar las columnas de la tabla
        initTableColumns();
        // Cargar los datos de los apartamentos en la tabla
        loadApartamentos();

        // Configurar el campo de búsqueda
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredApartamentos.setPredicate(createPredicate(newValue));
        });
    }

    private void initTableColumns() {
        nombreApartamentoColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        direccionApartamentoColumn.setCellValueFactory(cellData -> cellData.getValue().direccionProperty());
        numHabitacionesColumn.setCellValueFactory(cellData -> cellData.getValue().numHabitacionesProperty().asObject());
        capacidadMaxColumn.setCellValueFactory(cellData -> cellData.getValue().capacidadMaxProperty().asObject());
    }

    private void loadApartamentos() {
        ApartamentoDB apartamentoDB = new ApartamentoDB();
        filteredApartamentos = new FilteredList<>(FXCollections.observableArrayList(apartamentoDB.getApartamentosToUsuario(Sesion.getUsuario())), p -> true);
        apartamentoTableView.setItems(filteredApartamentos);
    }

    private Predicate<Apartamento> createPredicate(String searchText) {
        return apartamento -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchApartamento(apartamento, searchText);
        };
    }

    private boolean searchApartamento(Apartamento apartamento, String searchText) {
        return (apartamento.getNombre().toLowerCase().contains(searchText.toLowerCase())) ||
               (apartamento.getDireccion().toLowerCase().contains(searchText.toLowerCase())) ||
               (Integer.toString(apartamento.getNumHabitaciones()).contains(searchText)) ||
               (Integer.toString(apartamento.getCapacidadMax()).contains(searchText));
    }

    @FXML
    private void agregarApartamento() {
        // Mostrar un diálogo para que el empleado ingrese los datos del nuevo apartamento
        Dialog<Apartamento> dialog = new Dialog<>();
        dialog.setTitle("Agregar Apartamento");
        dialog.setHeaderText("Ingresa los datos del nuevo apartamento");

        // Setear los botones de OK y Cancelar en el diálogo
        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        // Crear campos de texto para que el empleado ingrese los datos del nuevo apartamento
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombreField = new TextField(null);
        nombreField.setPromptText("Nombre");
        TextField direccionField = new TextField(null);
        direccionField.setPromptText("Direccion");

        Spinner<Integer> numHabitacionesSpinner = new Spinner<>(1, Integer.MAX_VALUE, 1);
        numHabitacionesSpinner.setEditable(true);
        numHabitacionesSpinner.setPromptText("Num Habitaciones");

        Spinner<Integer> capacidadMaxSpinner = new Spinner<>(1, Integer.MAX_VALUE, 1);
        capacidadMaxSpinner.setEditable(true);
        capacidadMaxSpinner.setPromptText("Capacidad Max");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Direccion:"), 0, 1);
        grid.add(direccionField, 1, 1);
        grid.add(new Label("Num Habitaciones:"), 0, 2);
        grid.add(numHabitacionesSpinner, 1, 2);
        grid.add(new Label("Capacidad Max:"), 0, 3);
        grid.add(capacidadMaxSpinner, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Convertir el resultado del diálogo a un objeto Apartamento cuando se presiona OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                return new Apartamento(nombreField.getText(), direccionField.getText(),
                        numHabitacionesSpinner.getValue(), capacidadMaxSpinner.getValue());
            }
            return null;
        });

        // Mostrar el diálogo y procesar el resultado cuando se presiona OK
        Optional<Apartamento> result = dialog.showAndWait();
        result.ifPresent(apartamento -> {
            ApartamentoDB apartamentoDB = new ApartamentoDB();
            apartamentoDB.saveApartamento(apartamento.getNombre(), apartamento.getDireccion(),
                    apartamento.getNumHabitaciones(), apartamento.getCapacidadMax());
        });

        refreshTable();
    }

    @FXML
    private void modificarApartamento() {
        // Mostrar un diálogo para que el empleado seleccione el apartamento a modificar
        Apartamento selectedApartamento = apartamentoTableView.getSelectionModel().getSelectedItem();
        if (selectedApartamento == null) {
            // Si no se selecciona ningún apartamento, mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Selecciona un apartamento antes de modificar.");
            alert.showAndWait();
            return;
        }

        // Mostrar un diálogo similar al de agregarApartamento pero con los datos del apartamento seleccionado prellenados
        Dialog<Apartamento> dialog = new Dialog<>();
        dialog.setTitle("Modificar Apartamento");
        dialog.setHeaderText("Modifica los datos del apartamento seleccionado");

        // Setear los botones de OK y Cancelar en el diálogo
        ButtonType buttonTypeOk = new ButtonType("Aceptar", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, ButtonType.CANCEL);

        // Crear campos de texto prellenados con los datos del apartamento seleccionado
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombreField = new TextField(selectedApartamento.getNombre());
        TextField direccionField = new TextField(selectedApartamento.getDireccion());

        Spinner<Integer> numHabitacionesSpinner = new Spinner<>(1, Integer.MAX_VALUE,
                selectedApartamento.getNumHabitaciones());
        numHabitacionesSpinner.setEditable(true);
        numHabitacionesSpinner.setPromptText("Num Habitaciones");

        Spinner<Integer> capacidadMaxSpinner = new Spinner<>(1, Integer.MAX_VALUE,
                selectedApartamento.getCapacidadMax());
        capacidadMaxSpinner.setEditable(true);
        capacidadMaxSpinner.setPromptText("Capacidad Max");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Direccion:"), 0, 1);
        grid.add(direccionField, 1, 1);
        grid.add(new Label("Num Habitaciones:"), 0, 2);
        grid.add(numHabitacionesSpinner, 1, 2);
        grid.add(new Label("Capacidad Max:"), 0, 3);
        grid.add(capacidadMaxSpinner, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Convertir el resultado del diálogo a un objeto Apartamento cuando se presiona OK
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonTypeOk) {
                return new Apartamento(selectedApartamento.getIdApartamento(), nombreField.getText(),
                        direccionField.getText(), numHabitacionesSpinner.getValue(), capacidadMaxSpinner.getValue());
            }
            return null;
        });

        // Mostrar el diálogo y procesar el resultado cuando se presiona OK
        Optional<Apartamento> result = dialog.showAndWait();
        result.ifPresent(apartamento -> {
            ApartamentoDB apartamentoDB = new ApartamentoDB();
            apartamentoDB.updateApartamento(selectedApartamento.getIdApartamento(), apartamento.getNombre(),
                    apartamento.getDireccion(), apartamento.getNumHabitaciones(), apartamento.getCapacidadMax());
        });

        refreshTable();
    }

    @FXML
    private void eliminarApartamento() {
        // Mostrar un diálogo para que el empleado seleccione el apartamento a eliminar
        Apartamento selectedApartamento = apartamentoTableView.getSelectionModel().getSelectedItem();
        if (selectedApartamento == null) {
            // Si no se selecciona ningún apartamento, mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Selecciona un apartamento antes de eliminar.");
            alert.showAndWait();
            return;
        }

        // Mostrar un diálogo de confirmación para confirmar la eliminación de el apartamento seleccionado
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar Eliminación");
        alert.setHeaderText("¿Estás seguro de que quieres eliminar este apartamento?");
        alert.setContentText(
                "ID: " + selectedApartamento.getIdApartamento() + "\nNombre: " + selectedApartamento.getNombre());

        // Obtener el resultado del diálogo de confirmación y eliminar el apartamento si se confirma
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ApartamentoDB apartamentoDB = new ApartamentoDB();
            apartamentoDB.deleteApartamento(selectedApartamento.getIdApartamento());
        }

        refreshTable();
    }

    private void refreshTable() {
        loadApartamentos();
    }

    @FXML
    private void verIngresos() throws IOException {
        Apartamento selectedApartamento = apartamentoTableView.getSelectionModel().getSelectedItem();
        if (selectedApartamento == null) {
            // Si no se selecciona ningún apartamento, mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Selecciona un apartamento antes de ver sus ingreos.");
            alert.showAndWait();
            return;
        }

        Sesion.setIdApartamento(selectedApartamento.getIdApartamento());

        cargarInterfaz("/interfaz/Ingresos.fxml", "Tabla ingresos");

    }

    @FXML
    private void verGastos() throws IOException {
        Apartamento selectedApartamento = apartamentoTableView.getSelectionModel().getSelectedItem();
        if (selectedApartamento == null) {
            // Si no se selecciona ningún apartamento, mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Selecciona un apartamento antes de ver sus gastos.");
            alert.showAndWait();
            return;
        }

        Sesion.setIdApartamento(selectedApartamento.getIdApartamento());

        cargarInterfaz("/interfaz/Gastos.fxml", "Tabla gastos");
    }

    @FXML
    private void cerrar() throws IOException {
        cargarInterfaz("/interfaz/Login.fxml", "GEAT - Iniciar Sesión");

        // Cerrar la ventana actual
        Stage stage = (Stage) apartamentoTableView.getScene().getWindow();
        stage.close();
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
}
