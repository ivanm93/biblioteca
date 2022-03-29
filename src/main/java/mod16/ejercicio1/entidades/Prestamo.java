/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.entidades;


import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author cristian
 */
@Entity
public class Prestamo {
      @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
      private String id;
      
      private Date fechaPrestamo;
   
      private Date fechaDevolucion;
      private Boolean alta;
      @OneToOne
      private Libro libro;
      @OneToOne
      private Cliente cliente;

    public Prestamo() {
    }

    public Prestamo(Date fechaPrestamo, Date fechaDevolucion, Boolean alta, Libro libro, Cliente cliente) {
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.alta = alta;
        this.libro = libro;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", fechaPrestamo=" + fechaPrestamo + ", fechaDevolucion=" + fechaDevolucion + ", alta=" + alta + ", libro=" + libro + ", cliente=" + cliente + '}';
    }
      
      
}
