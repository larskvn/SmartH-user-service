package com.smarthealth.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultService {

    private String nameMedic;
    private String nameSpecialty;
    private String diagnosis;
    private String treatment;
    private String recommendations;
    private int idUser;
}
