package com.galileo.cu.unidadesusuarios.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.galileo.cu.commons.models.Objetivos;
import com.galileo.cu.commons.models.Unidades;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface ObjetivosRepository extends CrudRepository<Objetivos, Long> {
    List<Objetivos> findByTraccarID(Long traccarID);

    List<Objetivos> findByTraccarIDAndOperaciones_Unidades(Long traccarID, Unidades unidad);
}
