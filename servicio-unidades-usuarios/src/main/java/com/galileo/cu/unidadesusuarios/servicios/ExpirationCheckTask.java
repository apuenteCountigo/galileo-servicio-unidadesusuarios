package com.galileo.cu.unidadesusuarios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.galileo.cu.commons.models.Conexiones;
import com.galileo.cu.commons.models.Objetivos;
import com.galileo.cu.commons.models.Permisos;
import com.galileo.cu.commons.models.Unidades;
import com.galileo.cu.commons.models.UnidadesUsuarios;
import com.galileo.cu.commons.models.Usuarios;
import com.galileo.cu.commons.models.dto.BodyDelDevicePermissions;
import com.galileo.cu.commons.models.dto.BodyDelGroupPermissions;
import com.galileo.cu.commons.models.dto.DevicesTraccar;
import com.galileo.cu.commons.models.dto.GroupsTraccar;
import com.galileo.cu.unidadesusuarios.clientes.TraccarClient;
import com.galileo.cu.unidadesusuarios.repositorios.ConexionesRepository;
import com.galileo.cu.unidadesusuarios.repositorios.ExpiraUserRepository;
import com.galileo.cu.unidadesusuarios.repositorios.ObjetivosRepository;
import com.galileo.cu.unidadesusuarios.repositorios.OperacionesRepository;
import com.galileo.cu.unidadesusuarios.repositorios.PermisosRepository;
import com.galileo.cu.unidadesusuarios.repositorios.UnidadesUsuariosRepository;
import com.galileo.cu.unidadesusuarios.repositorios.UsuariosRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ExpirationCheckTask {
    @Autowired
    private ExpiraUserRepository expiraUserRepository;

    @Autowired
    private UsuariosRepository usersRepository;

    @Autowired
    private TraccarClient traccarClient;

    @Autowired
    PermisosRepository perRepo;

    @Autowired
    ObjetivosRepository objRepo;

    @Autowired
    OperacionesRepository operRepo;

    @Autowired
    private UnidadesUsuariosRepository uuRepo;

    @Autowired
    private ConexionesRepository conRepo;

    // @Scheduled(cron = "0 0 0 * * *") // Ejecutar todos los días a las 00:00
    @Scheduled(cron = "0 0 23 * * *")
    public void checkForExpiredRecords() {
        log.info("::::::EXPIRANDO::::: ");
        // LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        // Obtener la fecha y hora actual, truncando los nanosegundos para una
        // comparación precisa
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault()).withNano(0);

        List<UnidadesUsuarios> expiredRecords = null;
        try {
            expiredRecords = expiraUserRepository.findByExpiraBefore(now);
            log.info("::::::NOW==" + now.toString());
            log.info("::::::QUANTY==" + expiredRecords.size());
        } catch (Exception e) {
            log.error("*******Fallo consultando los usuarios expirados: ", e.getMessage());
            throw new RuntimeException("Fallo consultando los usuarios expirados");
        }

        for (UnidadesUsuarios record : expiredRecords) {
            log.info("Registro expirado: " + record.getUsuario().getTraccarID());

            Unidades unidad = record.getUnidad();

            List<DevicesTraccar> resDevices = null;
            try {
                resDevices = traccarClient.getDevices(record.getUsuario().getTraccarID().toString());
            } catch (Exception e) {
                log.error("*******Fallo consultando dispocitivos en traccar: ", e.getMessage());
                throw new RuntimeException("Fallo consultando dispocitivos en traccar");
            }

            for (DevicesTraccar dt : resDevices) {
                try {
                    BodyDelDevicePermissions bDDP = new BodyDelDevicePermissions(record.getUsuario().getTraccarID(),
                            dt.getId());
                    List<Objetivos> objs = objRepo
                            .findByTraccarIDAndOperaciones_Unidades(Long.valueOf(dt.getId()), unidad);
                    log.info("######" + dt.getId() + ":::" + objs.size());
                    if (objs.size() > 0) {
                        log.info("****Eliminando Permiso en dispositivo");
                    }
                    // try {
                    // traccarClient.delDevices(bDDP);
                    // } catch (Exception e) {
                    // log.error("*******Fallo eliminando dispositivo en traccar: ",
                    // e.getMessage());
                    // throw new RuntimeException("Fallo eliminando dispositivo en traccar");
                    // }
                } catch (Exception e) {
                    log.error("*******Fallo iterando dispositivos en traccar: ", e.getMessage());
                    throw new RuntimeException("Fallo iterando dispositivos en traccar");
                }
                log.info(dt.getName());
            }

            List<GroupsTraccar> resGroups = null;
            try {
                resGroups = traccarClient.getGroups(record.getUsuario().getTraccarID().toString());
            } catch (Exception e) {
                log.error("*******Fallo consultando grupos en traccar: ", e.getMessage());
                throw new RuntimeException("Fallo consultando grupos en traccar");
            }

            // for (GroupsTraccar gt : resGroups) {
            // try {
            // BodyDelGroupPermissions bDGP = new
            // BodyDelGroupPermissions(record.getUsuario().getTraccarID(),
            // gt.getId());
            // try {
            // traccarClient.delGroups(bDGP);
            // } catch (Exception e) {
            // log.error("*******Fallo eliminando grupo en traccar: ", e.getMessage());
            // throw new RuntimeException("Fallo eliminando grupo en traccar");
            // }
            // log.info(gt.getName());
            // } catch (Exception e) {
            // log.error("*******Fallo iterando grupos en traccar: ", e.getMessage());
            // throw new RuntimeException("Fallo iterando grupos en traccar");
            // }
            // }

            // try {
            // List<Permisos> permisos = perRepo.findByUsuarios(record.getUsuario());
            // for (Permisos per : permisos) {
            // perRepo.delete(per);
            // }
            // } catch (Exception e) {
            // log.error("Fallo eliminando los Permisos del Usuario en Operaciones y
            // Objetivos");
            // log.error(e.getMessage());
            // throw new RuntimeException("Fallo eliminando los Permisos del Usuario en
            // Operaciones y Objetivos");
            // }

            // try {
            // uuRepo.delete(uuRepo.findById(record.getId()).get());
            // } catch (Exception e) {
            // log.error("Fallo eliminando la relación del usuario con la unidad");
            // log.error(e.getMessage());
            // throw new RuntimeException("Fallo eliminando la relación del usuario con la
            // unidad");
            // }
        }
    }
}
