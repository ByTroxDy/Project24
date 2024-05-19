package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Gasto {
	private final SimpleIntegerProperty idGasto;
	private final SimpleStringProperty tipoGasto;
	private final SimpleStringProperty concepto;
	private final SimpleStringProperty fecha;
	private final SimpleStringProperty nifProveedor;
	private final SimpleDoubleProperty iva;
	private final SimpleDoubleProperty totalIVA;
	private final SimpleDoubleProperty totalGasto;
	private final SimpleStringProperty pagado;
	private final SimpleIntegerProperty idApartamento;
	private final SimpleStringProperty nifCliente;

	public Gasto(int idGasto, String tipoGasto, String concepto, String fecha, String nifProveedor, double iva,
			double totalIVA, double totalGasto, String pagado, int idApartamento, String nifCliente) {
		this.idGasto = new SimpleIntegerProperty(idGasto);
		this.tipoGasto = new SimpleStringProperty(tipoGasto);
		this.concepto = new SimpleStringProperty(concepto);
		this.fecha = new SimpleStringProperty(fecha);
		this.nifProveedor = new SimpleStringProperty(nifProveedor);
		this.iva = new SimpleDoubleProperty(iva);
		this.totalIVA = new SimpleDoubleProperty(totalIVA);
		this.totalGasto = new SimpleDoubleProperty(totalGasto);
		this.pagado = new SimpleStringProperty(pagado);
		this.idApartamento = new SimpleIntegerProperty(idApartamento);
		this.nifCliente = new SimpleStringProperty(nifCliente);
		
	}
	
	public Gasto(String tipoGasto, String concepto, String fecha, String nifProveedor, double iva,
			double totalIVA, double totalGasto, String pagado, int idApartamento, String nifCliente) {
		this.idGasto = new SimpleIntegerProperty();
		this.tipoGasto = new SimpleStringProperty(tipoGasto);
		this.concepto = new SimpleStringProperty(concepto);
		this.fecha = new SimpleStringProperty(fecha);
		this.nifProveedor = new SimpleStringProperty(nifProveedor);
		this.iva = new SimpleDoubleProperty(iva);
		this.totalIVA = new SimpleDoubleProperty(totalIVA);
		this.totalGasto = new SimpleDoubleProperty(totalGasto);
		this.pagado = new SimpleStringProperty(pagado);
		this.idApartamento = new SimpleIntegerProperty(idApartamento);
		this.nifCliente = new SimpleStringProperty(nifCliente);
		
	}

	public int getIdGasto() {
		return idGasto.get();
	}

	public SimpleIntegerProperty idGastoProperty() {
		return idGasto;
	}

	public String getTipoGasto() {
		return tipoGasto.get();
	}

	public SimpleStringProperty tipoGastoProperty() {
		return tipoGasto;
	}

	public String getConcepto() {
		return concepto.get();
	}

	public SimpleStringProperty conceptoProperty() {
		return concepto;
	}
	
	public String getFecha() {
		return fecha.get();
	}

	public SimpleStringProperty fechaProperty() {
		return fecha;
	}
	
	public String getNifProveedor() {
		return nifProveedor.get();
	}

	public SimpleStringProperty nifProveedorProperty() {
		return concepto;
	}
	
	public double getIva() {
		return iva.get();
	}

	public SimpleDoubleProperty ivaProperty() {
		return iva;
	}
	
	public double getTotalIVA() {
		return totalIVA.get();
	}

	public SimpleDoubleProperty totalIVAProperty() {
		return totalIVA;
	}
	
	public double getTotalGasto() {
		return totalGasto.get();
	}

	public SimpleDoubleProperty totalGastoProperty() {
		return totalGasto;
	}
	
	public String getPagado() {
		return pagado.get();
	}

	public SimpleStringProperty pagadoProperty() {
		return pagado;
	}
	
	public int getIdApartamento() {
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

}
