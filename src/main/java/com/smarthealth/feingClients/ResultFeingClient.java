package com.smarthealth.feingClients;

import com.smarthealth.Models.ResultService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "result-service", url = "https://smart-resultados.azurewebsites.net", path = "/resultado")
public interface ResultFeingClient {

    @PostMapping()
    ResultService add(@RequestBody ResultService r);

    @GetMapping("/byuser/{idUser}")
    List<ResultService> getResult(@PathVariable("idUser") int idUser);

}
