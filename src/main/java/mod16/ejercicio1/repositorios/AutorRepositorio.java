/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.repositorios;

import mod16.ejercicio1.entidades.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cristian
 */
@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {
 
      @Query("SELECT a FROM Autor a WHERE a.id = :id")
    public Autor buscarPorId(@Param("id") String id);
    
    @Query("SELECT a FROM Autor a WHERE a.nombre = :nombre")
    public Autor buscarPorNombre(@Param("nombre") String nombre);
    
    }

