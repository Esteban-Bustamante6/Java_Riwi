package Model;

import java.util.ArrayList;
import java.util.List;

public abstract sealed class Empleado extends Personas permits Developer, Gerente {
    protected byte id;
    protected short codigoOficina;
    protected float sueldoBasicoF;
    protected double bonoMensual;
    protected double sueldofinal;
    protected boolean activo;
    protected int puntaje;

    public Empleado(String nombre, int edad, int cedula, byte id, short codigoOficina, 
                    float sueldoBasicoF, double bonoMensual, double sueldofinal, 
                    boolean activo, int puntaje) {
        super(nombre, edad, cedula);
        this.id = id;
        this.codigoOficina = codigoOficina;
        this.sueldoBasicoF = sueldoBasicoF;
        this.bonoMensual = bonoMensual;
        this.sueldofinal = sueldofinal;
        this.activo = activo;
        this.puntaje = puntaje;
    }

    // Getters para funcionalidad
    public byte getId() { return id; }
    public String genombre() { return nombre; } 
    public int getEdad() { return edad; }
    public short getCodigoOficina() { return codigoOficina; }
    public float getSueldoBasicoF() { return sueldoBasicoF; }
    public double getBonoMensual() { return bonoMensual; }
    public double getSueldofinal() { return sueldofinal; }
    public boolean isActivo() { return activo; }
    public int getPuntaje() { return puntaje; }
}
