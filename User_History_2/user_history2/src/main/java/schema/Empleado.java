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
        private boolean activo;
        private int puntaje;

    public Empleado(byte id,String nombre, short codigoOficina, int edad, long cedula, float sueldoBasicoF, double bonoMensual, char genero, double sueldofinal , boolean activo, int puntaje) {
        this.id = id;
        this.codigoOficina = codigoOficina;
        this.edad = edad;
        this.cedula = cedula;
        this.sueldoBasicoF = 1500000.0f;
        this.bonoMensual = bonoMensual;
        this.genero = genero;
        this.nombre = nombre;
        this.sueldofinal = calcularSueldoFinal();
        this.activo = true;
        this.puntaje = 84;
    }

    public double calcularSueldoFinal() {
        double multBono = 1.10 ;
        double descuento = 0.05;
        if (this.id % 2== 0){
            // si el id es par se le añadira un doble bono jjs
            multBono = (this.id % 2 == 0) ?2.10 : 1.10 ;
            return sueldofinal = (this.sueldoBasicoF + this.bonoMensual* multBono)-(this.sueldoBasicoF * descuento);
        }
    
        return sueldofinal = (this.sueldoBasicoF + this.bonoMensual* multBono)-(this.sueldoBasicoF * descuento);
    }

    public double getSueldofinal() {
        return sueldofinal;
    }

    public boolean validarElegibilidad() {
        return this.puntaje >= 85 && this.edad <= 30 && this.activo;
    }

    public byte getId() {
        return id;
    }


    public String getNombre() {
        return nombre;
    }

    public long getCedula() {
        return cedula;
        
    }




}
