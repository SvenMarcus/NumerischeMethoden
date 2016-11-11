package irmb.numerik;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import java.lang.Math;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.*;
import java.io.PrintStream.*;

/**
 * @author kraft
 */
public class stationär_Dirichlet_RB {
    static int num_nodes;      // # grid nodes
    static long num_iterations; // number of iterations

    static double dx,             // grid distance
            L = 0.1,          // System length
            Di_BC_Left = 0.0, // Value of Dirichlet BC left side
            Di_BC_Right = 1.0, // Value of Dirichlet BC right side
            eps = 1e-15,
            delta,
            T1[],
            T2[],
            Koordinate[],
            error,
            scale_factor = 100000.0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // in order to compute the solution for different grid sizes,
        // we use grids consisting of 2^n grid nodes where n=2 .. n_max

        int n_max = 9; //maximal

        // in order to memorize the different errors for different grids,  
        // we allocate a vector
        double Error[] = new double[n_max];
        double Dx[] = new double[n_max]; //Array der Knotenabstände für alle Gitter
        //Beispiel: 1. Eintrag: Abstand zw. Knoten für 4 Knoten, 2. Eintrag Abstand zw. Knoten für 8 Knoten

        num_nodes = 2;
        for (int n = 0; n < n_max; n++) {
            num_nodes *= 2;//num_nodes=num_nodes*2;
            Dx[n] = L / (num_nodes - 1);

            Error[n] = error = compute_solution(num_nodes);
//            error = compute_solution(num_nodes);
            System.out.println("error for " + num_nodes + " grid nodes = "
                    + error);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - start) / 1000);
        for (int n = 0; n < n_max - 2; n++) {
            System.out.println("Convergence order for level " + n + " to "
                    + (n + 1) + "= "
                    + (Math.log(Error[n + 1]) - Math.log(Error[n]))
                    / (Math.log(Dx[n + 1]) - Math.log(Dx[n])));
        }
    }

    public static void write_output(double f[]) {

        String filename = "data_" + Integer.toString((int) (num_iterations))
                + ".txt";

        PrintStream printStream = null;
        try {
            printStream = new PrintStream(filename);

            printStream.println("wrote file for Iteration #: " + num_iterations);
            for (int i = 0; i < num_nodes; i++) {
                printStream.format("%.20f;%.20f;%.20f%n",
                        Koordinate[i], f[i], f_analytic(Koordinate[i]));

            }

            printStream.close();
            System.out.println("wrote " + filename);


        } catch (IOException dummy) {
            // Print out the exception that occurred
            System.out.println("Unable to create " + filename
                    + ":" + dummy.getMessage());

            if (printStream != null) {
                printStream.close();
            }

        }
    }

    public static double f(double x) {
//        T''(x)=f(x)=sin(x*2*Math.PI/L)
//        return (Math.sin(x * 2 * Math.PI / L));
//        T''(x)=f(x)=scale_factor*x^2 ->
        return (scale_factor * x * x);
    }

    public static double compute_residuum() {
        double maximum = 0.0;
        for (int i = 1; i < num_nodes - 1; i++) {
            maximum = Math.max(Math.abs(T1[i] - T2[i]), maximum);
        }
        return (maximum);
    }

    public static double compute_true_residuum(double T[]) {
        double maximum = 0.0;
        for (int i = 1; i < num_nodes - 1; i++) {
            //System.out.println(Koordinate[i] + "   " + T[i] + "   " 
            //+ f_analytic(Koordinate[i]) + "   " 
            //+ Math.abs(T[i]-f_analytic(Koordinate[i])));
            maximum = Math.max(Math.abs(T[i] - f_analytic(Koordinate[i])), maximum);
        }
        return (maximum);
    }

    public static double f_analytic(double x) {
        //T''(x)=f(x)=sin(x*2*Math.PI/L)
        //return(?) Hausübung

        //T''(x)=f(x)=scale_factor*x^2 ->
        //4th order  polynomial a+b*x+c*x^2+d*x^3+e*x^4
        //T(0)=Di_BC_Left -> a=Di_BC_Left
        //T''(x)=f(x)=scale_factor*x^2 -> 2*c+6*d*x+12*e*x^2=scale_factor*x^2
        // -> c=d=0, e=scale_factor/12
        //T(L)=Di_BC_Right
        // ->T(L)=Di_BC_Left + b*L + e * L^4 = Di_BC_Right  
        // -> b=(Di_BC_Right-Di_BC_Left-e*L^4)/L 
        return (Di_BC_Left + (Di_BC_Right - Di_BC_Left - 1.0 / 12.
                * scale_factor * Math.pow(L, 4.0)) / L * x
                + 1.0 / 12.0 * scale_factor * Math.pow(x, 4.0));
//        return -Math.sin(2. * x * Math.PI / L) * Math.pow(L, 2) / (Math.PI * Math.PI * 4.)
//                + (Di_BC_Right - Di_BC_Left) * x / L
//                + Di_BC_Left;
    }

    public static double compute_solution(int num_nodes) {
        dx = L / (num_nodes - 1);


        // allocate memory for arrays
        T1 = new double[(int) num_nodes];
        T2 = new double[(int) num_nodes];

        Koordinate = new double[(int) num_nodes];

        // Determine coordinates of grid points
        Koordinate[0] = 0.0;
        for (int i = 1; i < num_nodes; i++) {
            Koordinate[i] = Koordinate[i - 1] + dx;
        }
        // Initiate temperature arrays:
        T1[0] = T2[0] = Di_BC_Left;
        T1[num_nodes - 1] = T2[num_nodes - 1] = Di_BC_Right;

        for (int i = 1; i < num_nodes - 1; i++) {
            T1[i] = T2[i] = 0.0;
        }
        num_iterations = 0;
        delta = 1000000.0;

        //while not converged, do iterations:
        while (delta >= eps) {
            for (int i = 1; i < num_nodes - 1; i++) {
                T2[i] = 0.5 * (T1[i + 1] + T1[i - 1] - dx * dx * f(Koordinate[i]));
            }
            for (int i = 1; i < num_nodes - 1; i++) {
                T1[i] = 0.5 * (T2[i + 1] + T2[i - 1] - dx * dx * f(Koordinate[i]));
            }
            num_iterations += 2;

            delta = compute_residuum();
        }

        //System.out.println("finished computation after " + num_iterations 
        //                                                 + " iterations");
        write_output(T1);
        //System.out.println("maximum error for dx = " + dx + " = " + error);

        return (compute_true_residuum(T1));
    }


}
