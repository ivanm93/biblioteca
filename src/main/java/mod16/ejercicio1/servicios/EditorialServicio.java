/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.servicios;

import java.util.List;
import java.util.Optional;
import mod16.ejercicio1.entidades.Editorial;
import mod16.ejercicio1.errores.ErrorServicio;
import mod16.ejercicio1.repositorios.EditorialRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cristian
 */
@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepositorio EditorialRepositorio;

    public void agregarEditorial(String nombre) throws ErrorServicio {
        validar(nombre);
        Editorial e = new Editorial();

        e.setNombre(nombre);
        e.setAlta(true);
        EditorialRepositorio.save(e);
    }

    public void modificarEditorial(String nombre, String id) throws ErrorServicio {
        validar(nombre);
        Optional<Editorial> respuesta = EditorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial e = respuesta.get();
            e.setNombre(nombre);
            EditorialRepositorio.save(e);
        } else {
            throw new ErrorServicio("No se encontro nombre solicitado");
        }
    }

    public void eliminarEditorial( String id) throws ErrorServicio {
     
        Optional<Editorial> respuesta = EditorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            EditorialRepositorio.deleteById(id);
        } else {
            throw new ErrorServicio("No se encontro nombre solicitado");
        }
    }

    public List<Editorial> listarEditoriales() {
        return EditorialRepositorio.findAll();

    }
      public Editorial buscarPorId(String id) throws ErrorServicio{     
     Optional <Editorial> respuesta = EditorialRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Editorial editorial = respuesta.get();
          return editorial;
        }else {
            throw new ErrorServicio("No se encontro Editorial");  
        }
        
    }
            @Transactional()
    public Editorial alta(String id) {
        Editorial entidad = EditorialRepositorio.buscarPorId(id);
        entidad.setAlta(true);
        return EditorialRepositorio.save(entidad);
    }

    @Transactional()
    public Editorial baja(String id) {
        Editorial entidad = EditorialRepositorio.buscarPorId(id);
        entidad.setAlta(false);
        return EditorialRepositorio.save(entidad);
    }   

public void validar(String nombre) throws ErrorServicio{ 
        if (nombre==null ||nombre.isEmpty()) {
        throw new ErrorServicio("Es necesario colocar nombre de autor");
        }
        
    }
           
}
