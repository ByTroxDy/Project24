package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Gasto;

public class GastoDB {

    private Connection conn;

    public GastoDB() {
        try {
			conn = ConexionDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public List<Gasto> getAllGastos() {
    	List<Gasto> gastos = new ArrayList<>();

        String query = "SELECT * FROM gastos";

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
            	Gasto gasto = new Gasto(
                        rs.getInt("id"),
                        rs.getString("tipoGasto"),
                        rs.getString("concepto"),
                        rs.getString("fecha"),
                        rs.getString("nifProveedor"),
                        rs.getDouble("iva"),
                        rs.getDouble("totalIVA"),
                        rs.getDouble("totalGasto"),
                        rs.getString("pagado"),
                        rs.getInt("idApartamento"),
                        rs.getString("nifCliente")
                );
                gastos.add(gasto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gastos;
    }
    
    public List<Gasto> getGastosToApartameto(int idApartamento) {
    	List<Gasto> gastos = new ArrayList<>();

        String query = "SELECT * FROM gastos WHERE idApartamento = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)){
        	pstmt.setInt(1, idApartamento);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            	Gasto gasto = new Gasto(
                        rs.getInt("id"),
                        rs.getString("tipoGasto"),
                        rs.getString("concepto"),
                        rs.getString("fecha"),
                        rs.getString("nifProveedor"),
                        rs.getDouble("iva"),
                        rs.getDouble("totalIVA"),
                        rs.getDouble("totalGasto"),
                        rs.getString("pagado"),
                        rs.getInt("idApartamento"),
                        rs.getString("nifCliente")
                );
                gastos.add(gasto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gastos;
    }
    
    public void saveGasto(Gasto gasto) {
        String query = "INSERT INTO gastos (tipoGasto, concepto, fecha, nifProveedor, iva, totalIVA, totalGasto, pagado, idApartamento, nifCliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, gasto.getTipoGasto());
            pstmt.setString(2, gasto.getConcepto());
            pstmt.setString(3, gasto.getFecha());
            pstmt.setString(4, gasto.getNifProveedor());
            pstmt.setDouble(5, gasto.getIva());
            pstmt.setDouble(6, gasto.getTotalIVA());
            pstmt.setDouble(7, gasto.getTotalGasto());
            pstmt.setString(8, gasto.getPagado());
            pstmt.setInt(9, gasto.getIdApartamento());
            pstmt.setString(10, gasto.getNifCliente());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateGasto(Gasto gasto) {
        String query = "UPDATE gastos SET tipoGasto = ?, concepto = ?, fecha = ?, nifProveedor = ?, iva = ?, totalIVA = ?, totalGasto = ?, pagado = ?, idApartamento = ?, nifCliente = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, gasto.getTipoGasto());
            pstmt.setString(2, gasto.getConcepto());
            pstmt.setString(3, gasto.getFecha());
            pstmt.setString(4, gasto.getNifProveedor());
            pstmt.setDouble(5, gasto.getIva());
            pstmt.setDouble(6, gasto.getTotalIVA());
            pstmt.setDouble(7, gasto.getTotalGasto());
            pstmt.setString(8, gasto.getPagado());
            pstmt.setInt(9, gasto.getIdApartamento());
            pstmt.setString(10, gasto.getNifCliente());
            pstmt.setInt(11, gasto.getIdGasto());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteGasto(Gasto gasto) {
        String query = "DELETE FROM gastos WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, gasto.getIdGasto());
            pstmt.executeUpdate();
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
    
}
