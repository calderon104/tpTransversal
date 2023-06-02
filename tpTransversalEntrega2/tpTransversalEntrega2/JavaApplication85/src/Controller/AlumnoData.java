package Controller;

import Model.Alumno;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AlumnoData {
    
    public void guardarAlumno(Alumno alumno) {
        String sql = "INSERT into alumno(nombre,apellido,dni,fecha_nacimiento,estado) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //DEVUEL CON SU KEY 
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getDni());
            ps.setDate(4, Date.valueOf(alumno.getFecha_nacimiento()));//cambiando el localDate a un Date para sql
            ps.setBoolean(5, alumno.isEstado());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                alumno.setAlumno_id(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Alumno guardado.");
            } else {
                JOptionPane.showMessageDialog(null, "El alumno no pudo ser guardado.");
            }
            ps.close();// se cierra los PS para por que pueden causar perdida de memoria o simplemente cerrar la conexion.
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Se encontro un problema al cargar el alumno " + e.getMessage());
        }
    }
    
    public Alumno buscarAlumno(int id) {
        String sql = "SELECT * FROM alumno WHERE id_alumno=?";
        Alumno alumno = new Alumno();
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs;
            rs = ps.executeQuery();//devuelve un conjunto de resultados//al ser una key devuelve 1 solo
            if (rs.next()) {
                alumno.setAlumno_id(id);
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setDni(rs.getString("dni"));
                alumno.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
            } else {
                JOptionPane.showMessageDialog(null, "El alumno no existe");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno " + e.getMessage());
        }
        
        return alumno;
    }
    
    public Alumno modificarAlumno(Alumno alumno) {
        String sql = "UPDATE alumno SET nombre = ? , apellido = ?, dni = ?, fecha_nacimiento = ? WHERE  id_alumno = ?";
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql);
            ps.setString(1, alumno.getNombre());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getDni());
            ps.setDate(4, Date.valueOf(alumno.getFecha_nacimiento()));
            ps.setInt(5, alumno.getAlumno_id());
            int res = ps.executeUpdate();
            if (res == 1) {
                JOptionPane.showMessageDialog(null, "Alumno modificado.");
            } else {
                JOptionPane.showMessageDialog(null, "Alumno inexistente");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No a podido actualizarse el alumno " + e.getMessage());
        }
        
        return alumno;
    }
    public void eleminarAlumno(int id){
        try {
            //buscar el alumno por id, y setearle el estado en 0.
            String sql="UPDATE alumno SET estado=0 WHERE id_alumno=?";
            PreparedStatement ps= Conexion.conectar().prepareStatement(sql);
            ps.setInt(1,id);
            int res=ps.executeUpdate();
            ps.close();
            if(res==1){
            JOptionPane.showMessageDialog(null, "Se 'elimino' el alumno");
            }else{
            JOptionPane.showMessageDialog(null, "El alumno no se encuentra ");
            }
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se a podido acceder a la tabla de alumnos "+e.getMessage());
        }
    }
    public List<Alumno> listaAlumnos(){
        List<Alumno> listaDeAlumnos= new ArrayList();
        try {
           String sql="SELECT * FROM alumno WHERE estado=1";
            PreparedStatement ps= Conexion.conectar().prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
            Alumno alum= new Alumno();
            alum.setAlumno_id(rs.getInt("id_alumno"));
            alum.setNombre(rs.getString("nombre"));
            alum.setApellido(rs.getString("apellido"));
            alum.setDni(rs.getString("dni"));
            alum.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            alum.setEstado(rs.getBoolean("estado"));
            listaDeAlumnos.add(alum);
            }
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se a podido acceder a la tabla de Alumnos "+e.getMessage());
        }
        return listaDeAlumnos;
    }
    public void activarAlumno(int id){
        try {
            //buscar el alumno por id, y setearle el estado en 0.
            String sql="UPDATE alumno SET estado=1 WHERE id_alumno=?";
            PreparedStatement ps= Conexion.conectar().prepareStatement(sql);
            ps.setInt(1,id);
            int res=ps.executeUpdate();
            ps.close();
            if(res==1){
            JOptionPane.showMessageDialog(null, "Se 'activo' el alumno");
            }else{
            JOptionPane.showMessageDialog(null, "El alumno no se encuentra ");
            }
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se a podido acceder a la tabla de alumnos "+e.getMessage());
        }
    }
}
