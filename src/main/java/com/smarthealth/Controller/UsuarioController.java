package com.smarthealth.Controller;

import com.smarthealth.Emun.Rol;
import com.smarthealth.Entity.UsuarioEntity;
import com.smarthealth.Models.ResultService;
import com.smarthealth.Models.ValidationService;
import com.smarthealth.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;

    /*@Autowired
    PasswordEncoder passwordEncoder;*/


    @GetMapping
    public List<UsuarioEntity> findAll(){
        return usuarioService.findAll();
    }

    @GetMapping("/{username}")
    public UsuarioEntity obtenerUser(@PathVariable("username") String username){
        return usuarioService.obtenerUser(username);

    }

    @GetMapping("/id/{userid}")
    public ResponseEntity<UsuarioEntity> obtenerUsuario(@PathVariable("userid") int userid){
        UsuarioEntity usuarioEntity = usuarioService.getUsuarioById(userid);
        if (usuarioEntity == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioEntity);
    }

/*
    @PostMapping("/")
    public UsuarioEntity save(@RequestBody UsuarioEntity usuario)  throws Exception {
        usuario.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Rol.PATIENT);

        return usuarioService.save(usuario);

    }*/

    @DeleteMapping("/{userid}")
    public UsuarioEntity delete(@PathVariable Integer userid){
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setEnable(false);
        return usuarioService.delete(UsuarioEntity.builder().userid(userid).build());
    }



    //conexion con resultados con Rest Template

    //get con restTemplate

    @GetMapping("/resultado/{idUser}")
    public  ResponseEntity<List<ResultService>> getResultados(@PathVariable("idUser") int userid){
        UsuarioEntity usuarioEntity = usuarioService.getUsuarioById(userid);
        if (usuarioEntity == null)
            return ResponseEntity.notFound().build();
        List<ResultService> resultServices =usuarioService.getResultado(userid);
        return ResponseEntity.ok(resultServices);
    }




    // get y post con open feignClient para resultado de los pacientes


    @GetMapping("/getAllResult/{idUser}")
    public ResponseEntity<Map<String, Object>> getUserResult(@PathVariable("idUser") int idUser){
        Map<String, Object> result = usuarioService.getUserResult(idUser);
        return ResponseEntity.ok(result);

    }

    @PostMapping("/saveResult/{userId}")
    public ResponseEntity<ResultService> saveResult(@PathVariable("userId") int userId, @RequestBody ResultService r){
        if (usuarioService.getUsuarioById(userId) == null)
            return ResponseEntity.notFound().build();
        ResultService resultServiceNew = usuarioService.saveResult(userId,r);
        return ResponseEntity.ok(r);
    }




    @GetMapping("/getAllValid/{medicId}")
    public ResponseEntity<Map<String, Object>> getMedicValidation(@PathVariable("medicId") int medicId){
        Map<String, Object> validation = usuarioService.getMedicValidation(medicId);
        return ResponseEntity.ok(validation);

    }
    @PostMapping("/saveValid/{medicId}")
    public ResponseEntity<ValidationService> saveValid(@PathVariable("medicId") int medicId, @RequestBody ValidationService v){
        if (usuarioService.getUsuarioById(medicId) == null)
            return ResponseEntity.notFound().build();
        ValidationService validationServiceNew = usuarioService.saveValid(medicId,v);
        return ResponseEntity.ok(v);
    }





}
