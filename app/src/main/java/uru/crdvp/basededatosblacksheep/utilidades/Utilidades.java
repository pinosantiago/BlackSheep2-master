package uru.crdvp.basededatosblacksheep.utilidades;

import java.util.Date;

public class Utilidades {

    // Constantes de tabla de Usuario
    public static final String TABLA_USUARIO = "usuarios";
    public static final String CAMPO_IDUSUARIO = "idUsuario";
    public static final String CAMPO_CONTRASEÑA = "contraseña";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_FECHANACIMIENTO = "fechaNacimiento";
    public static final String CAMPO_PAIS = "pais";
    // Sentencia SQL
    public static final String CREAR_TABLA_USUARIO = "CREATE TABLE "+TABLA_USUARIO+" ("+CAMPO_IDUSUARIO+" INTEGER, "+CAMPO_CONTRASEÑA+" TEXT, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_FECHANACIMIENTO+" DATE, "+CAMPO_PAIS+" TEXT)";

    // Constantes de tabla de UsuarioPerfiles
    // Tabla de Relacion
    public static final String TABLA_USUARIO_PERFILES = "usuarioPerfiles";
    public static final String CAMPO_IDPERFILU = "idPerfilU";
    public static final String CAMPO_IDPERFIL_USUARIO = "idUsuario";
    // Sentencia SQL
    public static final String CREAR_TABLA_USUARIO_PERFILES = "CREATE TABLE "+TABLA_USUARIO_PERFILES+" ("+CAMPO_IDPERFILU+" INTEGER, "+CAMPO_IDPERFIL_USUARIO+" TEXT)";

    // Constantes de tabla de Perfiles
    public static final String TABLA_PERFILES = "perfiles";
    public static final String CAMPO_IDPERFIL = "idPerfil";
    public static final String CAMPO_PERFIL_NOMBRE = "nombre";
    // Sentencia SQL
    public static final String CREAR_TABLA_PERFILES = "CREATE TABLE "+TABLA_PERFILES+" ("+CAMPO_IDPERFIL+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_PERFIL_NOMBRE+" TEXT)";

    // Constantes de tabla de Cajas
    public static final String TABLA_CAJAS = "cajas";
    public static final String CAMPO_IDCAJA = "idCaja";
    public static final String CAMPO_CAJA_IDPERFIL = "idPerfil";
    public static final String CAMPO_CAJA_NOMBRE = "nombre";
    public static final String CAMPO_CAJA_PORCENTAJE = "porcentaje";
    public static final String CAMPO_CAJA_MONTO = "monto";
    public static final String CAMPO_CAJA_DESCRIPCION = "descripcion";
    // Sentencia SQL
    public static final String CREAR_TABLA_CAJAS = "CREATE TABLE "+TABLA_CAJAS+" ("+CAMPO_IDCAJA+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_CAJA_IDPERFIL+" TEXT,"+CAMPO_CAJA_NOMBRE+" TEXT, "+CAMPO_CAJA_PORCENTAJE+" INT, "+CAMPO_CAJA_MONTO+" DOBLE,"+CAMPO_CAJA_DESCRIPCION+" TEXT)";

    // Constantes de tabla de Movimientos
    public static final String TABLA_MOVIMIENTOS = "movimientos";
    public static final String CAMPO_IDMOVIMIENTOS = "idMovimiento";
    public static final String CAMPO_MOVIMIENTO_IDPERFIL = "idPerfil";
    public static final String CAMPO_MOVIMIENTO_MONTO = "monto";
    public static final String CAMPO_MOVIMIENTO_INGEGR = "ingresoEgreso";
    public static final String CAMPO_MOVIMIENTO_CAJA = "idCaja";
    public static final String CAMPO_MOVIMIENTO_DESCRIPCION = "descripcion";
    // Sentencia SQL
    public static final String CREAR_TABLA_MOVIMIENTOS = "CREATE TABLE "+TABLA_MOVIMIENTOS+" ("+CAMPO_IDMOVIMIENTOS+" INTEGER PRIMARY KEY AUTOINCREMENT, "+CAMPO_MOVIMIENTO_IDPERFIL+" INTEGER,"+CAMPO_MOVIMIENTO_MONTO+" DOBLE, "+CAMPO_MOVIMIENTO_INGEGR+" INT, "+CAMPO_MOVIMIENTO_CAJA+" INT,"+CAMPO_MOVIMIENTO_DESCRIPCION+" TEXT)";

    // Constantes de tabla de MovimientoCajasPerfiles
    // Tabla de Relacion
    public static final String TABLA_MOVIMIENTO_CAJAS_PERFILES = "movimientoCajasPerfiles";
    public static final String CAMPO_MCP_IDPERFIL = "idPerfil";
    public static final String CAMPO_MCP_IDCAJA = "idCaja";
    public static final String CAMPO_MCP_IDMOVIMIENTO = "idMovimiento";
    // Sentencia SQL
    public static final String CREAR_TABLA_MOVIMIENTO_CAJAS_PERFILES = "CREATE TABLE "+TABLA_MOVIMIENTO_CAJAS_PERFILES+" ("+CAMPO_MCP_IDPERFIL+" INTEGER, "+CAMPO_MCP_IDCAJA+" INTEGER, "+CAMPO_MCP_IDMOVIMIENTO+" INTEGER)";
}
