package com.galileo.cu.unidadesusuarios.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.galileo.cu.commons.models.Usuarios;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface UsuariosRepository extends CrudRepository<Usuarios, Long> {

}
