package com.galileo.cu.unidadesusuarios.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.galileo.cu.commons.models.Permisos;
import com.galileo.cu.commons.models.Usuarios;

@RepositoryRestResource(exported = false)
public interface PermisosRepository extends CrudRepository<Permisos, Long> {

    Permisos findByUsuarios(Usuarios usu);
    
}
