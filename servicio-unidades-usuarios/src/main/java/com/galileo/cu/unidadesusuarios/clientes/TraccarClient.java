package com.galileo.cu.unidadesusuarios.clientes;

import com.galileo.cu.commons.models.dto.DevicesTraccar;
import com.galileo.cu.commons.models.dto.GroupsTraccar;

import java.net.URI;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "traccarApi", url = "EMPTY", configuration = DynamicFeignUrlInterceptor.class) // configuration =
                                                                                                   // DynamicFeignUrlInterceptor.class)
public interface TraccarClient {
    @GetMapping("/devices")
    List<DevicesTraccar> getDevices(@RequestParam("userId") String userId);

    @GetMapping("/groups")
    List<GroupsTraccar> getGroups(@RequestParam("userId") String userId);

    @DeleteMapping("/groups/{id}")
    String delGroups(@PathVariable int id);

    @DeleteMapping("/devices/{id}")
    String delDevices(@PathVariable int id);

    // @PostMapping("/otro-recurso")
    // String postRecursoConBodyYQueryParams(@RequestParam("queryParam1") String
    // queryParam1,
    // @RequestBody RequestBodyDto bodyParams);
}
