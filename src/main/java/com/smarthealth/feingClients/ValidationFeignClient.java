package com.smarthealth.feingClients;


import com.smarthealth.Models.ResultService;
import com.smarthealth.Models.ValidationService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "validation-service", url = "http://localhost:8004", path = "/valoracion")
public interface ValidationFeignClient {

    @PostMapping()
    ValidationService add(@RequestBody ValidationService v);

    @GetMapping("/byuser/{medicId}")
    List<ValidationService> getValid(@PathVariable("medicId") int medicId);
}
