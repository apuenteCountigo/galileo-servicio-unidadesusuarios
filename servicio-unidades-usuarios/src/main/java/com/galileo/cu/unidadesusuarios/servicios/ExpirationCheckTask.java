package com.galileo.cu.unidadesusuarios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.galileo.cu.commons.models.Conexiones;
import com.galileo.cu.commons.models.UnidadesUsuarios;
import com.galileo.cu.commons.models.Usuarios;
import com.galileo.cu.unidadesusuarios.clientes.TraccarClient;
import com.galileo.cu.unidadesusuarios.repositorios.ConexionesRepository;
import com.galileo.cu.unidadesusuarios.repositorios.ExpiraUserRepository;
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
    private ConexionesRepository conRepo;

    // @Scheduled(cron = "0 0 0 * * *") // Ejecutar todos los días a las 00:00
    @Scheduled(cron = "0 20 23 * * *")
    public void checkForExpiredRecords() {
        log.info("::::::EXPIRANDO::::: ");
        // LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
        // Obtener la fecha y hora actual, truncando los nanosegundos para una
        // comparación precisa
        LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault()).withNano(0);

        List<UnidadesUsuarios> expiredRecords = expiraUserRepository.findByExpiraBefore(now);
        log.info("::::::NOW==" + now.toString());
        log.info("::::::QUANTY==" + expiredRecords.size());

        for (UnidadesUsuarios record : expiredRecords) {
            log.info("Registro expirado: " + record.getUsuario().getTraccarID());
            List<Conexiones> cons = conRepo.findByServicio("TRACCAR");
            Conexiones con = cons.get(0);
            log.info("Conexión Usuario: " + con.getUsuario());

            String resDevices = traccarClient.getDevices(record.getUsuario().getTraccarID().toString());
            log.info(resDevices);
        }
    }
}
