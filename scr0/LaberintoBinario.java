
public class LaberintoBinario {

    /*
     * Funcion recursiva para buscar un camino en el laberinto binario
     */
    public static boolean hayCamino(int[][] laberinto, int x, int y, boolean[][] visitado) {
        int rows = laberinto.length;
        int cols = laberinto[0].length;

        //1. Validaciones de limites y obstaculos
        if (x < 0 || y < 0 || x >= rows || y >= cols || laberinto[x][y] == 0 || visitado[x][y]) {
            return false;
        }

        //2. Caso exitoso: Completo el laberinto
        if (x == rows - 1 && y == cols - 1) {
            return true;
        }

        //3. Marcar posición como visitada
        visitado[x][y] = true;

        //4. Probar en las 4 direcciones que haya camino
        boolean camino =
                hayCamino(laberinto, x + 1, y, visitado) || // Abajo
                hayCamino(laberinto, x - 1, y, visitado) || // Arriba
                hayCamino(laberinto, x, y + 1, visitado) || // Derecha
                hayCamino(laberinto, x, y - 1, visitado);   // Izquierda
        return camino;
    }

    // Nuevo: genera un laberinto aleatorio con probabilidad de obstáculo
    public static int[][] generarLaberintoAleatorio(int rows, int cols, double probObstaculo) {
        int[][] lab = new int[rows][cols];
        java.util.Random rnd = new java.util.Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                lab[i][j] = (rnd.nextDouble() < probObstaculo) ? 0 : 1;
            }
        }
        // Asegurar que inicio y fin sean accesibles
        lab[0][0] = 1;
        lab[rows - 1][cols - 1] = 1;
        return lab;
    }

    // Nuevo: imprime el laberinto por consola
    private static void imprimirLaberinto(int[][] lab) {
        for (int i = 0; i < lab.length; i++) {
            for (int j = 0; j < lab[0].length; j++) {
                System.out.print(lab[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Generar laberinto aleatorio de 5x5 con 30% de obstáculos
        int[][] laberinto = generarLaberintoAleatorio(5, 5, 0.3);
        imprimirLaberinto(laberinto);
        boolean[][] visitado = new boolean[laberinto.length][laberinto[0].length];

        if (hayCamino(laberinto, 0, 0, visitado)) {
            System.out.println("Camino Encontrado desde (0,0) hasta (" + (laberinto.length - 1) + "," + (laberinto[0].length - 1) + ")");
        } else {
            System.out.println("No hay un camino posible en el laberinto.");
        }
    }
}
