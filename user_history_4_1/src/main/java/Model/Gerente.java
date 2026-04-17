package Model;

public final class Gerente extends Empleado {
    private double presupuestoMensual;

    public Gerente(String nombre, int edad, int cedula, byte id, short codigoOficina, 
                float sueldoBasicoF, double bonoMensual, double sueldofinal, 
                boolean activo, int puntaje, double presupuesto) {
        super(nombre, edad, cedula, id, codigoOficina, sueldoBasicoF, bonoMensual, sueldofinal, activo, puntaje);
        this.presupuestoMensual = presupuesto;
    }

    public double getPresupuestoMensual() { return presupuestoMensual; }
}

