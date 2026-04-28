package hu_4;

import java.util.Scanner;

import Mennu.MennuEmpresa;
import Mennu.MennuEmpleado;
import utils.logearEmpresa;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Bienvenido al sistema =====");
            System.out.println("1. Soy una Empresa");
            System.out.println("2. Soy un Empleado");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    if (logearEmpresa.logearComoEmpresa()) {
                        MennuEmpresa.menuEmpresa();
                    }
                    break;
                case 2:
                    if (logearEmpresa.logearComoEmpleado()) {
                        MennuEmpleado.menuEmpleado();
                    }
                    break;
                case 3:
                    System.out.println("Hasta luego!");
                    return;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}