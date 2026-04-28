package staticc;

import Model.Empresa;

public class SesionEmpresa {
    private static Empresa.InnerEmpresa empresaActual = null;

    public static void setEmpresaActual(Empresa.InnerEmpresa empresa) {
        empresaActual = empresa;
    }

    public static Empresa.InnerEmpresa getEmpresaActual() {
        return empresaActual;
    }
} 
