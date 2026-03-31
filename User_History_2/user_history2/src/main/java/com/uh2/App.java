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
                    System.out.print("Nombre de la empresa a gestionar: ");
                    sc.nextLine(); 
                    String nombreBusqueda = sc.nextLine();
                    
                    Empresa empresaActual = buscarEmpresa(nombreBusqueda);

                    if (empresaActual == null) {
                        System.out.println("Empresa no existe. Creándola...");

                        RegisterEmpresa regEmp = new RegisterEmpresa(listaTotalEmpresas);
                        regEmp.registrarEmpresa(); 

                        Empresa nuevaEmpresa = regEmp.getEmpresaCreada();
                        if (nuevaEmpresa != null) {
                            listaTotalEmpresas.add(nuevaEmpresa);
                        }
                    }

                    System.out.println("\nDigite el ID del empleado para consultar:");
                    int idBuscar = sc.nextInt();
                    Empleado empEncontrado = buscarEmpleado(idBuscar);

                    if (empEncontrado != null) {
                        System.out.println(" EMPLEADO ENCONTRADO:");
                        System.out.println("Nombre: " + empEncontrado.getNombre());
                        System.out.println("Cédula: " + empEncontrado.getCedula());
                        System.out.println("Sueldo Final: " + empEncontrado.getSueldofinal());
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
                    if (listaTotalEmpleados.isEmpty()) {
                        System.out.println("No hay empleados registrados.");
                    }
                    else {
                        System.out.println("");
                        System.out.println("------------ Menu de gestión de empleados:--------------");
                        System.out.println(" Desea eliminar un empleado? (1. Sí)");
                        System.out.println(" Desea escribir una calificacion trimestral a un empleado? (2. Sí)");
                        System.out.println(" Desea volver al menú principal? (3. Sí)");
                        switch (sc.nextInt()) {
                            case 1:
                                System.out.print("Ingrese el ID a eliminar: ");
                                int idEliminar = sc.nextInt();

                                Empleado empAEliminar = buscarEmpleado(idEliminar);

                                if (empAEliminar != null) {
                                    listaTotalEmpleados.remove(empAEliminar);
                                    System.out.println("Empleado eliminado exitosamente.");
                                } else {
                                    System.out.println("No se encontró ningún empleado con ese ID.");
                                }
                                break;

                            case 2:
                                    System.out.print("Ingrese el ID del empleado para procesar desempeño: ");
                                    int idCalificacion = sc.nextInt();
                                    Empleado empACalificar = buscarEmpleado(idCalificacion);

                                    if (empACalificar != null) {
                                        double[][] matrizDesempeno = new double[1][3];
                                        String[] trimestres = {"Primer", "Segundo", "Tercer"};

                                        // 2. Bucles anidados para recorrer la matriz
                                        for (int i = 0; i < matrizDesempeno.length; i++) {
                                            System.out.println("Ingresando notas para: " + empACalificar.getNombre());
                                            double sumaNotas = 0;

                                            for (int j = 0; j < matrizDesempeno[i].length; j++) {
                                                System.out.print("Nota " + trimestres[j] + " Trimestre: ");
                                                matrizDesempeno[i][j] = sc.nextDouble();
                                                sumaNotas += matrizDesempeno[i][j];
                                            }

                                            double promedioFinal = sumaNotas / matrizDesempeno[i].length;

                                            // Documentación: Se realiza casting para simplificar el reporte, 
                                            int puntajeSimplificado = (int) promedioFinal;

                                            System.out.println("\n--- REPORTE DE RENDIMIENTO ---");
                                            System.out.println("Promedio Real (double): " + promedioFinal);
                                            System.out.println("Puntaje Simplificado (int): " + puntajeSimplificado);
                                            System.out.println("------------------------------");
                                        }
                                    } else {
                                        System.out.println("No se encontró ningún empleado con ese ID.");
                                    }
                                break;

                            case 3:
                                // Volver al menú principal
                                break;
                        }
                    }
                    break;

                case 3:
                    condition = false;
                    break;
            }
        }
    }

    // MÉTODOS DE BÚSQUEDA (Para evitar duplicados)
    private static Empresa buscarEmpresa(String nombreBusqueda) {
        for (Empresa e : listaTotalEmpresas) {
            if (e.getNombre().equalsIgnoreCase(nombreBusqueda)) return e;
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