package model;

import javafx.beans.property.SimpleStringProperty;

public class Cliente {
    private final SimpleStringProperty nifCliente;
    private final SimpleStringProperty nombreCompleto;
    private final SimpleStringProperty telefono;

    public Cliente(String nifCliente, String nombreCompleto, String telefono) {
        this.nifCliente = new SimpleStringProperty(nifCliente);
        this.nombreCompleto = new SimpleStringProperty(nombreCompleto);
        this.telefono = new SimpleStringProperty(telefono);
    }

    public String getNifCliente() {
        return nifCliente.get();
    }

    public SimpleStringProperty nifClienteProperty() {
        return nifCliente;
    }

    public String getNombreCompleto() {
        return nombreCompleto.get();
    }

    public SimpleStringProperty nombreCompletoProperty() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono.get();
    }

    public SimpleStringProperty telefonoProperty() {
        return telefono;
    }
}
