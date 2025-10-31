package scrCompetencia1;

import java.util.Scanner;

public class Sumar {
    private int sunma[][];

    public Sumar() {
        this.sunma = new int[3][3];
    }

    public static void main(String[] args) {
        Sumar programa = new Sumar();
        programa.leerMatriz();
        programa.imprimirMatriz();
        programa.imprimirSumaFilas();
        programa.imprimirSumaColumnas();
    }

    private void leerMatriz() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese 9 números para la matriz 3x3(espacios) ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                while (!sc.hasNextInt()) {
                    sc.next(); 
                }
                sunma[i][j] = sc.nextInt();
            }
        }
    }

    private void imprimirMatriz() {
        System.out.println("Matriz ingresada:");
        for (int i = 0; i < 3; i++) {
            StringBuilder line = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                if (j > 0) line.append(" ");
                line.append(sunma[i][j]);
            }
            System.out.println(line.toString());
        }
    }

    private void imprimirSumaFilas() {
        System.out.println("Suma por filas:");
        for (int i = 0; i < 3; i++) {
            int suma = 0;
            StringBuilder expr = new StringBuilder();
            for (int j = 0; j < 3; j++) {
                if (j > 0) expr.append("+");
                expr.append(sunma[i][j]);
                suma += sunma[i][j];
            }
            System.out.printf("Fila %d [%s]= %d%n", i + 1, expr.toString(), suma);
        }
    }

    private void imprimirSumaColumnas() {
        System.out.println("Suma por columnas:");
        for (int j = 0; j < 3; j++) {
            int suma = 0;
            StringBuilder expr = new StringBuilder();
            for (int i = 0; i < 3; i++) {
                if (i > 0) expr.append("+");
                expr.append(sunma[i][j]);
                suma += sunma[i][j];
            }
            System.out.printf("Columna %d [%s]= %d%n", j + 1, expr.toString(), suma);
        }
    }
}