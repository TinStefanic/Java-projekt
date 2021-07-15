package najkraciputevi;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;


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
    private ArrayList[] gNeighbour;
    private ArrayList<Integer>[] gWeight;
    
    /**
     * Konstruktor, stvara graf od n vrhova bez bridova.
     * 
     * @param n broj vrhova u grafu.
     */
    public Graph(int n) {
        this.n = n;
        gMatrix = new Integer[n][n];
        gNeighbour = new ArrayList[n];
        gWeight = new ArrayList[n];
        for (int i = 0; i < n; ++i) {
            gNeighbour[i] = new ArrayList<Integer>();
            gWeight[i] = new ArrayList<Integer>();
        }
    }
    
    /**
     * Dodaje novi brid u graf, tj. mijenja težinu brida
     * ako brid već postoji u grafu.
     * 
     * @param start početni vrh
     * @param end završni vrh.
     * @param w težina brida.
     */
    public void addEdge(int start, int end, int w) {
        if (start == end) return; // Ne dozvoljavamo petlje.
        gMatrix[start][end] = w;
        int pos = gNeighbour[start].indexOf(end);
        if (pos == -1) { // Dodavanje novog brida.
            gNeighbour[start].add(end);
            gWeight[start].add(w);
        } else { // Promjena težine postojećeg brida.
            gWeight[start].set(pos, w);
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
    public Integer getWeightBetween(int start, int end) {
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
        return (Integer)gNeighbour[start].get(pos);
    }
    
    /**
     * Vraća težinu brida između vrha start i vrha na poziciji pos u njegovoj listi susjeda.
     * 
     * @param start početni vrh
     * @param pos pozicija u listi susjeda
     * @return težina brida između vrha start i vrha na poziciji pos
     */
    public Integer getWeightAt(int start, int pos) {
        return (Integer)gWeight[start].get(pos);
    }
    
    /**
     * Vraća brid između vrha start i vrha na poziciji pos u njegovoj listi susjeda.
     * @param start početni vrh
     * @param pos pozicija u listi susjeda
     * @return brid između vrha start i vrha na poziciji pos
     */
    public Edge getEdgeAt(int start, int pos) {
        return new Edge(start, getNeighbourAt(start, pos), getWeightAt(start, pos));
    }
    
    /**
     * Vraća broj susjeda u listi susjeda od vrha start.
     * 
     * @param start promatrani početni vrh
     * @return broj susjeda u listi susjeda od start
     */
    public int getNumNeighbours(int start) {
        return (Integer)gNeighbour[start].size();
    }
    
    /**
     * Provjerava ima li graf negativne bridove.
     */
    public boolean hasNegativeWeights() {
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (gMatrix[i][j] != null && gMatrix[i][j] < 0)
                    return true;
            }
        }
        return false;
    }
    /**
     * Stvara random generirani graf s danim parametrima, bez negativnih bridova.
     * 
     * @param n broj vrhova u grafu
     * @param directed je li graf usmjeren
     * @param density koliko su bridovi česti u grafu, 1.0 znači da postoji
     *                brid između svaka dva vrha
     * @param maxWeight najveća težina u grafu, uključivo
     * @return random generirani graf
     */
    public static Graph randomGraph(int n, boolean directed, double density, int maxWeight) {
        Random rand = new Random();
        Graph ret = new Graph(n);
        for (int i = 0; i < n; ++i) {
            for (int j = (directed) ? 0 : i+1; j < n; ++j) {
                if (i == j) continue; // Ne dozvoljavamo petlje.
                // Brid je već dodan.
                if (ret.getWeightBetween(j, i) != null) continue;
                if (rand.nextDouble() < density) {
                    int w = rand.nextInt(maxWeight)+1;
                    if (directed) {
                        if (rand.nextBoolean()) ret.addEdge(i, j, w);
                        else ret.addEdge(j, i, w);
                    } else {
                        ret.addEdge(i, j, w);
                        ret.addEdge(j, i, w);
                    }
                }
            }
        }
        return ret;
    }
    
    /**
     * Stvara random generirani graf s danim parametrima, bez negativnih bridova.
     * 
     * @param n broj vrhova u grafu
     * @param directed je li graf usmjeren
     * @param density koliko su bridovi česti u grafu, 1.0 znači da postoji
     *                brid između svaka dva vrha
     * @return random generirani graf
     */
    public static Graph randomGraph(int n, boolean directed, double density) {
        return randomGraph(n, directed, density, 10);
    }
    
    /**
     * Stvara random generirani graf s danim parametrima, bez negativnih bridova.
     * 
     * @param n broj vrhova u grafu
     * @param directed je li graf usmjeren
     * @return random generirani graf
     */
    public static Graph randomGraph(int n, boolean directed) {
        // Parametar je ovako izabran da bi odprilike svaki vrh imao 3 susjeda.
        return randomGraph(n, directed, 3. / n);
    }
    
    /**
     * Stvara random generirani neusmjereni graf, bez negativnih bridova.
     * 
     * @param n broj vrhova u grafu
     * @return random generirani graf
     */
    public static Graph randomGraph(int n) {
        return randomGraph(n, false);
    }
    
    /**
     * Stvara random generirani neusmjereni graf s n vrhova, bez negativnih bridova.
     * 
     * @return random generirani graf
     */
    public static Graph randomGraph() {
        return randomGraph(10);
    }
    
    /**
     * Vraća graf zapisan kao string.
     */
    @Override
    public String toString() {
        String ret = "";
        for (int i = 0; i < n; ++i) {
            ret += i + ".";
            for (int j = 0; j < n; ++j) {
                if (gMatrix[i][j] != null) {
                    if (!ret.endsWith(".")) ret += ",";
                    ret += " " + j;
                    ret += ":" + gMatrix[i][j];
                }
            }
            ret += "\n";
        }
        return ret;
    } 
}