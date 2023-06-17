package com.smarthealth.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.smarthealth.Emun.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="usuarios")
public class UsuarioEntity {

    @Id
    @Column(name = "userid" )
    private int userid;

    @NotBlank
    private String username;

    @NotBlank
    private String lastname;

    @NotBlank
    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @NotBlank
    private String phone;


    @Enumerated(EnumType.STRING)
    private Rol rol;

    private boolean enable = true;


}
