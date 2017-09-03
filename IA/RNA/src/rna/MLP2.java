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
public class MLP2 {
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
          { -1.7654757301245172,	-0.9247802565991459,	-0.8837399836783383,	-0.7028124821345982,	-0.9753807911087297,	-0.7228130758625317},
                  {-1.9277143451315286,	-0.7655571854721908,	-0.8873004417704801,	-1.168182345480259,	-0.88490126711492,	-0.8329506128181601},
                  {-1.9571128759372893,	-0.961185025619985,	-0.8839720753802197,	-0.7569752735509145,	-0.9955035911680664,	-0.9438336396435432},
                  {15.353866623606919,	-0.9110318002159763,	-0.08788245671322897,	-0.46315935265774394,	-1.774719899534169	,-1.3817357033607447},
                  {-1.840192564695172,	-0.6028643240748162,	-0.6794639806919588,	-1.0618100349027015,	-0.8082726949585352,	-0.84987296457928}

            };

        
        
        double[][] pesos_oculta_salida = {
            {7.500158144866977,	-7.496777542603501},
                {-0.24791463895772387,	-0.14929412484666355},
                {0.12390540069512727,	0.12261495641455206},
                {0.22656684664869572,	0.252304105736841},
                {0.002826815387812716,	0.37266048822125425},
                {-0.10609015589176661,	0.1321433421703027}
   
        };
        
        
        double[] bias_entrada = {
            0.2042398322597595,
-0.10482559674871086,
0.059639397691176294,
-0.18266554358059395,
0.0738744512444765,

            };
        double[] bias_oculta = {
            -2.281535525256011,
-0.956887213291699,
-0.9555885464205406,
-0.5342252484222059,
-0.6954695725665484,
-0.9308443402179971

            };
        
        double[] bias_salida = {
            -0.6633571802255636,
0.6551933219023989
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
            System.out.println(">No reconozco el numero");
            return -1;
        }    
    }
}
