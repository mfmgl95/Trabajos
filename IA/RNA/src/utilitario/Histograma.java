package utilitario;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author GLizarragaMF
 *///src/imagenes/hola3.png
public class Histograma {
    
    public static double[] obtenerRGB(String rutaIamgen){
        double[] colorRGBpromedio = {0,0,0};
        int heigth, width, srcPixel;
        try {
            // TODO code application logic here
            InputStream input = new FileInputStream(rutaIamgen);
            
            ImageInputStream imagenInput = ImageIO.createImageInputStream(input);
            BufferedImage imagen = ImageIO.read(imagenInput);// puedo manipular la imagen
            
            heigth = imagen.getHeight();
            width = imagen.getWidth();
            
            System.out.println("ANCHO:  "+width+"    -    ALTURA:     "+heigth);
            for(int y = 0; y < heigth; y++){
                for(int x = 0; x < width; x++){
                    srcPixel = imagen.getRGB(x, y);
                    Color c= new Color(srcPixel);
//                    int valR = c.getRed();
//                    int valG = c.getGreen();
//                    int valB = c.getBlue();
//                    System.out.println("("+valR+","+valG+","+valB+")");
                    colorRGBpromedio[0] += c.getRed();
                    colorRGBpromedio[1] += c.getGreen();
                    colorRGBpromedio[2] += c.getBlue();
                }
            }
            colorRGBpromedio[0] = colorRGBpromedio[0]/(heigth*width);
            colorRGBpromedio[1] = colorRGBpromedio[1]/(heigth*width);
            colorRGBpromedio[2] = colorRGBpromedio[2]/(heigth*width);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Histograma.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {        
            Logger.getLogger(Histograma.class.getName()).log(Level.SEVERE, null, ex);
        }
        return colorRGBpromedio;
    }
    
    
}
