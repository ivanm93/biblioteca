/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.servicios;

import java.util.List;
import java.util.Optional;
import mod16.ejercicio1.entidades.Cliente;
import mod16.ejercicio1.errores.ErrorServicio;
import mod16.ejercicio1.repositorios.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cristian
 */
@Service
public class ClienteServicio {
     @Autowired
    private ClienteRepositorio clienteRepositorio;
    
    public void agregarCliente(Long documento, String nombre, String apellido, String telefono ) throws ErrorServicio{
        validar(documento, nombre, apellido, telefono);
        Cliente c = new Cliente();
        
      c.setAlta(true);
     c.setApellido(apellido);
     c.setDocumento(documento);
     c.setNombre(nombre);
     c.setTelefono(telefono);
    
      clienteRepositorio.save(c);
    }

    public void modificarCliente(String id, Long documento, String nombre, String apellido, String telefono) throws ErrorServicio{
       validar(documento, nombre, apellido, telefono);
     Optional <Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
        Cliente c = respuesta.get();
        
     c.setApellido(apellido);
     c.setDocumento(documento);
     c.setNombre(nombre);
     c.setTelefono(telefono);
      clienteRepositorio.save(c);
    
    } else {
            throw new ErrorServicio("No se encontro cliente");
        }
    }
    
    public void eliminarCliente(String id) throws ErrorServicio{
     Optional <Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            clienteRepositorio.deleteById(id);
        }else {
            throw new ErrorServicio("No se encontro cliente a eliminar");
        }
    }
    public List<Cliente> listarClientes(){
         return clienteRepositorio.findAll();
         
    }
           public Cliente buscarPorId(String id) throws ErrorServicio{     
     Optional <Cliente> respuesta = clienteRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
          return cliente;
        }else {
            throw new ErrorServicio("No se encontro cliente");  
        }   
    }
                 @Transactional()
    public Cliente alta(String id) {
        Cliente entidad = clienteRepositorio.buscarPorId(id);
        entidad.setAlta(true);
        return clienteRepositorio.save(entidad);
    }

    @Transactional()
    public Cliente baja(String id) {
        Cliente entidad = clienteRepositorio.buscarPorId(id);
        entidad.setAlta(false);
        return clienteRepositorio.save(entidad);
    }   
        public void validar(Long documento, String nombre, String apellido, String telefono) throws ErrorServicio{ 

               if (documento==null ||documento<0) {
        throw new ErrorServicio("Es necesario colocar documento");
        }   
                  if (nombre==null ||nombre.isEmpty()) {
        throw new ErrorServicio("Es necesario colocar nombre");
        }  
                     if (apellido==null ||apellido.isEmpty()) {
        throw new ErrorServicio("Es necesario colocar apellido");
        }  
                        if (telefono==null ||telefono.isEmpty()) {
        throw new ErrorServicio("Es necesario colocar telefono");
        }  
  }   
}
