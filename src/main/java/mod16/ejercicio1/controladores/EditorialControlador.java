/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.controladores;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mod16.ejercicio1.entidades.Editorial;
import mod16.ejercicio1.errores.ErrorServicio;
import mod16.ejercicio1.servicios.EditorialServicio;
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
@RequestMapping("/editorial")
public class EditorialControlador {

    @Autowired
    private EditorialServicio editorialservicio;

//metodos CRUD
//LISTAR
    @GetMapping("/listadeeditoriales")
    public String editoriales(ModelMap modelo) {
        List<Editorial> listaEditoriales = editorialservicio.listarEditoriales();
        modelo.put("listaEditoriales", listaEditoriales);
        return "editoriales.html";
    }
//AGREGAR

    @GetMapping("/agregareditorial")
    public String agregarEditorial() {
        return "agregareditorial.html";
    }

    @PostMapping("/registrarEditorial")
    public String registrarEditorial(ModelMap modelo, @RequestParam String nombre) {
        try {
            editorialservicio.agregarEditorial(nombre);
            modelo.put("exito", "Guardado con exito");
            return "agregareditorial";
        } catch (ErrorServicio ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", "Formulario incompleto");
            return "agregareditorial";
        }

    }
//EDITAR

    @RequestMapping("/editareditorial/{id}")
    public ModelAndView editarEditorial(@PathVariable(name = "id") String id) throws ErrorServicio {
        ModelAndView modelo = new ModelAndView("editareditorial");
        Editorial editorial = editorialservicio.buscarPorId(id);
        modelo.addObject("editorial", editorial);
        return modelo;
    }

    @PostMapping("/guardareditorialeditado")
    public String guardarEditorialEditado(ModelMap modelo, @RequestParam String nombre, @RequestParam String id) throws ErrorServicio {
        try {
            editorialservicio.modificarEditorial(nombre, id);
            modelo.put("exito", "Guardado con exito");
            Editorial editorial = editorialservicio.buscarPorId(id);
            modelo.put("editorial", editorial);
            return "editareditorial";
        } catch (ErrorServicio ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", "Formulario incompleto");
            Editorial editorial = editorialservicio.buscarPorId(id);
            modelo.put("editorial", editorial);
            return "editareditorial";
        }
    }

    //ELIMINAR
    @RequestMapping("/eliminareditorial/{id}")
    public String eliminarEditorial(@PathVariable(name = "id") String id) {
        try {
            editorialservicio.eliminarEditorial(id);
            return "redirect:/editorial/listadeeditoriales";
        } catch (ErrorServicio ex) {
            Logger.getLogger(EditorialControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/editorial/listadeeditoriales";
        }
    }
     //ALTA Y BAJA 
  
    @GetMapping("/baja/{id}")
    public String baja(@PathVariable String id) {
        try {
            editorialservicio.baja(id);
            return "redirect:/editorial/listadeeditoriales";
        } catch (Exception e) {
            return "redirect:/";
        }

    }

    @GetMapping("/alta/{id}")
    public String alta(@PathVariable String id) {

        try {
           editorialservicio.alta(id);
            return "redirect:/editorial/listadeeditoriales";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
