package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import model.Ingreso;

public class IngresoDB {

    private Connection conn;

    public IngresoDB() {
        try {
			conn = ConexionDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public List<Ingreso> getAllIngresos() {
        List<Ingreso> ingresos = new ArrayList<>();

        String query = "SELECT * FROM ingresos";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Ingreso ingreso = new Ingreso(
                    rs.getInt("id"),
                    rs.getString("tipoFactura"),
                    rs.getString("fechaEntrada"),
                    rs.getString("fechaSalida"),
                    rs.getInt("numeroNoches"),
                    rs.getInt("numeroPersonas"),
                    rs.getInt("idTarifa"),
                    rs.getDouble("descuento"),
                    rs.getDouble("totalIVA"),
                    rs.getDouble("totalFactura"),
                    rs.getString("observaciones"),
                    rs.getInt("idApartamento"),
                    rs.getString("nifCliente"),
                    rs.getInt("idIntermediario")
                );
                ingresos.add(ingreso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingresos;
    }
    
    public List<Ingreso> getIngresosToApartamento(int idApartamento) {
        List<Ingreso> ingresos = new ArrayList<>();

        String query = "SELECT * FROM ingresos WHERE idApartamento = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)){
        	pstmt.setInt(1, idApartamento);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Ingreso ingreso = new Ingreso(
                    rs.getInt("id"),
                    rs.getString("tipoFactura"),
                    rs.getString("fechaEntrada"),
                    rs.getString("fechaSalida"),
                    rs.getInt("numeroNoches"),
                    rs.getInt("numeroPersonas"),
                    rs.getInt("idTarifa"),
                    rs.getDouble("descuento"),
                    rs.getDouble("totalIVA"),
                    rs.getDouble("totalFactura"),
                    rs.getString("observaciones"),
                    rs.getInt("idApartamento"),
                    rs.getString("nifCliente"),
                    rs.getInt("idIntermediario")
                );
                ingresos.add(ingreso);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingresos;
    }

    public void saveIngreso(Ingreso ingreso) {
        String query = "INSERT INTO ingresos (tipoFactura, fechaEntrada, fechaSalida, numeroNoches, numeroPersonas, idTarifa, descuento, totalIVA, totalFactura, observaciones, idApartamento, nifCliente, idIntermediario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, ingreso.getTipoFactura());
            pstmt.setString(2, ingreso.getFechaEntrada());
            pstmt.setString(3, ingreso.getFechaSalida());
            pstmt.setInt(4, ingreso.getNumeroNoches());
            pstmt.setInt(5, ingreso.getNumeroPersonas());
            pstmt.setInt(6, ingreso.getIdTarifa());
            pstmt.setDouble(7, ingreso.getDescuento());
            pstmt.setDouble(8, ingreso.getTotalIVA());
            pstmt.setDouble(9, ingreso.getTotalFactura());
            pstmt.setString(10, ingreso.getObservaciones());
            pstmt.setInt(11, ingreso.getIdApartamento());
            pstmt.setString(12, ingreso.getNifCliente());
            pstmt.setInt(13, ingreso.getIdIntermediario());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateIngreso(Ingreso ingreso) {
        String query = "UPDATE ingresos SET tipoFactura = ?, fechaEntrada = ?, fechaSalida = ?, numeroNoches = ?, numeroPersonas = ?, idTarifa = ?, descuento = ?, totalIVA = ?, totalFactura = ?, observaciones = ?, idApartamento = ?, nifCliente = ?, idIntermediario = ? WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, ingreso.getTipoFactura());
            pstmt.setString(2, ingreso.getFechaEntrada());
            pstmt.setString(3, ingreso.getFechaSalida());
            pstmt.setInt(4, ingreso.getNumeroNoches());
            pstmt.setInt(5, ingreso.getNumeroPersonas());
            pstmt.setInt(6, ingreso.getIdTarifa());
            pstmt.setDouble(7, ingreso.getDescuento());
            pstmt.setDouble(8, ingreso.getTotalIVA());
            pstmt.setDouble(9, ingreso.getTotalFactura());
            pstmt.setString(10, ingreso.getObservaciones());
            pstmt.setInt(11, ingreso.getIdApartamento());
            pstmt.setString(12, ingreso.getNifCliente());
            pstmt.setInt(13, ingreso.getIdIntermediario());
            pstmt.setInt(14, ingreso.getIdIngreso());

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Ingreso modificado correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo modificar el ingreso.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteIngreso(int idIngreso) {
        String query = "DELETE FROM ingresos WHERE id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idIngreso);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Información");
                alert.setHeaderText(null);
                alert.setContentText("Ingreso eliminado correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo eliminar el ingreso.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public List<Integer> obtenerIdTarifasDisponibles() throws SQLException {
        List<Integer> idTarifas = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT id FROM tarifas")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                idTarifas.add(rs.getInt("id"));
            }
        }
        return idTarifas;
    }

    public List<String> obtenerNifClientesDisponibles() throws SQLException {
        List<String> nifClientes = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT nif FROM clientes")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                nifClientes.add(rs.getString("nif"));
            }
        }
        return nifClientes;
    }

    public List<Integer> obtenerIdIntermediariosDisponibles() throws SQLException {
        List<Integer> idIntermediarios = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT id FROM intermediarios")) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                idIntermediarios.add(rs.getInt("id"));
            }
        }
        return idIntermediarios;
    }
    
}
