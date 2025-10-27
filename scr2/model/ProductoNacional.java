package scr2.model;

import java.sql.Date;
import scr2.excepciones.CantidadNegativaException;

public class ProductoNacional extends ProductoBase {

    public ProductoNacional(String nombre, int cantidad, Date fechaIngreso) throws CantidadNegativaException {
        super(nombre, cantidad, fechaIngreso);
    }

    @Override
    public double getImpuestoPercent() {
        return 0.0;
    }
}
