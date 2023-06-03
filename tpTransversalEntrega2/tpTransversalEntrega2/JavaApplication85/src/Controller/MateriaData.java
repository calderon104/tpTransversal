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
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Gonza
 */
public class MateriaData {
    private Connection conn;
    

    public MateriaData(Connection conexion) {
        this.conn = conexion;
    }
    
    public void guardarMateria(Materia m){
        String sql= "INSERT INTO materias VALUES (null,?,?,?)";
        
        try{
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, m.getNombre());
            ps.setInt(2, m.getAnio());
            ps.setBoolean(3, m.isEstado());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                System.out.println(rs.getInt(1));
                m.setId_materia(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Materia agregada.");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al cargar materia: "+e.toString());
        }
    }
    
    public Materia buscarMateria(int id){
        String sql = "SELECT * FROM materias WHERE id_materia=?";
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
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
    public Materia modificarMateria(Materia m){
        
        String sql = "UPDATE materias SET nombre=?,anio=?,estado=? WHERE id_materia=?";
        
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,m.getNombre());
            ps.setInt(2,m.getAnio());
            ps.setBoolean(3, m.isEstado());
            ps.setInt(4, m.getId_materia());
            ps.execute();
//            if(ps.execute()){
//                JOptionPane.showMessageDialog(null, "Materia editada correctamente");
//            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al actualizar materia: "+e.toString());
        }
        
        return m;
    }
    
    public void eliminarMateria(int id){
        String sql= "UPDATE materias SET estado=0 WHERE id_materia= ?";
        try{
            PreparedStatement ps= conn.prepareStatement(sql);
            ps.setInt(1, id);  
            ps.execute();
                
        }
        catch(SQLException e){
            System.out.println("Algo salio mal: "+e.toString());
        }
    }
    
    public ArrayList<Materia> listarMaterias(){
        String sql= "SELECT * FROM materias WHERE estado=1";
        ArrayList<Materia> lm= new ArrayList();
        try{
            PreparedStatement ps = conn.prepareStatement(sql);
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
