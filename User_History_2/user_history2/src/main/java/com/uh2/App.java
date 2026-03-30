package com.uh2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.RegisterEmpleado;
import utils.RegisterEmpresa;
import schema.Empleado;

public class App {
    // 1. Lista estática para que sea accesible desde el main
    private static List<Empleado> listaTotalEmpleados = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean condition = true;

        while (condition) {
            System.out.println("\n--- BIENVENIDO AL SISTEMA ---");
            System.out.println("1. Registrar Empresa y Buscar Empleado");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    RegisterEmpresa registerEmpresa = new RegisterEmpresa();
                    registerEmpresa.registrarEmpresa();

                    System.out.println("\nIngrese el ID del empleado para ver su información:");
                    int idBuscar = sc.nextInt();

                    // 2. Lógica de búsqueda correcta
                    Empleado encontrado = null;
                    for (Empleado e : listaTotalEmpleados) {
                        if (e.getId() == idBuscar) {
                            encontrado = e;
                            break;
                        }
                    }

                    if (encontrado != null) {
                        System.out.println("✅ Información del empleado: " + encontrado.getNombre());
                    } else {
                        System.out.println("❌ Empleado no encontrado.");
                        System.out.println("Si desea registrar un nuevo empleado, ingrese 1:");
                        if (sc.nextInt() == 1) {
                            RegisterEmpleado reg = new RegisterEmpleado();
                            reg.registrarEmpleado();
                            
                            // 3. ¡IMPORTANTE! Guardar el empleado en la lista del main
                            listaTotalEmpleados.addAll(reg.getEmpleados());
                        }
                    }
                    break;

                case 2:
                    condition = false;
                    System.out.println("Saliendo del sistema...");
                    break;

                default:
                    System.out.println("Opción no válida.");
            }
        }
        sc.close();
    }
}