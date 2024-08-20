package com.galileo.cu.unidadesusuarios.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.galileo.cu.commons.models.Unidades;

@RepositoryRestResource(exported = false)
public interface UnidadesRepository extends CrudRepository<Unidades, Long>{
    
}
