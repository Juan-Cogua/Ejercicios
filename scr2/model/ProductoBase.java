package scr2.model;

import java.sql.Date;
import scr2.excepciones.CantidadNegativaException;

public abstract class ProductoBase {
    private String nombre;
    private int cantidad;
    private Date fechaIngreso;

    public ProductoBase(String nombre, int cantidad, Date fechaIngreso) throws CantidadNegativaException {
        if (cantidad < 0) throw new CantidadNegativaException("Cantidad negativa: " + cantidad);
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.fechaIngreso = fechaIngreso;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setCantidad(int cantidad) throws CantidadNegativaException {
        if (cantidad < 0) throw new CantidadNegativaException("Cantidad negativa: " + cantidad);
        this.cantidad = cantidad;
    }

    public void agregarCantidad(int cantidad) throws CantidadNegativaException {
        if (cantidad < 0) throw new CantidadNegativaException("Cantidad negativa al agregar: " + cantidad);
        this.cantidad += cantidad;
    }

    public void retirarCantidad(int cantidad) throws CantidadNegativaException, IllegalArgumentException {
        if (cantidad < 0) throw new CantidadNegativaException("Cantidad negativa al retirar: " + cantidad);
        if (cantidad > this.cantidad) throw new IllegalArgumentException("No hay suficiente stock para retirar: " + cantidad);
        this.cantidad -= cantidad;
    }

    public abstract double getImpuestoPercent();

    @Override
    public String toString() {
        return String.format("%s (cantidad=%d, fecha=%s)", nombre, cantidad, fechaIngreso);
    }
}
