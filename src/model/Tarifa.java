package model;

import javafx.beans.property.*;

public class Tarifa {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombreTarifa = new SimpleStringProperty();
    private final DoubleProperty montoTarifa = new SimpleDoubleProperty();
    // Otros atributos y propiedades...

    public Tarifa(int id, String nombreTarifa, double montoTarifa /* Otros parámetros... */) {
        this.id.set(id);
        this.nombreTarifa.set(nombreTarifa);
        this.montoTarifa.set(montoTarifa);
        // Inicializar otros atributos...
    }

    // Getters y setters
    // (Omitidos por brevedad)
}
