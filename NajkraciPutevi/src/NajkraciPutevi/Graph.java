package NajkraciPutevi;

import java.util.ArrayList;


/**
 * Klasa koja reprezentira graf, graf je spremljen kao matrica
 * i kao niz lista, radi lakše implementacije algoritama.
 */ 
public class Graph {
    // Broj vrhova u grafu.
    private int n;
    // Matrična reprezentacija grafa.
    private Integer[][] gMatrix;
    
    // Liste susjeda i odgovarajuće težine odgovarajućeg vrha.
    private ArrayList<Integer>[] gNeighbour;
    private ArrayList<Integer>[] gWeight;
    
    // Je li graf dovršen, graf se smatra završenim nakon prvog
    // poziva metode koja postavlja upit o bridovima i više se 
    // ne može mijenjati nakon toga.
    private boolean complete = false;
    
    /**
     * Konstruktor, stvara graf od n vrhova bez bridova.
     * 
     * @param n broj vrhova u grafu.
     */
    public Graph(int n) {
        this.n = n;
        gMatrix = new Integer[n][n];
        for (int i = 0; i < n; ++i) {
            gNeighbour[i] = new ArrayList<Integer>();
            gWeight[i] = new ArrayList<Integer>();
        }
    }
    
    /**
     * Dodaje novi brid u graf, tj. mijenja težinu brida
     * ako brid već postoji u grafu. Baca iznimku ako je 
     * metoda pozvana nakon što se graf više ne smije mijenjati.
     * 
     * @param start početni vrh
     * @param end završni vrh.
     * @param w težina brida.
     */
    public void addEdge(int start, int end, int w) throws Exception {
        if (complete) throw new Exception("Graf se više ne može mijenjati.");
        gMatrix[start][end] = w;
        int pos = gNeighbour[start].indexOf(end);
        if (pos == -1) { // Dodavanje novog brida.
            gNeighbour[start].add(end);
            gWeight[start].add(w);
        } else { // Promjena težine postojećeg brida.
            gWeight[start].set(end, w);
        }
    }
    
    /**
     * Vraća boj vrhova u grafu
     */
    public int getN()
    {
        return this.n;
    }
    
    /**
     * Vraća težinu brida između dva brida, ili null
     * ako brid ne postoji.
     * 
     * @param start početni vrh
     * @param end završni vrh
     * @return težina brida između dva vrha, ili null ako brid ne postoji
     */
    public Integer weightBetween(int start, int end) {
        complete = true;
        if (start < 0 || start >= n || end < 0 || end >= n) return null;
        else return gMatrix[start][end];
    }
    
    /**
     * Vraća susjeda od vrha start na poziciji pos u njegovoj listi susjeda.
     * 
     * @param start početni vrh
     * @param pos pozicija u listi susjeda
     * @return indeks susjeda na poziciji pos
     */
    public Integer getNeighbourAt(int start, int pos) {
        complete = true;
        return gNeighbour[start].get(pos);
    }
    
    /**
     * Vraća težinu brida između vrha start i vrha na poziciji pos u njegovoj listi susjeda.
     * 
     * @param start početni vrh
     * @param pos pozicija u listi susjeda
     * @return težina brida između vrha start i vrha na poziciji pos
     */
    public Integer getWeightAt(int start, int pos) {
        complete = true;
        return gWeight[start].get(pos);
    }
    
    /**
     * Vraća broj susjeda u listi susjeda od vrha start.
     * 
     * @param start promatrani početni vrh
     * @return broj susjeda u listi susjeda od start
     */
    public int getNumNeighbours(int start) {
        complete = true;
        return gNeighbour[start].size();
    }
    
    /**
     * Vraća je li faza konstrukcije grafa završila.
     * 
     * @return boolean je li graf gotov s konstrukcijom 
     */
    public boolean isComplete() {
        return complete;
    }
}