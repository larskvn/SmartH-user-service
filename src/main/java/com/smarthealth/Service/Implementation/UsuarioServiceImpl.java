package com.smarthealth.Service.Implementation;


import com.smarthealth.Entity.UsuarioEntity;
import com.smarthealth.Models.ResultService;
import com.smarthealth.Models.ValidationService;
import com.smarthealth.Repository.UsuarioRepository;
import com.smarthealth.Service.UsuarioService;
import com.smarthealth.feingClients.ResultFeingClient;
import com.smarthealth.feingClients.ValidationFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ResultFeingClient resultFeingClient;

    @Autowired
    private ValidationFeignClient validationFeignClient;



    @Override
    public UsuarioEntity save(UsuarioEntity usuario) throws Exception {
        int userid = usuario.getUserid();
        UsuarioEntity usuarioEntity = usuarioRepository.findOneByUserid(userid);
        if (usuarioEntity != null) {
            System.out.println("Usuario ya está registrado");
            throw new Exception("Usuario ya está presente");
        } else {
            usuarioEntity= usuarioRepository.save(usuario);
        }
        return usuarioEntity;
    }

    @Override
    public List<UsuarioEntity> findAll() {
        return usuarioRepository.findAll();
    }


    @Override
    public UsuarioEntity obtenerUser(String username) {
        return usuarioRepository.findByUsername(username);
    }


    @Override
    public UsuarioEntity getUsuarioById(int userid) {
        return usuarioRepository.findById(userid).orElse(null);
    }

    @Override
    public UsuarioEntity delete(UsuarioEntity u) {
        UsuarioEntity usuarioEntity = usuarioRepository.getById(u.getUserid());
        usuarioEntity.setEnable(false);
        return usuarioRepository.save(usuarioEntity);
    }


    //conexion entre microservicios
    @Override
    public List<ResultService> getResultado(int idUser) {
        List<ResultService> resultados = restTemplate.getForObject("https://smart-resultados.azurewebsites.net/resultado/byuser/" + idUser, List.class);
        return resultados;
    }



    @Override
    public List<ValidationService> getValoracion(int medicId) {
        List<ValidationService> valoracions = restTemplate.getForObject("http://localhost:8004/valoracion/byuser/" + medicId, List.class);
        return valoracions;
    }


    // resultado

    @Override
    public ResultService saveResult(int userId, ResultService r) {
        r.setIdUser(userId);
        ResultService resultServiceNew = resultFeingClient.add(r);
        return resultServiceNew;
    }

    @Override
    public Map<String, Object> getUserResult(int idUser) {
        Map<String, Object> result = new HashMap<>();
        UsuarioEntity usuarioEntity = usuarioRepository.findById(idUser).orElse(null);
        if (usuarioEntity == null){
            result.put("Mensaje","no exite el usuario");
            return result;
        }
        result.put("User", idUser); // para traer los datos del user usuarioEntity
        List<ResultService> resultados = resultFeingClient.getResult(idUser);
        if (resultados.isEmpty())
            result.put("Resultado"," ese usuario no tiene resultados disponibles");
        else
            result.put("Resultados", resultados);
        return result;
    }

    // valoracion
    @Override
    public ValidationService saveValid(int medicId, ValidationService v) {
        v.setMedicId(medicId);
        ValidationService validationServiceNew = validationFeignClient.add(v);
        return validationServiceNew;
    }

    @Override
    public Map<String, Object> getMedicValidation(int medicId) {
        Map<String, Object> validation = new HashMap<>();
        UsuarioEntity usuarioEntity = usuarioRepository.findById(medicId).orElse(null);
        if (usuarioEntity == null){
            validation.put("Mensaje","no exite el usuario");
            return validation;
        }
        validation.put("Id de medico", medicId); // para traer los datos del user usuarioEntity
        List<ValidationService> validationServices = validationFeignClient.getValid(medicId);
        if (validationServices.isEmpty())
            validation.put("Valoracion"," este medico no tiene valoracion disponibles");
        else
            validation.put("Valoracion", validationServices);
        return validation;
    }


}
