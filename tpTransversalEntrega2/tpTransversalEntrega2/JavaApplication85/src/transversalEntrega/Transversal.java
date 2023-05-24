/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transversalEntrega;

import Controller.AlumnoData;
import Controller.Conexion;
import Controller.CursadaData;
import Controller.MateriaData;
import Model.Alumno;
import Model.Inscripcion;
import Model.Materia;
import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author Gonza
 */
public class Transversal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MateriaData md = new MateriaData(Conexion.conectar());
        AlumnoData ad = new AlumnoData();
        CursadaData cd = new CursadaData();
        System.out.println("MATERIAS");
        for(Materia m : md.listarMaterias()){
            System.out.println("Nombre: "+m.getNombre()+" - Id: "+m.getId_materia());
        }
        System.out.println("ALUMNOS:");
        for(Alumno a : ad.listaAlumnos()){
            System.out.println(a.getNombre()+" "+a.getApellido()+" ID: "+a.getAlumno_id());
        }
        System.out.println("");
        System.out.println("Materia Buscada con id - 4 - : "+md.buscarMateria(4).getNombre());
        System.out.println("Eliminar esta materia.");
        md.eliminarMateria(4);
        System.out.println("LISTA DE MATERIAS DESPUES DE BORRAR");
        for(Materia m : md.listarMaterias()){
            System.out.println("Nombre: "+m.getNombre()+" - Id: "+m.getId_materia());
        }
        
        //CREAMOS MATERIA Y ALUMNO PARA GUARDAR EN BASE DE DATOS
        Materia m1= new Materia("Portugues",2,true);
        Alumno a1 = new Alumno("Pepito","Guardia","41354874",new Date(2023-1900,2,15).toLocalDate(),true);
        
        System.out.println("Agregar la materia: "+m1.getNombre());
        md.guardarMateria(m1);
        
        System.out.println("Agregar al alumno: "+a1.getNombre()+" "+a1.getApellido());
        ad.guardarAlumno(a1);
        
        Inscripcion ins = new Inscripcion(a1, m1, 0);
        System.out.println("Guardar Inscripcion");
        cd.guardarInscripcion(ins);
        
        
        System.out.println("INSCRIPCIONES:");
        for(Inscripcion i : cd.obetenerInscripciones()){
            System.out.println(i.getId_inscripcion()+" - "+i.getId_alumno().getNombre()+" "+i.getId_alumno().getApellido()+" / "+i.getId_materia().getNombre());
        }
    }
    
}
