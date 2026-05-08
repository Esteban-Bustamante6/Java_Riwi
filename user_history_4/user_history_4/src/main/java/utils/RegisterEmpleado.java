package utils;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import schema.Empleado;
import schema.Empleado.*;
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
        scanner.nextLine();
        if (id < 0) {
            System.out.println("ID no válido. Debe ser un número positivo.");
            return;
        }

        System.out.print("Nombre del empleado: ");
        String nombre = scanner.nextLine();

        if (nombre.trim().isEmpty()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }   

        System.out.print("Código de oficina: ");
        short codigoOficina = scanner.nextShort();

        if (codigoOficina < 0) {
            System.out.println("Código de oficina no válido. Debe ser un número positivo.");
            return;
            
        }

        System.out.print("Edad del empleado: ");
        int edad = scanner.nextInt();  

        if (edad < 18 || edad > 100) {
            System.out.println("Edad no válida. Debe ser entre 18 y 100 años.");
            return;
        }

        System.out.print("Cédula del empleado: ");
        long cedula = scanner.nextLong();

        if (cedula < 0) {
            System.out.println("Cédula no válida. Debe ser un número positivo.");
            return;

        }if (String.valueOf(cedula).length() < 7 || String.valueOf(cedula).length() > 10) {
            System.out.println("Cédula no válida. Debe tener entre 7 y 10 dígitos.");
            return;
            
        }


        System.out.print("Bono mensual: ");
        double bonoMensual = scanner.nextDouble();

        System.out.print("Género (M/F): ");
        char genero = scanner.next().toUpperCase().charAt(0);

        if (genero != 'M' && genero != 'F') {
            System.out.println("Género no válido. Debe ser 'M' o 'F'.");
            return;
            
        }

        Empleado empleado = new Empleado(id, nombre, codigoOficina, edad, cedula, 1500000.0f, bonoMensual, genero, 0.0, true, 84);
        this.empleado = empleado;

        listaEmpleados.add(this.empleado);

        System.out.println("Empleado registrado exitosamente.");
    }
    public List<Empleado> getEmpleados() {
        return listaEmpleados;
    }
}
