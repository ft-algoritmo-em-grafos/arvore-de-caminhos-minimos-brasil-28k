/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desenhaGrafo;

/**
 *
 * @author meira
 */
public class Segment {
    private Coordinate u;
    private Coordinate v;
    
    public Segment(Coordinate u, Coordinate v){
        this.u=u;
        this.v=v;
    }

    public Coordinate getU() {
        return u;
    }

    public Coordinate getV() {
        return v;
    }
    
    
}
