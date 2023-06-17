package com.smarthealth.Repository;

import com.smarthealth.Entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity,Integer> {





    /*@Query("SELECT r FROM Resultado r WHERE r.userid = :userid")
    List<UsuarioEntity> findByUserId(@Param("userid") Long userid);*/


    public  UsuarioEntity findByUsername(String username);

    //Optional<UsuarioEntity> findByUsername(String username);


    UsuarioEntity findOneByUserid(int userid);



}
