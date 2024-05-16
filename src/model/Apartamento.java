package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Apartamento {
    private final SimpleIntegerProperty idApartamento;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty direccion;
    private final SimpleIntegerProperty numHabitaciones;
    private final SimpleIntegerProperty capacidadMax;

    public Apartamento(int idApartamento, String nombre, String direccion, int numHabitaciones, int capacidadMax) {
        this.idApartamento = new SimpleIntegerProperty(idApartamento);
        this.nombre = new SimpleStringProperty(nombre);
        this.direccion = new SimpleStringProperty(direccion);
        this.numHabitaciones = new SimpleIntegerProperty(numHabitaciones);
        this.capacidadMax = new SimpleIntegerProperty(capacidadMax);
    }
    
    public Apartamento(String nombre, String direccion, int numHabitaciones, int capacidadMax) {
        this.idApartamento = new SimpleIntegerProperty();
		this.nombre = new SimpleStringProperty(nombre);
        this.direccion = new SimpleStringProperty(direccion);
        this.numHabitaciones = new SimpleIntegerProperty(numHabitaciones);
        this.capacidadMax = new SimpleIntegerProperty(capacidadMax);
    }
    
    public int getIdApartamento() {
        return idApartamento.get();
    }

	public SimpleIntegerProperty idApartamentoProperty() {
		return idApartamento;
	}
	
	public String getNombre() {
        return nombre.get();
    }

	public SimpleStringProperty nombreProperty() {
		return nombre;
	}
	
	public String getDireccion() {
        return direccion.get();
    }

	public SimpleStringProperty direccionProperty() {
		return direccion;
	}
	
	public int getNumHabitaciones() {
        return numHabitaciones.get();
    }

	public SimpleIntegerProperty numHabitacionesProperty() {
		return numHabitaciones;
	}
	
	public int getCapacidadMax() {
        return capacidadMax.get();
    }

	public SimpleIntegerProperty capacidadMaxProperty() {
		return capacidadMax;
	}
    
}
