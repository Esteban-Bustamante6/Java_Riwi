package utils;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import schema.Empleado;
public class RegisterEmpleado {
    private List<Empleado> listaEmpleados = new ArrayList<>();
    private Empleado empleado;

    public RegisterEmpleado() {
    }

    public RegisterEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void registrarEmpleado() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("ID del empleado: ");
        byte id = scanner.nextByte();

        System.out.print("Nombre del empleado: ");
        String nombre = scanner.nextLine();

        System.out.print("Código de oficina: ");
        short codigoOficina = scanner.nextShort();

        System.out.print("Edad del empleado: ");
        int edad = scanner.nextInt();  

        System.out.print("Cédula del empleado: ");
        long cedula = scanner.nextLong();

        System.out.print("Sueldo básico: ");
        float sueldoBasicoF = scanner.nextFloat();

        System.out.print("Bono mensual: ");
        double bonoMensual = scanner.nextDouble();

        System.out.print("Género (M/F): ");
        char genero = scanner.next().charAt(0);

        Empleado empleado = new Empleado(id, codigoOficina, edad, cedula, sueldoBasicoF, bonoMensual, genero,nombre);
        this.empleado = empleado;

        listaEmpleados.add(this.empleado);

        System.out.println("Empleado registrado exitosamente.");
    }
    public List<Empleado> getEmpleados() {
        return listaEmpleados;
    }
}
