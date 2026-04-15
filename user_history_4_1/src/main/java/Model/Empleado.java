package Model;

import java.util.ArrayList;
import java.util.List;

public class Empleado {
    public record InnerEmpleado(byte id, short codigoOficina, int edad, long cedula, float sueldoBasicoF, double bonoMensual, char genero, String nombre, double sueldofinal, boolean activo, int puntaje ) {
        
    }
}
