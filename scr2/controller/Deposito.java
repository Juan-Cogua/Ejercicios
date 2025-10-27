package scr2.controller;

import scr2.model.ProductoBase;
import scr2.excepciones.UbicacionInvalidaException;
import scr2.excepciones.CantidadNegativaException;

import java.util.ArrayList;
import java.util.List;

public class Deposito {
    private String nombre;
    private ProductoBase[][] productos;

    public Deposito(String nombre, int filas, int columnas) {
        this.nombre = nombre;
        this.productos = new ProductoBase[filas][columnas];
    }

    private void validarUbicacion(int fila, int columna) throws UbicacionInvalidaException {
        if (productos == null || productos.length == 0 || fila < 0 || fila >= productos.length || columna < 0 || columna >= productos[0].length) {
            throw new UbicacionInvalidaException("Ubicación inválida: fila=" + fila + " columna=" + columna);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void colocarProducto(int fila, int columna, ProductoBase producto) throws UbicacionInvalidaException, CantidadNegativaException {
        validarUbicacion(fila, columna);
        ProductoBase existente = productos[fila][columna];
        if (existente == null) {
            productos[fila][columna] = producto;
        } else {
            if (existente.getNombre().equals(producto.getNombre())) {
                existente.agregarCantidad(producto.getCantidad());
            } else {
                productos[fila][columna] = producto;
            }
        }
    }

    public void retirarProducto(int fila, int columna, int cantidad) throws UbicacionInvalidaException, CantidadNegativaException {
        validarUbicacion(fila, columna);
        ProductoBase existente = productos[fila][columna];
        if (existente == null) {
            throw new IllegalArgumentException("La ubicación está vacía");
        }
        existente.retirarCantidad(cantidad);
        if (existente.getCantidad() == 0) {
            productos[fila][columna] = null;
        }
    }

    public int stockTotal() {
        int total = 0;
        for (int i = 0; i < productos.length; i++) {
            for (int j = 0; j < productos[i].length; j++) {
                if (productos[i][j] != null) total += productos[i][j].getCantidad();
            }
        }
        return total;
    }

    public List<String> detectarUbicacionesVacias() {
        List<String> vacias = new ArrayList<>();
        for (int i = 0; i < productos.length; i++) {
            for (int j = 0; j < productos[i].length; j++) {
                if (productos[i][j] == null) {
                    String linea = String.format("%s,%d,%d", nombre, i + 1, j + 1);
                    vacias.add(linea);
                }
            }
        }
        return vacias;
    }

    // Muestra el contenido del depósito con índices de fila/columna empezando en 1
    public void mostrarContenido() {
        System.out.println("Contenido del depósito " + nombre + ":");
        if (productos == null || productos.length == 0) {
            System.out.println("(vacío)");
            return;
        }
        // Imprimir encabezado de columnas
        System.out.print("     ");
        for (int j = 0; j < productos[0].length; j++) {
            System.out.printf("  C%-3d", j + 1);
        }
        System.out.println();

        for (int i = 0; i < productos.length; i++) {
            System.out.printf("F%-3d", i + 1);
            System.out.print(" ");
            for (int j = 0; j < productos[i].length; j++) {
                String s;
                if (productos[i][j] == null) s = "Vacío";
                else s = String.format("%s(%d)", productos[i][j].getNombre(), productos[i][j].getCantidad());
                System.out.print(String.format("[%s] ", s));
            }
            System.out.println();
        }
    }
}
