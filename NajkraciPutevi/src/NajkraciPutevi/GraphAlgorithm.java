package NajkraciPutevi;

/**
 * Nadklasa za sve algoritme.
 */
public abstract class GraphAlgorithm {
    // Zadnje vrijeme izvršavanja.
    private long lastTime = -1;
    
    /**
     * Vraća najmanju udaljenost od vrha start do vrha end.
     * @param start početni vrh
     * @param end završni vrh
     * @return najmanja udaljenost između vrhova start i end
     */
    public Integer query(int start, int end) {
        long startTime = System.currentTimeMillis();
        Integer ret = runAlgorithm(start, end);
        long endTime = System.currentTimeMillis();
        lastTime = endTime - startTime;
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
}
