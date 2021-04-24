package uru.crdvp.basededatosblacksheep.entidades;

import java.io.Serializable;

public class Perfil implements Serializable {

    private Integer idPerfil;
    private String nombre;

    public Perfil(Integer idPerfil, String nombre) {
        this.idPerfil = idPerfil;
        this.nombre = nombre;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
