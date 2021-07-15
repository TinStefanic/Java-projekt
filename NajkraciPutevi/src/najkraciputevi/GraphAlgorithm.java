package najkraciputevi;

/**
 * Nadklasa za sve algoritme.
 */
public abstract class GraphAlgorithm {
    // Zadnje vrijeme izvršavanja.
    private long lastTime = -1;
    
    // Pomoćna polja za rekonstrukciju najmanjeg puta.
    protected int[] minDist, parent;
    
    // Parametri zadnjeg poziva algoritma.
    protected int lStart, lEnd;
    
    protected Graph g;
    
    public GraphAlgorithm(Graph g) {
        this.g = g;
    }
    
    /**
     * Vraća najmanju udaljenost od vrha start do vrha end.
     * Ako put ne postoji vraća null.
     * @param start početni vrh
     * @param end završni vrh
     * @return najmanja udaljenost između vrhova start i end
     */
    public Integer query(int start, int end) {
        long startTime = System.nanoTime();
        Integer ret = runAlgorithm(start, end);
        long endTime = System.nanoTime();
        lastTime = endTime - startTime;
        lStart = start;
        lEnd = end;
        return ret;
    }
    
    /**
     * Vraća trajanje zadnjeg izvršavanja u milisekundama,
     * tj. -1 ako algoritam još ni jednom nije bio izvršen.
     */
    public long getLastTime() {
        return lastTime;
    }
    
    /**
     * Metoda koju izvedene klase implementiraju sa svojim algoritmom.
     */
    protected abstract Integer runAlgorithm(int start, int end);
    
    /**
     * Vraća ime algoritma. 
     */
    public abstract String getName();
    
    /**
     * Vraća najkraći put u grafu dobiven iz zadnjeg poziva algoritma,
     * tj. null ako nema puta ili su start i end isti vrhovi.
     */
    public abstract ShortestPath getShortestPath();
    
    // Pomoćne funkcije za JNI.
    private void setMinDistAt(int i, int val) {
        minDist[i] = val;
    }
    private void setParentAt(int i, int val) {
        parent[i] = val;
    }
}
