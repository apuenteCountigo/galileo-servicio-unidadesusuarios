package com.galileo.cu.unidadesusuarios.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.galileo.cu.commons.models.Operaciones;
import com.galileo.cu.commons.models.Unidades;

@RepositoryRestResource(exported = false)
public interface OperacionesRepository extends CrudRepository<Operaciones, Long> {
    List<Operaciones> findByIdGrupoAndUnidades(Long idGrupo, Unidades unidad);
}
