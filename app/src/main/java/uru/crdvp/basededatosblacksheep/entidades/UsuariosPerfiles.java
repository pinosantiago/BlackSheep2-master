package uru.crdvp.basededatosblacksheep.entidades;

import java.io.Serializable;

public class UsuariosPerfiles implements Serializable {
    private String idUsuario;
    private Integer idPerfil;

    public UsuariosPerfiles(String idUsuario, Integer idPerfil) {
        this.idUsuario = idUsuario;
        this.idPerfil = idPerfil;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }
}
