/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Alumno;
import Model.Inscripcion;
import Model.Materia;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Gonza
 */
public class CursadaData {
    
        AlumnoData alumnoData;
        MateriaData materiaData;
    
    
    public void guardarInscripcion(Inscripcion insc) {
        String sql = "INSERT INTO inscripcion (id_alumno, id_materia, nota) VALUES";
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); //DEVUEL CON SU KEY 
            ps.setInt(1, insc.getId_alumno().getAlumno_id());
            ps.setInt(2, insc.getId_materia().getId_materia());
            ps.setDouble(3, insc.getNota());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();            
            if (rs.next()) {
                insc.setId_inscripcion(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Inscripcion guardada.");
            } else {
                JOptionPane.showMessageDialog(null, "La Inscripcion no pudo ser guardada.");
            }
            ps.close();// se cierra los PS para por que pueden causar perdida de memoria o simplemente cerrar la conexion.
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Se encontro un problema al cargar la inscripcion " + e.getMessage());
        }
    }
    
    public List<Inscripcion> obetenerInscripciones() {
        String sql = "SELECT * FROM inscripcion";
        Inscripcion insc = null;
        List<Inscripcion> inscripciones = new ArrayList();
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql);
            ResultSet rs;
            rs = ps.executeQuery();//devuelve un conjunto de resultados
            if (rs.next()) {
                insc.setId_inscripcion(rs.getInt("id_inscripcion"));
                insc.setId_alumno(alumnoData.buscarAlumno(rs.getInt("id_alumno")));
                insc.setId_materia(materiaData.buscarMateria(rs.getInt("id_materia")));
                insc.setNota(rs.getDouble("nota"));
                inscripciones.add(insc);
            } else {
                JOptionPane.showMessageDialog(null, "No existen inscripciones");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla inscripcion " + e.getMessage());
        }
        
        return inscripciones;
    }
    
    public List<Inscripcion> obetenerInscripcionesPorAlumno(int id) {
        String sql = "SELECT * FROM inscripcion WHERE id_alumno = ?";
        Inscripcion insc = null;
        List<Inscripcion> inscripciones = new ArrayList();
        Alumno alumno = alumnoData.buscarAlumno(id);
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs;
            rs = ps.executeQuery();//devuelve un conjunto de resultados
            if (rs.next()) {
                insc.setId_inscripcion(rs.getInt("id_inscripcion"));
                insc.setId_alumno(alumno);
                insc.setId_materia(materiaData.buscarMateria(rs.getInt("id_materia")));
                insc.setNota(rs.getDouble("nota"));
                inscripciones.add(insc);
            } else {
                JOptionPane.showMessageDialog(null, "No existen inscripciones para este id_alumno");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla inscripcion " + e.getMessage());
        }
        
        return inscripciones;
    }
    
    
    public List<Materia> obetenerMateriasCursadas(int id) {
        String sql = "SELECT * FROM materias where id_materia in (SELECT id_materia FROM inscripcion WHERE id_alumno = ? AND nota IS NOT NULL)";
        Materia materia = null;
        List<Materia> materiasCursadas = new ArrayList();
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs;
            rs = ps.executeQuery();//devuelve un conjunto de resultados
            if (rs.next()) {
                materia.setAnio(rs.getInt("anio"));
                materia.setEstado(rs.getBoolean("estado"));
                materia.setId_materia(rs.getInt("id_materia"));
                materia.setNombre(rs.getString("nombre"));
                materiasCursadas.add(materia);
            } else {
                JOptionPane.showMessageDialog(null, "No existen materias cursadas para este alumno");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materias " + e.getMessage());
        }
        
        return materiasCursadas;
    }
    
    public List<Materia> obetenerMateriasNoCursadas(int id) {
        String sql = "SELECT * FROM materias where id_materia in (SELECT id_materia FROM inscripcion WHERE id_alumno = ? AND nota IS NULL)";
        Materia materia = null;
        List<Materia> materiasCursadas = new ArrayList();
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs;
            rs = ps.executeQuery();//devuelve un conjunto de resultados
            if (rs.next()) {
                materia.setAnio(rs.getInt("anio"));
                materia.setEstado(rs.getBoolean("estado"));
                materia.setId_materia(rs.getInt("id_materia"));
                materia.setNombre(rs.getString("nombre"));
                materiasCursadas.add(materia);
            } else {
                JOptionPane.showMessageDialog(null, "No existen materias no cursadas para este alumno");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla materias " + e.getMessage());
        }
        
        return materiasCursadas;
    }
    
    public void borrarInscripcionMateriaAlumno(int idAlumno, int idMateria){
        try {
            //buscar el alumno por id, y setearle el estado en 0.
            String sql="DELETE FROM inscripcion WHERE id_alumno=? AND id_materia=?";
            PreparedStatement ps= Conexion.conectar().prepareStatement(sql);
            ps.setInt(1,idAlumno);
            ps.setInt(2,idMateria);
            
            if(!ps.execute()){
                JOptionPane.showMessageDialog(null,"Error al borrar inscripcion");
            }
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se a podido acceder a la tabla inscripcion "+e.getMessage());
        }
    }
    
    public void actualizarNota(int idAlumno, int idMateria, double nota){
        try {
            //buscar el alumno por id, y setearle el estado en 0.
            String sql="UPDATE inscripcion SET nota = ? WHERE id_alumno=? AND id_materia=?";
            PreparedStatement ps= Conexion.conectar().prepareStatement(sql);
            ps.setDouble(1,nota);
            ps.setInt(2,idAlumno);
            ps.setInt(3,idMateria);
            
            if(!ps.execute()){
                JOptionPane.showMessageDialog(null,"Error al actualizar nota");
            }
                
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se a podido acceder a la tabla inscripcion "+e.getMessage());
        }
    }
    
     public List<Alumno> obtenerAlumnosXMateria(int idMateria){
        String sql = "SELECT * FROM alumnos where id_alumno in (SELECT id_alumno FROM inscripcion WHERE id_materia = ?)";
        Alumno alumno = null;
        List<Alumno> alumnosEnXMateria = new ArrayList();
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, idMateria);
            ResultSet rs;
            rs = ps.executeQuery();//devuelve un conjunto de resultados
            if (rs.next()) {
                alumno.setAlumno_id(rs.getInt("id_alumno"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setApellido(rs.getString("apellido"));
                alumno.setDni(rs.getString("dni"));
                alumno.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
                alumno.setEstado(rs.getBoolean("estado"));
                alumnosEnXMateria.add(alumno);
            } else {
                JOptionPane.showMessageDialog(null, "No existen alumnos inscriptos en esta materia");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla alumnos " + e.getMessage());
        }
        
        return alumnosEnXMateria;
    }
    
}
