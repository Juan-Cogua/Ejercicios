package src;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RutaMensajero {

    public static void main(String[] args) {
        int n = 4; // tamaño N x N; puedes cambiarlo
        double probTransitable = 0.6; // probabilidad de que una celda sea transitable (1)
        int[][] ciudad = generarCiudadAleatoria(n, probTransitable);
        System.out.println("Ciudad generada (" + n + "x" + n + "):");
        printCiudad(ciudad);
        runSimulacion(ciudad);
    }

    // Genera una matriz aleatoria N x N con probabilidad de celdas transitables
    public static int[][] generarCiudadAleatoria(int n, double probTransitable) {
        int[][] ciudad = new int[n][n];
        java.util.Random rnd = new java.util.Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ciudad[i][j] = (rnd.nextDouble() < probTransitable) ? 1 : 0;
            }
        }
        // Asegurar que inicio y destino sean transitables
        ciudad[0][0] = 1;
        ciudad[n - 1][n - 1] = 1;
        return ciudad;
    }

    // Imprime la matriz en consola de forma legible
    public static void printCiudad(int[][] ciudad) {
        for (int i = 0; i < ciudad.length; i++) {
            for (int j = 0; j < ciudad[i].length; j++) {
                System.out.print(ciudad[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void runSimulacion(int[][] ciudad) {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Validar matriz (asumimos cuadrada cuando se genera aleatoriamente)
        if (ciudad == null || ciudad.length == 0) {
            System.out.println("Matriz vacía.");
            return;
        }
        int n = ciudad.length;
        // Solo validar que los valores sean 0/1 (si la matriz viene de fuera, podría necesitar validaciones adicionales)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < ciudad[i].length; j++) {
                if (ciudad[i][j] != 0 && ciudad[i][j] != 1) {
                    System.out.println("La matriz solo puede contener 0 o 1.");
                    return;
                }
            }
        }

        // Buscar ruta
        boolean[][] visitado = new boolean[n][n];
        List<Coord> ruta = new ArrayList<>();
        boolean hayRuta = hayCamino(ciudad, 0, 0, visitado, ruta);

        // Construir registro y leer/guardar en formato legible usando BufferedReader(FileReader) y BufferedWriter(FileWriter)
        Path archivo = Path.of("src", "ruta_mensajero.txt");
        List<List<String>> registros = new ArrayList<>();

        try {
            // Leer bloques previos (separados por línea en blanco) con BufferedReader y FileReader
            try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(archivo.toFile()))) {
                String line;
                List<String> bloque = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        if (!bloque.isEmpty()) {
                            registros.add(new ArrayList<>(bloque));
                            bloque.clear();
                        }
                        continue;
                    }
                    bloque.add(line);
                }
                if (!bloque.isEmpty()) registros.add(bloque);

                System.out.println("Archivo '" + archivo.toString() + "' tiene " + registros.size() + " registros antes de añadir uno nuevo.");
            }

            // Calcular datos a guardar
            String timestamp = ahora.format(fmt);
            String rutaStr = hayRuta ? formatRuta(ruta) : "";
            long minutos = hayRuta ? Math.max(0, ruta.size() - 1) * 5L : 0L;
            String entregaStr = hayRuta ? ahora.plusMinutes(minutos).format(fmt) : "";

            // Mostrar en terminal con el mismo formato que se guarda
            System.out.println("Ejecución: " + timestamp);
            if (hayRuta) {
                System.out.println("RUTA: " + rutaStr);
                System.out.println("Tiempo estimado (min): " + minutos);
                System.out.println("Entrega estimada: " + entregaStr);
            } else {
                System.out.println("SIN RUTA");
            }
            System.out.println();

            // Añadir registro legible con BufferedWriter en modo APPEND usando FileWriter (append = true)
            try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(archivo.toFile(), true))) {
                bw.write("Ejecución: " + timestamp);
                bw.newLine();
                if (hayRuta) {
                    bw.write("RUTA: " + rutaStr);
                    bw.newLine();
                    bw.write("Tiempo estimado (min): " + minutos);
                    bw.newLine();
                    bw.write("Entrega estimada: " + entregaStr);
                    bw.newLine();
                } else {
                    bw.write("SIN RUTA");
                    bw.newLine();
                }
                bw.newLine(); // separador entre registros
                bw.flush();
            }

            System.out.println("Registro añadido a: " + archivo.toAbsolutePath());

        } catch (IOException e) {
            System.out.println("Error I/O: " + e.getMessage());
        }
    }

    private static String formatRuta(List<Coord> ruta) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ruta.size(); i++) {
            Coord c = ruta.get(i);
            sb.append("(").append(c.x).append(",").append(c.y).append(")");
            if (i < ruta.size() - 1) sb.append(" -> ");
        }
        return sb.toString();
    }


    public static boolean hayCamino(int[][] ciudad, int x, int y, boolean[][] visitado, List<Coord> ruta) {
        int n = ciudad.length;
        // Validar límites y obstáculos
        if (x < 0 || y < 0 || x >= n || y >= n) return false;
        if (ciudad[x][y] == 0 || visitado[x][y]) return false;

        // Marcar posición y añadir a la ruta
        visitado[x][y] = true;
        ruta.add(new Coord(x, y));

        // Caso alcanzado
        if (x == n - 1 && y == n - 1) {
            return true;
        }

        // Probar 4 direcciones: abajo, arriba, derecha, izquierda
        if (hayCamino(ciudad, x + 1, y, visitado, ruta) ||
            hayCamino(ciudad, x - 1, y, visitado, ruta) ||
            hayCamino(ciudad, x, y + 1, visitado, ruta) ||
            hayCamino(ciudad, x, y - 1, visitado, ruta)) {
            return true;
        }

        // Backtrack
        ruta.remove(ruta.size() - 1);
        return false;
    }

    private static class Coord {
        int x, y;
        Coord(int x, int y) { this.x = x; this.y = y; }
    }
}
