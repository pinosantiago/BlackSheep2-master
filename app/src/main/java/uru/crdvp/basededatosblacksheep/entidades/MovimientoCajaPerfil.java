package uru.crdvp.basededatosblacksheep.entidades;

public class MovimientoCajaPerfil {
    private Integer idMovimiento;
    private Integer idCaja;
    private Integer idPerfil;

    public MovimientoCajaPerfil(Integer idMovimiento, Integer idCaja, Integer idPerfil) {
        this.idMovimiento = idMovimiento;
        this.idCaja = idCaja;
        this.idPerfil = idPerfil;
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }

    public Integer getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = idPerfil;
    }
}
