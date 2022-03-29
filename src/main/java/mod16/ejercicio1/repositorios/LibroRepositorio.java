/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.repositorios;

import java.util.List;
import mod16.ejercicio1.entidades.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cristian
 */
@Repository
public interface LibroRepositorio extends JpaRepository<Libro, String>{
    
    @Query("SELECT l FROM Libro l WHERE l.id = :id")
    public Libro buscarPorId(@Param("id") String id);
    
       @Query("SELECT a FROM Libro a WHERE a.titulo = :titulo")
    public Libro buscarPorTitulo(@Param("titulo") String titulo);
    
     @Query("SELECT a FROM Libro a WHERE a.autor.nombre = :nombre")
    public Libro buscarPorAutor(@Param("nombre") String nombre);
    
     @Query("SELECT a FROM Libro a WHERE a.editorial.nombre = :nombre")
    public Libro buscarPorEditorial(@Param("nombre") String nombre);
    
}