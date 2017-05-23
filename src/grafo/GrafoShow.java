/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

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
        m.le();
        m.imprimePontos();
        m.CriarMatrizDistancia();
        
        //Hortolandia;Sao Paulo;SP;-22.85;-47.25
        //Campinas;Sao Paulo;SP;-22.9;-47.0833333
        System.out.print("Distancia: H -> C "+m.distancia(-22.85, -22.9, -47.25, -47.0833333));
    }
    
}
