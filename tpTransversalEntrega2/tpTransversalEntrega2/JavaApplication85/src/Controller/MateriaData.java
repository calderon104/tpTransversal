/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Materia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gonza
 */
public class MateriaData {
    private Connection conn;
    private Conexion conexion;

    public MateriaData(Conexion conexion) {
        this.conexion = conexion;
    }
    
    public void guardarMateria(Materia m){
        String sql= "INSERT INTO materias VALUES (null,?,?,?)";
        
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getAnio());
            ps.setBoolean(3, m.isEstado());
            
            if(ps.execute()){
                System.out.println("Materia cargada con exito");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al cargar materia: "+e.toString());
        }
    }
    
    public Materia buscarMateria(int id){
        String sql = "SELECT * FROM materias WHERE id=?";
        try{
            PreparedStatement ps = conn.prepareCall(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                Materia m = new Materia(rs.getInt("id_materia"), rs.getString("nombre"), rs.getInt("anio"), rs.getBoolean("estado"));
                return m;
            }
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al intentar buscar materia: "+e.toString());
        }
        return null;
    }
    public Materia modificarMateria(int id , Materia m){
        
        String sql = "UPDATE materias SET id_materia= ?, nombre=?,anio=?,estado=? WHERE id_materia=?";
        
        try{
            PreparedStatement ps = conn.prepareCall(sql);
            ps.setInt(1,m.getId_materia());
            ps.setString(2,m.getNombre());
            ps.setInt(3,m.getAnio());
            ps.setBoolean(4, m.isEstado());
            ps.setInt(5, id);
            
            if(ps.execute()){
                System.out.println("Modificacion exitosa");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al actualizar materia: "+e.toString());
        }
        
        return m;
    }
    
    public void eliminarMateria(int id){
        String sql= "DELETE FROM materias WHERE id_materia= ?";
        try{
            PreparedStatement ps= conn.prepareCall(sql);
            ps.setInt(1, id);
            
            if(!ps.execute()){
                JOptionPane.showMessageDialog(null,"Error al borrar!");
            }
        }
        catch(SQLException e){
            System.out.println("Algo salio mal: "+e.toString());
        }
    }
    
    public ArrayList<Materia> listarMaterias(){
        String sql= "SELECT * FROM materias";
        ArrayList<Materia> lm= new ArrayList();
        try{
            PreparedStatement ps = conn.prepareCall(sql);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                lm.add(new Materia(rs.getInt("id_materia"), rs.getString("nombre"), rs.getInt("anio"), rs.getBoolean("estado")));                
            }
        }catch(SQLException e){
            System.out.println("Algo salio mal : "+e.toString());
        }
        return lm;
    }
    
}
