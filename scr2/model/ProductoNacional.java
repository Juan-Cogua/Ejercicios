package scr2.model;

import java.sql.Date;

public class ProductoNacional extends ProductoBase {

    public ProductoNacional(String nombre, int cantidad, Date fechaIngreso) {
        super(nombre, cantidad, fechaIngreso);
    }

    public double getImpuestoPercent() {
        return 0.0;
    }
}
