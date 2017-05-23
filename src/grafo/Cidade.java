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
public class Cidade {
    String nome;
    String estado;
    String UF;
    double lati;
    double longi;

    void setCidade(String[] lista) {
        this.nome = lista[0];
        this.estado = lista[1];
        this.UF = lista[2];
        this.lati = Double.parseDouble(lista[3]);
        this.longi = Double.parseDouble(lista[4]);
    }
        
}
