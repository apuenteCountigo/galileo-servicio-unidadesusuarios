package com.galileo.cu.unidadesusuarios.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.galileo.cu.commons.models.Conexiones;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface ConexionesRepository extends JpaRepository<Conexiones, Long> {
    List<Conexiones> findByServicio(String servicio);
}
