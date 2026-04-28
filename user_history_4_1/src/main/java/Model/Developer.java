package Model;
import java.util.ArrayList;
import Model.Empleado;


public final class Developer extends Empleado {
    private String lenguajeProgramacion;

    public Developer(String nombre, int edad, int cedula, byte id, short codigoOficina, 
                    float sueldoBasicoF, double bonoMensual, double sueldofinal, 
                    boolean activo, int puntaje, String lenguaje) {
        super(nombre, edad, cedula, id, codigoOficina, sueldoBasicoF, bonoMensual, sueldofinal, activo, puntaje);
        this.lenguajeProgramacion = lenguaje;
    }

    public String getLenguajeProgramacion() { return lenguajeProgramacion; }
}
