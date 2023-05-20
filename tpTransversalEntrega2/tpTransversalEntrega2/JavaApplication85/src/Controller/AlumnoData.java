package Controller;

import Model.Alumno;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class AlumnoData {

    public Alumno buscarAlumno(int id) {
        String sql = "SELECT * FROM alumno WHERE id_alumno=?";
        Alumno alumno = new Alumno();
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs;
            rs=ps.executeQuery();//devuelve un conjunto de resultados//al ser una key devuelve 1 solo
            if(rs.next()){
            alumno.setAlumno_id(id);
            alumno.setNombre(rs.getString("nombre"));
            alumno.setApellido(rs.getString("apellido"));
            alumno.setDni(rs.getString("dni"));
            alumno.setFecha_nacimiento(rs.getDate("fecha_nacimiento").toLocalDate());
            alumno.setEstado(rs.getBoolean("estado"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "El alumno no se pudo encontrar "+e.getMessage());
        }

        return alumno;
    }
}
