package najkraciputevi;

/**
 * Dijkstrin algoritam za traženje najkraćeg puta u grafu.
 */
public class DijkstraAlgorithm extends GraphAlgorithm {
    public DijkstraAlgorithm(Graph g) {
        super();
        this.g = g;
    }
    
    @Override
    protected Integer runAlgorithm(int start, int end) {
        int ret = runAlgorithmNative(start, end);
        if (ret == -1) return null;
        else return (Integer)ret;
    }
    
    static {
        System.loadLibrary("libNajkraciPutevi");
    }
    
    private native int runAlgorithmNative(int start, int end);
}
