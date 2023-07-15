package com.smarthealth.Controller;

import com.smarthealth.Emun.Rol;
import com.smarthealth.Entity.UsuarioEntity;
import com.smarthealth.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterMedic {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/medic")
    public UsuarioEntity save(@RequestBody UsuarioEntity usuario) throws Exception{
        usuario.setPassword(this.passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Rol.MEDIC);
        return usuarioService.save(usuario);
    }
}
