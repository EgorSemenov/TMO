import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class TMO {
    private static double[][] E;
    private static Vector<double[][]> Qi;
    private static Vector<double[][]> Fi;

    private static void print(double[][] matrix) {
        for (double[] row : matrix) {
            for (double elem : row) {
                System.out.print(elem + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static double[][] Gaus(double[][] A, double[][] b) { //решение системы линейных уравнение методом Гаусса
        double[][] x = new double[1][A.length];
        double[][] B = new double[A.length][A[0].length];
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

    private static double[][] mulMatr(double[][] A, int val) {
        double[][] res = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                res[i][j] = A[i][j] * val;
            }
        }
        return res;
    }

    private static double[][] mulMatrix(double[][] A, double[][] B) {
        double[][] res = new double[A.length][B[0].length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                for (int k = 0; k < B.length; k++) {
                    res[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return res;
    }

    private static double[][] InverseMatr(double[][] A) { //нахождение обратной матрицы
        double[][] inverse = new double[A.length][A[0].length];

        for (int i = 0; i < A.length; i++) {
            double[][] b = new double[A.length][1];
            b[i][0] = 1;
            inverse[i] = Gaus(A, b)[0];
        }
        inverse = TransposeMatr(inverse);

        return inverse;
    }

    private static double[][] TransposeMatr(double[][] A) {
        double[][] res = new double[A[0].length][A.length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = A[j][i];
            }
        }
        return res;
    }

    private static double[][] mulVect(double[] a, double[] b) {
        double[][] res = new double[a.length][b.length];
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                res[i][j] = a[i] * b[j];
            }
        }
        return res;
    }

    private static double[] matrXColumn(double[][] d, double[] x1) {
        double[] r = new double[d.length];
        for (int i = 0; i < d.length; i++) {
            for (int j = 0; j < d.length; j++) {
                r[i] += d[i][j] * x1[j];
            }
        }
        return r;
    }

    private static double[][] readMatrix(Scanner sc, int n) {
        double[][] res = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                res[i][j] = sc.nextDouble();
            }
        }
        return res;
    }

    private static double[][] KronMult(double[][] A, double[][] B) { //кронекерово произведение матриц
        double[][] K = new double[B.length * A.length][B[0].length * A[0].length];

        for (int i = 0; i < A.length; i++)
            for (int j = 0; j < A[0].length; j++)

                for (int q = 0; q < B.length; q++)
                    for (int p = 0; p < B[0].length; p++)
                        K[i * B.length + q][j * B[0].length + p] = A[i][j] * B[q][p];

        return K;
    }

    private static double[][] copyMatrix(double[][] A) {
        double[][] res = new double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                res[i][j] = A[i][j];
            }
        }
        return res;
    }

    private static double[][] KronSum(double[][] A, double[][] B) {
        double[][] Ia = new double[B.length][B[0].length];
        double[][] Ib = new double[A.length][A[0].length];
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
    private static double[][] Sum(double[][] A, double[][] B) { //сумма матриц
        double[][] K = new double[A[0].length][A.length];
        for (int i = 0; i < A[0].length; i++) {
            for (int j = 0; j < A.length; j++) {
                K[i][j] = B[i][j] + A[i][j];
            }
        }
        return K;
    }

    private static double norm(double[][] matrix) {
        double norm = 0;
        for (double[] row : matrix) {
            double summ = 0;
            for (double element : row)
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
        double[][] D0 = readMatrix(sin, n);
        double[][] D1 = readMatrix(sin, n);
        int M = sin.nextInt();
        double[] beta = new double[n];
        double[][] betaM = new double[n][1];
        for (int i = 0; i < n; i++) {
            beta[i] = betaM[i][0] = sin.nextDouble();

        }
        double[][] S = readMatrix(sin, n);
        double eps = sin.nextDouble();
        E = new double[n][n];
        double[][] Im = new double[M][M];
        for (int i = 0; i < n; ++i) {
            E[i][i] = 1;
            Im[i][i] = 1;
        }
        double[] e = new double[n];
        for (int i = 0; i < n; i++) {
            e[i] = -1;
        }
        double[] S0 = matrXColumn(S, e);
        double[][] S0M = new double[n][1];
        for (int i = 0; i < n; i++) {
            S0M[i][0] = S0[i];
        }
        double[][] Q0 = KronMult(E, mulVect(S0, beta));
        double[][] Q1 = KronSum(D0, S);
        double[][] Q2 = KronMult(D1, Im);
        double[][] Q01 = KronMult(D1, betaM);
        double[][] Q10 = KronMult(E, S0M);
        double[][] GPrev = new double[n * M][n * M];
        print(Q01);
        double[][] GNext;
        double normG;
        do {
            GNext = mulMatrix(InverseMatr(mulMatr(Q1, -1)), (Sum(Q0, mulMatrix(Q2, mulMatrix(GPrev, GPrev)))));
            normG = norm(Sum(GNext, mulMatr(GPrev, -1)));
            GPrev = copyMatrix(GNext);
        } while (normG < eps);
        double[][] G0 = mulMatrix(mulMatr(InverseMatr(Sum(Q1, mulMatrix(Q2, GPrev))), -1), Q10);
        double normF = norm(E);
//        for (int i = 1; normF >= eps; i++) {
//            if (i == 0) {
//                Qi.add(Sum(Q0, mulMatrix(Q01, G0)));
//                Qi.add(Q01);
//            } else {
//                Qi.add(Sum(Q1, mulMatrix(Q2, GPrev)));
//                Qi.add(Q2);
//            }
        // int[][] tmp = countF(i);
        // normF=norm(tmp);
        // Fi.add(tmp);
        //       }
    }
}