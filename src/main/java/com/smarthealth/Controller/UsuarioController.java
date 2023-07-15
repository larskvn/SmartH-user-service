package com.smarthealth.Controller;

import com.smarthealth.Emun.Rol;
import com.smarthealth.Entity.UsuarioDTO;
import com.smarthealth.Entity.UsuarioEntity;
import com.smarthealth.Models.ResultService;
import com.smarthealth.Models.ValidationService;
import com.smarthealth.Repository.UsuarioRepository;
import com.smarthealth.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UsuarioRepository usuarioRepository;

    /*@Autowired
    PasswordEncoder passwordEncoder;*/


    @GetMapping
    public List<UsuarioEntity> findAll(){
        return usuarioService.findAll();
    }



    @GetMapping("/userLogin/actual")
    public ResponseEntity<UsuarioDTO> getUserDetails(Authentication authentication) {
        String username = authentication.getName();
        UsuarioEntity usuarioEntity = usuarioRepository.findByUsername(username);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUserid(usuarioEntity.getUserid());
        usuarioDTO.setUsername(usuarioEntity.getUsername());
        usuarioDTO.setLastname(usuarioEntity.getLastname());
        usuarioDTO.setBirthdate(usuarioEntity.getBirthdate());
        usuarioDTO.setPhone(usuarioEntity.getPhone());
        usuarioDTO.setRol(usuarioEntity.getRol());
        usuarioDTO.setEnable(usuarioEntity.isEnable());

        return ResponseEntity.ok(usuarioDTO);
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


    @GetMapping("/getAllResult")
    public ResponseEntity<List<ResultService>> getResultados(Authentication authentication) {
        String username = authentication.getName();
        UsuarioEntity usuarioEntity = usuarioService.obtenerUser(username);
        if (usuarioEntity == null) {
            return ResponseEntity.notFound().build();
        }

        int userid = usuarioEntity.getUserid();
        List<ResultService> resultServices = usuarioService.getResultado(userid);

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
