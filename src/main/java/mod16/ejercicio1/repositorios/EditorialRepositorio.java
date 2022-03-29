/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod16.ejercicio1.repositorios;

import java.util.List;
import mod16.ejercicio1.entidades.Editorial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author cristian
 */
@Repository
public interface EditorialRepositorio extends JpaRepository<Editorial, String>{
    
      @Query("SELECT l FROM Editorial l WHERE l.id = :id")
    public Editorial buscarPorId(@Param("id") String id);
    
        @Query("SELECT e FROM Editorial e WHERE e.nombre = :nombre")
    public Editorial buscarPorNombre(@Param("nombre") String nombre);

}


