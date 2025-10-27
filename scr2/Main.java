package scr2;

import scr2.controller.InventarioManager;
import scr2.model.ProductoImportado;
import scr2.model.ProductoNacional;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        InventarioManager manager = new InventarioManager(2, 3);

        boolean running = true;
        while (running) {
            System.out.println("\nMenu:\n1. Mostrar depósito\n2. Agregar producto\n3. Retirar producto\n4. Calcular stock por depósito\n5. Detectar ubicaciones vacías y registrar en alertas_stock.txt\n6. Salir");
            System.out.print("Elige una opción: ");
            String opt = sc.nextLine().trim();
            try {
                switch (opt) {
                    case "1":
                        System.out.print("Cuál depósito? (A/B/C): ");
                        String d = sc.nextLine().trim().toUpperCase();
                        manager.mostrarDeposito(d);
                        break;
                    case "2":
                        System.out.print("Depósito (A/B/C): ");
                        String dep = sc.nextLine().trim().toUpperCase();
                        System.out.print("Fila (empieza en 1): "); int filaInput = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Columna (empieza en 1): "); int colInput = Integer.parseInt(sc.nextLine().trim());
                        int fila = filaInput - 1; 
                        int col = colInput - 1; 
                        System.out.print("Nombre producto: "); String nombre = sc.nextLine().trim();
                        System.out.print("Cantidad: "); int cant = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Fecha de ingreso (YYYY-MM-DD): "); String fechaStr = sc.nextLine().trim();
                        Date fecha;
                        try {
                            fecha = Date.valueOf(fechaStr);
                        } catch (IllegalArgumentException ex) {
                            System.out.println("Formato de fecha inválido. Use YYYY-MM-DD. Operación cancelada.");
                            break;
                        }
                        System.out.print("Tipo (N para nacional / I para importado): "); String tipo = sc.nextLine().trim().toUpperCase();
                        if (tipo.equals("N")) {
                            ProductoNacional p = new ProductoNacional(nombre, cant, fecha);
                            manager.agregarProducto(dep, fila, col, p);
                        } else {
                            ProductoImportado p = new ProductoImportado(nombre, cant, fecha);
                            manager.agregarProducto(dep, fila, col, p);
                        }
                        System.out.println("Producto agregado.");
                        break;
                    case "3":
                        System.out.print("Depósito (A/B/C): ");
                        String rd = sc.nextLine().trim().toUpperCase();
                        System.out.print("Fila (empieza en 1): "); int rfInput = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Columna (empieza en 1): "); int rcInput = Integer.parseInt(sc.nextLine().trim());
                        int rf = rfInput - 1; int rc = rcInput - 1; // convertir a 0-based
                        System.out.print("Cantidad a retirar: "); int rcant = Integer.parseInt(sc.nextLine().trim());
                        manager.retirarProducto(rd, rf, rc, rcant);
                        System.out.println("Retiro realizado.");
                        break;
                    case "4":
                        System.out.print("Depósito (A/B/C): "); String sd = sc.nextLine().trim().toUpperCase();
                        int total = manager.stockPorDeposito(sd);
                        System.out.println("Stock total depósito " + sd + ": " + total);
                        break;
                    case "5":
                        String ruta = "alertas_stock.txt";
                        List<String> alertas = manager.generarAlertasTxt(ruta);
                        System.out.println("Alertas guardadas en " + ruta + " (" + alertas.size() + " entradas)");
                        break;
                    case "6": running = false; break;
                    default: System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        sc.close();
    }
}
