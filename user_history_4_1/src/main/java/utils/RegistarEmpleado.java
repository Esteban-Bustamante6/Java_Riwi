package utils;

import java.util.Scanner;

import Model.Empleado;
import Model.Empresa;
import staticc.SesionEmpresa;
import staticc.listEmpleado;
import utils.ProcesoEmpleado;

public class RegistarEmpleado {
    public static void registrarEmpleado() {
        Scanner sc = new Scanner(System.in);

        Empresa.InnerEmpresa empresaActual = SesionEmpresa.getEmpresaActual();
        if (empresaActual == null) {
            System.out.println("Error: No hay empresa logueada.");
            return;
        }

        System.out.println("\n-- Registro de Empleado para: " + empresaActual.nombre() + " --");
        System.out.print("ID : ");
        byte id = sc.nextByte();
        System.out.print("Código de Oficina : ");
        short codigoOficina = sc.nextShort();
        System.out.print("Edad : ");
        int edad = sc.nextInt();
        System.out.print("Cédula : ");
        long cedula = sc.nextLong();
        System.out.print("Sueldo Básico : ");
        float sueldoBasicoF = sc.nextFloat();
        System.out.print("Bono Mensual : ");
        double bonoMensual = sc.nextDouble();
        System.out.print("Género : ");
        char genero = sc.next().charAt(0);
        System.out.print("Nombre : ");
        String nombre = sc.next();
        System.out.print("¿Está Activo? (true/false): ");
        boolean activo = sc.nextBoolean();
        System.out.print("Puntaje : ");
        int puntaje = sc.nextInt();

        Empleado.InnerEmpleado empleado = new Empleado.InnerEmpleado(
            id, codigoOficina, edad, cedula, sueldoBasicoF,
            bonoMensual, genero, nombre, ProcesoEmpleado.calcularSalarioFinal(puntaje, bonoMensual), activo, puntaje
        );

        empresaActual.agregarEmpleado(empleado);
        listEmpleado.agregarEmpleado(empleado);

        System.out.println("\nEmpleado '" + nombre + "' registrado exitosamente.");
    }
}