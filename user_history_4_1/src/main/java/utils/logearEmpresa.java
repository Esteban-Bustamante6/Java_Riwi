package utils;

import Model.Empleado;
import Model.Empresa;
import staticc.SesionEmpresa;
import staticc.SesionEmpleado;
import staticc.listEmpresa;

import java.util.Scanner;

public class logearEmpresa {

    // Login para empresa
    public static boolean logearComoEmpresa() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nombre de la empresa: ");
        String nombreEmpresa = sc.nextLine();

        for (Empresa.InnerEmpresa empresa : listEmpresa.getEmpresas()) {
            if (empresa.nombre().equalsIgnoreCase(nombreEmpresa)) {
                SesionEmpresa.setEmpresaActual(empresa);
                System.out.println("Bienvenido, " + empresa.nombre() + "!");
                return true;
            }
        }
        System.out.println("Empresa no encontrada.");
        RegistarEmpresas.registrarEmpresa();
        return false;
    }

    // Login para empleado por cédula
    // Busca en todas las empresas a qué empresa pertenece el empleado
    public static boolean logearComoEmpleado() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Ingrese su cédula: ");

        long cedula;
        try {
            cedula = sc.nextLong();
        } catch (Exception e) {
            System.out.println("Cédula inválida.");
            sc.nextLine();
            return false;
        }

        for (Empresa.InnerEmpresa empresa : listEmpresa.getEmpresas()) {
            for (Empleado emp : empresa.empleados()) {
                if (emp.getCedula() == cedula) {
                    // Guarda tanto el empleado como la empresa a la que pertenece
                    SesionEmpleado.setEmpleadoActual(emp, empresa);
                    System.out.println("Bienvenido, " + emp.getNombre() + "!");
                    return true;
                }
            }
        }
        System.out.println("Empleado no encontrado.");
        return false;
    }
}