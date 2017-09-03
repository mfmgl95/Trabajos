package rna;


import javax.swing.JOptionPane;
import rna.MLP;
import utilitario.Histograma;

/**
 *
 * @author GLizarragaMF
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Histograma h = new Histograma();
//        
//        float rgbPromedio[];
//        rgbPromedio = h.obtenerRGB("src/imagenes/m4.png");
//        
//        for (int i = 0; i < rgbPromedio.length; i++) {
//            System.out.println(" - "+rgbPromedio[i]);           
//        }

        double[] entrada = {245.6624,215.0965,206.8189,-1,200};

        int sol = MLP2.analizar(entrada);
        switch(sol){
            case (-1):{
                JOptionPane.showMessageDialog(null, "No reconozco");
                break;
            }
            case 0:{
                JOptionPane.showMessageDialog(null, "MAnzana");
                break;
            }
            case 1:{
                JOptionPane.showMessageDialog(null, "Piña");
                break;
            }
        }


    }
    
}
