package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class OperacionesDB {

    private Connection conn;

    public OperacionesDB() {
        try {
			conn = ConexionDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public double obtenerTotalIngresos() throws SQLException {
        double totalIngresos = 0.0;
        try (PreparedStatement stmtIngresos = conn.prepareStatement("SELECT SUM(TotalFactura) FROM ingresos")) {
            ResultSet rsIngresos = stmtIngresos.executeQuery();
            if (rsIngresos.next()) {
                totalIngresos = rsIngresos.getDouble(1);
            }
        }
        return totalIngresos;
    }

    public double obtenerTotalGastos() throws SQLException {
        double totalGastos = 0.0;
        try (PreparedStatement stmtGastos = conn.prepareStatement("SELECT SUM(TotalGasto) FROM gastos")) {
            ResultSet rsGastos = stmtGastos.executeQuery();
            if (rsGastos.next()) {
                totalGastos = rsGastos.getDouble(1);
            }
        }
        return totalGastos;
    }

    public double calcularLiquidacionIVA() throws SQLException {
        LocalDate fechaActual = LocalDate.now();
        int trimestre = (fechaActual.getMonthValue() - 1) / 3 + 1;
        LocalDate primerDiaTrimestre = LocalDate.of(fechaActual.getYear(), (trimestre - 1) * 3 + 1, 1);
        LocalDate ultimoDiaTrimestre = primerDiaTrimestre.plusMonths(3).minusDays(1);

        double totalIVAIngresos = 0.0;
        double totalIVAGastos = 0.0;
        
        String sqlIngresos = "SELECT SUM(totalIVA) FROM ingresos WHERE fechaEntrada BETWEEN ? AND ?";
        try (PreparedStatement statement = conn.prepareStatement(sqlIngresos)) {
            statement.setDate(1, java.sql.Date.valueOf(primerDiaTrimestre));
            statement.setDate(2, java.sql.Date.valueOf(ultimoDiaTrimestre));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalIVAIngresos = resultSet.getDouble(1);
            }
        }

        String sqlGastos = "SELECT SUM(totalIVA) FROM gastos WHERE fecha BETWEEN ? AND ?";
        try (PreparedStatement statement = conn.prepareStatement(sqlGastos)) {
            statement.setDate(1, java.sql.Date.valueOf(primerDiaTrimestre));
            statement.setDate(2, java.sql.Date.valueOf(ultimoDiaTrimestre));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalIVAGastos = resultSet.getDouble(1);
            }
        }

        return totalIVAIngresos - totalIVAGastos;
    }
    
    public void cerrarConexion() {
        try {
            if (conn != null) {
            	conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
