package com.galileo.cu.unidadesusuarios.clientes;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "traccarApi", configuration = DynamicFeignUrlInterceptor.class)
public interface TraccarClient {
    @GetMapping("/devices")
    String getDevices(@RequestParam("userId") String userId);

    @GetMapping("/groups")
    String getGroups(@RequestParam("userId") String userId);

    // @PostMapping("/otro-recurso")
    // String postRecursoConBodyYQueryParams(@RequestParam("queryParam1") String
    // queryParam1,
    // @RequestBody RequestBodyDto bodyParams);
}
