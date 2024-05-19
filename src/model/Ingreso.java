package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Ingreso {
	private final SimpleIntegerProperty idIngreso;
	private final SimpleStringProperty tipoFactura;
	private final SimpleStringProperty fechaEntrada;
	private final SimpleStringProperty fechaSalida;
	private final SimpleIntegerProperty numeroNoches;
	private final SimpleIntegerProperty numeroPersonas;
	private final SimpleIntegerProperty idTarifa;
	private final SimpleDoubleProperty descuento;
	private final SimpleDoubleProperty totalIVA;
	private final SimpleDoubleProperty totalFactura;
	private final SimpleStringProperty observaciones;
	private final SimpleIntegerProperty idApartamento;
	private final SimpleStringProperty nifCliente;
	private final SimpleIntegerProperty idIntermediario;

	public Ingreso(Integer idIngreso, String tipoFactura, String fechaEntrada, String fechaSalida, int numeroNoches,
			int numeroPersonas, Integer idTarifa, double descuento, double totalIVA, double totalFactura,
			String observaciones, Integer idApartamento, String nifCliente, Integer idIntermediario) {
		this.idIngreso = new SimpleIntegerProperty(idIngreso);
		this.tipoFactura = new SimpleStringProperty(tipoFactura);
		this.fechaEntrada = new SimpleStringProperty(fechaEntrada);
		this.fechaSalida = new SimpleStringProperty(fechaSalida);
		this.numeroNoches = new SimpleIntegerProperty(numeroNoches);
		this.numeroPersonas = new SimpleIntegerProperty(numeroPersonas);
		this.idTarifa = new SimpleIntegerProperty(idTarifa);
		this.descuento = new SimpleDoubleProperty(descuento);
		this.totalIVA = new SimpleDoubleProperty(totalIVA);
		this.totalFactura = new SimpleDoubleProperty(totalFactura);
		this.observaciones = new SimpleStringProperty(observaciones);
		this.idApartamento = new SimpleIntegerProperty(idApartamento);
		this.nifCliente = new SimpleStringProperty(nifCliente);
		this.idIntermediario = new SimpleIntegerProperty(idIntermediario);
	}
	
	public Ingreso(String tipoFactura, String fechaEntrada, String fechaSalida, int numeroNoches,
			int numeroPersonas, Integer idTarifa, double descuento, double totalIVA, double totalFactura,
			String observaciones, int idApartamento, String nifCliente, Integer idIntermediario) {
		this.idIngreso = new SimpleIntegerProperty();
		this.tipoFactura = new SimpleStringProperty(tipoFactura);
		this.fechaEntrada = new SimpleStringProperty(fechaEntrada);
		this.fechaSalida = new SimpleStringProperty(fechaSalida);
		this.numeroNoches = new SimpleIntegerProperty(numeroNoches);
		this.numeroPersonas = new SimpleIntegerProperty(numeroPersonas);
		this.idTarifa = new SimpleIntegerProperty(idTarifa);
		this.descuento = new SimpleDoubleProperty(descuento);
		this.totalIVA = new SimpleDoubleProperty(totalIVA);
		this.totalFactura = new SimpleDoubleProperty(totalFactura);
		this.observaciones = new SimpleStringProperty(observaciones);
		this.idApartamento = new SimpleIntegerProperty(idApartamento);
		this.nifCliente = new SimpleStringProperty(nifCliente);
		this.idIntermediario = new SimpleIntegerProperty(idIntermediario);
	}


	public Integer getIdIngreso() {
		return idIngreso.get();
	}

	public SimpleIntegerProperty idIngresoProperty() {
		return idIngreso;
	}

	public String getTipoFactura() {
		return tipoFactura.get();
	}

	public SimpleStringProperty tipoFacturaProperty() {
		return tipoFactura;
	}

	public String getFechaEntrada() {
		return fechaEntrada.get();
	}

	public SimpleStringProperty fechaEntradaProperty() {
		return fechaEntrada;
	}

	public String getFechaSalida() {
		return fechaSalida.get();
	}

	public SimpleStringProperty fechaSalidaProperty() {
		return fechaSalida;
	}
	
	public int getNumeroNoches() {
		return numeroNoches.get();
	}
	
	public SimpleIntegerProperty numeroNochesProperty() {
		return numeroNoches;
	}
	
	public int getNumeroPersonas() {
		return numeroPersonas.get();
	}

	public SimpleIntegerProperty numeroPersonasProperty() {
		return numeroPersonas;
	}
	
	public Integer getIdTarifa() {
		return idTarifa.get();
	}

	public SimpleIntegerProperty idTarifaProperty() {
		return idTarifa;
	}
	
	public double getDescuento() {
		return descuento.get();
	}

	public SimpleDoubleProperty descuentoProperty() {
		return descuento;
	}
	
	public double getTotalIVA() {
		return totalIVA.get();
	}

	public SimpleDoubleProperty totalIVAProperty() {
		return totalIVA;
	}
	
	public double getTotalFactura() {
		return totalFactura.get();
	}

	public SimpleDoubleProperty totalFacturaProperty() {
		return totalFactura;
	}
	
	public String getObservaciones() {
		return observaciones.get();
	}

	public SimpleStringProperty observacionesProperty() {
		return observaciones;
	}
	
	public Integer getIdApartamento() {
		return idApartamento.get();
	}

	public SimpleIntegerProperty idApartamentoProperty() {
		return idApartamento;
	}
	
	public String getNifCliente() {
		return nifCliente.get();
	}

	public SimpleStringProperty nifClienteProperty() {
		return nifCliente;
	}
	
	public Integer getIdIntermediario() {
		return idIntermediario.get();
	}

	public SimpleIntegerProperty idIntermediarioProperty() {
		return idIntermediario;
	}

}
