package com.galileo.cu.unidadesusuarios.repositorios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.galileo.cu.commons.models.Permisos;
import com.galileo.cu.commons.models.TipoEntidad;
import com.galileo.cu.commons.models.Usuarios;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface PermisosRepository extends CrudRepository<Permisos, Long> {

    List<Permisos> findByUsuarios(Usuarios usu);

    List<Permisos> findByUsuariosAndIdEntidad(Usuarios usu, int idEntidad);

    List<Permisos> findByUsuariosAndIdEntidadAndTipoEntidad(Usuarios usu, long idEntidad, TipoEntidad tipoEntidad);

}
