package com.smarthealth.Entity;

import com.smarthealth.Emun.Rol;

import java.time.LocalDate;

public class UsuarioDTO {
    private long userid;
    private String username;
    private String lastname;
    private LocalDate birthdate;
    private String phone;
    private Rol rol;
    private boolean enable;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public UsuarioDTO(long userid, String username, String lastname, LocalDate birthdate, String phone, Rol rol, boolean enable) {
        this.userid = userid;
        this.username = username;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.phone = phone;
        this.rol = rol;
        this.enable = enable;
    }

    public UsuarioDTO() {
    }
}
