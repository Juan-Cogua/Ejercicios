package scr2.model;

import java.sql.Date;

public class ProductoImportado extends ProductoBase {

    public ProductoImportado(String nombre, int cantidad, Date fechaIngreso) {
        super(nombre, cantidad, fechaIngreso);
    }

    // impuesto adicional del 5% (puede usarse para cálculos de costo)
    public double getImpuestoPercent() {
        return 0.05;
    }
}
