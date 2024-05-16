package model;

public class Sesion {
    private static String usuario;
    private static int idApartamento;

    public static String getUsuario() {
        return usuario;
    }

    public static void setUsuario(String user) {
    	usuario = user;
    }
    
    public static int getIdApartamento() {
    	return idApartamento;
    }
    
    public static void setIdApartamento(int id) {
    	idApartamento = id;
    }
    
}
