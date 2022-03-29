/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.servicios;

import java.util.List;
import java.util.Optional;
import mod16.ejercicio1.entidades.Autor;
import mod16.ejercicio1.errores.ErrorServicio;
import mod16.ejercicio1.repositorios.AutorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cristian
 */
@Service
public class AutorServicio {
    
    @Autowired
    private AutorRepositorio AutorRepositorio;
    
    public void agregarAutor(String nombre) throws ErrorServicio{
        validar(nombre);
        Autor a = new Autor();
        
      a.setNombre(nombre);
    
      AutorRepositorio.save(a);
    }

    public void modificarAutor(String nombre, String id) throws ErrorServicio{
     validar(nombre);
     Optional <Autor> respuesta = AutorRepositorio.findById(id);
        if (respuesta.isPresent()) {
        Autor a = respuesta.get();
          a.setNombre(nombre);
          a.setId(id);
    AutorRepositorio.save(a);
    } else {
            throw new ErrorServicio("No se encontro nombre solicitado");
        }
    }
    
    public void eliminarAutor(String id) throws ErrorServicio{
     Optional <Autor> respuesta = AutorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            AutorRepositorio.deleteById(id);
        }else {
            throw new ErrorServicio("No se encontro nombre solicitado");
        }
    }
    public List<Autor> listarAutores(){
         return AutorRepositorio.findAll();
         
    }
           public Autor buscarPorId(String id) throws ErrorServicio{     
     Optional <Autor> respuesta = AutorRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Autor autor = respuesta.get();
          return autor;
        }else {
            throw new ErrorServicio("No se encontro Autor");  
        }
        
    }

        public void validar(String nombre) throws ErrorServicio{ 
        if (nombre==null ||nombre.isEmpty()) {
        throw new ErrorServicio("Es necesario colocar nombre de autor");
        }      
    }
        @Transactional()
    public Autor alta(String id) {
        Autor entidad = AutorRepositorio.buscarPorId(id);
        entidad.setAlta(true);
        return AutorRepositorio.save(entidad);
    }

    @Transactional()
    public Autor baja(String id) {
        Autor entidad = AutorRepositorio.buscarPorId(id);
        entidad.setAlta(false);
        return AutorRepositorio.save(entidad);
    }       
}
