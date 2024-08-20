package com.galileo.cu.unidadesusuarios.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.galileo.cu.commons.models.UnidadesUsuarios;
import com.galileo.cu.unidadesusuarios.repositorios.ExpiraUserRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.sql.Timestamp;

@Slf4j
@Component
public class ExpirationCheckTask {
    @Autowired
    private ExpiraUserRepository expiraUserRepository;

    // @Scheduled(cron = "0 0 0 * * *") // Ejecutar todos los días a las 00:00
    @Scheduled(cron = "0 12 7 * * *")
    public void checkForExpiredRecords() {
        log.info("::::::EXPIRANDO::::: ");
        LocalDateTime nowLocalDateTime = LocalDateTime.now();
        Timestamp nowTimestamp = Timestamp.valueOf(nowLocalDateTime);

        // Buscar registros con fecha de expiración anterior a 'now'
        List<UnidadesUsuarios> expiredRecords = expiraUserRepository.findByExpiraBefore(nowTimestamp);
        log.info("::::::NOW==" + nowTimestamp.toString());
        log.info("::::::QUANTY==" + expiredRecords.size());

        for (UnidadesUsuarios record : expiredRecords) {
            // Aquí puedes realizar la operación que necesites con cada registro que ha
            // expirado
            log.info("Registro expirado: " + record);
        }
    }
}
