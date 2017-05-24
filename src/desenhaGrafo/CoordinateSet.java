package desenhaGrafo;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Class used to parse an input file into a object.
 */
public class CoordinateSet {

    /**
     * Set of coordinates
     */
    private ArrayList<Coordinate> coordinates;
    
    
    
    /**
     * Input File
     */
    private File file;
    

    /**
     * Constructor
     *
     * @param f - instance input file.
     */
    public CoordinateSet(File f) {
        this.file = f;
        coordinates = new ArrayList<>();
        this.read();
    }

    /**
     * Load and parse the inpute file.
     */
    private void read() {

        BufferedReader br = Util.getBr(file);
        String line = Util.nextLine(br);
        while (line != null) {
            if (line.contains(";")) {
                String list[] = line.split(";");
                double lat = Double.parseDouble(list[3]);
                double lon = Double.parseDouble(list[4]);
                Coordinate aux1 = new Coordinate(lon, -lat);
                coordinates.add(aux1);
            }
            line = Util.nextLine(br);
        }
        
        System.out.printf("x [%.2f,%.2f]\n",Util.getMinX(coordinates),Util.getMaxX(coordinates));
        System.out.printf("y [%.2f,%.2f]\n",Util.getMinY(coordinates),Util.getMaxY(coordinates));
        Util.normalize(coordinates,2000);
        System.out.printf("x [%.2f,%.2f]\n",Util.getMinX(coordinates),Util.getMaxX(coordinates));
        System.out.printf("y [%.2f,%.2f]\n",Util.getMinY(coordinates),Util.getMaxY(coordinates));

    }

    /**
     * obtain the instance file
     *
     * @return the file
     */
    public File getFile() {
        return file;
    }

  
  public ArrayList<Coordinate> getCoordinates(){
      return this.coordinates;
  }
  
  
          
   public static void main(String[] args) {
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
