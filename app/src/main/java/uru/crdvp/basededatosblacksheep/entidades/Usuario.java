package uru.crdvp.basededatosblacksheep.entidades;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable {

    //--> Que el objeto sea Serializable signigica que puedo enviarlo por parametros!!!
    private String idUsuario;
    private String contraseña;
    private String nombre;
    private Date fechaNacimiento;
    private String pais;

    public Usuario(String idUsuario, String contraseña, String nombre, Date fechaNacimiento, String pais) {
        this.idUsuario = idUsuario;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
