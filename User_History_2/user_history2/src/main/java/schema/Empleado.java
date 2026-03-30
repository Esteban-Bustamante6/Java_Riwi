package schema;

public class Empleado {
        private byte id;
        private short codigoOficina;  
        private int edad;            
        private long cedula;        
        private float sueldoBasicoF;  
        private double bonoMensual;   
        private char genero;          
        private String nombre;
        private double sueldofinal;

    public Empleado(byte id, short codigoOficina, int edad, long cedula, float sueldoBasicoF, double bonoMensual, char genero, String nombre) {
        this.id = id;
        this.codigoOficina = codigoOficina;
        this.edad = edad;
        this.cedula = cedula;
        this.sueldoBasicoF = sueldoBasicoF;
        this.bonoMensual = bonoMensual;
        this.genero = genero;
        this.nombre = nombre;
        this.sueldofinal = calcularSueldoFinal();
    }

    private double calcularSueldoFinal() {
        return sueldoBasicoF + bonoMensual;
    }

    public byte getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

}
