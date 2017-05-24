/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

import desenhaGrafo.CoordinateSet;
import desenhaGrafo.Image;
import desenhaGrafo.SegmentSet;
import desenhaGrafo.Util;
import java.io.File;

/**
 *
 * @author alciomar
 */
public class GrafoShow {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        DesenhaPontos m = new DesenhaPontos();
        
        //é necessário excluir arquivo alciomar_result
        
        //m.le();
       // m.imprimePontos();
       // m.CriarMatrizDistancia();
        
        
        File f = new File("coordenadasCorrigidas.txt");
        CoordinateSet cs = new CoordinateSet(f);
        
        
        Image img = new Image((int)Util.getMaxX(cs.getCoordinates())+1, (int)Util.getMaxY(cs.getCoordinates())+1);
        img.drawCoordinate(cs.getCoordinates());
        File outFile = new File("BrasilDots.png");
        img.WriteFile(outFile);        
        f = new File("Result_Alciomar.txt");
        SegmentSet segs = new SegmentSet(f);
        segs.drawAnimated(img,1f);
        segs.drawSegments(img,.1f);
        
        
    }
    
}
