/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;



import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author juany
 */
public class Conexion {
    private Connection con = null;

    public Conexion() {
        try{
            String url = "jdbc:mysql://localhost:3306/ulp";
            String user = "root";
            String password = "";
            con = (Connection) DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos!");
        }catch(SQLException e){
            
            JOptionPane.showMessageDialog(null, "Error al conectar "+e.getMessage());
        }
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
    
    public void cerrarConexion(){
       try {
            if (con != null) {
                con.close();
                System.out.println("Coneccion cerrada");
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar la conexión a la base de datos: " + ex.getMessage());
        }
    }
    
    
    
    
    
    
    
    
}
