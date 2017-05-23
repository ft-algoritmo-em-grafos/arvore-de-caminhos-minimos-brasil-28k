/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.processing.Filer;

/**
 *
 * @author augustomeira
 */
public class DesenhaPontos {

    int[][][] figura;
    static final File dir
            = new File("");

    public void setPreto(int i, int j) {
        figura[i][j][0] = 0;
        figura[i][j][1] = 0;
        figura[i][j][2] = 0;
    }

    public void setBranco(int i, int j) {
        figura[i][j][0] = 255;
        figura[i][j][1] = 255;
        figura[i][j][2] = 255;
    }

//    public void linha(int x1, int y1, int x2, int x2){
//        sqrt((x1-x2)
//        
//    }
    public void imprimePontos() {
        try {
            int xmax = Integer.MIN_VALUE;
            int ymax = Integer.MIN_VALUE;
            int xmin = Integer.MAX_VALUE;
            int ymin = Integer.MAX_VALUE;

            //primeira vez encontra os limites xmax, xmin, ymax, ymin
            //segunda vez imprime o arquivo ppm
            for (int vez = 0; vez < 2; vez++) {
                int dx = 0;
                int dy = 0;
                BufferedWriter out = null;
                if (vez == 1) {
                    dx = xmax - xmin + 1;
                    dy = ymax - ymin + 1;
                    //cada posicao da matrix eh uma coordenada x,y com tres
                    //dimensoes [x][y][0] [x][y][1] [x][y][2] para R-G-B
                    figura = new int[dx][dy][3];
                    out = new BufferedWriter(new FileWriter(new File("aux.ppm")));
                    //cabecalho da figura ppm
                    //http://en.wikipedia.org/wiki/Netpbm_format#PPM_example
                    out.write("P3\n");
                    out.write(dy + " " + dx + "\n");
                    out.write("255\n");

                    //inicializa matriz quadriculada
                    for (int i = 0; i < dx; i++) {
                        for (int j = 0; j < dy; j++) {
                            if (i % 100 == 9 || j % 100 == 9) {
                                setPreto(i, j);
                            } else {
                                setBranco(i, j);
                            }

                        }
                    }

                }

                //coordenadas.
                BufferedReader b
                        = new BufferedReader(
                                new FileReader(
                                        new File("coordenadasCorrigidas.txt")));

                //leitura
                String linha = b.readLine();
                while (linha != null && linha.length() > 0) {
                    String[] lista = linha.split(";");

                    double a1 = Double.parseDouble(lista[3]);
                    double a2 = Double.parseDouble(lista[4]);

                    //converte em inteiro com um fator de escala 30.
                    int x = (int) (a1 * 30);
                    int y = (int) (a2 * 30);

                    if (vez == 0) {
                        //procura min  e max
                        if (x < xmin) {
                            xmin = x;
                        }
                        if (x > xmax) {
                            xmax = x;
                        }
                        if (y < ymin) {
                            ymin = y;
                        }
                        if (y > ymax) {
                            ymax = y;
                        }
                    } else {
                        //imprime latitude e longitude
                        setPreto(x - xmin, y - ymin);
                    }
                    assert (ymin <= ymax);

                    linha = b.readLine();
                }
                if (vez == 1) {
                    //imprime o arquivo.
                    for (int i = dx - 1; i >= 0; i--) {
                        for (int j = 0; j < dy; j++) {
                            out.write(figura[i][j][0] + " "
                                    + figura[i][j][1] + " "
                                    + figura[i][j][2] + " ");
                        }
                        out.write("\n");
                    }

                    out.write("\n");
                    out.close();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(DesenhaPontos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //leitura de pontos. Exemplo.
    public void le() {
        File dir
                = new File("");
        BufferedReader b = null;

        List<Cidade> cities = new ArrayList<Cidade>();

        try {
            b = new BufferedReader(
                    new FileReader(
                            new File("coordenadasCorrigidas.txt")));

            String linha = b.readLine();
            while (linha != null && linha.length() > 0) {
                String[] lista = linha.split(";");
                for (int i = 0; i < lista.length; i++) {
                    System.out.println(lista[i] + " ");
                }
                double lati = Double.parseDouble(lista[3]);
                double longi = Double.parseDouble(lista[4]);

                linha = b.readLine();

                Cidade cd = new Cidade();
                cd.setCidade(lista);

                cities.add(cd);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double distancia(double lat1, double lat2, double lon1, double lon2) {
        double R = 6371.0; //Raio da terra Km
        double dLat = (lat2 - lat1) * Math.PI / 180.0;
        double dlon = (lon2 - lon1) * Math.PI / 180;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180)
                * Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c;

        //return Math.pow(d,2);
        return d;
    }

    void CriarMatrizDistancia() {

        BufferedReader b = null;

        List<Cidade> listaCidade = new ArrayList<Cidade>();
        List<Cidade> listaCidadeFile = new ArrayList<Cidade>();
        List<String> listaUFVisitados = new ArrayList<String>();

        try {
            b = new BufferedReader(
                    new FileReader(
                            new File("coordenadasCorrigidas.txt")));

            String linha = b.readLine();
            Vertice ultimaCidade = new Vertice();
            Cidade origenCidade = new Cidade();
            boolean origem = true;

            String nomeCidadeOrigem = "Campinas";
            String ufCidade = "SP";

            int peso = 0;

            while (linha != null && linha.length() > 0) {
                String[] lista = linha.split(";");
                Cidade cd = new Cidade();
                cd.setCidade(lista);
                listaCidadeFile.add(cd);
                if (cd.nome.equals(nomeCidadeOrigem) && cd.UF.equals(ufCidade)) {
                    origenCidade = cd;
                    listaCidade.add(origenCidade);
                }
                linha = b.readLine();
            }

            for (int i = 0; i < listaCidadeFile.size(); i++) {
                if (origenCidade.UF.equals(listaCidadeFile.get(i).UF)) {
                    Cidade cd = new Cidade();
                    cd = listaCidadeFile.get(i);
                    if (!cd.equals(origenCidade)) {
                        listaCidade.add(cd);
                    }
                }

                if (i + 1 == listaCidadeFile.size()) {

                    ultimaCidade = gerarCaminho(listaCidade, peso);
                    listaCidade.clear();
                    listaUFVisitados.add(origenCidade.UF);

                    int menor = Integer.MAX_VALUE;
                    Cidade cdMenor = new Cidade();

                    for (int j = 0; j < listaCidadeFile.size(); j++) {

                        boolean cdOutroEstado = true;
                        int countUF = 0;
                        for (int k = 0; k < listaUFVisitados.size(); k++) {
                            cdOutroEstado = listaUFVisitados.get(k).equals(listaCidadeFile.get(j).UF);
                            if (!cdOutroEstado) {
                                countUF++;
                            }
                        }

                        if (!cdOutroEstado && countUF == listaUFVisitados.size()) {
                            int distancia = (int) distancia(Double.parseDouble(ultimaCidade.getLatitude()), listaCidadeFile.get(j).lati, Double.parseDouble(ultimaCidade.getLongitude()), listaCidadeFile.get(j).longi);
                            if (menor > distancia) {
                                menor = distancia;
                                cdMenor = listaCidadeFile.get(j);
                            }
                        }
                    }

                    System.err.println(cdMenor);
                    listaCidade.add(cdMenor);
                    origenCidade = cdMenor;

                    peso = ultimaCidade.getDistancia();

                    if (Integer.MAX_VALUE != menor) {
                        i = 0;
                    }

                }

            }
            String aux = "";

//            while (linha != null && linha.length() > 0) {
//
//                String[] lista = linha.split(";");
//                Cidade cd = new Cidade();
//                cd.setCidade(lista);
//
//                if (origem) {
//                    origenCidade = cd;
//                    origem = false;
//                    listaCidade.add(origenCidade);
//                    linha = b.readLine();
//                    continue;
//
//                }
//
//                boolean valida = UltimoEstado.equals(lista[2]);
//                if (valida || UltimoEstado.equals("")) {
//                    listaCidade.add(cd);
//
//                } else {
//                    gerarCaminho(listaCidade);
//                    listaCidade.clear();
//
//                    listaCidade.add(origenCidade);
//                    listaCidade.add(cd);
//                }
//                UltimoEstado = cd.UF;
//
//                linha = b.readLine();
//            }
            //   gerarCaminho(listaCidade);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Vertice gerarCaminho(List<Cidade> listaCidade, int peso) {

        int[][] matrizDistancia = null;
        Vertice ultimaCidade = null;

        int ContaCidade = listaCidade.size();

        matrizDistancia = new int[ContaCidade][ContaCidade];
        //int cidadePorCidade = 0;
        //int distanciaBuscaVertice = 21;

        for (int i = 0; i < ContaCidade; i++) {
            for (int j = 0; j < ContaCidade; j++) {

                int distancia = (int) distancia(listaCidade.get(i).lati, listaCidade.get(j).lati, listaCidade.get(i).longi, listaCidade.get(j).longi);


              // if (distancia <= distanciaBuscaVertice) {

                    matrizDistancia[i][j] = (int)Math.pow(distancia, 2);  

                    if (i != j) {
                        matrizDistancia[i][j] = matrizDistancia[i][j] + peso;
                    }

             //       cidadePorCidade++;
             //   }
            }

            
            //adiciona radio de busca para a cidade
//            if (cidadePorCidade < 7) {
//                i = i - 1;
//                distanciaBuscaVertice += distanciaBuscaVertice;
//                //distanciaBuscaVertice += 70;
//            } else {
//                distanciaBuscaVertice = 21;
//            }

      //      cidadePorCidade = 0;
        }

        Grafo teste = new Grafo();
        List<Vertice> List = LerDoArquivo_Alciomar.lerGrafo(matrizDistancia, listaCidade);

        teste.setVertices(List);

        Vertice i1 = new Vertice();
        Vertice i2 = new Vertice();
        i1 = List.get(0);
        //i1 = teste.encontrarVertice("Campinas");
        //i2 = teste.encontrarVertice("Porto Velho");

        List<Vertice> resultado = new ArrayList<Vertice>();
        Dijkstra algoritmo = new Dijkstra();
        resultado = algoritmo.encontrarMenorCaminhoDijkstra(teste, i1, i2);

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter("Result_Alciomar.txt", true));

            for (int i = 0; i < resultado.size(); i++) {
                //Cidade cd = listaCidade.
                //0.0166667000;-68.5000000000;0.1000000000;-68.4666667000
                if ((i + 1) < resultado.size()) {

                    bw.write(resultado.get(i).getLatitude() + ";" + resultado.get(i).getLongitude() + ";");
                    bw.write(resultado.get(i + 1).getLatitude() + ";" + resultado.get(i + 1).getLongitude());
                    bw.newLine();

                } else {
                    ultimaCidade = resultado.get(i);
                }

                System.out.println(resultado.get(i).getDescricao());
            }
            bw.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally { // always close the file
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ioe2) {
                    // just ignore it
                }
            }
        }

//        for (int i = 0; i < listaCidade.size(); i++) {
//            if (listaCidade.get(i).nome.equals(ultimaCidade)) {
//                return listaCidade.get(i);
//            }
//        }
        return ultimaCidade;

    }

}
