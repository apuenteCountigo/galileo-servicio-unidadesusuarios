package com.galileo.cu.unidadesusuarios.clientes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.galileo.cu.commons.models.Conexiones;
import com.galileo.cu.unidadesusuarios.repositorios.ConexionesRepository;

public class DynamicFeignUrlInterceptor implements RequestInterceptor {
    @Autowired
    private ConexionesRepository conRepo; // Este servicio recuperará la URL de la base de datos

    @Override
    public void apply(RequestTemplate template) {
        List<Conexiones> cons = conRepo.findByServicio("TRACCAR");
        Conexiones con = cons.get(0);
        String dynamicUrl = con.getIpServicio() + ":" + con.getPuerto() + "/api"; // Aquí obtienes la URL desde la base
                                                                                  // de datos
        template.target(dynamicUrl); // Establece la URL base dinámica

        // Obtener las credenciales desde la base de datos o configuración
        String username = con.getUsuario(); // Ejemplo de método para obtener el usuario
        String password = con.getPassword(); // Ejemplo de método para obtener la contraseña

        // Construir la cabecera Authorization con Basic Auth
        String auth = username + ":" + password;
        String encodedAuth = Base64Utils.encodeToString(auth.getBytes());
        String authHeader = "Basic " + encodedAuth;

        // Añadir la cabecera Authorization a la solicitud
        template.header("Authorization", authHeader);
    }
}
