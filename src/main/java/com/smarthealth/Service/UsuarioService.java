package com.smarthealth.Service;

import com.smarthealth.Entity.UsuarioEntity;
import com.smarthealth.Models.ResultService;
import com.smarthealth.Models.ValidationService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UsuarioService {

    public UsuarioEntity save(UsuarioEntity usuario) throws Exception;

    public List<UsuarioEntity> findAll();

    public  UsuarioEntity obtenerUser(String username);


    //integrado
    public UsuarioEntity getUsuarioById(int userid);


    public UsuarioEntity delete(UsuarioEntity u);

    public UsuarioEntity update(UsuarioEntity u);


    //implementado
    List<ResultService> getResultado(int idUser);

    List<ValidationService> getValoracion(int medicId);


    public ResultService saveResult(int userId, ResultService r);
    public Map<String, Object> getUserResult(int idUser);


    // valoracion de midicos

    public ValidationService saveValid(int medicId, ValidationService v);
    public Map<String, Object> getMedicValidation(int medicId);
}
