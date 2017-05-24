/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenhaGrafo;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author meira
 */
public class SegmentSet {

    private ArrayList<Segment> segmentSet;
    private ArrayList<Coordinate> coordinates;

    public SegmentSet(File file) {
        segmentSet = new ArrayList<>();
        coordinates = new ArrayList<>();
        BufferedReader br = Util.getBr(file);
        String line = Util.nextLine(br);
        while (line != null) {
            if (line.contains(";")) {
                String list[] = line.split(";");
                double lat = Double.parseDouble(list[0]);
                double lon = Double.parseDouble(list[1]);
                Coordinate aux1 = new Coordinate(lon, -lat);
                coordinates.add(aux1);
                lat = Double.parseDouble(list[2]);
                lon = Double.parseDouble(list[3]);
                Coordinate aux2 = new Coordinate(lon, -lat);
                coordinates.add(aux2);
                Segment aux3 = new Segment(aux1, aux2);
                segmentSet.add(aux3);
            }
            //System.out.println(line);
            line = Util.nextLine(br);
        }

        System.out.printf("x [%.2f,%.2f]\n", Util.getMinX(coordinates), Util.getMaxX(coordinates));
        System.out.printf("y [%.2f,%.2f]\n", Util.getMinY(coordinates), Util.getMaxY(coordinates));
        Util.normalize(coordinates,2000);
        System.out.printf("x [%.2f,%.2f]\n", Util.getMinX(coordinates), Util.getMaxX(coordinates));
        System.out.printf("y [%.2f,%.2f]\n", Util.getMinY(coordinates), Util.getMaxY(coordinates));
    }

    public ArrayList<Segment> getSegments() {
        return this.segmentSet;
    }

    public void drawAnimated(Image img,float thickness) {

        int top = 0;
        int max = 100;

        for (Segment se : this.segmentSet) {
            img.drawSegment(se,thickness);

            if (++top % max == 0) {

                File outFile = new File("BrasilMST" + top + ".png");
                img.WriteFile(outFile);
                max = (int) (1.1 * max);

            }

        }
        File outFile = new File("BrasilMST" + top + ".png");
        img.WriteFile(outFile);

    }
    
    public void drawSegments(Image img, float thickness) {

        
        

        for (Segment se : this.segmentSet) {
            img.drawSegment(se,thickness);
        }
        File outFile = new File("BrasilMST.png");
        img.WriteFile(outFile);

    }
    
    
}
