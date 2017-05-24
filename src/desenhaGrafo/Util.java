/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenhaGrafo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author meira Some util methods.
 */
public class Util {

    

    /**
     * Create a buffered reader to the file.
     *
     * @param f the file.
     * @return the buffered reader created.
     */
    public static BufferedReader getBr(File f) {
        BufferedReader out = null;
        FileInputStream fi;
        try {
            fi = new FileInputStream(f);
            InputStreamReader fstream = new InputStreamReader(fi, "UTF-8");
            out = new BufferedReader(fstream);
        } catch (IOException ex) {

            System.out.println("Error to open the file for reading: " + f.getAbsolutePath());
            System.out.println("Check the file and try again");
            System.exit(1);
        }
        return out;
    }

    /**
     * Read the next line and handling the exception.
     *
     * @param br - buffered reader.
     * @return the read line.
     */
    public static String nextLine(BufferedReader br) {
        String line = null;
        try {
            line = br.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(-1);
        }
        return line;
    }

    /**
     * Read the next non empty line and handling the exception.
     *
     * @param br - buffered reader.
     * @return the read line.
     */
    public static String nextNonEmptyLine(BufferedReader br) {
        String line;
        do {
            line = nextLine(br);
        } while (line != null && line.length() < 2);

        return line;
    }

    /**
     * Creates a buffered writer and handling the exception.
     *
     * @param fname - file getName
     * @return BufferedReader created
     */
    public static BufferedWriter getBw(File fname) {

        BufferedWriter out = null;
        FileOutputStream f = null;
        try {
            f = new FileOutputStream(fname);
            OutputStreamWriter fstream = new OutputStreamWriter(f, "UTF-8");
            out = new BufferedWriter(fstream);
        } catch (IOException ex) {

            System.out.println("Error to open the file for writing: " + fname.getAbsolutePath());
            System.out.println("Check the file and try again");
            ex.printStackTrace();
            System.exit(1);
        }
        return out;
    }

    /**
     * Handling the exception an i/o exception.
     *
     * @param ex - exception
     * @param f - file
     */
    public static void ioException(IOException ex, File f) {
        ex.printStackTrace();
        System.out.println("Input/Output Problem " + f.getAbsolutePath());
        System.exit(-1);
    }

    /**
     * Create a time stamp. Useful in many contexts.
     *
     * @return the created timestamp.
     */
    public static String timeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HHmm").format(Calendar.getInstance().getTime());
    }

    /**
     * Print an array.
     *
     * @param aux the array.
     * @return The correspondent string.
     */
    public static String arrayToString(ArrayList aux) {
        String ret = "";
        for (int i = 0; i < aux.size(); i++) {
            ret += aux.get(i) + "-";
        }
        ret += "\n";
        return ret;
    }

    /**
     * Convert a rational number to a integer.
     *
     * @param line - original rational number.
     * @return the associated integer.
     */
    /*public static int scale(String line) {
        Double value = Double.parseDouble(line);
        value = value * scale;
        return value.intValue();
    }*/

    public static double getMinX(ArrayList<Coordinate> coordinates) {
        double min = Integer.MAX_VALUE;
        for (Coordinate aux : coordinates) {
            if (aux.getX() < min) {
                min = aux.getX();
            }
        }
        return min;
    }

    public static double getMinY(ArrayList<Coordinate> coordinates) {
        double min = Integer.MAX_VALUE;
        for (Coordinate aux : coordinates) {
            if (aux.getY() < min) {
                min = aux.getY();
            }
        }
        return min;
    }

    public static double getMaxX(ArrayList<Coordinate> coordinates) {
        double max = Integer.MIN_VALUE;
        for (Coordinate aux : coordinates) {
            if (aux.getX() > max) {
                max = aux.getX();
            }
        }
        return max;
    }

    public static double getMaxY(ArrayList<Coordinate> coordinates) {
        double max = Integer.MIN_VALUE;
        for (Coordinate aux : coordinates) {
            if (aux.getY() > max) {
                max = aux.getY();
            }
        }
        return max;
    }

    public static void normalize(ArrayList<Coordinate> coordinates, int max) {
        double minX = getMinX(coordinates);
        double minY = getMinY(coordinates);
        double deltaX = getMaxX(coordinates) - getMinX(coordinates);
        double deltaY = getMaxY(coordinates)- getMinY(coordinates);
        
        for (Coordinate aux : coordinates) {
            double x = aux.getX();
            double y = aux.getY();
            x = x-minX;
            y = y-minY;
            x = x/deltaX;
            y = y/deltaY;
            x*=max;
            y*=max;
            aux.setX(x);
            aux.setY(y);            
        }
        
        
    }

}
