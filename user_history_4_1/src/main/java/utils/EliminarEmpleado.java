package utils;

import java.util.Scanner;
import Model.Empleado;
import Model.Empresa;
import staticc.SesionEmpresa;
import staticc.listEmpleado;

public class EliminarEmpleado {

    public static void eliminarEmpleado() {
        Empresa.InnerEmpresa empresa = SesionEmpresa.getEmpresaActual();
        if (empresa == null) {
            System.out.println("Error: No hay empresa logueada.");
            return;
        }

        System.out.println("Empleados de " + empresa.nombre() + ":");
        for (Empleado emp : empresa.empleados()) {
            System.out.println("  ID: " + emp.getId() + " | Nombre: " + emp.getNombre());
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("\nIngrese el ID del empleado a eliminar: ");
        byte idBuscar = sc.nextByte();

        // removeIf recorre la lista y elimina el elemento que cumpla la condición
        boolean eliminadoDeEmpresa = empresa.empleados()
                .removeIf(emp -> emp.getId() == idBuscar);

        // También lo eliminamos de la lista global de empleados
        boolean eliminadoGlobal = listEmpleado.getEmpleados()
                .removeIf(emp -> emp.getId() == idBuscar);

        if (eliminadoDeEmpresa) {
            System.out.println("Empleado con ID " + idBuscar + " eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún empleado con ese ID.");
        }
    }
}