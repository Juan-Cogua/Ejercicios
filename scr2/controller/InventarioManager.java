package scr2.controller;

import scr2.excepciones.ArchivoCreacionException;
import scr2.excepciones.CantidadNegativaException;
import scr2.excepciones.UbicacionInvalidaException;
import scr2.model.ProductoBase;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class InventarioManager {
    private Deposito depositoA;
    private Deposito depositoB;
    private Deposito depositoC;

    public InventarioManager(int filas, int columnas) {
        this.depositoA = new Deposito("A", filas, columnas);
        this.depositoB = new Deposito("B", filas, columnas);
        this.depositoC = new Deposito("C", filas, columnas);
    }

    private Deposito seleccionarDeposito(String codigo) {
        switch (codigo.toUpperCase()) {
            case "A": return depositoA;
            case "B": return depositoB;
            case "C": return depositoC;
            default: return null;
        }
    }

    public void agregarProducto(String depositoCodigo, int fila, int columna, ProductoBase producto) throws UbicacionInvalidaException, CantidadNegativaException {
        Deposito d = seleccionarDeposito(depositoCodigo);
        if (d == null) throw new IllegalArgumentException("Depósito desconocido: " + depositoCodigo);
        d.colocarProducto(fila, columna, producto);
    }

    public void retirarProducto(String depositoCodigo, int fila, int columna, int cantidad) throws UbicacionInvalidaException, CantidadNegativaException {
        Deposito d = seleccionarDeposito(depositoCodigo);
        if (d == null) throw new IllegalArgumentException("Depósito desconocido: " + depositoCodigo);
        d.retirarProducto(fila, columna, cantidad);
    }

    public int stockPorDeposito(String depositoCodigo) {
        Deposito d = seleccionarDeposito(depositoCodigo);
        if (d == null) throw new IllegalArgumentException("Depósito desconocido: " + depositoCodigo);
        return d.stockTotal();
    }

    public List<String> generarAlertasTxt(String rutaTxt) throws ArchivoCreacionException {
        List<String> todasVacias = new ArrayList<>();
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaTxt, false))) {
            pw.println("Alertas de ubicaciones vacías");
            pw.println("Formato: Deposito, Fila(1-base), Columna(1-base)");
            pw.println("-------------------------------");
    
            Deposito[] depositos = {depositoA, depositoB, depositoC};
    
            for (Deposito deposito : depositos) {
                pw.println("Depósito: " + deposito.getNombre());
                int filas = deposito.getFilas();
                int columnas = deposito.getColumnas();
                int anchoCelda = 12; // Ancho fijo para cada celda
    
                for (int i = 0; i < filas; i++) {
                    for (int j = 0; j < columnas; j++) {
                        if (deposito.getProducto(i, j) != null) {
                            pw.print(String.format("%-" + anchoCelda + "s", "P")); // Producto
                        } else {
                            String ubicacion = String.format("%s,%d,%d", deposito.getNombre(), i + 1, j + 1);
                            pw.print(String.format("%-" + anchoCelda + "s", ubicacion)); // Ubicación vacía
                            todasVacias.add(ubicacion);
                        }
                        if (j < columnas - 1) pw.print("| ");
                    }
                    pw.println(); // Nueva línea al final de cada fila
                }
                pw.println(); // Espacio entre depósitos
            }
        } catch (IOException e) {
            throw new ArchivoCreacionException("No se puede crear/escribir el archivo: " + e.getMessage());
        }
        return todasVacias;
    }

    // Genera alertas de ubicaciones vacías y las escribe en un TXT con formato alineado.
    public List<String> generarAlertasTxtPorDeposito(String rutaTxt, String depositoCodigo) throws ArchivoCreacionException {
        Deposito deposito = seleccionarDeposito(depositoCodigo);
        if (deposito == null) throw new IllegalArgumentException("Depósito desconocido: " + depositoCodigo);
    
        List<String> vacias = new ArrayList<>();
        try (PrintWriter pw = new PrintWriter(new FileWriter(rutaTxt, false))) {
            pw.println("Alertas de ubicaciones vacías");
            pw.println("Formato: Deposito, Fila(1-base), Columna(1-base)");
            pw.println("-------------------------------");
            pw.println("Depósito: " + deposito.getNombre());
    
            int filas = deposito.getFilas();
            int columnas = deposito.getColumnas();
            int anchoCelda = 12; // Ancho fijo para cada celda
    
            for (int i = 0; i < filas; i++) {
                for (int j = 0; j < columnas; j++) {
                    if (deposito.getProducto(i, j) != null) {
                        pw.print(String.format("%-" + anchoCelda + "s", "P")); // Producto
                    } else {
                        String ubicacion = String.format("%s,%d,%d", deposito.getNombre(), i + 1, j + 1);
                        pw.print(String.format("%-" + anchoCelda + "s", ubicacion)); // Ubicación vacía
                        vacias.add(ubicacion);
                    }
                    if (j < columnas - 1) pw.print("| "); // Separador entre columnas
                }
                pw.println(); // Nueva línea al final de cada fila
            }
        } catch (IOException e) {
            throw new ArchivoCreacionException("No se puede crear/escribir el archivo: " + e.getMessage());
        }
        return vacias;
    } 
    public void mostrarDeposito(String codigo) {
        Deposito d = seleccionarDeposito(codigo);
        if (d == null) {
            System.out.println("Depósito desconocido: " + codigo);
            return;
        }
        d.mostrarContenido();
    }
}