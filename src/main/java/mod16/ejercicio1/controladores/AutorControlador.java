/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mod16.ejercicio1.entidades.Autor;
import mod16.ejercicio1.errores.ErrorServicio;
import mod16.ejercicio1.servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorservicio;

    //metodos CRUD
//LISTAR
    @GetMapping("/listadeautores")
    public String autores(ModelMap modelo) {
        List<Autor> listaAutores = autorservicio.listarAutores();
        modelo.put("listaAutores", listaAutores);
        return "autores.html";
    }

//AGREGAR
    @GetMapping("/agregarautor")
    public String agregarAutor() {
        return "agregarautor.html";
    }

    @PostMapping("/registrarAutor")
    public String registrarAutor(ModelMap modelo, @RequestParam String nombre) {
        try {
            autorservicio.agregarAutor(nombre);
            modelo.put("exito", "Guardado con exito");
            return "agregarautor";
        } catch (ErrorServicio ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", "Formulario incompleto");
            return "agregarautor";
        }
    }
//EDITAR  

    @GetMapping("/editarautor/{id}")
    public ModelAndView editarAutor(@PathVariable(name = "id") String id) throws ErrorServicio {
        ModelAndView modelo = new ModelAndView("editarautor");
        Autor autor = autorservicio.buscarPorId(id);
        modelo.addObject("autor", autor);
        return modelo;
    }

    @PostMapping("/guardarautoreditado")
    public String guardarAutorEditado(ModelMap modelo, @RequestParam String nombre, @RequestParam String id) throws ErrorServicio {
        try {
            
            autorservicio.modificarAutor(nombre, id);
            modelo.put("exito", "Guardado con exito");
            Autor autor = autorservicio.buscarPorId(id);
            modelo.put("autor", autor);
            return "editarautor";
        } catch (ErrorServicio ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", "Formulario incompleto");
            Autor autor = autorservicio.buscarPorId(id);
            modelo.put("autor", autor);
            return "editarautor";
        }
    }
    //ELIMINAR

    @RequestMapping("/eliminarautor/{id}")
    public String eliminarAutor(ModelMap modelo, @PathVariable(name = "id") String id) {
        try {
            autorservicio.eliminarAutor(id);
            modelo.put("exito", "Eliminado");
            return "redirect:/autor/listadeautores";
            
        } catch (ErrorServicio ex) {
            Logger.getLogger(AutorControlador.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            return "redirect:/autor/listadeautores";
        }
    }
 //ALTA Y BAJA 
  
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        try {
            autorservicio.baja(id);
            return "redirect:/autor/listadeautores";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {

        try {
           autorservicio.alta(id);
            return "redirect:/autor/listadeautores";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
