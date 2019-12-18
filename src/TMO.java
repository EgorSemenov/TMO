import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class TMO {
    private static int[][] E;
    private static Vector<int[][]> Qi;
    private static Vector<int[][]> Fi;
    private static void print(int[][] matrix) {
        for (int i = 0; i < matrix[0].length; ++i) {
            for (int j = 0; j < matrix.length; ++j) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static int[][] Gaus(int[][] A, int[][] b) { //решение системы линейных уравнение методом Гаусса

        int[][] x = new int[1][A.length];
        int[][] B = new int[A.length][A[0].length];
        for (int i = 0; i < B.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                B[i][j] = A[i][j];
            }
        }
        for (int i = 0; i < B.length; i++) {

            double d1 = B[i][i];
            for (int j = i; j < B.length; j++) B[i][j] /= d1;
            b[i][0] /= d1;

            for (int j = i + 1; j < B.length; j++) {
                double d2 = B[j][i];
                for (int k = i; k < B.length; k++) {
                    B[j][k] -= (B[i][k] * d2);
                }
                b[j][0] -= (b[i][0] * d2);
            }
        }

        x[0][x[0].length - 1] = b[b.length - 1][0];
        for (int i = x[0].length - 2; i >= 0; i--) {
            for (int j = x[0].length - 1; j > i; j--) {
                x[0][i] -= x[0][j] * B[i][j];
            }
            x[0][i] += b[i][0];
        }

        return x;
    }

    public static int[][] mulMatr(int[][] A, int val) {
        int[][] res = new int[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                res[i][j] = A[i][j] * val;
            }
        }
        return res;
    }

    public static int[][] mulMatrix(int[][] A, int[][] B) {
        int[][] res = new int[A.length][B.length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                for (int k = 0; k < res[0].length; k++) {
                    res[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return res;
    }

    static public int[][] InverseMatr(int[][] A) { //нахождение обратной матрицы
        int[][] inverse = new int[A.length][A[0].length];

        for (int i = 0; i < A.length; i++) {
            int[][] b = new int[A.length][1];
            b[i][0] = 1;
            inverse[i] = Gaus(A, b)[0];
        }
        inverse = TransposeMatr(inverse);

        return inverse;
    }

    private static int[][] TransposeMatr(int[][] A) {
        int[][] res = new int[A.length][A[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = A[j][i];
            }
        }
        return res;
    }

    private static int[][] mulVect(int[] a, int[] b) {
        int[][] res = new int[a.length][b.length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = a[i] * b[j];
            }
        }
        return res;
    }

    private static int[] matrXColumn(int[][] d, int[] x1) {
        int[] r = new int[d.length];
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d.length; j++) {
                r[i] += d[i][j] * x1[j];
            }
        }
        return r;
    }

    private static int[][] readMatrix(Scanner sc, int n) {
        int[][] res = new int[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                res[i][j] = sc.nextInt();
            }
        }
        return res;
    }

    private static int[][] KronMult(int[][] A, int[][] B) { //кронекерово произведение матриц
        int[][] K = new int[B.length * A.length][B[0].length * A[0].length];

        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)

                for (int q = 0; q < B.length; q++)
                    for (int p = 0; p < B[0].length; p++)
                        K[i * B.length + q][j * B[0].length + p] = A[i][j] * B[q][p];

        return K;
    }

    private static int[][] copyMatrix(int[][] A) {
        int[][] res = new int[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                res[i][j] = A[i][j];
            }
        }
        return res;
    }

    private static int[][] KronSum(int[][] A, int[][] B) {
        int[][] Ia = new int[B.length][B[0].length];
        int[][] Ib = new int[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            Ia[i][i] = Ib[i][i] = 1;
        }
        return Sum(KronMult(A, Ib), KronMult(B, Ia));
    }
    //    private static int[][] countF(int index){
//        if(index == 0){
//            return E;
//        }
//        int[][] res = mulMatrix(countF(index-1),);
//    }
    private static int[][] Sum(int[][] A, int[][] B) { //сумма матриц
        int[][] K = new int[A[0].length][A.length];
        for (int i = 0; i < A[0].length; i++) {
            for (int j = 0; j < A.length; j++) {
                K[i][j] = B[i][j] + A[i][j];
            }
        }
        return K;
    }

    public static double norm(int[][] matrix) {
        double norm = 0;
        for (int[] row : matrix) {
            double summ = 0;
            for (int element : row)
                summ += Math.abs(element);
            if (summ > norm)
                norm = summ;
        }
        return norm;
    }

    public static void main(String[] args) throws Exception {
        Scanner sin = new Scanner(new FileReader("input.txt"));
        int W = sin.nextInt();
        Qi = new Vector<>();
        Fi = new Vector<>();
        int n = W + 1;
        int[][] D0 = readMatrix(sin, n);
        int[][] D1 = readMatrix(sin, n);
        int M = sin.nextInt();
        int[] beta = new int[n];
        int[][] betaM = new int[n][1];
        for (int i = 0; i < n; i++) {
            beta[i] = betaM[i][0] = sin.nextInt();

        }
        int[][] S = readMatrix(sin, n);
        double eps = sin.nextDouble();
        E = new int[n][n];
        int[][] Im = new int[M][M];
        for (int i = 0; i < n; ++i) {
            E[i][i] = 1;
            Im[i][i] = 1;
        }
        int[] e = new int[n];
        for (int i = 0; i < n; i++) {
            e[i] = -1;
        }
        int[] S0 = matrXColumn(S, e);
        int[][] S0M = new int[n][1];
        for (int i = 0; i < n; i++) {
            S0M[i][0] = S0[i];
        }
        int[][] Q0 = KronMult(E, mulVect(S0, beta));
        int[][] Q1 = KronSum(D0, S);
        int[][] Q2 = KronMult(D1, Im);
        int[][] Q01 = KronMult(D1, betaM);
        int[][] Q10 = KronMult(E, S0M);
        int[][] GPrev = new int[n * M][n * M];
        print(Q0);
        print(Q1);
        print(Q2);
        int[][] GNext = new int[n * M][n * M];
        double normG = 0;
        do {
            GNext = mulMatrix(InverseMatr(mulMatr(Q1, -1)), (Sum(Q0, mulMatrix(Q2, mulMatrix(GPrev, GPrev)))));
            normG = norm(Sum(GNext, mulMatr(GPrev, -1)));
            GPrev = copyMatrix(GNext);
        } while (normG < eps);
        int[][] G0 = mulMatrix(mulMatr(InverseMatr(Sum(Q1, mulMatrix(Q2, GPrev))), -1), Q10);
        double normF = norm(E);
        for(int i = 1;normF>=eps ;i++){
            if(i == 0){
                Qi.add(Sum(Q0,mulMatrix(Q01,G0)));
                Qi.add(Q01);
            }
            else{
                Qi.add(Sum(Q1,mulMatrix(Q2,GPrev)));
                Qi.add(Q2);
            }
            // int[][] tmp = countF(i);
            // normF=norm(tmp);
            // Fi.add(tmp);
        }
    }
}