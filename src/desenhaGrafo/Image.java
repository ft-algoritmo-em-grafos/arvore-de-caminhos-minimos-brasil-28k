package desenhaGrafo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author meira. Class used to write a route in a given background image.
 */
public class Image {

    /**
     * The image
     */
    BufferedImage img;

    /**
     * Get the image width
     *
     * @return width
     */
    public int getWidth() {
        return img.getWidth();
    }

    /**
     * Get the image height
     *
     * @return height
     */
    public int getHeight() {
        return img.getHeight();
    }

    /**
     * Create an image object
     * @param width image width 
     * @param height image height
     */     
    public Image(int width, int height) {

        this.img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    }

    /**
     * Write the current image in the output file.
     *
     * @param output - the output file.
     */
    public void WriteFile(File output) {
        try {
            ImageIO.write((BufferedImage) this.img, "png", output);
        } catch (IOException ex) {
            Logger.getLogger(Image.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }

    /**
     * Write a String in the image.
     *
     * @param text
     */
    public void writeString(String text) {

        int posX = getWidth() / 5;
        int posY = 10 * getHeight() / 12;
        int linesPread = getHeight() / 45;
        Graphics g = this.img.getGraphics();
        g.setColor(Color.black);
        //Font currentFont = g.getFont();
        //Font newFont = currentFont.deriveFont();
        Font newFont = new Font("Courier", Font.PLAIN, (int) (this.getHeight() * 0.02F));
        g.setFont(newFont);

        String list[] = text.split("\n");
        int lineCounter = 0;
        int columCounter = 0;

        Color bgColor = new Color(1f, 1f, 1f, .9f);

        for (String line : list) {
            int x = posX / 30 + posX * columCounter;
            int y = posY + lineCounter * linesPread;
            FontMetrics fm = g.getFontMetrics();
            Rectangle2D rect = fm.getStringBounds(line, g);
            g.setColor(bgColor);
            g.fillRect(x,
                    y - fm.getAscent(),
                    (int) rect.getWidth(),
                    (int) rect.getHeight());

            g.setColor(Color.black);
            g.drawString(line, x, y);
            if (lineCounter++ == 7) {
                lineCounter = 0;
                columCounter++;
            }
        }

    }

    /**
     * Draw a line segment between two coordinates.
     *
     * @param seg
     */
    public void drawSegment(Segment seg,float thickness) {
        Coordinate a = seg.getU();
        Coordinate b = seg.getV();
        Graphics2D graf = (Graphics2D) this.img.getGraphics();
        //thickness
        graf.setStroke(new BasicStroke(getHeight()*thickness/ 500.0F));
        graf.setColor(Color.blue);
        graf.drawLine((int)a.getX(), (int)a.getY(), (int)b.getX(),(int)b.getY());
    }
    
    public void drawCoordinate(ArrayList<Coordinate> set){        
        for(Coordinate aux: set){            
            this.DrawCircularPoint((int)aux.getX(), (int)aux.getY(),1 , Color.black);
        }
    }
    
    
    public void drawSegments(ArrayList<Segment> set, float thickness){
        
        for(Segment aux: set){            
            this.drawSegment(aux, thickness);
        }
    }
    
    public void DrawCircularPoint(int x, int y, int radius, Color color) {
        int diameter = 2 * radius;
        Graphics2D g = (Graphics2D) this.img.getGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.fillOval(x - radius, y - radius, diameter, diameter);
        g.setColor(color.darker());
        g.drawOval(x - radius, y - radius, diameter, diameter);

    }
}
