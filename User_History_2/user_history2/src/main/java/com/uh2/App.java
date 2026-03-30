package com.uh2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import utils.RegisterEmpleado;
import utils.RegisterEmpresa;
import schema.Empleado;
import schema.Empresa;

public class App {
    // 1. LISTAS GLOBALES (Nuestra "Base de Datos" temporal)
    private static List<Empleado> listaTotalEmpleados = new ArrayList<>();
    private static List<Empresa> listaTotalEmpresas = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean condition = true;

        while (condition) {
            System.out.println("\n=== SISTEMA DE GESTIÓN ===");
            System.out.println("1. Registrar/Gestionar Empresa");
            System.out.println("2. Ver todos los empleados registrados");
            System.out.println("3. Salir");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    // Primero pedimos el nombre para ver si ya existe
                    System.out.print("Nombre de la empresa a gestionar: ");
                    sc.nextLine(); // Limpiar buffer
                    String nombreBusqueda = sc.nextLine();
                    
                    Empresa empresaActual = buscarEmpresa(nombreBusqueda);

                    if (empresaActual == null) {
                        System.out.println("Empresa no existe. Creándola...");
                        RegisterEmpresa regEmp = new RegisterEmpresa();
                        regEmp.registrarEmpresa(); 
                        // Aquí deberías tener un método en RegisterEmpresa que te devuelva el objeto creado
                        // Supongamos que lo tienes y se llama getEmpresa()
                        Empresa nuevaEmpresa = regEmp.getEmpresaCreada();
                        listaTotalEmpresas.add(nuevaEmpresa);
                    }

                    System.out.println("\nDigite el ID del empleado para consultar:");
                    int idBuscar = sc.nextInt();
                    Empleado empEncontrado = buscarEmpleado(idBuscar);

                    if (empEncontrado != null) {
                        // AQUÍ ESTÁ EL TRUCO: Usar los getters para mostrar la info
                        System.out.println(" EMPLEADO ENCONTRADO:");
                        System.out.println("Nombre: " + empEncontrado.getNombre());
                        System.out.println("Cédula: " + empEncontrado.getCedula());
                    } else {
                        System.out.println("No existe. ¿Desea registrarlo? (1. Sí / 2. No)");
                        if (sc.nextInt() == 1) {
                            RegisterEmpleado regEmpld = new RegisterEmpleado();
                            regEmpld.registrarEmpleado();
                            
                            // IMPORTANTE: Recuperar el empleado y meterlo a la lista global
                            listaTotalEmpleados.addAll(regEmpld.getEmpleados());
                            System.out.println("¡Empleado guardado en la base de datos global!");
                        }
                    }
                    break;

                case 2:
                    System.out.println("--- LISTA TOTAL DE EMPLEADOS ---");
                    for(Empleado e : listaTotalEmpleados) {
                        System.out.println("ID: " + e.getId() + " | Nombre: " + e.getNombre());
                    }
                    break;

                case 3:
                    condition = false;
                    break;
            }
        }
    }

    // MÉTODOS DE BÚSQUEDA (Para evitar duplicados)
    private static Empresa buscarEmpresa(String nombre) {
        for (Empresa e : listaTotalEmpresas) {
            if (e.getNombre().equalsIgnoreCase(nombre)) return e;
        }
        return null;
    }

    private static Empleado buscarEmpleado(int id) {
        for (Empleado e : listaTotalEmpleados) {
            if (e.getId() == id) return e;
        }
        return null;
    }
}