package grafo;

import com.sun.xml.internal.ws.commons.xmlutil.Converter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class LerDoArquivo_Alciomar {

    public static List<Vertice> lerGrafo(int[][] cidade,List<Cidade> listaCidade) {

        Grafo g = new Grafo();
        Vertice v = null;
        
        String vertices[];
        String linha;
        ArrayList<String[]> s1 = new ArrayList<String[]>();

        //BufferedReader br = new BufferedReader(new FileReader(f));
        Map<String, Vertice> mapa = new HashMap<String, Vertice>();

        for (int i = 0; i < cidade.length; i++) {

            v = (Vertice) mapa.get(listaCidade.get(i).nome);
           // v = (Vertice) mapa.get(i);
            if (v == null) {
                v = new Vertice();
            }

            List<Vertice> vizinhosAtual = new ArrayList<Vertice>();
            List<Aresta> arestasAtual = new ArrayList<Aresta>();
            v.setDescricao(listaCidade.get(i).nome);
            //v.setDescricao(Integer.toString(i));
            mapa.put(listaCidade.get(i).nome, v);
            //mapa.put(Integer.toString(i), v);

            for (int j = 0; j < cidade.length; j++) {

                if (i != j && cidade[i][j] != 0) {

                    Vertice vit;
                    
                    vit = mapa.get(listaCidade.get(j).nome);
                   // vit = mapa.get(j);
                    if (vit == null) {
                        vit = new Vertice();
                    }
                    vit.setDescricao(listaCidade.get(j).nome);
                   // vit.setDescricao(Integer.toString(j));
                    vizinhosAtual.add(vit);
                    mapa.put(listaCidade.get(j).nome, vit);
                    //mapa.put(Integer.toString(j), vit);

                    Aresta ait = new Aresta(v, vit);
                    ait.setPeso((int) cidade[i][j]);
                    arestasAtual.add(ait);

                    v.setVizinhos(vizinhosAtual);
                    v.setArestas(arestasAtual);
                    
                    v.setLatitude(listaCidade.get(i).lati);
                    v.setLongitude(listaCidade.get(i).longi);
                    
                    
                    
                    vizinhosAtual.clear();
                    arestasAtual.clear();
                }

            }

            g.adicionarVertice(v);
        }

        return g.getVertices();
    }
}
