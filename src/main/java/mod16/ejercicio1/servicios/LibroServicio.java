/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.servicios;

import java.util.List;
import java.util.Optional;
import mod16.ejercicio1.entidades.Autor;
import mod16.ejercicio1.entidades.Editorial;
import mod16.ejercicio1.entidades.Libro;
import mod16.ejercicio1.errores.ErrorServicio;
import mod16.ejercicio1.repositorios.AutorRepositorio;
import mod16.ejercicio1.repositorios.EditorialRepositorio;
import mod16.ejercicio1.repositorios.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cristian
 */
@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio LibroRepositorio;
    @Autowired
    private AutorRepositorio AutorRepositorio;
    @Autowired
    private EditorialRepositorio EditorialRepositorio;

    public void agregarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, String nombreAutor, String nombreEditorial) throws ErrorServicio {
        validarLibro(isbn, titulo, anio, ejemplares, nombreAutor, nombreEditorial);
      
                Integer cero=0;
                Libro l = new Libro();
                Autor autor = AutorRepositorio.buscarPorNombre(nombreAutor);
                Editorial editorial = EditorialRepositorio.buscarPorNombre(nombreEditorial);
                if(autor.getAlta()==false){
                  throw new ErrorServicio("Asegurese que el autor este dado de alta");
                }
                  if(editorial.getAlta()==false){
                  throw new ErrorServicio("Asegurese que el editorial este dado de alta");
                }
       
        l.setIsbn(isbn);
        l.setTitulo(titulo);
        l.setAnio(anio);
        l.setEjemplares(ejemplares);
        l.setEjemplaresPrestados(cero);
        l.setEjemplaresRestantes(ejemplares);
        l.setAlta(true);
        l.setAutor(autor);
        l.setEditorial(editorial);
        
        LibroRepositorio.save(l);
    }

    public void modificarLibro(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, String nombreAutor, String nombreEditorial) throws ErrorServicio {
        validarLibro(isbn, titulo, anio, ejemplares, nombreAutor, nombreEditorial);

        Autor autor = AutorRepositorio.buscarPorNombre(nombreAutor);
        Editorial editorial = EditorialRepositorio.buscarPorNombre(nombreEditorial);
        Optional<Libro> respuesta = LibroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro l = respuesta.get();
            l.setIsbn(isbn);
            l.setTitulo(titulo);
            l.setAnio(anio);
            l.setEjemplares(ejemplares);
            l.setEjemplaresRestantes(l.getEjemplares()-l.getEjemplaresPrestados());
            l.setAutor(autor);
            l.setEditorial(editorial);
            
            if(ejemplares>l.getEjemplares()){
                l.setAlta(true); 
            }
            if(autor.getAlta()==false){
                  throw new ErrorServicio("Asegurese que el autor este dado de alta");
                }
            if( editorial.getAlta()==false){
                  throw new ErrorServicio("Asegurese que el editorial este dado de alta");
                }
          
            LibroRepositorio.save(l);
        } else {
            throw new ErrorServicio("No se encontro libro a modificar");
        }
    }

    public void eliminarLibro(String id) throws ErrorServicio {
        Optional<Libro> respuesta = LibroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            LibroRepositorio.deleteById(id);
        } else {
            throw new ErrorServicio("No se encontro Libro a eliminar");
        }
    }

    public Libro buscarPorId(String id) throws ErrorServicio {
        Optional<Libro> respuesta = LibroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            return libro;
        } else {
            throw new ErrorServicio("No se encontro Libro");
        }

    }

    public List<Libro> listarLibros() {
        return LibroRepositorio.findAll();
    }


    public void validarLibro(Long isbn, String titulo, Integer anio, Integer ejemplares, String nombreAutor, String nombreEditorial) throws ErrorServicio {

        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("Es necesario colocar titulo");
        }
        if (anio == null || anio < 1) {
            throw new ErrorServicio("Es necesario colocar año");
        }
        if (ejemplares == null || ejemplares < 0) {
            throw new ErrorServicio("Es necesario colocar ejemplares");
        }
       
        if (isbn == null || isbn < 0) {
            throw new ErrorServicio("Es necesario colocar isbn");
        }
        if (nombreAutor == null || nombreAutor.isEmpty()) {
            throw new ErrorServicio("Es necesario colocar nombre de autor");
        }
        if (nombreEditorial == null || nombreEditorial.isEmpty()) {
            throw new ErrorServicio("Es necesario colocar nombre del editorial");
        }
    }

}
