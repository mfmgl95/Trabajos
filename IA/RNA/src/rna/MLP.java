/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rna;

import javax.swing.JOptionPane;

/**
 *
 * @author GLizarragaMF
 */
public class MLP {
    public static int analizar(double[] entrada){
//        int filas_entrada = 7;
//        int col_entrada = 5;
        int neuronas_entrada = 5;
        int neuronas_oculta = 2;
        int neuronas_salida = 2;
        int i, j, k;
        
        /*int[][] patron = {  {0, 1, 1, 1, 0},
                              {1, 0, 0, 0, 1},
                              {1, 0, 0, 0, 1},
                              {1, 0, 0, 0, 1},
                              {1, 0, 0, 0, 1},
                              {1, 0, 0, 0, 1},
                              {0, 1, 1, 1, 0}};*/
        
        double[][] pesos_entrada_oculta = {
            {-0.630920238635136,	0.41326159959942516,	0.08952617896762344,	-0.38153912019222935,	-0.3739725373098333,	0.4590088214354687},
            {-0.323500074526895,	0.38987835023111234,	-0.14173521568039743,	-0.7183556379599946,	-0.3633017337852544,	0.3146400003865943},
            {-0.7244206021535657,	0.47075060046516876,	0.26123715963888267,	-0.6505697538245693,	0.0022406019944384396,	0.40832701789930187},
            {5.8050676188379615,	-5.0468093077939065,	-0.9327284132749257,	5.570068744490127,	2.094371429388999,	-3.2118794043568393},
            {-0.7500622475160992,	0.6720080896177083,	0.02015448432985093,	-0.5189248707849097,	-0.21389063044204265,	0.335104689240263}
            };

        
        
        double[][] pesos_oculta_salida = {
            { 4.192949437167409,	-4.0847762011095785},
            {-3.816523581381259,	4.062538323997101},
            {-0.8382643499945509,	0.8441498388844202},
            {3.9582234302405763,	-3.91992067729717},
            {1.1848868342192833,	-1.3867031337982423},
            {-2.6518467762985005,	2.3802379190025023}     
        };
        
        
        double[] bias_entrada = {
            -0.1498436715806336,
            0.048754412258966734,
            -0.1143727571164736,
            -0.04777674256856704,
            -0.005504488545170161
            };
        double[] bias_oculta = {
            -0.45320532514721346,
            0.5678396622751078,
            -0.056518841887638546,
            -0.49840775694343487,
            -0.1767691387221519,
            0.11441983030486384
            };
        
        double[] bias_salida = {
            -0.8236824128068123,
            0.8593250954730346,
        };
        
//        int[] entrada = new int[neuronas_entrada];
        double[] sumatoria_entrada = new double[neuronas_entrada];
        double[] sigmoidal_entrada = new double[neuronas_entrada];
        
        double[] oculta = new double[neuronas_oculta];
        double[] sumatoria_oculta = new double[neuronas_oculta];
        double[] sigmoidal_oculta = new double[neuronas_oculta];
        
        double[] salida = new double[neuronas_salida];
        double[] sumatoria_salida = new double[neuronas_salida];
        double[] sigmoidal_salida = new double[neuronas_salida];


        //1. procesamiento de la capa de entrada
        //1.1 linealizar el patron de entrada
        /*for (i = 0; i < filas_entrada; i++) {
            for (j = 0; j < col_entrada; j++) {
                
                entrada[i * col_entrada + j] = patron[i][j];
            }
        }*/

        //1.2 sumar la entrada con el bias y calcular sigmidal
        for (i = 0; i < neuronas_entrada; i++) {
            sumatoria_entrada[i] = entrada[i] + bias_entrada[i];
            sigmoidal_entrada[i] = 1 / (1 + Math.exp(-1 * sumatoria_entrada[i]));
        }
        //2. procesamiento de la capa oculta
        // 2.1 sumatoria de productos de entrada por peso
        for (j = 0; j < neuronas_oculta; j++) {
            oculta[j] = 0;
            for (i = 0; i < neuronas_entrada; i++) {
                oculta[j] += sigmoidal_entrada[i] * pesos_entrada_oculta[i][j];
            }
        }
        //2.2 sumatoria mas bias y calculo de sigmoidal
        for (i = 0; i < neuronas_oculta; i++) {
            sumatoria_oculta[i] = oculta[i] + bias_oculta[i];
            sigmoidal_oculta[i] = 1 / (1 + Math.exp(-1 * sumatoria_oculta[i]));
        }

        //3. procesamiento de la capa salida
        //3.1 suamtoria de productos de salida oculta x peso
        for (j = 0; j < neuronas_salida; j++) {
            salida[j] = 0;
            for (i = 0; i < neuronas_oculta; i++) {
                salida[j] += sigmoidal_oculta[i] * pesos_oculta_salida[i][j];
            }
        }
        //3.2 sumatoria mas bias y calculo de sigmoidal
        for (i = 0; i < neuronas_salida; i++) {
            sumatoria_salida[i] = salida[i] + bias_salida[i];
            sigmoidal_salida[i] = 1 / (1 + Math.exp(-1 * sumatoria_salida[i]));
            System.out.println("Sigmoidal [" + i + "]- " + sigmoidal_salida[i]);
        }
        // 4.construyendo la interface de salida
        double mayor = -0.999;
        int neurona_activada = -1;
        for (i = 0; i < neuronas_salida; i++) {
            if (sigmoidal_salida[i] > mayor) {
                
                mayor = sigmoidal_salida[i];
                neurona_activada = i;
            }
        }
        if (mayor > 0.80 ) { // heuristica para saber si el patron es coherente
//            switch (neurona_activada) {
//                case 0:
//                    System.out.println("Manzana");
//                    JOptionPane.showMessageDialog(null, "Manzana");break;
//                case 1:
//                    System.out.println("Pina");
//                    JOptionPane.showMessageDialog(null, "Piña");break;                                          
//            }
            return neurona_activada;
        } else
        { //JOptionPane.showMessageDialog(null, "No reconozco el numero");
            System.out.println("No reconozco el numero");
            return -1;
        }    
    }
}
