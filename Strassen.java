/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package strassen;

/**
 *
 * @author ayoung
 */
public class Strassen {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        double[][]a = genMatrix(2);
        double[][]b = genMatrix(2);
        printMatrix(a);
        printMatrix(b);
        printMatrix(add(a, b, -1));
        printMatrix(multiply(a, b));
    }
    
    public static double[][] multiply(double[][] a, double[][] b) {
        checkInput(a, b);
        int n = a.length;
        double[][] ans = new double[n][n];
        // loop through rows of matrix A
        for (int i = 0; i < n; i++) {
            // loop through columns of matrix A
            for (int j = 0; j < n; j++) {
                double sum = 0.0;
                // loop through rows of matrix B
                for (int k = 0; k < n; k++) {
                    sum += a[i][k] * b[k][j];
                }
                ans[i][j] = sum;
            }
        }
        return ans;
    }
    
    public static double[][] add(double[][] a, double[][] b, int sign) {
        checkInput(a, b);
        int n = a.length;
        // loop through rows
        for (int i = 0; i < n; i++) {
            // loop through columns
            for (int j = 0; j < n; j++) {
                a[i][j] += sign * b[i][j];
            }
        }
        return a;
    }
    
    public static void multiplyStrassen (double[][] a, double[][] b,
            double[] ans, int n, double offset) {
        
    }
    
    public static void checkInput(double[][] a, double[][] b) {
        int rows_a = a.length;
        int cols_a = a[0].length;
        int rows_b = b.length;
        int cols_b = b[0].length;
        if (rows_a == 0 || cols_a == 0)
            throw new IllegalArgumentException("Null matrix A");
        if (rows_b == 0 || cols_b == 0)
            throw new IllegalArgumentException("Null matrix B");
        if (rows_a != cols_a)
            throw new IllegalArgumentException("Non-square matrix B");
        if (rows_b != cols_b)
            throw new IllegalArgumentException("Non-square matrix B");
        if (cols_a != rows_b)
            throw new IllegalArgumentException("Unequal matrix dimensions");
    }
    
    // generates a random nxn matrix
    public static double[][] genMatrix(int n) {
        double[][] ans = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][j] = Math.round(Math.random() * 10);
            }
        }
        return ans;
    }
    
    public static void printMatrix(double[][] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < a.length; j++) {
                System.out.print(a[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println();
    }
}
