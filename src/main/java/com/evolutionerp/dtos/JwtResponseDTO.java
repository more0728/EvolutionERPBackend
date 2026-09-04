package com.evolutionerp.dtos;

import java.io.Serializable;

public class JwtResponseDTO implements Serializable {

    private String jwttoken;

    public JwtResponseDTO() {
    }

    public String getJwttoken() {
        return jwttoken;
    }

    public void setJwttoken(String jwttoken) {
        this.jwttoken = jwttoken;
    }

}