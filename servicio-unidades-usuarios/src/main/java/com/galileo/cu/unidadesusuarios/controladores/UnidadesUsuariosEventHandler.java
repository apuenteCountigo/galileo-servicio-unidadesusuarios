package com.galileo.cu.unidadesusuarios.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galileo.cu.commons.models.AccionEntidad;
import com.galileo.cu.commons.models.Permisos;
import com.galileo.cu.commons.models.TipoEntidad;
import com.galileo.cu.commons.models.Trazas;
import com.galileo.cu.commons.models.Unidades;
import com.galileo.cu.commons.models.UnidadesUsuarios;
import com.galileo.cu.commons.models.Usuarios;
import com.galileo.cu.unidadesusuarios.repositorios.PermisosRepository;
import com.galileo.cu.unidadesusuarios.repositorios.TrazasRepository;
import com.galileo.cu.unidadesusuarios.repositorios.UnidadesRepository;
import com.galileo.cu.unidadesusuarios.repositorios.UnidadesUsuariosRepository;
import com.galileo.cu.unidadesusuarios.repositorios.UsuariosRepository;

@Component
@RepositoryEventHandler(UnidadesUsuarios.class)
public class UnidadesUsuariosEventHandler {

    @Autowired
	UnidadesUsuariosRepository uniUserRepo;

	@Autowired
	HttpServletRequest req;

	@Autowired
	TrazasRepository trazasRepo;

	@Autowired
	UnidadesRepository uniRepo;

    @Autowired
	UsuariosRepository usuRepo;

	@Autowired
	PermisosRepository perRepo;
	
	@Autowired
    ObjectMapper objectMapper;

	String descripcionTraza;
    
    @HandleBeforeCreate
    public void handleUnidadesBeforeCreate(UnidadesUsuarios uu){
        /*Validando Autorización */
		ValidateAuthorization val= new ValidateAuthorization();
		try {
			System.out.println("REQUEST HandleBeforeCreate: "+req.getMethod());
			val.setObjectMapper(objectMapper);
			val.setReq(req);
			if (!val.Validate()) {
				throw new RuntimeException("Fallo el Usuario Enviado no Coincide con el Autenticado ");
			}
		} catch (Exception e) {
			System.out.println("Fallo Antes de Crear UnidadesUsuarios Validando Autorización: "+e.getMessage());
			throw new RuntimeException("Fallo Antes de Crear UnidadesUsuarios Validando Autorización: "+e.getMessage());
		}

        Usuarios usu = usuRepo.findById(uu.getUsuario().getId()).get();
        if (usu.getPerfil().getId() == 1) {
            System.out.println("Fallo, No se Puede Asignar un Usuario Super Administrador a una Unidad");
            throw new RuntimeException("No se Puede Asignar un Usuario Super Administrador a una Unidad");
        }
    }

    @HandleAfterCreate
    public void handleUnidadesAfterCreate(UnidadesUsuarios uu){
        /*Validando Autorización */
		ValidateAuthorization val= new ValidateAuthorization();
		try {
			System.out.println("REQUEST HandleAfterCreate: "+req.getMethod());
			val.setObjectMapper(objectMapper);
			val.setReq(req);
			if (!val.Validate()) {
				throw new RuntimeException("Fallo el Usuario Enviado no Coincide con el Autenticado ");
			}
		} catch (Exception e) {
			System.out.println("Fallo Despues de Crear UnidadesUsuarios Validando Autorización: "+e.getMessage());
			throw new RuntimeException("Fallo Despues de Crear UnidadesUsuarios Validando Autorización: "+e.getMessage());
		}

        Usuarios usu = usuRepo.findById(uu.getUsuario().getId()).get();
        Unidades uni = uniRepo.findById(uu.getUnidad().getId()).get();
        
        ActualizarTraza(val, uu.getId(), 4, 1, "Fue Asignado el Usuario " + usu.getTip()+" a la Unidad "+uni.getDenominacion(),
				"Fallo Insertando la Asignación del Usuario a la Unidad en la Trazabilidad");

		if (uu.getEstado().getId() == 6) {
			usu.setUnidad(uni);
			usuRepo.save(usu);
		}
    }

