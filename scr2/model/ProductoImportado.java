package scr2.model;

import java.sql.Date;
import scr2.excepciones.CantidadNegativaException;

public class ProductoImportado extends ProductoBase {

    public ProductoImportado(String nombre, int cantidad, Date fechaIngreso) throws CantidadNegativaException {
        super(nombre, cantidad, fechaIngreso);
    }

    @Override
    public double getImpuestoPercent() {
        return 0.05;
    }
}
