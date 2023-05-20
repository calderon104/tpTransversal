package Controller;

import Model.Alumno;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class AlumnoData {

    public void guardarAlumno(Alumno alumno) {
        String sql = "INSERT into alumno(nombre,apellido,dni,fecha_nacimiento,estado)";
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Se encontro un problema al cargar el alumno "+ e.getMessage());
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
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "El alumno no se pudo encontrar " + e.getMessage());
        }

        return alumno;
    }
}
