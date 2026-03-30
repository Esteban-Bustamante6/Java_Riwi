import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class UH_1{


    public static void main(String[] args){
        Scanner capturar = new Scanner(System.in);

        try {
            System.out.println("1. ¿Quieres ver las empresas? si/no");
            String mostrarEmpresa = capturar.nextLine();
        if (mostrarEmpresa.equals("si")){
            Empresarecord Empresa = Empresa1();
            System.out.println("Empresa: " + Empresa.Nombre());
            System.out.println("Primer empleado: " + Empresa.Nomina().get(0).nombre);

            System.out.println("2. ¿Quieres ver los detalles del empleado? si/no");
            String MostrarDetalles = capturar.nextLine();
    

            if (MostrarDetalles.equals(("si"))){
            Empleado primerEmpleado = registrarEmpleado();
            System.out.println("Mi Nombre es: " + primerEmpleado.nombre + " Soy el Trabajador # "+ primerEmpleado.id + " Tengo " +primerEmpleado.edad + "Años" + " Mi sueldo final es de $" + primerEmpleado.sueldofinal);
            } else {
                throw new IllegalArgumentException("Acceso denegado: El usuario no quiso ver detalles.");

            }
        }
        } catch (IllegalArgumentException e) {
            System.out.println("No se pudo Obtener informacion de la Empresa: " + e.getMessage());
        }
    }
    public static Empleado registrarEmpleado() {
        Empleado temp = new Empleado(
            (byte) 1, (short) 10,  22, 123456789L, 'M', true, "Esteban");
        
        return temp;
    } 

    
    public static class Empleado{
        private byte id;
        private short codigoOficina;  
        private int edad;            
        private long cedula;        
        private float sueldoBasicoF;  
        private double bonoMensual;   
        private char genero;          
        private boolean esActivo;     
        private String nombre;
        private double sueldofinal;

        public Empleado(byte id, short codigoOficina, int edad, long cedula, char genero, boolean esActivo, String nombre){

            if (edad > 100){
                throw new IllegalArgumentException("demasiado mayor");
            }
            if (edad < 18 ){
                throw new IllegalArgumentException("Muy joven para trabajar");
            }

            this.id = id;
            this.codigoOficina = codigoOficina;
            this.edad = edad;
            this.cedula = cedula;
            this.sueldoBasicoF = 1500000.0f;
            this.bonoMensual = 10000.0;
            this.genero = genero;
            this.esActivo = esActivo;
            this.nombre = nombre;
            this.sueldofinal = calcularSalarioFinal();
        }

        public double calcularSalarioFinal(){
            // lo primero que se hace es multiplicar el bono mensual por 1.10 y el sueldo basico por 0.05 , luego estos dos resultados se restas y luego se suman con el sueldo basico 
            // es decir primero se hace las multiplicaciones luego resta y por ultimo la suma de todos los resulados
            double multBono = 1.10 ;
            double descuento = 0.05;
            if (this.id % 2== 0){
                // si el id es par se le añadira un doble bono jjs
                multBono = (this.id % 2 == 0) ?2.10 : 1.10 ;
                return (this.sueldoBasicoF + this.bonoMensual* multBono)-(this.sueldoBasicoF * descuento);
            }
        
            return (this.sueldoBasicoF + this.bonoMensual* multBono)-(this.sueldoBasicoF * descuento);
        }

    }


    public record Empresarecord (String Nombre, int nit, LocalDate AñoFundacion, List <Empleado> Nomina ){};

    public static Empresarecord Empresa1(){
        List<Empleado> listNomina = new ArrayList<>();
        Empleado empleado1 = registrarEmpleado();
        listNomina.add(empleado1);
        return  new Empresarecord("Indutex", 23112,LocalDate.parse("2000-03-07"),listNomina);
    }


}