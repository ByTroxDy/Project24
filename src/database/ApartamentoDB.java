package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;
import model.Apartamento;
import model.Sesion;

public class ApartamentoDB {
	
    private Connection conn;

    public ApartamentoDB() {
        try {
			conn = ConexionDB.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public List<Apartamento> getAllApartamentos() {
    	List<Apartamento> apartamentos = new ArrayList<>();
    	
        String query = "SELECT * FROM apartamentos";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Apartamento apartamento = new Apartamento(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getInt("numHabitaciones"),
                        rs.getInt("capacidadMax")
                );
                apartamentos.add(apartamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return apartamentos;
    }
    
    public List<Apartamento> getApartamentosToUsuario(String usuario) {
    	List<Apartamento> apartamentos = new ArrayList<>();
    	
        String query = "SELECT * FROM apartamentos WHERE usuario = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        	pstmt.setString(1, usuario);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Apartamento apartamento = new Apartamento(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getInt("numHabitaciones"),
                        rs.getInt("capacidadMax")
                );
                apartamentos.add(apartamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return apartamentos;
    }
    
    public void saveApartamento(String nombre, String direccion, int numHabitaciones, int capacidadMax) {
        String query = "INSERT INTO apartamentos (nombre, direccion, numHabitaciones, capacidadMax, usuario) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, direccion);
            pstmt.setInt(3, numHabitaciones);
            pstmt.setInt(4, capacidadMax);
            pstmt.setString(5, Sesion.getUsuario());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText(null);
                alert.setContentText("Apartamento agregado correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo agregar el apartamento.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateApartamento(int idApartamento, String nuevoNombre, String nuevaDireccion, int nuevoNumHab, int nuevaCapacidadMax) {
        String query = "UPDATE apartamentos SET nombre = ?, direccion = ?, numHabitaciones = ?, capacidadMax = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, nuevaDireccion);
            pstmt.setInt(3, nuevoNumHab);
            pstmt.setInt(4, nuevaCapacidadMax);
            pstmt.setInt(5, idApartamento);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText(null);
                alert.setContentText("Apartamento modificado correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo modificar el apartamento.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteApartamento(int idApatamento) {
        String query = "DELETE FROM apartamentos WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idApatamento);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 1) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("INFORMATION");
                alert.setHeaderText(null);
                alert.setContentText("Apartamento eliminado correctamente.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo eliminar el apartamento.");
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

}
