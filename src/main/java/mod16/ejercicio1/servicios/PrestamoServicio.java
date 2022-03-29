/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.servicios;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import mod16.ejercicio1.entidades.Cliente;
import mod16.ejercicio1.entidades.Libro;
import mod16.ejercicio1.entidades.Prestamo;
import mod16.ejercicio1.errores.ErrorServicio;
import mod16.ejercicio1.repositorios.ClienteRepositorio;
import mod16.ejercicio1.repositorios.LibroRepositorio;
import mod16.ejercicio1.repositorios.PrestamoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */
@Service
public class PrestamoServicio {

    @Autowired
    private PrestamoRepositorio PrestamoRepositorio;
    @Autowired
    private LibroRepositorio librorepositorio;
    @Autowired
    private ClienteRepositorio clienterepositorio;

    public void agregarPrestamo(Date fechaPrestamo, Date fechaDevolucion, String nombreLibro, String nombreCliente) throws ErrorServicio {
        validar(fechaPrestamo, fechaDevolucion, nombreLibro, nombreCliente);
        Prestamo p = new Prestamo();

        Libro libro = librorepositorio.buscarPorTitulo(nombreLibro);
        Cliente cliente = clienterepositorio.buscarPorNombre(nombreCliente);

        p.setFechaDevolucion(fechaDevolucion);
        p.setFechaPrestamo(fechaPrestamo);
        p.setCliente(cliente);

        if(cliente.getAlta()==false){
                  throw new ErrorServicio("Asegurese que el cliente este dado de alta");
                }
        if (libro.getEjemplaresPrestados() < libro.getEjemplares()) {
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - 1);

            if (libro.getEjemplaresPrestados() == libro.getEjemplares()) {
                libro.setAlta(false);
            }
            p.setLibro(libro);
        } else {
            throw new ErrorServicio("Libro dado de baja,porque no hay ejemplares disponibles");
        }


        PrestamoRepositorio.save(p);
    }

    public void modificarPrestamo(String id, Date fechaPrestamo, Date fechaDevolucion, String nombreLibro, String nombreCliente) throws ErrorServicio {
        validar(fechaPrestamo, fechaDevolucion, nombreLibro, nombreCliente);

        Optional<Prestamo> respuesta = PrestamoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Prestamo p = respuesta.get();

            p.setFechaDevolucion(fechaDevolucion);
            p.setFechaPrestamo(fechaPrestamo);

            Libro libro = librorepositorio.buscarPorTitulo(nombreLibro);
            if (libro != p.getLibro()) {

                if (libro.getEjemplaresPrestados() < libro.getEjemplares()) {
                    libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() + 1);
                    libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() - 1);
                    p.getLibro().setEjemplaresPrestados(libro.getEjemplaresPrestados() - 1);
                    p.getLibro().setEjemplaresRestantes(libro.getEjemplaresRestantes() + 1);

                    if (libro.getEjemplaresPrestados() == libro.getEjemplares()) {
                        libro.setAlta(false);
                    }
                    p.setLibro(libro);
                } else {
                    throw new ErrorServicio("Libro dado de baja,porque no hay ejemplares disponibles");
                }
            }

            Cliente cliente = clienterepositorio.buscarPorNombre(nombreCliente);
            p.setCliente(cliente);
            PrestamoRepositorio.save(p);

        } else {
            throw new ErrorServicio("No se encontro prestamo");
        }
    }

    public void eliminarPrestamo(String id) throws ErrorServicio {
        Optional<Prestamo> respuesta = PrestamoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Prestamo p = respuesta.get();

            Libro libro = librorepositorio.buscarPorTitulo(p.getLibro().getTitulo());
            libro.setEjemplaresPrestados(libro.getEjemplaresPrestados() - 1);
            libro.setEjemplaresRestantes(libro.getEjemplaresRestantes() + 1);
            libro.setAlta(true);

            PrestamoRepositorio.deleteById(id);

        } else {
            throw new ErrorServicio("No se encontro prestamo a eliminar");
        }
    }

    public List<Prestamo> listarPrestamos() {
        return PrestamoRepositorio.findAll();

    }

    public Prestamo buscarPorId(String id) throws ErrorServicio {
        Optional<Prestamo> respuesta = PrestamoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();
            return prestamo;
        } else {
            throw new ErrorServicio("No se encontro prestamo");
        }
    }

    public void validar(Date fechaPrestamo, Date fechaDevolucion, String nombreLibro, String nombreCliente) throws ErrorServicio {
        if (fechaPrestamo == null) {
            throw new ErrorServicio("Es necesario colocar fecha de prestamo");
        }
        if (fechaDevolucion == null) {
            throw new ErrorServicio("Es necesario colocar fecha de devolucion");
        }
        if (nombreLibro == null || nombreLibro.isEmpty()) {
            throw new ErrorServicio("Es necesario colocar nombre del libro");
        }
        if (nombreCliente == null || nombreCliente.isEmpty()) {
            throw new ErrorServicio("Es necesario colocar nombre del cliente");
        }
    }
}
