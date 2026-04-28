package utils;

import java.util.ArrayList;
import java.util.Scanner;

import Model.Empresa;
import staticc.listEmpresa;

public class RegistarEmpresas {
    
    public static void registrarEmpresa() {

        Scanner sc = new Scanner(System.in);
        System.out.println("");
        System.out.println("------------Menú de Empresa-------------");
        System.out.println("");
        System.out.println("vamos a completar los campos para registrar tu empresa:");
        System.out.print("ID : ");
        byte id = sc.nextByte();
        System.out.print("Nombre : ");
        String nombre = sc.next();
        System.out.print("Dirección : ");
        String direccion = sc.next();
        System.out.print("Sedes disponibles: " + listEmpresa.SEDES_DISPONIBLES);
        String sede = sc.next();
        

        Empresa.InnerEmpresa empresa = new Empresa.InnerEmpresa(id, nombre,direccion, sede, new ArrayList<>());
        listEmpresa.agregarEmpresa(empresa);
        System.out.println("Empresa registrada exitosamente.");
    }
}