    @HandleBeforeDelete
    public void handleUnidadesBeforeDelete(UnidadesUsuarios uu){
        /*Validando Autorización */
		ValidateAuthorization val= new ValidateAuthorization();
		try {
			System.out.println("REQUEST HandleBeforeCreate: "+req.getMethod());
			val.setObjectMapper(objectMapper);
			val.setReq(req);
			if (!val.Validate()) {
				throw new RuntimeException("Fallo el Usuario Enviado no Coincide con el Autenticado ");
			}
		} catch (Exception e) {
			System.out.println("Fallo Antes de Eliminar UnidadesUsuarios Validando Autorización: "+e.getMessage());
			throw new RuntimeException("Fallo Antes de Eliminar UnidadesUsuarios Validando Autorización: "+e.getMessage());
		}
    }

    @HandleAfterDelete
    public void handleUnidadesAfterDelete(UnidadesUsuarios uu){
        /*Validando Autorización */
		ValidateAuthorization val= new ValidateAuthorization();
		try {
			System.out.println("REQUEST HandleAfterDelete: "+req.getMethod());
			val.setObjectMapper(objectMapper);
			val.setReq(req);
			if (!val.Validate()) {
				throw new RuntimeException("Fallo el Usuario Enviado no Coincide con el Autenticado ");
			}
		} catch (Exception e) {
			System.out.println("Fallo Despues de Eliminar UnidadesUsuarios Validando Autorización: "+e.getMessage());
			throw new RuntimeException("Fallo Despues de Eliminar UnidadesUsuarios Validando Autorización: "+e.getMessage());
		}

		try {
			ActualizarTraza(val, uu.getId(), 4, 2, "Fue Desasignado el Usuario " + uu.getUsuario().getTip()+" de la Unidad "+uu.getUnidad().getDenominacion(),
				"Fallo Insertando la Desasignación del Usuario a la Unidad en la Trazabilidad");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

		try {
			Permisos per = perRepo.findByUsuarios(uu.getUsuario());
			if (per != null) {
				perRepo.delete(per);
			}
		} catch (Exception e) {
			System.out.println("Fallo La Eliminación de los Permisos del Usuario en Operaciones y Objetivos");
			System.out.println(e.getMessage());
			throw new RuntimeException("Fallo La Eliminación de los Permisos del Usuario en Operaciones y Objetivos");
		}
        
        try {
			if (uu.getEstado().getId() == 6) {
				Usuarios usu = uu.getUsuario();
				usu.setUnidad(null);
				usuRepo.save(usu);
			}
		} catch (Exception e) {
			System.out.println("Fallo al Eliminar la Unidad en el Usuario");
			System.out.println(e.getMessage());
			throw new RuntimeException("Fallo al Eliminar la Unidad en el Usuario");
		}
    }

    private void ActualizarTraza(ValidateAuthorization val, long idEntidad, int idTipoEntidad,
			int idAccion, String trazaDescripcion, String errorMessage) {
		try {
			System.out.println("Unidades Usuarios Trazabilidad");
			Trazas traza = new Trazas();
			AccionEntidad accion = new AccionEntidad();
			Usuarios usuario = new Usuarios();
			TipoEntidad entidad = new TipoEntidad();

			entidad.setId(idTipoEntidad);
			accion.setId(idAccion);
			usuario.setId(Long.parseLong(val.getJwtObjectMap().getId()));

			traza.setAccionEntidad(accion);
			traza.setTipoEntidad(entidad);
			traza.setUsuario(usuario);
			traza.setIdEntidad((int)idEntidad);
			traza.setDescripcion(trazaDescripcion);
			trazasRepo.save(traza);
		} catch (Exception e) {
			System.out.println(errorMessage);
			System.out.println(e.getMessage());
			throw new RuntimeException(errorMessage);
		}
	}
}
