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
import mod16.ejercicio1.entidades.Editorial;
import mod16.ejercicio1.entidades.Libro;
import mod16.ejercicio1.errores.ErrorServicio;
import mod16.ejercicio1.servicios.AutorServicio;
import mod16.ejercicio1.servicios.EditorialServicio;
import mod16.ejercicio1.servicios.LibroServicio;
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
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroservicio;
    @Autowired
    private AutorServicio autorservicio;
    @Autowired
    private EditorialServicio editorialservicio;

    //metodos CRUD
//LISTAR
    @GetMapping("/listadelibros")
    public String libros(ModelMap modelo) {
        List<Libro> listaLibros = libroservicio.listarLibros();
        modelo.put("listaLibros", listaLibros);
        return "libros.html";
    }
//AGREGAR

    @GetMapping("/agregarlibro")
    public String agregarLibro(ModelMap modelo) {
        List<Autor> autores = autorservicio.listarAutores();
        modelo.put("autores", autores);
        List<Editorial> editoriales = editorialservicio.listarEditoriales();
        modelo.put("editoriales", editoriales);
        return "agregarlibro.html";
    }

    @PostMapping("/registrarLibro")
    public String registrarLibro(ModelMap modelo, @RequestParam(required = false) String titulo, @RequestParam(required = false) String nombreAutor, @RequestParam(required = false) String nombreEditorial, @RequestParam(required = false) Integer anio, @RequestParam(required = false) Long isbn, @RequestParam(required = false) Integer ejemplares) {
        try {
            libroservicio.agregarLibro(isbn, titulo, anio, ejemplares, nombreAutor, nombreEditorial);
            modelo.put("exito", "Guardado con exito");
            List<Autor> autores = autorservicio.listarAutores();
            modelo.put("autores", autores);
            List<Editorial> editoriales = editorialservicio.listarEditoriales();
            modelo.put("editoriales", editoriales);
            return "agregarlibro";
        } catch (ErrorServicio ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            List<Autor> autores = autorservicio.listarAutores();
            modelo.put("autores", autores);
            List<Editorial> editoriales = editorialservicio.listarEditoriales();
            modelo.put("editoriales", editoriales);
            return "agregarlibro";
        }
    }

//EDITAR
    @RequestMapping("/libroeditar/{id}")
    public ModelAndView editarLibro(@PathVariable(name = "id") String id, ModelMap modelo2) throws ErrorServicio {
        ModelAndView modelo = new ModelAndView("editarlibro");
        Libro libro = libroservicio.buscarPorId(id);
        modelo.addObject("libro", libro);
        List<Autor> autores = autorservicio.listarAutores();
        modelo2.put("autores", autores);
        List<Editorial> editoriales = editorialservicio.listarEditoriales();
        modelo2.put("editoriales", editoriales);
        return modelo;
    }

    @PostMapping("/guardarlibroeditado")
    public String guardarLibroEditado(ModelMap modelo, @RequestParam(required = false) String id, @RequestParam String titulo, @RequestParam(required = false) String nombreAutor, @RequestParam(required = false) String nombreEditorial, @RequestParam(required = false) Integer anio, @RequestParam(required = false) Long isbn, @RequestParam(required = false) Integer ejemplares) throws ErrorServicio {
        try {
            libroservicio.modificarLibro(id, isbn, titulo, anio, ejemplares, nombreAutor, nombreEditorial);
            modelo.put("exito", "Guardado con exito");
            Libro libro = libroservicio.buscarPorId(id);
            modelo.put("libro", libro);
            List<Autor> autores = autorservicio.listarAutores();
            modelo.put("autores", autores);
            List<Editorial> editoriales = editorialservicio.listarEditoriales();
            modelo.put("editoriales", editoriales);
            return "editarlibro";
        } catch (ErrorServicio ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            Libro libro = libroservicio.buscarPorId(id);
            modelo.put("libro", libro);
            List<Autor> autores = autorservicio.listarAutores();
            modelo.put("autores", autores);
            List<Editorial> editoriales = editorialservicio.listarEditoriales();
            modelo.put("editoriales", editoriales);
            return "editarlibro";
        }
    }

    //ELIMINAR
    @RequestMapping("/eliminarlibro/{id}")
    public String eliminarLibro(@PathVariable(name = "id") String id) {
        try {
            libroservicio.eliminarLibro(id);
            return "redirect:/libro/listadelibros";
        } catch (ErrorServicio ex) {
            Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/libro/listadelibros";
        }
    }


}
