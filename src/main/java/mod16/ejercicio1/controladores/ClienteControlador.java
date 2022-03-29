/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mod16.ejercicio1.entidades.Cliente;
import mod16.ejercicio1.errores.ErrorServicio;
import mod16.ejercicio1.servicios.ClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {
      @Autowired
    private ClienteServicio clienteservicio;

    //metodos CRUD
//LISTAR
    @GetMapping("/listadeclientes")
    public String Clientes(ModelMap modelo) {
        List<Cliente> listaClientes = clienteservicio.listarClientes();
        modelo.put("listaClientes", listaClientes);
        return "clientes.html";
    }

//AGREGAR
    @GetMapping("/agregarcliente")
    public String agregarCliente() {
        return "agregarcliente.html";
    }

    @PostMapping("/registrarcliente")
    public String registrarCliente(ModelMap modelo, @RequestParam(required=false) Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono) {
        try {
            
            clienteservicio.agregarCliente( documento, nombre, apellido, telefono);
            modelo.put("exito", "Guardado con exito");
            return "agregarcliente";
        } catch (ErrorServicio ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            return "agregarcliente";
        }
    }
//EDITAR  

    @GetMapping("/editarcliente/{id}")
    public ModelAndView editarCliente(@PathVariable(name = "id") String id) throws ErrorServicio {
        ModelAndView modelo = new ModelAndView("editarcliente");
        Cliente cliente = clienteservicio.buscarPorId(id);
        modelo.addObject("cliente", cliente);
        return modelo;
    }

    @PostMapping("/guardarclienteeditado")
    public String guardarClienteEditado(ModelMap modelo,@RequestParam                                                                                               String id, @RequestParam Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono)throws ErrorServicio {
        try {
            clienteservicio.modificarCliente(id,documento, nombre, apellido, telefono);
            modelo.put("exito", "Guardado con exito");
            Cliente cliente = clienteservicio.buscarPorId(id);
            modelo.put("cliente", cliente);
            return "editarcliente";
        } catch (ErrorServicio ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            Cliente cliente = clienteservicio.buscarPorId(id);
            modelo.put("cliente", cliente);
            return "editarcliente";
        }
    }
    //ELIMINAR

    @GetMapping("/eliminarcliente/{id}")
    public String eliminarCliente(ModelMap modelo, @PathVariable(name = "id") String id) {
        try {
            clienteservicio.eliminarCliente(id);
            modelo.put("exito", "Eliminado");
            return "redirect:/cliente/listadeclientes";
            
        } catch (ErrorServicio ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            return "redirect:/cliente/listadeclientes";
        }
    } 
     //ALTA Y BAJA 
  
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        try {
            clienteservicio.baja(id);
            return "redirect:/cliente/listadeclientes";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {

        try {
           clienteservicio.alta(id);
            return "redirect:/cliente/listadeclientes";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
