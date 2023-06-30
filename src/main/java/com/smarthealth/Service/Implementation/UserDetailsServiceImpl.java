package com.smarthealth.Service.Implementation;

import com.smarthealth.Entity.UsuarioEntity;
import com.smarthealth.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioEntity usuarioEntity= usuarioRepository.findByUsername(username);
        if (null== usuarioEntity){
            throw new UsernameNotFoundException("El usuario " + username +" no existe");
        }
        Collection<? extends GrantedAuthority> authorities = Stream.of(usuarioEntity.getRol())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());




        return new User(usuarioEntity.getUsername(),
                usuarioEntity.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }


   /* @Override
    public UserDetails loadUserByUsername(String username) {
        UsuarioEntity usuarioEntity = usuarioRepository.findByUsername(username);
                .orElseThrow(()-> new UsernameNotFoundException("El usuario no exite" + username + "no esxite"));
        Collection<? extends GrantedAuthority> authorities = Stream.of(usuarioEntity.getRol())
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());


        return new User(usuarioEntity.getUsername(),
                usuarioEntity.getPassword(),
                true,
                true,
                true,
                true,
                authorities);

    }*/
}
