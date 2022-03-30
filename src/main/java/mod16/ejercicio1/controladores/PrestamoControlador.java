/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.controladores;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mod16.ejercicio1.entidades.Cliente;
import mod16.ejercicio1.entidades.Libro;
import mod16.ejercicio1.entidades.Prestamo;
import mod16.ejercicio1.errores.ErrorServicio;
import mod16.ejercicio1.servicios.ClienteServicio;
import mod16.ejercicio1.servicios.LibroServicio;
import mod16.ejercicio1.servicios.PrestamoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/prestamo")
public class PrestamoControlador {
    
    @Autowired
    private PrestamoServicio prestamoservicio;
       @Autowired
    private LibroServicio libroservicio;
          @Autowired
    private ClienteServicio clienteservicio;

    //metodos CRUD
//LISTAR
    @GetMapping("/listadeprestamos")
    public String Prestamos(ModelMap modelo) {
        List<Prestamo> listaPrestamos = prestamoservicio.listarPrestamos();
        modelo.put("listaPrestamos", listaPrestamos);
        return "prestamos.html";
    }

//AGREGAR
    @GetMapping("/agregarprestamo")
    public String agregarPrestamo(ModelMap modelo) {
           List<Libro> libros = libroservicio.listarLibros();
            modelo.put("libros", libros);
            List<Cliente> clientes = clienteservicio.listarClientes();
            modelo.put("clientes", clientes);
        return "agregarprestamo.html";
    }

    @PostMapping("/registrarprestamo")
    public String registrarPrestamo(ModelMap modelo, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaPrestamo,
            @DateTimeFormat(fallbackPatterns ="yyyy-MM-dd") Date fechaDevolucion, @RequestParam String nombreLibro,@RequestParam String nombreCliente) {
  
        try {
            prestamoservicio.agregarPrestamo(fechaPrestamo, fechaDevolucion, nombreLibro, nombreCliente);
            modelo.put("exito", "Guardado con exito");
            List<Libro> libros = libroservicio.listarLibros();
            modelo.put("libros", libros);
            List<Cliente> clientes = clienteservicio.listarClientes();
            modelo.put("clientes", clientes);
            return "agregarprestamo";
        } catch (ErrorServicio ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
            List<Libro> libros = libroservicio.listarLibros();
            modelo.put("libros", libros);
            List<Cliente> clientes = clienteservicio.listarClientes();
            modelo.put("clientes", clientes);
            return "agregarprestamo";
        }
    }

//EDITAR
    @RequestMapping("/editarprestamo/{id}")
    public ModelAndView editarPrestamo(@PathVariable(name = "id") String id, ModelMap modelo2) throws ErrorServicio {
        ModelAndView modelo = new ModelAndView("editarprestamo");
        Prestamo prestamo = prestamoservicio.buscarPorId(id);
        modelo.addObject("prestamo", prestamo);
     List<Libro> libros = libroservicio.listarLibros();
            modelo2.put("libros", libros);
            List<Cliente> clientes = clienteservicio.listarClientes();
            modelo2.put("clientes", clientes);
        return modelo;
    }

    @PostMapping("/guardarprestamoeditado")
    public String guardarPrestamoEditado(ModelMap modelo, @RequestParam String id, @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaPrestamo,
             @DateTimeFormat(fallbackPatterns ="yyyy-MM-dd") Date fechaDevolucion, String nombreLibro, String nombreCliente) throws ErrorServicio {
        try {
            prestamoservicio.modificarPrestamo(id, fechaPrestamo, fechaDevolucion, nombreLibro, nombreCliente);
            modelo.put("exito", "Guardado con exito");
            
            Prestamo prestamo = prestamoservicio.buscarPorId(id);
            modelo.put("prestamo", prestamo);
            List<Libro> libros = libroservicio.listarLibros();
            modelo.put("libros", libros);
            List<Cliente> clientes = clienteservicio.listarClientes();
            modelo.put("clientes", clientes);
            return "editarprestamo";
        } catch (ErrorServicio ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            modelo.put("error", ex.getMessage());
           
          Prestamo prestamo = prestamoservicio.buscarPorId(id);
            modelo.put("prestamo", prestamo);
            List<Libro> libros = libroservicio.listarLibros();
            modelo.put("libros", libros);
            List<Cliente> clientes = clienteservicio.listarClientes();
            modelo.put("clientes", clientes);
            return "editarprestamo";
        }
    }

    //ELIMINAR
    @RequestMapping("/eliminarprestamo/{id}")
    public String eliminarPrestamo(@PathVariable(name = "id") String id) {
        try {
            prestamoservicio.eliminarPrestamo(id);
            return "redirect:/prestamo/listadeprestamos";
        } catch (ErrorServicio ex) {
            Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, ex);
            return "redirect:/prestamo/listadeprestamos";
        }
    }
//     //ALTA Y BAJA 
//  
//    @GetMapping("/baja/{id}")
//    public String baja(@PathVariable String id, ModelMap modelo) {
//        try {
//            prestamoservicio.baja(id);
//        
//            return "redirect:/prestamo/listadeprestamos";
//        } catch (Exception e) {
//           Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, e);                   
//            modelo.addAttribute("error", e.getMessage());        
//            return "redirect:/prestamo/listadeprestamos";
//        }
//
//    }
//
//    @GetMapping("/alta/{id}")
//    public String alta(@PathVariable String id, ModelMap modelo) {
//
//        try {
//           libroservicio.alta(id);
//            return "redirect:/prestamo/listadeprestamos";
//        } catch (Exception e) {
//              Logger.getLogger(LibroControlador.class.getName()).log(Level.SEVERE, null, e);
//             modelo.put("error", e.getMessage());
//            return "redirect:/prestamo/listadeprestamos";
//        }
//    }

}
