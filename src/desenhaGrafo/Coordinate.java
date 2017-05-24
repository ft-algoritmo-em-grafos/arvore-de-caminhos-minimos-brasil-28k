/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenhaGrafo;

/**
 * @author meira Class used to keep a Cartesian coordinate in 2D.;
 */
public class Coordinate {

    /**
     * Point in R^2.
     */
    private  double x, y;
    
    /**
     * Constructor
     * @param x  value
     * @param y  value
     */
     public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;        
        //System.out.println(this);

    }
    public String toString(){
        return String.format("(%d,%d)", x,y);        
    }
    
    public void setX(double value){
        this.x = value;
    }
    public void setY(double value){
        this.y = value;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    
    
}
