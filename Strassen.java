import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 *
 * @author Albert Young, Wilson Qin
 */
public class Strassen {
    
    int[][] p1;
    int[][] p2;
    int[][] p3;
    int[][] p4;
    int[][] p5;
    int[][] p6;
    int[][] p7;
    int[][] t1;
    int[][] t2;
    
    /**
     * @param args the command line arguments
     * $ ./strassen 0 dimension inputfile
     */
    public static void main(String[] args) {
//        int d = Integer.parseInt(args[2]);
//        
//        // dump ASCII file into matrices A and B
//        int[][]a = new int[d][d];
//        int[][]b = new int[d][d];
//        
//        try {
//            InputStream fis = new FileInputStream(args[3]);
//            BufferedReader br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
//            for (int i = 0; i < d; i++) {
//                for (int j = 0; j < d; j++) {
//                    a[i][j] = Integer.parseInt(br.readLine());
//                }
//            }
//            for (int i = 0; i < d; i++) {
//                for (int j = 0; j < d; j++) {
//                    b[i][j] = Integer.parseInt(br.readLine());
//                }
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        checkInput(a, b);
        
        int d = 4;
        int[][] a = genMatrix(d);
        int[][] b = genMatrix(d);
        printMatrix(a);
        printMatrix(b);
        
        Strassen s = new Strassen();
        int[][]ans = new int[d][d];
        ans = s.multiplyStrassen(a, b, ans);
        s.dumpMatrices();
        printMatrix(ans);
    }
    
    public int[][] multiplyStrassen(int[][] a, int[][] b, int[][] ans) {
        int n = ans.length;
        
        p1 = new int[n/2][n-1];
        p2 = new int[n/2][n-1];
        p3 = new int[n/2][n-1];
        p4 = new int[n/2][n-1];
        p5 = new int[n/2][n-1];
        p6 = new int[n/2][n-1];
        p7 = new int[n/2][n-1];
        t1 = new int[n/2][n-1];
        t2 = new int[n/2][n-1];
        
        return rec_mult(a, b, ans, n, 0, 0);
    }
    
    public int[][] rec_mult (int[][] a, int[][] b,
            int[][] c, int n, int j0, int offs) {
        int dim = n / 2;
        if (n == 1) {
            c[0][j0] = a[0][j0] * b[0][j0];
        }
        else {
            // P1 = A11(B12 - B22)
            // T1 = A11; T2 = B12 - B22
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    t1[i][j + offs] = a[i][j + j0];
                    t2[i][j + offs] = b[i][j + j0 + dim] - b[i + dim][j + j0 + dim];
                }
            }
            
            // P1 = T1 * T2
            rec_mult(t1, t2, p1, dim, offs, offs + dim);
            
            // P2 = (A11 + A12)B22
            // T1 = A11 + A12; T2 = B22
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    t1[i][j + offs] = a[i][j + j0] + a[i][j + j0 + dim];
                    t2[i][j + offs] = b[i + dim][j + j0 + dim];
                }
            }
            
            // P2 = T1 * T2
            rec_mult(t1, t2, p2, dim, offs, offs + dim);
            
            // P3 = (A21 + A22)B11
            // T1 = A21 + A22; T2 = B11
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    t1[i][j + offs] = a[i + dim][j + j0] + a[i + dim][j + j0 + dim];
                    t2[i][j + offs] = b[i][j + j0];
                }
            }
            
            // P3 = T1 * T2
            rec_mult(t1, t2, p3, dim, offs, offs + dim);
            
            // P4 = A22(B21 - B11)
            // T1 = A22; T2 = B21 - B11
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    t1[i][j + offs] = a[i + dim][j + j0 + dim];
                    t2[i][j + offs] = b[i + dim][j + j0] - b[i][j + j0];
                }
            }
            
            // P4 = T1 * T2
            rec_mult(t1, t2, p4, dim, offs, offs + dim);
            
            // P5 = (A11 + A22)(B11 + B22)
            // T1 = A11 + A22; T2 = B11 + B22
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    t1[i][j + offs] = a[i][j + j0] + a[i + dim][j + j0 + dim];
                    t2[i][j + offs] = b[i][j + j0] + b[i + dim][j + j0 + dim];
                }
            }
            
            // P5 = T1 * T2
            rec_mult(t1, t2, p5, dim, offs, offs + dim);
            
            // P6 = (A12 - A22)(B21 + B22)
            // T1 = A12 - A22; T2 = B21 + B22
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    t1[i][j + offs] = a[i][j + j0 + dim] - a[i + dim][j + j0 + dim];
                    t2[i][j + offs] = b[i + dim][j + j0] + b[i + dim][j + j0 + dim];
                }
            }
            
            // P6 = T1 * T2
            rec_mult(t1, t2, p6, dim, offs, offs + dim);
            
            // P7 = (A11 - A21)(B11 + B12)
            // T1 = A11 + A22; T2 = B11 + B12
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    t1[i][j + offs] = a[i][j + j0] - a[i + dim][j + j0];
                    t2[i][j + offs] = b[i][j + j0] + b[i][j + j0 + dim];
                }
            }
            
            // P7 = T1 * T2
            rec_mult(t1, t2, p7, dim, offs, offs + dim);
        }
        
        // combine
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                // C11 = P5 + P4 - P2 + P6
                c[i][j + j0] = p5[i][j + offs] + p4[i][j + offs] - p2[i][j + offs] + p6[i][j + offs];
                
                // C12 = P1 + P2
                c[i][j + j0 + dim] = p1[i][j + offs] + p2[i][j + offs];
                
                // C21 = P3 + P4
                c[i + dim][j + j0] = p3[i][j + offs] + p4[i][j + offs];
                
                // C22 = P5 + P1 - P3 - P7
                c[i + dim][j + j0 + dim] = p5[i][j + offs] + p1[i][j + offs] - p3[i][j + offs] - p7[i][j + offs];
            }
        }
        return c;
    }
    
        public int[][] multiplyStandard(int[][] a, int[][] b) {
        checkInput(a, b);
        int n = a.length;
        int[][] c = new int[n][n];
        // loop through rows of matrix A
        for (int i = 0; i < n; i++) {
            // loop through rows of matrix B
            for (int k = 0; k < n; k++) {
                int sum = 0;
                // loop through columns of matrix A
                for (int j = 0; j < n; j++) {
                    c[i][j] = c[i][j] + a[i][k]*b[k][j];
                }
            }
        }
        return c;
    }
    
    public int[][] add(int[][] a, int[][] b, int sign) {
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
    
    public static void checkInput(int[][] a, int[][] b) {
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
    public static int[][] genMatrix(int n) {
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ans[i][j] = (int)(Math.round(Math.random() * 9));
            }
        }
        return ans;
    }
    
    public static void printMatrix(int[][] a) {
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
    
    public void dumpMatrices() {
        System.out.println("P1: ");
        printMatrix(p1);
        System.out.println("P2: ");
        printMatrix(p2);
        System.out.println("P3: ");
        printMatrix(p3);
        System.out.println("P4: ");
        printMatrix(p4);
        System.out.println("P5: ");
        printMatrix(p5);
        System.out.println("P6: ");
        printMatrix(p6);
        System.out.println("P7: ");
        printMatrix(p7);
        System.out.println("T1: ");
        printMatrix(t1);
        System.out.println("T2: ");
        printMatrix(t2);
    }
    
    public static boolean isPowerOf2(int n) {
        return (n & -n) == n; 
    }
}
