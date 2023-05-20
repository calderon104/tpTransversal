
package Model;


public class Inscripcion {
    private String id_inscripcion;
    private Alumno id_alumno;
    private Materia id_materia;
    private double nota;
    //constructor vacio
    public Inscripcion() {
    }
    //Constructor lleno
    public Inscripcion(String id_inscripcion, Alumno id_alumno, Materia id_materia, double nota) {
        this.id_inscripcion = id_inscripcion;
        this.id_alumno = id_alumno;
        this.id_materia = id_materia;
        this.nota = nota;
    }
    //constructor sin id
    public Inscripcion(Alumno id_alumno, Materia id_materia, double nota) {
        this.id_alumno = id_alumno;
        this.id_materia = id_materia;
        this.nota = nota;
    }
    
    public String getId_inscripcion() {
        return id_inscripcion;
    }

    public void setId_inscripcion(String id_inscripcion) {
        this.id_inscripcion = id_inscripcion;
    }

    public Alumno getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(Alumno id_alumno) {
        this.id_alumno = id_alumno;
    }

    public Materia getId_materia() {
        return id_materia;
    }

    public void setId_materia(Materia id_materia) {
        this.id_materia = id_materia;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
